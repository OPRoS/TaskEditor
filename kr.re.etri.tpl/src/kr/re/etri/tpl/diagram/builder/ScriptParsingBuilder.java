package kr.re.etri.tpl.diagram.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.grammar.ScriptParser;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.IRTMDefines;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

/**
 * 프로제트내에 Script 파일이 변경되면 문법 검사를 수행한다.
 * Script 파일이 변경되어 저장되면 수동 또는 자동으로 실행된다. 
 * @author sfline
 *
 */
public class ScriptParsingBuilder extends IncrementalProjectBuilder implements IExecutableExtension {

	/**
	 * Builder ID
	 */
	public static final String builderId = "kr.re.etri.tpl.builder.ScriptParsingBuilder";
	
	/**
	 * 생성자
	 */
	public ScriptParsingBuilder() {
	}

	/**
	 * build 메소드가 호출되기 전에 초기화를 위해 호출된다.
	 * @param config 설정 정보
	 * @param propertyName
	 * @param data
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		
	}

	/**
	 * Project의 build를 수행하면 수행되는 메소드이다.
	 * ScriptParsingBuilder는 TaskScriptNature와 연결되어 있다.
	 * ScriptParsingBuilder는 Task Script가 변경(저장)되면 동작하고, Nature가
	 * 프로젝트에 적용될 때도 동작한다.
	 */
//	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {

		if((kind == IncrementalProjectBuilder.AUTO_BUILD) ||
			(kind == IncrementalProjectBuilder.INCREMENTAL_BUILD)) {
			IResourceDelta delta = getDelta(getProject());
			if(delta == null) {
				return null;
			}
	
//			IResourceDelta[] children = delta.getAffectedChildren();
			IResourceDelta[] children = delta.getAffectedChildren(IResourceDelta.ADDED|IResourceDelta.CHANGED, 0);
			monitor.beginTask("task script validate", children.length);
	
			for(int i = 0; i < children.length; i += 1) {
				IResourceDelta child = children[i];
				scriptValidate(child.getResource(), args, monitor);
	
				if(checkCancel(monitor)) {
					return null;
				}
				monitor.worked(i);
			}
			
			monitor.done();
		}
		else if(kind != IncrementalProjectBuilder.FULL_BUILD) {
			IProject project = (IProject)getProject();
		}

		
		return null;
	}

	/**
	 * Project clean 메뉴를 선택하였을 때 실행된다.
	 * Builder 수행 시 생성된 자원 및 Marker 등을 삭제할 수 있다.
	 */
//	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		IProject project = getProject();
		project.deleteMarkers(MarkerLogger.TYPE_SCRIPT, false, IResource.DEPTH_INFINITE);
		project.deleteMarkers(MarkerLogger.TYPE_DIAGRAM, false, IResource.DEPTH_INFINITE);
	}

	/**
	 * 프로젝트에 포함된 Script 파일 들의 문법 검사를 수행한다.
	 * @param resource 프로젝트 자원(Folder, File 등)
	 * @param args 실행 Argument
	 * @param monitor 프로그래스 모니터
	 */
	private void scriptValidate(IResource resource, Map args, IProgressMonitor monitor) {
		if(resource instanceof IFolder) {
			IFolder folder = (IFolder)resource;
			IResource []members;
			try {
				members = folder.members();
			} catch (CoreException e) {
				return;
			}
			for(int i = 0; i < members.length; i += 1) {
				scriptValidate(members[i], args, monitor);
			}
			
			return;
		}

		// 파일이 Task Script파일 인 경우만 문법 검사를 수행한다.
		if(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equals(resource.getFileExtension()) == false) {
			return;
		}

		// 문법 검사를 수행한 스크립트 파일에 대한 모든 Problem Marker를 삭제한다.
		ScriptParser parser = new ScriptParser((IFile)resource);
		MarkerLogger markLogger = new MarkerLogger((IFile)resource, MarkerLogger.TYPE_SCRIPT);
		markLogger.clearProblem();

		// Problem Marker를 등록할 리스너를 등록한다.
		parser.addErrorListener(markLogger);

		ModelDiagram incModel = parser.parse();
	}

	/**
	 * 사용자가 "Cancle"을 요청하였는지 확인한다.
	 * @param monitor 프로그래스 모니터
	 * @return
	 */
	private boolean checkCancel(IProgressMonitor monitor) {
		if(monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		
		if(isInterrupted()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Project에 Builer를 연결한다.
	 * @param project
	 */
	public static void addBuilderToProject(IProject project) {
		if(project.isOpen() == false) {
			return;
		}

		IProjectDescription description;
		try {
			description = project.getDescription();
		}
		catch(CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			return;
		}

		// 프로젝트의 BuildSpec 정보를 가져온다.
		ICommand []cmds = description.getBuildSpec();
		for(int idx = 0; idx < cmds.length; idx += 1) {
			if(cmds[idx].getBuilderName().equals(builderId)) {
				return;
			}
		}

		// BuildSpec정보에 ScriptBuilder에대한 정보를 추가한다.
		ICommand newCmd = description.newCommand();
		newCmd.setBuilderName(builderId);
		List<ICommand> newCmds = new ArrayList<ICommand>();
		newCmds.addAll(Arrays.asList(cmds));
		newCmds.add(newCmd);
		
		// 변경된 BuildSpec을 설정한다.
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));
		
		try {
			// Project에 변경된 Description정보를 설정한다.
			// Builder 정보는 프로젝트의 .project 파일내 BuildSpec정보에 추가된다.
			project.setDescription(description, null);
		}
		catch(CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
		}
	}

	/**
	 * Project에 ScriptParsingBuilder를 해제한다.
	 * @param project
	 */
	public static void removeBuilderFromProject(IProject project){
		if(project.isOpen() == false) {
			return;
		}
		
		
		IProjectDescription description;
		try {
			description = project.getDescription();
		}
		catch(CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			return;
		}

		// BuildSpec 정보에서 ScriptBuilder의 위치를 찾는다.
		int index = -1;
		ICommand []cmds = description.getBuildSpec();
		for(int idx = 0; idx < cmds.length; idx += 1) {
			if(cmds[idx].getBuilderName().equals(builderId)) {
				index = idx;
				break;
			}
		}

		// 위치를 찾을 수 없다면 Builder가 연결되지 않은 것이므로
		// 종료한다.
		if(index == -1) {
			return;
		}

		// ScriptBuilder를 BuildSpec에서 삭제한다.
		List<ICommand> newCmds = new ArrayList<ICommand>();
		newCmds.addAll(Arrays.asList(cmds));
		newCmds.remove(index);

		// 변경된 BuildSpec 정보를 Description에 설정한다.
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));

		try {
			// 변경된 Description을 프로젝트에 설정한다.
			// Builder정보는 프로젝트의 .project파일의 BuildSpec정보에 삭제된다. 
			project.setDescription(description, null);
		}
		catch(CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
		}
	}
}
