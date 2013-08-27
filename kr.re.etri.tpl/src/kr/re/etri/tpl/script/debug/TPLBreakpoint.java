package kr.re.etri.tpl.script.debug;

import java.util.Map;

import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;

public class TPLBreakpoint extends LineBreakpoint implements ILineBreakpoint {

	public static final String MARKER_TYPE = "kr.re.etri.tpl.debug.tplBreakpointMarker";
	public static final String SCRIPT_INFO = "kr.re.etri.tpl.script.debug.scriptInfo";
	private IScriptNode info;
	
	public TPLBreakpoint() {
		// KJH 20110215, !기본클래스 생성을 위해 꼭 필요함
	}
	
	public TPLBreakpoint(final IResource resource, final int lineNumber, final IScriptNode info, final Map attributes) throws DebugException {
		IWorkspaceRunnable wr = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				setMarker(resource.createMarker(/*JavaLineBreakpoint.getMarkerType()*/
						MARKER_TYPE));
				
				attributes.put(IBreakpoint.ID, getModelIdentifier());
				attributes.put(IBreakpoint.PERSISTED, Boolean.TRUE);
				attributes.put(IBreakpoint.ENABLED, Boolean.TRUE);
				attributes.put(IMarker.LINE_NUMBER, lineNumber);
				if (info != null) {	// KJH 20110524, 조건 추가
					attributes.put(SCRIPT_INFO, info.getStart());
				}
				
				ensureMarker().setAttributes(attributes);
			}
		}; 
		run(getMarkerRule(resource), wr);
		this.info = info;
	}
	
	@Override
	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		configureAtStartup();
	}

	@Override
	public void setEnabled(boolean enabled) throws CoreException {
		// KJH 20110302 s, breakpoint enable/disable
		super.setEnabled(enabled);
		DebugManager dManager = DebugManager.getDefault();
		if (dManager.isRunning()) {
			IResource resource = getMarker().getResource();
			// KJH 20110315 s, 현재 디버깅중인 프로젝트와 다를 경우 breakpoint를 전송하지 않음
			IProject project = resource.getProject();
			if (project != null && project.equals(dManager.getCurProject())) {
				String file = resource.getFullPath().removeFirstSegments(1).toString();
				if (enabled) {
					dManager.registry(null, file, getInfo().getFullPath(), getLineNumber());
				} else {
					dManager.remove(null, file, getInfo().getFullPath(), getLineNumber());
				}
			}
			// KJH 20110315 e, 현재 디버깅중인 프로젝트와 다를 경우 breakpoint를 전송하지 않음
		}
		// KJH 20110302 e, breakpoint enable/disable
	}

	@Override
	public String getModelIdentifier() {
		return "kr.re.etri.tpl";
//		return MARKER_TYPE;
	}
	
	// KJH 20110216 s,
	private void configureAtStartup() throws CoreException {
		IMarker marker = getMarker();
		int offset = marker.getAttribute(SCRIPT_INFO, -1);
		
		if (offset > -1) {
			IResource resource = marker.getResource();
			if (resource instanceof IFile) {
				IFile file = (IFile)resource;
				IScriptNode root = ScriptManager.getInstance().getTree(file, null);
				IScriptNode info = root.getChildByOffset(offset);
				
				this.info = info;
			}
		}
	}
	// KJH 20110216 e,

	public IScriptNode getInfo() {
		return info;
	}

}
