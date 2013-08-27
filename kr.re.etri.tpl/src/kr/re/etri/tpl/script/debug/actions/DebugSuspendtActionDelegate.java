package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;

import org.eclipse.jface.action.IAction;

public class DebugSuspendtActionDelegate extends AbstractDebugActionDelegate {

	@Override
	public void run(IAction action) {
		DebugManager dManager = DebugManager.getDefault();
		
		if (!dManager.isRunning()) {
			dManager.suspend(fWindow);
		}
	}

	@Override
	protected void update() {
		DebugManager dManager = DebugManager.getDefault();
		boolean enable = false;
		if (dManager.isRunning() && dManager.isSuspend()) {
			enable = true;
		}
		fAction.setEnabled(enable);
	}

}
