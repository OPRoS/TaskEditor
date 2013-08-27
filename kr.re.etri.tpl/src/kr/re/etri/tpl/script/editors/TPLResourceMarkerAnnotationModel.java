package kr.re.etri.tpl.script.editors;

import kr.re.etri.tpl.script.debug.TPLBreakpoint;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

/**
 * @see TPLDocumentProvider.createAnnotationModel(Object element)
 */
public class TPLResourceMarkerAnnotationModel extends
		ResourceMarkerAnnotationModel {

	public TPLResourceMarkerAnnotationModel(IResource resource) {
		super(resource);
	}

	@Override
	protected IMarker[] retrieveMarkers() throws CoreException {
		return getResource().findMarkers(TPLBreakpoint.MARKER_TYPE, true, IResource.DEPTH_ZERO);
	}

}
