package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.thread.ThreadNode;

import org.eclipse.jface.action.IAction;

public class MonitoringSuspendActionDelegate extends AbstractDebugActionDelegate {

	@Override
	public void run(IAction action) {
		DebugManager dManager = DebugManager.getDefault();
		
		dManager.suspend(fWindow);
	}

	@Override
	protected void update() {
		DebugManager dManager = DebugManager.getDefault();
//		ThreadNode thread = dManager.getSelectedThread();

		if (!dManager.isEnabled()) {
			fAction.setEnabled(false);
			return;
		}
			
		if (dManager.isRunning() && dManager.isMonitoring()) {
			fAction.setEnabled(!dManager.isSuspend());
			return;
		}
		fAction.setEnabled(false);
	}

}
