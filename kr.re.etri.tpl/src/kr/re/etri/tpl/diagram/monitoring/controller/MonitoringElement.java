package kr.re.etri.tpl.diagram.monitoring.controller;

import org.apache.log4j.Logger;

public class MonitoringElement {
	private static Logger logger = Logger.getLogger(MonitoringElement.class);
	private Object element;
	private boolean checked=false;
	public MonitoringElement(Object element){
		this.element= element;
	}
	
	public void setChecked(boolean b){
		checked = b;
	}

	public boolean getChecked(){
		return checked;
	}
	
	public Object getElement(){
		return element;
	}
	
	public boolean equals(Object o){
		logger.debug("Test equals : "+o);
		if(!(o instanceof MonitoringElement)){
			return false;
		}
		Object oo = ((MonitoringElement)o).getElement();
		if(element == oo){
			return true;
		}
		return false;
	}
	
}
