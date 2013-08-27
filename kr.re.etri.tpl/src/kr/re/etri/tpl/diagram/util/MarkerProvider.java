package kr.re.etri.tpl.diagram.util;


import java.util.ArrayList;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.listener.IErrorProvider;

public class MarkerProvider implements IErrorListener, IErrorProvider {

	private int infoCount;
	private int warningCount;
	private int errorCount;
	private ArrayList<IErrorListener> listenerList = new ArrayList<IErrorListener>();

	public MarkerProvider() {
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
	}

	public void resetLogCount() {
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
	}

	public void addErrorListener(IErrorListener listener) {
		if(listenerList.contains(listener)) {
			return;
		}
		listenerList.add(listener);
	}
	
	public void removeErrorListener(IErrorListener listener) {
		listenerList.remove(listener);
	}

	public boolean hasInfo() {
		return (infoCount > 0);
	}
	
	public boolean hasWarning() {
		return (warningCount > 0);
	}
	
	public boolean hasError() {
		return (errorCount > 0);
	}

	public void info(String message, int lineNum, int charStart, int charEnd) {
		for(IErrorListener listener : listenerList) {
			listener.info(message, lineNum, charStart, charEnd);
		}
		
		infoCount += 1;
	}

	public void warning(String message, int lineNum, int charStart, int charEnd) {
		for(IErrorListener listener : listenerList) {
			listener.warning(message, lineNum, charStart, charEnd);
		}
		
		warningCount += 1;
	}

	public void error(String message, int lineNum, int charStart, int charEnd) {
		for(IErrorListener listener : listenerList) {
			listener.error(message, lineNum, charStart, charEnd);
		}
		
		errorCount += 1;
	}
}
