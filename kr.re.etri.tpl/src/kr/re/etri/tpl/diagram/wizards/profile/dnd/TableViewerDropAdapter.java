package kr.re.etri.tpl.diagram.wizards.profile.dnd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.jdom.Element;

public class TableViewerDropAdapter extends ViewerDropAdapter {

	private static final Transfer[] SUPPORTED_DROP_TRANSFERS = new Transfer[] {
		PropertyTransfer.getTransfer(),
	};
	
	public TableViewerDropAdapter(StructuredViewer viewer) {
		super(viewer);
	}

	public Transfer[] getSupportedDropTransfers() {
		return SUPPORTED_DROP_TRANSFERS;
	}
	
	public TableViewer getViewer() {
		return (TableViewer)super.getViewer();
	}
	
	@Override
	public boolean performDrop(Object data) {
		DropTargetEvent event = getCurrentEvent();
		boolean result = false;
		if (PropertyTransfer.getTransfer().isSupportedType(event.currentDataType)) {
			if (data instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection)data;
				Object[] elements = selection.toArray();
				List<Element> moveList = new ArrayList<Element>();
				for (Object element : elements) {
					if (element instanceof Element) {
						moveList.add((Element)element);
					}
				}
				
				Object target = getCurrentTarget();
				if (target instanceof Element) {
					Element parent = ((Element)target).getParentElement();
					int index = parent.indexOf((Element)target);
					
					for (Element element : moveList) {
						Element parentElement = element.getParentElement();
						parentElement.removeContent(element);
					}

					int index2 = parent.indexOf((Element)target);
					if (index - index2 > 1) {
						index = index2 + 1;
					}
					
					if (index > -1 && index < parent.getContentSize()) {
						parent.addContent(index, moveList);
					} else {
						parent.addContent(moveList);
					}
					
					result = true;
				}
			}
		}
		
		if (result) {
			getViewer().refresh();
		}

		return result;
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		boolean result = false;
		
		if (target instanceof Element == false) {
			return false;
		}
		
		if (PropertyTransfer.getTransfer().isSupportedType(transferType)) {
			IStructuredSelection selection = (IStructuredSelection)
					PropertyTransfer.getTransfer().getSelection();
			Object[] elements = selection.toArray();
			
			for (Object element : elements) {
				if (element instanceof Element) {
					return true;
				}
			}
			result = true;
		}
		
		return result;
	}

}
