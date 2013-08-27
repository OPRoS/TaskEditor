package kr.re.etri.tpl.diagram.views.taskcontrol;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

public class TaskControllerLabelProvider extends LabelProvider implements TreeListener {

	private TreeViewer viewer;
	
	private static Image NODE_EXPANDED;
	private static Image NODE_COLLAPSED;
	
	public TaskControllerLabelProvider(TreeViewer viewer) {
		this.viewer = viewer;
		
		NODE_EXPANDED = TaskModelPlugin.getImageDescriptor(IconStrings.OPENPACK_16).createImage();
		NODE_COLLAPSED = TaskModelPlugin.getImageDescriptor(IconStrings.CLOSEPACK_16).createImage();
	}
	
	
	@Override
	public Image getImage(Object element) {
		if (element instanceof TreeItem2) {
			TreeItem2 item = (TreeItem2)element;
			boolean isExpanded = viewer.getExpandedState(item);
			if (item.isContainer()) {
				if (isExpanded) {
					return NODE_EXPANDED;
				}
				else {
					return NODE_COLLAPSED;
				}
			}
			else {
				return TaskModelPlugin.getImageDescriptor(IconStrings.RTS_16).createImage();
			}
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TreeItem2) {
			TreeItem2 item = (TreeItem2)element;
			return item.getText();
		}
		return super.getText(element);
	}


	@Override
	public void treeCollapsed(TreeEvent e) {
		updateImage((TreeItem)e.item, true);
	}


	@Override
	public void treeExpanded(TreeEvent e) {
		updateImage((TreeItem)e.item, false);
	}

	private void updateImage(TreeItem item, boolean isCollapsed) {
		Image image = isCollapsed ? NODE_COLLAPSED : NODE_EXPANDED;
		item.setImage(image);
	}

//	@Override
//	public void update(ViewerCell cell) {
//		Object element = cell.getElement();
//		StyledString text = new StyledString();
//		Widget widget = cell.getItem();
//		boolean isExpanded = false;
//		Image image = null;
//		
//		if (widget instanceof TreeItem) {
//			isExpanded = ((TreeItem)widget).getExpanded();
//		}
//		
//		if (element instanceof TreeItem2) {
//			TreeItem2 item = (TreeItem2)element;
//			if (item.isContainer()) {
//				int childCount = 0;
//				for (Object child : item.getChildren()) {
//					if (child instanceof TreeItem2 &&
//							((TreeItem2)child).isContainer() == false) {
//						childCount++;
//					}
//				}
//				text.append(item.getText());
//				text.append(" (" + childCount + ")", StyledString.COUNTER_STYLER);
//				if (isExpanded) {
//					image = TaskModelPlugin.getImageDescriptor(IconStrings.OPENPACK_16).createImage();
//				}
//				else {
//					image = TaskModelPlugin.getImageDescriptor(IconStrings.CLOSEPACK_16).createImage();
//				}
//			}
//			else {
//				text.append(item.getText());
//				image = TaskModelPlugin.getImageDescriptor(IconStrings.RTS_16).createImage();
//			}
//		}
//		
//		cell.setText(text.toString());
//		cell.setStyleRanges(text.getStyleRanges());
//		cell.setImage(image);
//		
//		super.update(cell);
//	}

}
