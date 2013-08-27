package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.thread.ThreadNode;

import org.eclipse.jface.action.IAction;

public class DebugStepActionDelegate extends AbstractDebugActionDelegate {

	@Override
	public void run(IAction action) {
		DebugManager dManager = DebugManager.getDefault();
		
		dManager.start(true, false, fWindow);
	}

	@Override
	protected void update() {
		DebugManager dManager = DebugManager.getDefault();
		ThreadNode thread = dManager.getSelectedThread();

		if (!dManager.isEnabled()) {
			fAction.setEnabled(false);
			return;
		}
			
		if (!dManager.isRunning()) {
			fAction.setEnabled(true);
			return;
		}
		else {
			if (dManager.isMonitoring()) {
				fAction.setEnabled(false);
				return;
			}
			if (thread != null) {
				fAction.setEnabled(!thread.isRunning());
				return;
			}
		}
		fAction.setEnabled(false);
	}

}
