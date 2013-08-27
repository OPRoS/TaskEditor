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
 * ������Ʈ���� Script ������ ����Ǹ� ���� �˻縦 �����Ѵ�.
 * Script ������ ����Ǿ� ����Ǹ� ���� �Ǵ� �ڵ����� ����ȴ�. 
 * @author sfline
 *
 */
public class ScriptParsingBuilder extends IncrementalProjectBuilder implements IExecutableExtension {

	/**
	 * Builder ID
	 */
	public static final String builderId = "kr.re.etri.tpl.builder.ScriptParsingBuilder";
	
	/**
	 * ������
	 */
	public ScriptParsingBuilder() {
	}

	/**
	 * build �޼ҵ尡 ȣ��Ǳ� ���� �ʱ�ȭ�� ���� ȣ��ȴ�.
	 * @param config ���� ����
	 * @param propertyName
	 * @param data
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		
	}

	/**
	 * Project�� build�� �����ϸ� ����Ǵ� �޼ҵ��̴�.
	 * ScriptParsingBuilder�� TaskScriptNature�� ����Ǿ� �ִ�.
	 * ScriptParsingBuilder�� Task Script�� ����(����)�Ǹ� �����ϰ�, Nature��
	 * ������Ʈ�� ����� ���� �����Ѵ�.
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
	 * Project clean �޴��� �����Ͽ��� �� ����ȴ�.
	 * Builder ���� �� ������ �ڿ� �� Marker ���� ������ �� �ִ�.
	 */
//	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		IProject project = getProject();
		project.deleteMarkers(MarkerLogger.TYPE_SCRIPT, false, IResource.DEPTH_INFINITE);
		project.deleteMarkers(MarkerLogger.TYPE_DIAGRAM, false, IResource.DEPTH_INFINITE);
	}

	/**
	 * ������Ʈ�� ���Ե� Script ���� ���� ���� �˻縦 �����Ѵ�.
	 * @param resource ������Ʈ �ڿ�(Folder, File ��)
	 * @param args ���� Argument
	 * @param monitor ���α׷��� �����
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

		// ������ Task Script���� �� ��츸 ���� �˻縦 �����Ѵ�.
		if(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equals(resource.getFileExtension()) == false) {
			return;
		}

		// ���� �˻縦 ������ ��ũ��Ʈ ���Ͽ� ���� ��� Problem Marker�� �����Ѵ�.
		ScriptParser parser = new ScriptParser((IFile)resource);
		MarkerLogger markLogger = new MarkerLogger((IFile)resource, MarkerLogger.TYPE_SCRIPT);
		markLogger.clearProblem();

		// Problem Marker�� ����� �����ʸ� ����Ѵ�.
		parser.addErrorListener(markLogger);

		ModelDiagram incModel = parser.parse();
	}

	/**
	 * ����ڰ� "Cancle"�� ��û�Ͽ����� Ȯ���Ѵ�.
	 * @param monitor ���α׷��� �����
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
	 * Project�� Builer�� �����Ѵ�.
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

		// ������Ʈ�� BuildSpec ������ �����´�.
		ICommand []cmds = description.getBuildSpec();
		for(int idx = 0; idx < cmds.length; idx += 1) {
			if(cmds[idx].getBuilderName().equals(builderId)) {
				return;
			}
		}

		// BuildSpec������ ScriptBuilder������ ������ �߰��Ѵ�.
		ICommand newCmd = description.newCommand();
		newCmd.setBuilderName(builderId);
		List<ICommand> newCmds = new ArrayList<ICommand>();
		newCmds.addAll(Arrays.asList(cmds));
		newCmds.add(newCmd);
		
		// ����� BuildSpec�� �����Ѵ�.
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));
		
		try {
			// Project�� ����� Description������ �����Ѵ�.
			// Builder ������ ������Ʈ�� .project ���ϳ� BuildSpec������ �߰��ȴ�.
			project.setDescription(description, null);
		}
		catch(CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
		}
	}

	/**
	 * Project�� ScriptParsingBuilder�� �����Ѵ�.
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

		// BuildSpec �������� ScriptBuilder�� ��ġ�� ã�´�.
		int index = -1;
		ICommand []cmds = description.getBuildSpec();
		for(int idx = 0; idx < cmds.length; idx += 1) {
			if(cmds[idx].getBuilderName().equals(builderId)) {
				index = idx;
				break;
			}
		}

		// ��ġ�� ã�� �� ���ٸ� Builder�� ������� ���� ���̹Ƿ�
		// �����Ѵ�.
		if(index == -1) {
			return;
		}

		// ScriptBuilder�� BuildSpec���� �����Ѵ�.
		List<ICommand> newCmds = new ArrayList<ICommand>();
		newCmds.addAll(Arrays.asList(cmds));
		newCmds.remove(index);

		// ����� BuildSpec ������ Description�� �����Ѵ�.
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));

		try {
			// ����� Description�� ������Ʈ�� �����Ѵ�.
			// Builder������ ������Ʈ�� .project������ BuildSpec������ �����ȴ�. 
			project.setDescription(description, null);
		}
		catch(CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
		}
	}
}
