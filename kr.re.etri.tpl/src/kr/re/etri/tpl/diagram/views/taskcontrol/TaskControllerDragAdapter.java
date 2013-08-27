package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;

public class TaskControllerDragAdapter extends DragSourceAdapter {
	private static final Transfer[] SUPPORTED_DRAG_TRANSFERS = new Transfer[] {
		LocalSelectionTransfer.getTransfer()
	};

	StructuredViewer viewer;
	
	public TaskControllerDragAdapter(StructuredViewer viewer) {
		super();
		this.viewer = viewer;
	}
	
	public Transfer[] getSupportedDragTransfers() {
		return SUPPORTED_DRAG_TRANSFERS;
	}
	
	@Override
	public void dragFinished(DragSourceEvent event) {
		LocalSelectionTransfer.getTransfer().setSelection(null);
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.dataType)) {
			event.data = selection;
		} else {
			event.doit = false;
		}
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		DragSource dragSource = (DragSource)event.widget;
		Control control = dragSource.getControl();
		if (control == control.getDisplay().getFocusControl()) {
			ISelection selection = viewer.getSelection();
			if (!selection.isEmpty()) {
				LocalSelectionTransfer.getTransfer().setSelection(selection);
				event.doit = true;
			} else {
				event.doit = false;
			}
		} else {
			event.doit = false;
		}
	}
	
	
}
