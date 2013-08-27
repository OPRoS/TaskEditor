package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

/*
 * KJH 20101109, new class
 */
public class TreeCellModifier implements ICellModifier {

	private TreeViewer viewer;
	private boolean enabled; 
	

	public TreeViewer getViewer() {
		return viewer;
	}
	
	public void setViewer(TreeViewer viewer) {
		this.viewer = viewer;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public boolean canModify(Object element, String property) {
		if (!enabled) {
			return false;
		}
		
		if (element instanceof TreeItem2) {
			TreeItem2 item = (TreeItem2)element;
			if (item.isContainer() && "/".equals(item.getText()) == false) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object getValue(Object element, String property) {
//		if (element instanceof TreeItem2) {
//			TreeItem2 item = (TreeItem2)element;
//			return item.getText();
//		}
		return element.toString();
	}

	@Override
	public void modify(Object element, String property, Object value) {
		if (!(value instanceof String) || ((String)value).equals(""))
			return;
		
		if (element instanceof TreeItem) {
			((TreeItem)element).setText((String)value);
			Object data = ((TreeItem)element).getData();
			if (data instanceof TreeItem2) {
				((TreeItem2)data).setText((String)value);
			}
		}
	}

}
