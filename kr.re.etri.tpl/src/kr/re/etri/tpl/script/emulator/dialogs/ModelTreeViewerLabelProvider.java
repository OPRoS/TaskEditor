package kr.re.etri.tpl.script.emulator.dialogs;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class ModelTreeViewerLabelProvider extends LabelProvider
		implements ITableLabelProvider, ITableColorProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof EmulatorTreeItem == false) {
			return null;
		}
		
//		EmulatorTreeItem item = (EmulatorTreeItem)element;
//		switch (columnIndex) {
//		case 0:
//			if (item.getItemType() == EmulatorTreeItem.MODEL) {
//				return TaskModelPlugin.getImageDescriptor(IconStrings.MODEL_16).createImage();
//			}
//			if (item.getItemType() == EmulatorTreeItem.SYMBOL) {
//				return TaskModelPlugin.getImageDescriptor(IconStrings.INVAR_16).createImage();
//			}
//		}
		
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof EmulatorTreeItem == false) {
			return null;
		}
		
		EmulatorTreeItem item = (EmulatorTreeItem)element;
		switch (columnIndex) {
		case 0:	// name
			return item.getName();
		case 1:	// type
			return item.getType();
		case 2:	// etype
			return item.getEtype();
		case 3:	// value
			return item.getValue();
		case 4:	// min
			return item.getMin();
		case 5:	// max
			return item.getMax();
		case 6:	// enum
			return item.getEnumm();
		}
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

}
