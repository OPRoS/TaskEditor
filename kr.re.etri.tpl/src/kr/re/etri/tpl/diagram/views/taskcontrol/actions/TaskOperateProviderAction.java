package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.dialog.TaskListDialog;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionProviderAction;

public class TaskOperateProviderAction extends SelectionProviderAction {
	public final static String actionId = "kr.re.etri.tpl.diagram.views.taskcontrol.actions.TaskToggleAction";
	private StructuredViewer viewer;
	public TaskOperateProviderAction(StructuredViewer viewer, String text) {
		super(viewer, text);
		setId(actionId);
		this.viewer = viewer;
	}

	@Override
	public void run() {
		Shell shell = viewer.getControl().getShell();
		TaskListDialog dialog = new TaskListDialog(shell);
		dialog.open();
	}
	
	// KJH 20110215 s, enable�� action���� ó��
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(TaskControlManager.getDefault().isDeployed());
	}
	// KJH 20110215 e, enable�� action���� ó��
}
