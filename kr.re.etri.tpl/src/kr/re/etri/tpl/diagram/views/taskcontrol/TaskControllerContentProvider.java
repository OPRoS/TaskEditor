package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TaskControllerContentProvider implements ITreeContentProvider {
	TaskControlManager manager = TaskControlManager.getDefault();
	
	public TaskControllerContentProvider() {
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof TreeItem2) {
			TreeItem2 item = (TreeItem2)parentElement;
//			return manager.dir(item);
			return item.getChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof TreeItem2) {
			return ((TreeItem2)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof TreeItem2) {
//			return manager.hasChildren((TreeItem2)element);
			return ((TreeItem2)element).hasChildren();
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
