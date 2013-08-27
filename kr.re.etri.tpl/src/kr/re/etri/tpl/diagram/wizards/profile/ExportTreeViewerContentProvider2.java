package kr.re.etri.tpl.diagram.wizards.profile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.jdom.Attribute;
import org.jdom.Element;

public class ExportTreeViewerContentProvider2 implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		List children = new ArrayList();
		if (parentElement instanceof Element) {
			children.addAll(((Element)parentElement).getAttributes());
			children.addAll(((Element)parentElement).getChildren());
		}
		return children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Element) {
			return ((Element)element).getParent();
		}
		else if (element instanceof Attribute) {
			return ((Attribute)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Element) {
			return ((Element)element).getChildren().size() > 0 ||
					((Element)element).getAttributes().size() > 0;
		}
		else if (element instanceof Attribute) {
			return false;
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
