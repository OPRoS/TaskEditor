package kr.re.etri.tpl.script.emulator;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.actions.AbstractDebugActionDelegate;
import kr.re.etri.tpl.script.emulator.dialogs.EmulatorDialog2;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindow;


public class EmulatorDialogOpenActionDelagate extends AbstractDebugActionDelegate {

	private IWorkbenchWindow window;
	private IResource resource;
	
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void run(IAction action) {
		if (resource == null) {
			resource = DebugManager.getDefault().getCurProject();
		}
		EmulatorDialog2 dialog = new EmulatorDialog2(window.getShell(), resource);
		dialog.open();
		resource = dialog.getResource();
	}

	@Override
	protected void update() {
		boolean isConnected = EmulatorManager.getDefault().isConnected();
		fAction.setEnabled(isConnected);
//		fAction.setEnabled(true);
	}

}
