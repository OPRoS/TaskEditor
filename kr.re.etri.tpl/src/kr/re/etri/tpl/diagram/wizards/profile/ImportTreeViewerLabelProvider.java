package kr.re.etri.tpl.diagram.wizards.profile;

import kr.re.etri.tpl.diagram.util.TreeItem;
import kr.re.etri.tpl.diagram.wizards.profile.ImportComponentProfilePage.ImportInfo;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ImportTreeViewerLabelProvider extends LabelProvider implements ITableLabelProvider{
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof TreeItem) {
			ImportInfo info = (ImportInfo)((TreeItem)element).getData();
			switch (columnIndex) {
			case 0:
				return info.getSourcefile().getName();
			case 1:
				return info.getTargetname();
			case 2:
				return Boolean.toString(info.isCreateTpd());
			case 3:
				return Boolean.toString(info.isCreateTpl());
			}
		}
		return null;
	}

}
