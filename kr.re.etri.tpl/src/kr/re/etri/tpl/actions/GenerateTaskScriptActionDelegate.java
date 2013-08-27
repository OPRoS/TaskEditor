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
 * 모델 정보로 Script를 생성하는 메뉴 실행 클래스이다.
 * @author sfline
 *
 */
public class GenerateTaskScriptActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * 메뉴 ID
	 */
	public final static String actionId = "kr.re.etri.tpl.actions.generatetaskscriptactiondelegate";
	/**
	 * 워크 벤치 윈도우
	 */
	private IWorkbenchWindow window;

	/**
	 * 생성자
	 */
	public GenerateTaskScriptActionDelegate() {
	}

	/**
	 * dispose시 자원을 해제하여야 한다.
	 * @Override
	 */
	public void dispose() {
	}


	/**
	 * 메뉴 실행시 초기화를 수행한다.
	 * @param window 워크벤치 윈도우
	 * @Override
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * 선택된 메뉴 기능을 수행한다.
	 * @Override
	 */
	public void run(IAction action) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		final IEditorPart editPart= wp.getActiveEditor();
		Shell shell = wbw.getShell();
		
		// 현재 활성화된 EditPart가 RTM 다이어그램 에디터 인 경우에만 실행 가능 
		if(!(editPart instanceof TPLDiagramEditor)) {
			return;
		}
		
		// 현재 편집 중인 파일이 어느 프로젝트에 포함되어 있는지를 알아낸다. 
		IEditorInput editorInput = editPart.getEditorInput();
		IFile contentFile = ((FileEditorInput)editorInput).getFile();
		IProject project = contentFile.getProject();
		
		// 스크립트를 저장할 경로를 선택한다.
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(shell, project, true, null);
		if(dialog.open() != Dialog.OK) {
			return;
		}

		// 현재 편집 중인 모델을 찾는다.
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

		// 선택된 경로에 같은 이름의 스크립트 파일들이 있는지 확인하여
		// 같은 이름의 파일이 있으면 덮어쓰기 여부를 확인한다.
		IFile file;
		String fileName;
		boolean fileExist = false;
		StringBuilder sb = new StringBuilder();
		fileName = String.format("%s_model.%s", modelName, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
		file = ((IContainer)resource).getFile(new Path(fileName));
		sb.append("선택된 경로에 이미 파일이 있습니다. 파일을 바꾸시겠습니까?").append("\r\n").append("\r\n");
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
			String[] dialogButtonLabels = new String[]{"확인", "취소"};
			MessageDialog msgDialog = new MessageDialog(shell, "TPL", null, sb.toString(), MessageDialog.QUESTION, dialogButtonLabels, 0);
			if(msgDialog.open() != Dialog.OK) {
				return;
			}
		}

		// 임시로 Builder를 프로젝트에 추가한다.
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
			
			// Project Build 기능을 이용하여 스크립트 생성 Builder를 수행한다.
			project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, ScriptBuilder.builderId, args, null);
			
			if (markLogger.hasError()) {
//				String[] dialogButtonLabels = new String[]{"확인"};
//				MessageDialog msgDialog = new MessageDialog(wbw.getShell(), "Error", null,
//						"Script 파일 생성에 실패", MessageDialog.ERROR, dialogButtonLabels, 0);
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

		// 임시로 추가된 Builder를 프로젝트로부터 삭제한다.
		ScriptBuilder.removeBuilderFromProject(project);
	}

	/**
	 * 메뉴 활성화를 변경한다.
	 * @param action 활성화 여부를 확인할 메뉴 Action
	 * @param selection 메뉴 Action의 선택 여부
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
