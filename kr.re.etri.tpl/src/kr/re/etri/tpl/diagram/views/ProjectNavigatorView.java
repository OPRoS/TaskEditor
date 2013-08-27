package kr.re.etri.tpl.diagram.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

public class ProjectNavigatorView extends CommonNavigator {

	public static final String VIEW_ID = "kr.re.etri.tpl.diagram.views.ProjectNavigatorView";
	
	public ProjectNavigatorView(){
//		DownloadMessageReader.getDefault().
	}

	public Object getAdapter(Class adapter) {
		if(adapter.isInstance(this)) {
			return this;
		}
		return super.getAdapter(adapter);
	}

	@Override
	protected CommonViewer createCommonViewerObject(Composite aParent) {
		return new ProjectCommonViewer(getViewSite().getId(), aParent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	}
}
