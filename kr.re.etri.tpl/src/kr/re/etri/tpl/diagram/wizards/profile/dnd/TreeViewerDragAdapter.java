package kr.re.etri.tpl.diagram.wizards.profile.dnd;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
/**
 * Supports dragging gadgets from a structured viewer.
 */
public class TreeViewerDragAdapter extends DragSourceAdapter {
	
	private static final Transfer[] SUPPORTED_DRAG_TRANSFERS = new Transfer[] {
		ProfileTransfer.getTransfer(),
	};
	
	private StructuredViewer viewer;
	
	public TreeViewerDragAdapter(StructuredViewer viewer) {
		this.viewer = viewer;
	}
	
	public Transfer[] getSupportedDragTransfers() {
		return SUPPORTED_DRAG_TRANSFERS;
	}
	
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragFinished(DragSourceEvent event) {
		ProfileTransfer.getTransfer().setSelection(null);
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragSetData(DragSourceEvent event) {
		ISelection selection = ProfileTransfer.getTransfer().getSelection();
		if (ProfileTransfer.getTransfer().isSupportedType(event.dataType)) {
			event.data = selection;
		}
		else {
			event.doit = false;
		}
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragStart(DragSourceEvent event) {
		ISelection selection = viewer.getSelection();
		if (!selection.isEmpty()) {
			ProfileTransfer.getTransfer().setSelection(selection);
			event.doit = true;
		} else {
			event.doit = false;
		}
	}
}