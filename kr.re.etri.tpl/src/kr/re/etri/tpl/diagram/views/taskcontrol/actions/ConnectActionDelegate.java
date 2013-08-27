package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import java.io.IOException;
import java.net.UnknownHostException;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControllerView;
import kr.re.etri.tpl.script.debug.DebugMessageClient;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ConnectActionDelegate implements IViewActionDelegate {

	TaskControllerView view;
	TaskControlManager manager = TaskControlManager.getDefault();

	@Override
	public void init(IViewPart view) {
		this.view = (TaskControllerView) view;
	}

	@Override
	public void run(IAction action) {
		boolean check = action.isChecked();
		Shell shell = view.getViewSite().getShell();

		try {

			if (check) { // connect
				String host = view.getHost();
				int port = view.getPort();

				// KJH 20101119 s, exception 종류 변경
				if (host == null || host.equals("")) {
					throw new UnknownHostException();
				} else if (port == -1) {
					throw new IllegalAccessException("port out of range: "
							+ port);
				} // KJH 20101119 e, exception 종류 변경
				
//				if (manager.connect(host, port)) {
				DebugMessageClient c = DebugMessageClient.open(host, port);
				if (c != null && c.isConnected()) {
					new Thread(c).start();
				} else {
					action.setChecked(false);
				}
			} else {
				manager.disconnect();
			}
			// KJH 20101119 s, ErrorDialog 뜨도록 수정함
		} catch (UnknownHostException e) {
			Status status = new Status(IStatus.ERROR,
					TaskModelPlugin.PLUGIN_ID, 0, "unknown host: " + e.getMessage(), e);
			ErrorDialog.openError(shell, "Engine Connection Error", null, status);
			action.setChecked(false);
		} catch (IllegalAccessException e) {
			Status status = new Status(IStatus.ERROR,
					TaskModelPlugin.PLUGIN_ID, 0, e.getMessage(), e);
			ErrorDialog.openError(shell, "Engine Connection Error", null, status);
			action.setChecked(false);
		} catch (IOException e) {
			Status status = new Status(IStatus.ERROR,
					TaskModelPlugin.PLUGIN_ID, 0, e.getMessage(), e);
			ErrorDialog.openError(shell, "Engine Connection Error", null, status);
			action.setChecked(false);
		}
		// KJH 20101119 e, ErrorDialog 뜨도록 수정함

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// KJH 20101221 s, Connect Action Toggle
		action.setChecked(manager.isConnected());
		// KJH 20101221 s, Connect Action Toggle
	}

}
