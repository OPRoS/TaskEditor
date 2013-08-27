package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import java.util.Iterator;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TreeItem2;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.actions.SelectionProviderAction;

public class RemoveProviderAction extends SelectionProviderAction {
	
	public final static String actionId = "kr.re.etri.tpl.diagram.views.taskcontrol.actions.RemoveAction"; 
	private StructuredViewer viewer;
	
	public RemoveProviderAction(StructuredViewer viewer, String text) {
		super(viewer, text);
		setId(actionId);
		this.viewer = viewer;
	}

	@Override
	public void run() {
		TaskControlManager manager = TaskControlManager.getDefault();
		IStructuredSelection structuredSelection = getStructuredSelection();
		for (Object element : structuredSelection.toArray()) {
			if (element instanceof TreeItem2) {
				manager.rmdir((TreeItem2)element);
				manager.dirall();
			}
		}

		viewer.setInput(manager.getRoot());
	}

	// KJH 20110215 s, enable을 action에서 처리
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		Iterator it = selection.iterator();
		while (it.hasNext()) {
			Object element = it.next();
			if("/".equals(element.toString())) {
				setEnabled(false);
				return;
			}
		}
		setEnabled(!selection.isEmpty());
	}
	// KJH 20110215 e, enable을 action에서 처리
}
