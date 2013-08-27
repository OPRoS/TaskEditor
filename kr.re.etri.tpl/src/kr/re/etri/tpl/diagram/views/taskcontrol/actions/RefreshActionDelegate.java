package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControllerView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class RefreshActionDelegate implements IViewActionDelegate {

	TaskControllerView view;
	
	@Override
	public void init(IViewPart view) {
		this.view = (TaskControllerView)view;
	}

	@Override
	public void run(IAction action) {
		TaskControlManager.getDefault().dirall();	// KJH 20110307, re dir
//		view.update();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
