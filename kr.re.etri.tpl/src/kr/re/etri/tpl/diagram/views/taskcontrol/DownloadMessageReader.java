package kr.re.etri.tpl.diagram.views.taskcontrol;

import java.util.LinkedList;
import java.util.Queue;

import kr.re.etri.tpl.diagram.views.taskcontrol.actions.DownloadRunnable;
import kr.re.etri.tpl.script.debug.DebugMessageClient;
import kr.re.etri.tpl.script.debug.ResponseMessageReader;

import org.eclipse.swt.widgets.Display;

public class DownloadMessageReader extends ResponseMessageReader {
	
	public static String ATTR_FNAME = "fname";
	public static String ATTR_LENGTH = "lengh";

	private Queue<DownloadRunnable> runnables;
	private Display display;
	
	private static DownloadMessageReader instance;
	
	public static DownloadMessageReader getDefault() {
		if (instance == null) {
			instance = new DownloadMessageReader();
		}
		return instance;
	}
	
	protected DownloadMessageReader() {
		runnables = new LinkedList<DownloadRunnable>();
	}
	
	public void addRunnable(DownloadRunnable runnable) {
		runnables.add(runnable);
	}
	
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	@Override
	public void update(String message) {
		super.update(message);
		if (!isValid())
			return;
		
//		String fname = getAttribute(ATTR_FNAME);
		String length = getAttribute(ATTR_LENGTH);
		int iLen = 0;
		String msg = "";
		
		try {
			iLen = Integer.parseInt(length);
		} catch (NumberFormatException e) {
			return;
		}
		
		if (iLen > 0) {
			msg = DebugMessageClient.getFormattedMessage(iLen + 7);
			int idx = msg.lastIndexOf("RES-EOF");
			if (idx > -1) {
				msg = msg.substring(0, idx);
			}
		}
		
		DownloadRunnable runnable = runnables.poll();
		
		if (display != null && !display.isDisposed() && runnable != null) {
			runnable.setContent(msg);
			display.asyncExec(runnable);
		}
	}

	@Override
	protected boolean isValidCommand(String command) {
		return ("download".equals(command));
	}

}
