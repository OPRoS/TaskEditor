package kr.re.etri.tpl.diagram.util;

import java.util.ArrayList;

import kr.re.etri.tpl.diagram.listener.ILogListener;
import kr.re.etri.tpl.diagram.listener.ILogProvider;

public class ProblemLogger implements ILogProvider {

	private int infoCount;
	private int warningCount;
	private int errorCount;
	private ArrayList<ILogListener> listenerList = new ArrayList<ILogListener>();

	public ProblemLogger() {
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
	}

	public void addLogListener(ILogListener listener) {
		if(listenerList.contains(listener)) {
			return;
		}
		listenerList.add(listener);
	}
	
	public void removeLogListener(ILogListener listener) {
		listenerList.remove(listener);
	}

	public void resetLogCount() {
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
	}
	
	public boolean hasInfo() {
		return (infoCount > 0);
	}
	
	public void info(String message) {
		this.info(message, "");
	}

	public void info(String message, String location) {
		for(ILogListener listener : listenerList) {
			listener.info(message, location);
		}

		infoCount += 1;
	}

	public boolean hasWarning() {
		return (warningCount > 0);
	}
	
	public void warning(String message) {
		this.warning(message, "");
	}
	
	public void warning(String message, String location) {
		for(ILogListener listener : listenerList) {
			listener.warning(message, location);
		}

		warningCount += 1;
	}
	
	public boolean hasError() {
		return (errorCount > 0);
	}
	
	public void error(String message) {
		this.error(message, "");
	}
	
	public void error(String message, String location) {
		for(ILogListener listener : listenerList) {
			listener.error(message, location);
		}

		errorCount += 1;
	}
}
