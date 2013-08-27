package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

public class UndeployAction extends Action {
	public final static String actionId = "kr.re.etri.tpl.diagram.views.taskcontrol.actions.UndeployAction";
	private TreeViewer viewer;
	
	public UndeployAction(String text, TreeViewer viewer) {
		super(text);
		setId(actionId);
		this.viewer = viewer;
	}

	@Override
	public void run() {
		TaskControlManager manager = TaskControlManager.getDefault();
	}
}
