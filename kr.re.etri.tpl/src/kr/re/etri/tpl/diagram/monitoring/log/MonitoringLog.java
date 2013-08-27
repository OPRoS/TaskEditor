package kr.re.etri.tpl.diagram.monitoring.log;

public class MonitoringLog {
	private String m_number;
	private String m_time;
	private String m_target;
	private String m_data;
	private String m_name;
	private String m_type;
	private String m_value;
	
	public MonitoringLog(String number, String time, String target, String data, String name , String type, String value){
		m_number = number;
		m_time = time;
		m_target = target;
		m_data = data;
		m_name = name;
		m_type = type;
		m_value = value;
	}

	public String getNumber(){
		return m_number;
	}
	public String getTime(){
		return m_time;
	}
	public String getTarget(){
		return m_target;
	}
	public String getData(){
		return m_data;
	}
	public String getName() {
		return m_name;
	}

	public String getType() {
		return m_type;
	}

	public String getValue() {
		return m_value;
	}
}
