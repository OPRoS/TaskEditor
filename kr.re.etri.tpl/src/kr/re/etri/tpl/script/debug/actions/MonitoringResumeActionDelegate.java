package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.thread.ThreadNode;

import org.eclipse.jface.action.IAction;

public class MonitoringResumeActionDelegate extends AbstractDebugActionDelegate {

	@Override
	public void run(IAction action) {
		DebugManager dManager = DebugManager.getDefault();
		
		if (!dManager.isRunning()) {
			dManager.start(false, true, fWindow);
		}
		else {
			dManager.resume(fWindow);
		}
	}

	@Override
	protected void update() {
		DebugManager dManager = DebugManager.getDefault();
//		ThreadNode thread = dManager.getSelectedThread();

		if (!dManager.isEnabled()) {
			fAction.setEnabled(false);
			return;
		}
		
		if (!dManager.isRunning()) {
			fAction.setEnabled(true);
			fAction.setText("Monitoring");
			fAction.setToolTipText("Monitoring");
			return;
		}
		else {
			if (!dManager.isMonitoring()) {
				fAction.setEnabled(false);
				fAction.setText("Monitoring");
				fAction.setToolTipText("Monitoring");
				return;
			}
			if (dManager.isSuspend()) {
				fAction.setEnabled(true);
				fAction.setText("Resume");
				fAction.setToolTipText("Resume");
				return;
			}
		}
		fAction.setEnabled(false);
		fAction.setText("Monitoring");
		fAction.setToolTipText("Monitoring");
	}

}
