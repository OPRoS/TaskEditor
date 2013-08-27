package kr.re.etri.tpl.diagram.wizards.profile;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.jdom.Attribute;
import org.jdom.Element;

public class ExportTreeViewerLabelProvider2 extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (element instanceof Element) {
				return ((Element)element).getName();
			}
			else if (element instanceof Attribute) {
				return ((Attribute)element).getName();
			}
			break;
		case 1:
			if (element instanceof Element) {
				return ((Element)element).getTextTrim();
			}
			else if (element instanceof Attribute) {
				return ((Attribute)element).getValue();
			}
			break;
		}
		return "";
	}

}
