package kr.re.etri.tpl.diagram.wizards.profile;

import kr.re.etri.tpl.diagram.util.TreeItem;
import kr.re.etri.tpl.diagram.wizards.profile.ExportComponentProfilePage.ExportInfo;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ExportTreeViewerLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof TreeItem) {
			TreeItem treeItem = (TreeItem)element;
			Object data = treeItem.getData();
			switch (columnIndex) {
			case 0:
				return treeItem.getText();
			case 1:
				if (data instanceof ExportInfo) {
					return ((ExportInfo)data).getTargetname();
				}
				return "";
			}
		}
		return null;
	}

}
