package kr.re.etri.tpl.diagram.wizards.profile.dnd;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.filter.ContentFilter;
/**
 * Supports dropping gadgets into a tree viewer.
 */
public class TreeViewerDropAdapter extends ViewerDropAdapter {
	
	private static final Transfer[] SUPPORTED_DROP_TRANSFERS = new Transfer[] {
		ProfileTransfer.getTransfer(),
	};
	
	public TreeViewerDropAdapter(StructuredViewer viewer) {
		super(viewer);
	}
	
	public Transfer[] getSupportedDropTransfers() {
		return SUPPORTED_DROP_TRANSFERS;
	}
	
	public TreeViewer getViewer() {
		return (TreeViewer)super.getViewer();
	}
	
	/**
	 * Method declared on ViewerDropAdapter
	 */
	public boolean performDrop(Object data) {
		DropTargetEvent event = getCurrentEvent();
		boolean result = false;
		if (ProfileTransfer.getTransfer().isSupportedType(event.currentDataType)) {
			if (data instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection)data;
				Object[] elements = selection.toArray();

				Object target = getCurrentTarget();

				int currentLocation = getCurrentLocation();
				Element newParent = null;
				int index = -1;
				
				if (target instanceof Element) {
					newParent = (Element)target;
					if (currentLocation == LOCATION_BEFORE) {
						newParent = newParent.getParentElement();
						index = newParent.indexOf((Element)target);
					}
					else if (currentLocation == LOCATION_AFTER) {
						newParent = newParent.getParentElement();
						index = newParent.indexOf((Element)target) + 1;
					}
				}
				else if (target instanceof Attribute) {
					newParent = ((Attribute)target).getParent();
					if (currentLocation == LOCATION_BEFORE) {
						index = newParent.getAttributes().indexOf(target);
					}
					else if (currentLocation == LOCATION_AFTER) {
						index = newParent.getAttributes().indexOf(target) + 1;
					}
				}
				
				if (newParent == null) {
					return false;
				}
				
				for (Object element : elements) {
					if (element instanceof Element) {
						Element ele = (Element)element;
						Element oldParent = ele.getParentElement();
						if (oldParent != null) {
							oldParent.removeContent(ele);
						}
						
						// target이 Attribute인 경우, Element는 Attribute의 바로 아래 위치(index = 0)
						if (target instanceof Attribute) {
							index = 0;
						}
						
						int size = newParent.getContentSize();
						if (index > -1 && index < size) {
							newParent.addContent(index, ele);
						} else {
							newParent.addContent(ele);
						}
						result = true;
					}
					else if (element instanceof Attribute) {
						Attribute attr = (Attribute)element;
						Element oldParent = attr.getParent();
						if (oldParent != null) {
							oldParent.removeAttribute(attr);
						}
						
						int size = newParent.getAttributes().size();
						if (index > -1 && index < size) {
							newParent.getAttributes().add(index, attr);
						} else {
							newParent.setAttribute(attr);
						}
						result = true;
					}

					if (newParent.getChildren().size() > 0) {
						newParent.removeContent(new ContentFilter(ContentFilter.TEXT));
					}
				}
			}
		}
		
		if (result) {
			getViewer().refresh();
		}
		
		return result;
	}
	/**
	 * Method declared on ViewerDropAdapter
	 */
	public boolean validateDrop(Object target, int op, TransferData type) {
		boolean result = false;
		
		if (ProfileTransfer.getTransfer().isSupportedType(type)) {
			IStructuredSelection selection = (IStructuredSelection)
					ProfileTransfer.getTransfer().getSelection();
			Object[] elements = selection.toArray();
			
			if (target instanceof Element) {
				Element targetEle = (Element)target;
				
				for (Object element : elements) {
					if (targetEle.equals(element)) {
						return false;	// 선택된 노드 중 target이 있는 경우
					}
					
					if (element instanceof Element &&
							targetEle.equals(((Element)element).getParentElement())) {
						return false;	// target이 Element의 parent인 경우
					}
					if (element instanceof Attribute &&
							targetEle.equals(((Attribute)element).getParent())) {
						return false;	// target이 Attribute의 parent인 경우
					}
					
					Element parent = targetEle.getParentElement();
					while (parent != null) {
						if (parent.equals(element)) {
							return false;	// target이 선택된 노드의 자식일 경우
						}
						parent = parent.getParentElement();
					}
					
					result = true;
				}
			}
			else if (target instanceof Attribute) {
				Attribute targetAttr = (Attribute)target;
				
				for (Object element : elements) {
					if (targetAttr.equals(element)) {
						return false;	// 선택된 노드 중 target이 있는 경우
					}
					
					Element parent = targetAttr.getParent();
					if (parent == null) {
						continue;
					}
					
					if (element instanceof Element &&
							parent.equals(((Element)element).getParentElement())) {
						return false;	// target이 Element의 parent인 경우
					}
					if (element instanceof Attribute &&
							parent.equals(((Attribute)element).getParent())) {
						return false;	// target이 Attribute의 parent인 경우
					}
					
					while (parent != null) {
						if (parent.equals(element)) {
							return false;	// target이 선택된 노드의 자식일 경우
						}
						parent = parent.getParentElement();
					}
					
					result = true;
				}
			}

		}
		
		return result;
	}

}