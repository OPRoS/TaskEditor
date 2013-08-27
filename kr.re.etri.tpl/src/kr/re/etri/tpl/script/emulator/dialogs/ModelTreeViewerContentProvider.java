package kr.re.etri.tpl.script.emulator.dialogs;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ModelTreeViewerContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof EmulatorTreeItem) {
			return ((EmulatorTreeItem)parentElement).getChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof EmulatorTreeItem) {
			((EmulatorTreeItem)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof EmulatorTreeItem) {
			return ((EmulatorTreeItem)element).hasChildren();
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EmulatorTreeItem) {
			return getChildren(inputElement);
		}
		return new Object[]{inputElement};
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
