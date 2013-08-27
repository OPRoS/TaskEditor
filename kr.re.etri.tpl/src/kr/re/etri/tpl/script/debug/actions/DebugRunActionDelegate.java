package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.thread.ThreadNode;

import org.eclipse.jface.action.IAction;

public class DebugRunActionDelegate extends AbstractDebugActionDelegate {

	@Override
	public void run(IAction action) {
		DebugManager dManager = DebugManager.getDefault();

		dManager.start(false, false, fWindow);
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
			fAction.setText("Debug");
			fAction.setToolTipText("Debug");
			return;
		}
		else {
			if (dManager.isMonitoring()) {
				fAction.setEnabled(false);
				fAction.setText("Debug");
				fAction.setToolTipText("Debug");
				return;
			}
			if (thread != null) {
				fAction.setEnabled(!thread.isRunning());
				fAction.setText("Resume");
				fAction.setToolTipText("Resume");
				return;
			}
		}
		fAction.setEnabled(false);
		fAction.setText("Debug");
		fAction.setToolTipText("Debug");
	}

}
