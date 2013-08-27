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
 * �� �����κ��� ��ũ��Ʈ�� �����ϴ� Builder Ŭ�����̴�.
 * @author sfline
 *
 */
public class ScriptBuilder extends IncrementalProjectBuilder {

	/**
	 * Builder ID
	 */
	public static final String builderId = "kr.re.etri.tpl.builder.ScriptBuilder";
	
	/**
	 * Builder�� �����ϱ� ���� argument ����
	 */
	protected Map buildArgs;
	
	/**
	 * ������
	 */
	public ScriptBuilder() {
	}

	/**
	 * Project�� build�� �����ϸ� ����Ǵ� �޼ҵ��̴�.
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
	 * �� �����κ��� ��ũ��Ʈ ������ �����Ѵ�.
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
			// Project resource������ ���ٸ� ������Ʈ �̸����� resource������ ã�´�.
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			if(resource == null){
				resource = root.findMember(new Path(getProject().getName()));
			}
			String n = getProject().getName();
			String fileName;
			ByteArrayOutputStream bStream;
			String modelName = rootModel.getName();

			// �� �̸��� �������� ������ ����Ʈ ������ �����Ѵ�.
			// �� �̸��� �����̸����� ���ȴ�.
			if(modelName == null) {
				modelName = "TPL";
			}

			monitor.worked(1);

			if(checkCancel(monitor)) {
				return;
			}
			// �� ���� ��  RTM Model ���� ������ ��ũ��Ʈ�� �����Ѵ�.
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

			// �� ���� �� RTM action ���� ������ ��ũ��Ʈ�� �����Ѵ�.
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

//			// �� ���� �� RTM behavior ���� ������ ��ũ��Ʈ�� �����Ѵ�.
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
//			// �� ���� �� RTM task ���� ������ ��ũ��Ʈ�� �����Ѵ�.
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

			// ��� �� ������ ��ũ��Ʈ�� �����Ѵ�.
			// �� ����� Model, Action, Task, Worker ���� ���� �����ϰų�
			// ����� ���� ��� �ϳ��� ���Ϸ� �����ϰų� �Ͽ��� �Ѵ�.
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

				ScriptManager.getInstance().getTPLList(file);	// KJH 20110623 s, TPL���� �߰� �� ��ũ��Ʈ Ʈ�� ����
			}

			monitor.worked(1);

			if(checkCancel(monitor)) {
				return;
			}

			// ������Ʈ�� ���ο� ������ �����Ǿ����Ƿ�
			// Project Explorer �� refresh�Ͽ� ���ο� ������ ���̵��� �Ѵ�.
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
	 * Project�� ����� Builder�� �����Ѵ�.
	 * @param project Builder ������ ������ Project
	 */
	public static void removeBuilderFromProject(IProject project){
		
		// Project�� ���� ���¸� Builder�� ������ �� ����.
		if(project.isOpen() == false) {
			return;
		}
		
		//Project�� Description������ �����´�.
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
