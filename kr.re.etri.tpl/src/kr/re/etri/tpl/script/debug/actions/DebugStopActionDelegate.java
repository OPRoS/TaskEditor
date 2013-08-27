package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;

import org.eclipse.jface.action.IAction;

public class DebugStopActionDelegate extends AbstractDebugActionDelegate {

	@Override
	public void run(IAction action) {
		DebugManager dManager = DebugManager.getDefault();
		
		if (dManager.isRunning()) {
			dManager.stop(fWindow);
		}
	}

	@Override
	protected void update() {
		DebugManager dManager = DebugManager.getDefault();

		if (!dManager.isEnabled()) {
			fAction.setEnabled(false);
			return;
		}
			
		fAction.setEnabled(dManager.isRunning());
	}

}
