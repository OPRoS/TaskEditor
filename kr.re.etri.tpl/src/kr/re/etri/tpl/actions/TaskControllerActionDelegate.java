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
	 * �޴� ID
	 */
	public final static String actionId = "kr.re.etri.tpl.actions.TaskControllerActionDelegate";
	/**
	 * ��ũ ��ġ ������
	 */
	private IWorkbenchWindow window;

	/**
	 * ������
	 */
	public TaskControllerActionDelegate() {
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
		
		TaskControllerDialog dialog = new TaskControllerDialog(this.window.getShell());
		dialog.open();
		

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
		IEditorPart editPart= wp.getActiveEditor();
		
		if(editPart instanceof TPLDiagramEditor) {
			action.setEnabled(true);
		}
		else {
			action.setEnabled(false);
		}
	}

}
