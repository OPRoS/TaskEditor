package kr.re.etri.tpl.diagram.wizards.profile;

import kr.re.etri.tpl.diagram.util.component.ProfileStrings;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.Element;

public class ExportTableViewerLabelProvider2 extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Element) {
			Element ele = (Element)element;
			switch (columnIndex) {
			case 0:
				return ele.getAttributeValue(ProfileStrings.NAME);
			case 1:
				return ele.getText();
			case 2:
				if (ele.getParentElement() != null) {
					Element parent = ele.getParentElement();
					int index = parent.indexOf(ele);
					if (index > -1) {
						for (int i = index + 1; i < parent.getContentSize(); i++) {
							Content content = parent.getContent(i);
							if (content instanceof Comment) {
								return ((Comment) content).getText();
							}
							else if (content instanceof Element) {
								break;
							}
						}
					}
					return "";
				}
				break;
			}
		}
		return null;
	}

}
