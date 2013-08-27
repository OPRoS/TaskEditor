package kr.re.etri.tpl.actions;

import kr.re.etri.tpl.diagram.dialogs.TaskControllerDialog;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class TaskControllerActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * 메뉴 ID
	 */
	public final static String actionId = "kr.re.etri.tpl.actions.TaskControllerActionDelegate";
	/**
	 * 워크 벤치 윈도우
	 */
	private IWorkbenchWindow window;

	/**
	 * 생성자
	 */
	public TaskControllerActionDelegate() {
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
		
		TaskControllerDialog dialog = new TaskControllerDialog(this.window.getShell());
		dialog.open();
		

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
		IEditorPart editPart= wp.getActiveEditor();
		
		if(editPart instanceof TPLDiagramEditor) {
			action.setEnabled(true);
		}
		else {
			action.setEnabled(false);
		}
	}

}
