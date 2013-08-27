package kr.re.etri.tpl.diagram.builder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLScriptGenerator;
import kr.re.etri.tpl.diagram.views.ProjectNavigatorView;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.part.FileEditorInput;

/**
 * 모델 정보로부터 스크립트를 생성하는 Builder 클래스이다.
 * @author sfline
 *
 */
public class ScriptBuilder extends IncrementalProjectBuilder {

	/**
	 * Builder ID
	 */
	public static final String builderId = "kr.re.etri.tpl.builder.ScriptBuilder";
	
	/**
	 * Builder를 수행하기 위한 argument 정보
	 */
	protected Map buildArgs;
	
	/**
	 * 생성자
	 */
	public ScriptBuilder() {
	}

	/**
	 * Project의 build를 수행하면 수행되는 메소드이다.
	 */
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		
		buildArgs = args;
		
		monitor.beginTask("Script Building", 3);
		generate(monitor);
		monitor.done();
		
		return null;
	}
	
	/**
	 * 모델 정보로부터 스크립트 파일을 생성한다.
	 * @param monitor
	 */
	protected void generate(IProgressMonitor monitor) throws CoreException {
		IResource resource = (IResource)buildArgs.get("path");
		IFile contentFile = (IFile)buildArgs.get("file");
		final ModelDiagram rootModel = (ModelDiagram)buildArgs.get("model");
		// KJH 20100617, diagram editor commandstack info
		CommandStack commandStack = (CommandStack)buildArgs.get("commandstack");
		MarkerLogger problemLogger = (MarkerLogger)buildArgs.get("logger");	// KJH 20110630, add argument
//		MarkerLogger problemLogger = new MarkerLogger(contentFile, MarkerLogger.TYPE_DIAGRAM);

		try {
			// Project resource정보가 없다면 프로젝트 이름으로 resource정보를 찾는다.
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			if(resource == null){
				resource = root.findMember(new Path(getProject().getName()));
			}
			String n = getProject().getName();
			String fileName;
			ByteArrayOutputStream bStream;
			String modelName = rootModel.getName();

			// 모델 이름이 설정되지 않으면 디폴트 값으로 설정한다.
			// 모델 이름이 파일이름으로 사용된다.
			if(modelName == null) {
				modelName = "TPL";
			}

			monitor.worked(1);

			if(checkCancel(monitor)) {
				return;
			}
			// 모델 정보 중  RTM Model 정의 내용을 스크립트로 생성한다.
			TPLScriptGenerator scriptGenerator = new TPLScriptGenerator(problemLogger);
//			bStream = new ByteArrayOutputStream();
//			scriptGenerator.generateModel(rootModel, bStream);
//			fileName = String.format("%s_model.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
//			file = ((IContainer)resource).getFile(new Path(fileName));
//			if(file.exists()) {
//				file.delete(true, null);
//			}
//
//			if(problemLogger.hasError() == false) {
//				file.create(new ByteArrayInputStream(bStream.toByteArray()), true, null);
//			}
//
//			monitor.worked(1);
//
//			if(checkCancel(monitor)) {
//				return;
//			}

			// 모델 정보 중 RTM action 정의 내용을 스크립트로 생성한다.
//			bStream = new ByteArrayOutputStream();
//			scriptGenerator.generateAction(rootModel, bStream);
//			fileName = String.format("%s_action.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
//			file = ((IContainer)resource).getFile(new Path(fileName));
//			if(file.exists()) {
//				file.delete(true, null);
//			}
//			if(problemLogger.hasError() == false) {
//				file.create(new ByteArrayInputStream(bStream.toByteArray()), true, null);
//			}
//
//			monitor.worked(1);
//
//			if(checkCancel(monitor)) {
//				return;
//			}

//			// 모델 정보 중 RTM behavior 정의 내용을 스크립트로 생성한다.
//			bStream = new ByteArrayOutputStream();
//			scriptGenerator.generateTask(rootModel, bStream);
//			fileName = String.format("%s_behavior.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
//			file = ((IContainer)resource).getFile(new Path(fileName));
//			if(file.exists()) {
//				file.delete(true, null);
//			}
//			if(problemLogger.hasError() == false) {
//				file.create(new ByteArrayInputStream(bStream.toByteArray()), true, null);
//			}
//
//			monitor.worked(1);
//
//			if(checkCancel(monitor)) {
//				return;
//			}
//
//			// 모델 정보 중 RTM task 정의 내용을 스크립트로 생성한다.
//			bStream = new ByteArrayOutputStream();
//			scriptGenerator.generateWorker(rootModel, bStream);
//			fileName = String.format("%s_task.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
//			file = ((IContainer)resource).getFile(new Path(fileName));
//			if(file.exists()) {
//				file.delete(true, null);
//			}
//			if(problemLogger.hasError() == false) {
//				file.create(new ByteArrayInputStream(bStream.toByteArray()), true, null);
//			}
//
//			monitor.worked(1);
//
//			if(checkCancel(monitor)) {
//				return;
//			}

			// 모든 모델 정보를 스크립트로 생성한다.
			// 위 내용들 Model, Action, Task, Worker 같이 개별 생성하거나
			// 여기와 같이 모두 하나의 파일로 생성하거나 하여야 한다.
			bStream = new ByteArrayOutputStream();
			scriptGenerator.generateAllInOne(rootModel, bStream);
			
			fileName = String.format("%s_task.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
			if(problemLogger.hasError() == false) {
				final IFile file = ((IContainer)resource).getFile(new Path(fileName));
				InputStream inputStream = new ByteArrayInputStream(bStream.toByteArray());

				if(file.exists()) {
//					file.delete(true, null);
					file.setContents(inputStream, true, true, monitor);
				} else {
					file.create(inputStream, true, null);
				}

				// KJH 20100616, script
				if(commandStack != null){
					commandStack.execute(new Command("set script") {

						@Override
						public void execute() {
							rootModel.setScript(file.getFullPath().toString());
						}

					});
				}

				ScriptManager.getInstance().getTPLList(file);	// KJH 20110623 s, TPL파일 추가 시 스크립트 트리 갱신
			}

			monitor.worked(1);

			if(checkCancel(monitor)) {
				return;
			}

			// 프로제트에 새로운 파일이 생성되었으므로
			// Project Explorer 를 refresh하여 새로운 파일이 보이도록 한다.
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
			IWorkbenchPage wp = wbw.getActivePage();
			IViewPart vp = wp.findView(ProjectNavigatorView.VIEW_ID);

			if(vp != null) {
				CommonViewer cv = ((CommonNavigator)vp).getCommonViewer();
				cv.refresh();
			}

			monitor.done();
		}
		catch (TPLException e) {
			e.printStackTrace();

			problemLogger.error(e.getMessage(), -1, -1, -1);
		}
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
	 * Project에 연결된 Builder를 삭제한다.
	 * @param project Builder 정보를 삭제할 Project
	 */
	public static void removeBuilderFromProject(IProject project){
		
		// Project가 닫힌 상태면 Builder를 삭제할 수 없다.
		if(project.isOpen() == false) {
			return;
		}
		
		//Project의 Description정보를 가져온다.
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
