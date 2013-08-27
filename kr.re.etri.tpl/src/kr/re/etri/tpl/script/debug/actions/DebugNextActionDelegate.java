package kr.re.etri.tpl.script.debug.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class DebugNextActionDelegate implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {

	}

	@Override
	public void init(IWorkbenchWindow window) {

	}

	@Override
	public void run(IAction action) {

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		boolean enable = false;
//		DebugManager debugManager = DebugManager.getDefault();
//		if (debugManager.isRunning() && debugManager.isSuspend()) {
//			enable = true;
//		}
		action.setEnabled(enable);

	}

}
