package kr.re.etri.tpl.diagram.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kr.re.etri.tpl.diagram.listener.ILogListener;
import kr.re.etri.tpl.diagram.listener.IMessageListener;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IMessageProvider;

public class TPLProblemLogger implements ILogListener, IMessageListener {
	public static final String markerId = "kr.re.etri.tpl.RTMProblemLogger";
	
	private IResource resource;
	private int infoCount;
	private int warningCount;
	private int errorCount;

	public TPLProblemLogger(IResource res) {
		this.resource = res;
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
	}
	
	public void resetLogCount() {
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
	}
	
	public boolean hasInfo() {
		return (infoCount > 0);
	}

	public void info(String message){
		this.info(message, "", Collections.EMPTY_MAP);
	}

	public void info(String message, String location){
		this.info(message, location, Collections.EMPTY_MAP);
	}

	public void info(String message, String location, Map args){
		try {
			IMarker marker = resource.createMarker(markerId);

			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			marker.setAttribute(IMarker.LOCATION, location);
			if(args != null) {
				Set<Map.Entry<String, Object>> set = args.entrySet();
				Iterator<Map.Entry<String, Object>> iter = set.iterator();
				while(iter.hasNext()) {
					Map.Entry<String, Object> entry = iter.next();
					marker.setAttribute(entry.getKey(), entry.getValue());
				}
			}
		}
		catch(CoreException e) {
			e.printStackTrace();
		}
		infoCount += 1;
	}
	
	public boolean hasWarning() {
		return (warningCount > 0);
	}
	
	public void warning(String message){
		this.warning(message, "", Collections.EMPTY_MAP);
	}
	
	public void warning(String message, String location) {
		this.warning(message, location, Collections.EMPTY_MAP);
	}
	
	public void warning(String message, String location, Map args) {
		try {
			IMarker marker = resource.createMarker(markerId);
	
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			marker.setAttribute(IMarker.LOCATION, location);
			if(args != null) {
				Set<Map.Entry<String, Object>> set = args.entrySet();
				Iterator<Map.Entry<String, Object>> iter = set.iterator();
				while(iter.hasNext()) {
					Map.Entry<String, Object> entry = iter.next();
					marker.setAttribute(entry.getKey(), entry.getValue());
				}
			}
		}
		catch(CoreException e) {
			e.printStackTrace();
		}

		warningCount += 1;
	}
	
	public boolean hasError() {
		return (errorCount > 0);
	}
	
	public void error(String message){
		this.error(message, "", Collections.EMPTY_MAP);
	}
	
	public void error(String message, String location) {
		this.error(message, location, Collections.EMPTY_MAP);
	}

	public void error(String message, String location, Map args) {
		try {
			IMarker marker = resource.createMarker(markerId);
	
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.LOCATION, location);
			if(args != null) {
				Set<Map.Entry<String, Object>> set = args.entrySet();
				Iterator<Map.Entry<String, Object>> iter = set.iterator();
				while(iter.hasNext()) {
					Map.Entry<String, Object> entry = iter.next();
					marker.setAttribute(entry.getKey(), entry.getValue());
				}
			}
		}
		catch(CoreException e) {
			e.printStackTrace();
		}

		errorCount += 1;
	}

	public static void clearProblem(IResource res) {
		if(res == null) {
			return;
		}

		try {
			//프로젝트에 대한 Problem을 모두 삭제한다.
			res.deleteMarkers(TPLProblemLogger.markerId, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			e.printStackTrace();
			TPLErrorLogger.error(e.getMessage(), e);
		}
	}

	public void clearProblem() {
		if(resource == null) {
			return;
		}
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;

		TPLProblemLogger.clearProblem(resource);
	}

	public void clearAllProblem() {
		if(resource == null) {
			return;
		}
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;

		IProject project = resource.getProject();

		TPLProblemLogger.clearProblem(project);
	}
	
	@Override
	public void fireMessage(String newMessage, int newType) {
		if(newMessage == null || newMessage.length() == 0) {
			return;
		}

		if(IMessageProvider.INFORMATION == newType ||
			IMessageProvider.NONE == newType) {
			this.info(newMessage);
		}
		else if(IMessageProvider.WARNING == newType) {
			this.warning(newMessage);
		}
		else if(IMessageProvider.ERROR == newType) {
			this.error(newMessage);
		}
	}
}
