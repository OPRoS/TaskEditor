package kr.re.etri.tpl.diagram.views;

import kr.re.etri.tpl.diagram.providers.ActionNavigatorContentProvider;
import kr.re.etri.tpl.diagram.providers.ActionNavigatorLabelProvider;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.navigator.CommonNavigator;

public class DiagramNavigationViewPart extends CommonNavigator {

	public static final String viewId = "kr.re.etri.tpl.views.DiagramNavigationViewPart";
	private TreeViewer viewer;

	public DiagramNavigationViewPart() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Viewer 가 생성되어 초기화 될때 호출된다.
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
/*
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		viewer.setContentProvider(new BehaviorNavigationContentProvider(getViewSite()));
		viewer.setLabelProvider(new BehaviorNavigationLabelProvider());
//		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
*/

		// Create the help context id for the viewer's control
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "kr.re.etri.tpl.views.BehaviorNavigationViewPart.viewer");
//		makeActions();
//		hookContextMenu();
//		hookDoubleClickAction();
//		contributeToActionBars();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
