package kr.re.etri.tpl.diagram.monitoring.controller;


import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

public class MonitoringCellModifier implements ICellModifier {
	private static Logger logger = Logger.getLogger(MonitoringCellModifier.class);
	private TableViewer viewer;
	
	public MonitoringCellModifier(TableViewer viewer){
		this.viewer = viewer;
	}
	@Override
	public boolean canModify(Object element, String property) {
		if(property.equals(MonitoringControlView.CHECKED_COLUMN)){
			return true;			
		}
		return false;
	}

	@Override
	public Object getValue(Object element, String property) {
		logger.debug(element+" : " + property);
		if(property.equals(MonitoringControlView.CHECKED_COLUMN)){
			boolean checked = ((MonitoringElement)element).getChecked();
			return checked;			
		}
		return null;
	}

	@Override
	public void modify(Object element, String property, Object value) {
		if(property.equals(MonitoringControlView.CHECKED_COLUMN)){
			TableItem item = (TableItem) element;
			logger.debug(item.getData()+" : " + property+ " : "+value);
			
			((MonitoringElement)item.getData()).setChecked((Boolean)value);
			viewer.refresh();
				
		}
	}

}
