package kr.re.etri.tpl.diagram.util;


import kr.re.etri.tpl.diagram.listener.IErrorListener;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class MarkerLogger implements IErrorListener {
	private static Logger logger = Logger.getLogger(MarkerLogger.class);
	public final static String TYPE_SCRIPT = "kr.re.etri.tpl.TaskScriptProblem";
	public final static String TYPE_DIAGRAM = "kr.re.etri.tpl.TaskDiagramProblem";

	private String markerType;
	private IResource resource;

	private int infoCount;
	private int warningCount;
	private int errorCount;

	public MarkerLogger(IResource res, String markerType) {
		this.resource = res;
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
		this.markerType = markerType;
	}

	public void resetLogCount() {
		this.infoCount = 0;
		this.warningCount = 0;
		this.errorCount = 0;
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
		if(addMarker(IMarker.SEVERITY_INFO, message, lineNum, charStart, charEnd)) {
			infoCount += 1;
		}
	}

	public void warning(String message, int lineNum, int charStart, int charEnd) {
		if(addMarker(IMarker.SEVERITY_WARNING, message, lineNum, charStart, charEnd)) {
			warningCount += 1;
		}
	}

	public void error(String message, int lineNum, int charStart, int charEnd) {
		if(addMarker(IMarker.SEVERITY_ERROR, message, lineNum, charStart, charEnd)){
			logger.debug(message);
			errorCount += 1;
		}
	}

	private boolean addMarker(final int severity, final String message, final int lineNum, final int charStart, final int charEnd){
		if(resource == null) {	// KJH 20110809
			return false;
		}
		IWorkspaceRunnable r= new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker= resource.createMarker(markerType);

				marker.setAttribute(IMarker.MESSAGE, message);
				marker.setAttribute(IMarker.SEVERITY, severity);
				marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
				marker.setAttribute(IMarker.LOCATION, String.format("line %d", lineNum));
		
				MarkerUtilities.setLineNumber(marker, lineNum);
				MarkerUtilities.setCharStart(marker, charStart);
				MarkerUtilities.setCharEnd(marker, charEnd);

				marker.setAttribute(IMarker.DONE, true);
			}
		};

		try {
			resource.getWorkspace().run(r, null,IWorkspace.AVOID_UPDATE, null);
		}
		catch(CoreException ce) {
			return false;
		}
		
		return true;
	}

	public static void clearProblem(IResource res, String markerType) {
		if(res == null) {
			return;
		}
		
		try {
			res.deleteMarkers(markerType, false, IResource.DEPTH_INFINITE);
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

		MarkerLogger.clearProblem(resource, markerType);
	}
}
