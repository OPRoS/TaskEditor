package kr.re.etri.tpl.script.debug;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;

import kr.re.etri.tpl.MessageListener;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleMessageReader implements MessageListener {

	private static ConsoleMessageReader instance;
	private MessageConsole consoleView;
	private MessageConsoleStream consoleStream;
	
	@Override
	public void update(String message) {
		if (message.startsWith("TPL-R>"))
			return;
		
		if (message.matches("[^A-Za-z0-9]*")) {	// KJH 20111123, add
			return;
		}
		
//		if (message.equals(""))
//			return;
		
		getConsoleStream().println(message);
	}
	
	public static ConsoleMessageReader getDefault() {
		if (instance == null)
			instance = new ConsoleMessageReader();
		return instance;
	}
	
	public MessageConsoleStream getConsoleStream() {
		IConsoleManager consoleManager = (IConsoleManager)ConsolePlugin.getDefault().getConsoleManager();
		for (IConsole console : consoleManager.getConsoles()) {
			if ("Console View".equals(console.getName())) {
				consoleView = (MessageConsole)console;
			}
		}
		
		if (consoleView == null) {
			consoleView = new MessageConsole("Console View", null);
			consoleManager.addConsoles(new IConsole[]{consoleView});
		}
		consoleManager.showConsoleView(consoleView);
		if (consoleStream == null) {
			consoleStream = consoleView.newMessageStream();
			System.setOut(new PrintStream(consoleStream));
		}
		
		return consoleStream;
	}

	/**
	 * @author KJH 20110411
	 */
	public void dispose() {
		IConsoleManager consoleManager = (IConsoleManager)ConsolePlugin.getDefault().getConsoleManager();
		
		if (consoleStream != null) {
			try {
				consoleStream.close();
				consoleStream = null;
			} catch (IOException e) {
			}
		}
		if (consoleView != null) {
			consoleView.clearConsole();
//			consoleManager.removeConsoles(new IConsole[] {consoleView});
		}
	}
}
