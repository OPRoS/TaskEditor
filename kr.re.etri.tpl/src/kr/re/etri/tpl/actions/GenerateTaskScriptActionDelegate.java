package kr.re.etri.tpl.actions;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.builder.ScriptBuilder;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

/**
 * �� ������ Script�� �����ϴ� �޴� ���� Ŭ�����̴�.
 * @author sfline
 *
 */
public class GenerateTaskScriptActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * �޴� ID
	 */
	public final static String actionId = "kr.re.etri.tpl.actions.generatetaskscriptactiondelegate";
	/**
	 * ��ũ ��ġ ������
	 */
	private IWorkbenchWindow window;

	/**
	 * ������
	 */
	public GenerateTaskScriptActionDelegate() {
	}

	/**
	 * dispose�� �ڿ��� �����Ͽ��� �Ѵ�.
	 * @Override
	 */
	public void dispose() {
	}


	/**
	 * �޴� ����� �ʱ�ȭ�� �����Ѵ�.
	 * @param window ��ũ��ġ ������
	 * @Override
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * ���õ� �޴� ����� �����Ѵ�.
	 * @Override
	 */
	public void run(IAction action) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		final IEditorPart editPart= wp.getActiveEditor();
		Shell shell = wbw.getShell();
		
		// ���� Ȱ��ȭ�� EditPart�� RTM ���̾�׷� ������ �� ��쿡�� ���� ���� 
		if(!(editPart instanceof TPLDiagramEditor)) {
			return;
		}
		
		// ���� ���� ���� ������ ��� ������Ʈ�� ���ԵǾ� �ִ����� �˾Ƴ���. 
		IEditorInput editorInput = editPart.getEditorInput();
		IFile contentFile = ((FileEditorInput)editorInput).getFile();
		IProject project = contentFile.getProject();
		
		// ��ũ��Ʈ�� ������ ��θ� �����Ѵ�.
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(shell, project, true, null);
		if(dialog.open() != Dialog.OK) {
			return;
		}

		// ���� ���� ���� ���� ã�´�.
		ModelDiagram rootModel = (ModelDiagram)((TPLDiagramEditor)editPart).getModel();
		String modelName = rootModel.getName();
		
		if(modelName == null) {
			modelName = "TPL";
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		Object[]path = dialog.getResult();
		IResource resource;
		if(path.length > 0) {
			resource = root.findMember((Path)path[0]);
			if(resource == null) {
				IFolder newFolder = root.getFolder((Path)path[0]);
				try {
					newFolder.create(IResource.FORCE, true, null);
					resource = newFolder;
					root.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
				
			}
		}
		else {
			resource = project;
		}

		// ���õ� ��ο� ���� �̸��� ��ũ��Ʈ ���ϵ��� �ִ��� Ȯ���Ͽ�
		// ���� �̸��� ������ ������ ����� ���θ� Ȯ���Ѵ�.
		IFile file;
		String fileName;
		boolean fileExist = false;
		StringBuilder sb = new StringBuilder();
		fileName = String.format("%s_model.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
		file = ((IContainer)resource).getFile(new Path(fileName));
		sb.append("���õ� ��ο� �̹� ������ �ֽ��ϴ�. ������ �ٲٽðڽ��ϱ�?").append("\r\n").append("\r\n");
		if(file.exists()) {
			sb.append(file.getFullPath().toString());
			fileExist = true;
		}
		
//		fileName = String.format("%s_action.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
//		file = ((IContainer)resource).getFile(new Path(fileName));
//		if(file.exists()) {
//			if(fileExist) {
//				sb.append("\r\n");
//			}
//			sb.append(file.getFullPath().toString());
//			fileExist = true;
//		}
		fileName = String.format("%s_task.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
		file = ((IContainer)resource).getFile(new Path(fileName));
		if(file.exists()) {
			if(fileExist) {
				sb.append("\r\n");
			}
			sb.append(file.getFullPath().toString());
			fileExist = true;
		}
		fileName = String.format("%s_behavior.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
		file = ((IContainer)resource).getFile(new Path(fileName));
		if(file.exists()) {
			if(fileExist) {
				sb.append("\r\n");
			}
			sb.append(file.getFullPath().toString());
			fileExist = true;
		}
		
		if(fileExist) {
			String[] dialogButtonLabels = new String[]{"Ȯ��", "���"};
			MessageDialog msgDialog = new MessageDialog(shell, "TPL", null, sb.toString(), MessageDialog.QUESTION, dialogButtonLabels, 0);
			if(msgDialog.open() != Dialog.OK) {
				return;
			}
		}

		// �ӽ÷� Builder�� ������Ʈ�� �߰��Ѵ�.
		ScriptBuilder.addBuilderToProject(project);

		try {
			MarkerLogger markLogger = new MarkerLogger((IFile)contentFile, MarkerLogger.TYPE_DIAGRAM);
			markLogger.clearProblem();

			Map args = new HashMap();
			args.put("path", resource);
			args.put("file", contentFile);
			args.put("model",rootModel);
			// KJH 20100617, diagram editor commandstack info
			args.put("commandstack", ((TPLDiagramEditor)editPart).getCommandStack());
			args.put("logger", markLogger);
			
			// Project Build ����� �̿��Ͽ� ��ũ��Ʈ ���� Builder�� �����Ѵ�.
			project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, ScriptBuilder.builderId, args, null);
			
			if (markLogger.hasError()) {
//				String[] dialogButtonLabels = new String[]{"Ȯ��"};
//				MessageDialog msgDialog = new MessageDialog(wbw.getShell(), "Error", null,
//						"Script ���� ������ ����", MessageDialog.ERROR, dialogButtonLabels, 0);
//				msgDialog.open();

				Display display = shell.getDisplay();
				if (display != null && !display.isDisposed()) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							IStatusLineManager statusLine = 
								editPart.getEditorSite().getActionBars().getStatusLineManager();
							statusLine.setErrorMessage("Failed to generate script file");
						}
					});
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// �ӽ÷� �߰��� Builder�� ������Ʈ�κ��� �����Ѵ�.
		ScriptBuilder.removeBuilderFromProject(project);
	}

	/**
	 * �޴� Ȱ��ȭ�� �����Ѵ�.
	 * @param action Ȱ��ȭ ���θ� Ȯ���� �޴� Action
	 * @param selection �޴� Action�� ���� ����
	 * @Override
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		if (wp == null) {
			return;
		}
		IEditorPart editPart= wp.getActiveEditor();
		
		if(editPart instanceof TPLDiagramEditor) {
			action.setEnabled(true);
		}
		else {
			action.setEnabled(false);
		}
	}

}
