package kr.re.etri.tpl.diagram.views;

import kr.re.etri.tpl.diagram.providers.RobotNavigatorContentProvider;
import kr.re.etri.tpl.diagram.providers.RobotNavigatorLabelProvider;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class RobotNavigationViewPart extends ViewPart {

	public static final String viewId = "kr.re.etri.tpl.diagram.views.RobotNavigationViewPart";
	private TreeViewer viewer;

	public RobotNavigationViewPart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		viewer.setContentProvider(new RobotNavigatorContentProvider(getViewSite()));
		viewer.setLabelProvider(new RobotNavigatorLabelProvider());
//		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());


		// Create the help context id for the viewer's control
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "kr.re.etri.tpl.views.RobotNavigationViewPart.viewer");
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
