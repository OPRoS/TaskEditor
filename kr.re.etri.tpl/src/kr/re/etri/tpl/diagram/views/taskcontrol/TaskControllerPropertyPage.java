package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class TaskControllerPropertyPage extends Page implements IPropertySheetPage, IAdaptable {
	
	private TaskControllerPropertyViewer viewer;

	@Override
	public void createControl(Composite parent) {
		viewer = new TaskControllerPropertyViewer();
	}

	@Override
	public Control getControl() {
		if (viewer == null)
			return null;
		return viewer.getControl();
	}

	@Override
	public void setFocus() {
		Control ctrl = viewer.getControl();
		if(ctrl != null) {
			ctrl.setFocus();
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
