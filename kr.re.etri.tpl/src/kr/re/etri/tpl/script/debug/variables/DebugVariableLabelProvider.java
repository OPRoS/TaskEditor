package kr.re.etri.tpl.script.debug.variables;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class DebugVariableLabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Variable) {
			Variable var = (Variable)element;

			switch(columnIndex){
			case 0:
				return var.getLine();
			case 1:
				return var.getName();
			case 2:
				return String.format("%s[%s]", var.getValue(), var.getType());
			case 3:
				return var.getFile();
			}
		}
		return "";
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

}
