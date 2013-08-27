package kr.re.etri.tpl.script.debug.thread;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.ResponseMessageReader;

public class ThreadMessageReader extends ResponseMessageReader {

	private DebugThreadView m_view;
	
	private static ThreadMessageReader instance;
	
	public static ThreadMessageReader getDefault() {
		if (instance == null)
			instance = new ThreadMessageReader(null);
		return instance;
	}
	
	private ThreadMessageReader(DebugThreadView view) {
		this.m_view = view;
	}
	
	private Shell getShell() {
		if (m_view != null) {
			return m_view.getSite().getShell();
		}
		return null;
	}
	
	private Display getDisplay() {
		Shell shell = getShell();
		if (shell != null && !shell.isDisposed()) {
			return shell.getDisplay();
		}
		return null;
	}
	
	@Override
	public void update(String message) {
		super.update(message);
		if (!isValid())
			return;

		String cmd = getAttribute(ATTR_CMD);
		String name = getAttribute(ATTR_NAME);
		String pthid = getAttribute(ATTR_PARENT);
		String thid = getAttribute(ATTR_THID);
		String threadline = getAttribute(ATTR_THLINE);	// KJH 20111129
		String file = getAttribute(ATTR_FILE);	// KJH 20111129
		
		DebugManager dManager = DebugManager.getDefault();

		if (cmd.equals("new")) {
			ThreadNode child = new ThreadNode(thid, name);
			child.setThreadLine(threadline);	// KJH 20111129
			child.setExpandedFile(file);	// KJH 20111130
			ThreadNode parent = dManager.getThread(pthid);

			if (parent != null) {
				parent.addChild(child);
			}
			dManager.addThread(child, getShell());
		}
		else if (cmd.equals("delete")) {
			ThreadNode child = dManager.getThread(thid);
			if (child != null) {
				ThreadNode parent = child.getParent();
				if (parent != null) {
					if (parent.getThid().equals(pthid)) {
						parent.removeChild(child);
					}
				}
				dManager.removeThread(child, getShell());
			}
		}
		
		updateView();
		clear();
	}

	@Override
	protected boolean isValidCommand(String command) {
		return ("thread".equals(command));
	}
	
	public void setView(DebugThreadView view) {
		this.m_view = view;
	}
	
	public void updateView() {
		Display display = getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					if (m_view != null) {
						m_view.update();
					}
				}
			});
		}
	}
}
