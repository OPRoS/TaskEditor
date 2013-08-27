package kr.re.etri.tpl.diagram.views.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ActionNavigatorDeleteActionDelegate implements IViewActionDelegate {

	public static final String action_id = "kr.re.etri.tpl.views.ActionNavigationViewPage.DeleteAction";
	private IViewPart targetPart;
	
	public ActionNavigatorDeleteActionDelegate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IViewPart view) {
		this.targetPart = view;
	}

	@Override
	public void run(IAction action) {

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if(action == null) {
			return;
		}
		
		action.setEnabled(selection != null);
		action.setEnabled(true);
	}
}
