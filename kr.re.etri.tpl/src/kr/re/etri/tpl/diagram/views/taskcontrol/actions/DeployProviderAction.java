package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TreeItem2;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.actions.SelectionProviderAction;

public class DeployProviderAction extends SelectionProviderAction {
	
	public final static String actionId = "kr.re.etri.tpl.diagram.views.taskcontrol.actions.DeployAction";
	
	public DeployProviderAction(StructuredViewer viewer, String text) {
		super(viewer, text);
		setId(actionId);
	}

	@Override
	public void run() {
		TaskControlManager manager = TaskControlManager.getDefault();

		IStructuredSelection selection = getStructuredSelection();
		Object[] objects = selection.toArray();
		for (Object obj : objects) {
			if ((obj instanceof TreeItem2) == false)
				continue;

			TreeItem2 item = (TreeItem2)obj;
			if (item.isContainer())
				continue;

			manager.deploy(item.getPath());	// KJH 20111125, TreeItem2->String
			// KJH 20101221, manager.update()시 dir을 새로하게 되는 문제가 있어서 수정
			getSelectionProvider().setSelection(selection);
		}
	}

	// KJH 20110215 s, enable을 action에서 처리
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof TreeItem2) {
				setEnabled(!((TreeItem2)element).isContainer());
				return;
			}
		}
		setEnabled(false);
	}
	// KJH 20110215 e, enable을 action에서 처리
}
