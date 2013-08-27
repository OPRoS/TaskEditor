package kr.re.etri.tpl.diagram.monitoring.log;


import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class MonitoringLogLabelProvider extends LabelProvider implements ITableLabelProvider{
	private static Logger logger = Logger.getLogger(MonitoringLogLabelProvider.class);
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(!(element instanceof MonitoringLog)){
			logger.warn("Element is not MonitoringLog");
			return null;
		}
		MonitoringLog log = (MonitoringLog)element;
		switch (columnIndex) {
		case 0:
			logger.info(log.getNumber());
			return log.getNumber();
		case 1:
			logger.info(log.getTime());
			return log.getTime();
		case 2:
			logger.info(log.getTarget());
			return log.getTarget();
		case 3:
			logger.info(log.getData());
			return log.getData();
		case 4:
			logger.info(log.getName());
			return log.getName();
		case 5:
			logger.info(log.getType());
			return log.getType();
		case 6:
			logger.info(log.getValue());
			return log.getValue();
		default:
			logger.error("Unsuppored column index.");
			return "";
		}
	}
}
