package kr.re.etri.tpl.diagram.wizards.profile;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.jdom.Element;

public class ExportTableViewerContentProvider2 implements
		IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Element) {
			return ((Element)inputElement).getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
