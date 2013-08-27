package kr.re.etri.tpl.diagram.providers;

import kr.re.etri.tpl.diagram.util.TreeItem;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

public class ActionNavigatorContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {
	
	public ActionNavigatorContentProvider()
	{
	}
	@Override
	public Object[] getElements(Object inputElement) {
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement == null) {
			return null;
		}

		if(parentElement instanceof TreeItem) {
			TreeItem item = (TreeItem)parentElement;
			return item.getChildren();
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element == null) {
			return null;
		}

		if(element instanceof TreeItem) {
			TreeItem item = (TreeItem)element;
			return item.getParent();
		}

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element == null) {
			return false;
		}

		if(element instanceof TreeItem) {
			TreeItem item = (TreeItem)element;
			return item.hasChildren();
		}

		return false;
	}

}
