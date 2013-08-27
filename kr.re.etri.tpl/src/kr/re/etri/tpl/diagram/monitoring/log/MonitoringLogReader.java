package kr.re.etri.tpl.diagram.monitoring.log;

import java.util.StringTokenizer;

import kr.re.etri.tpl.MessageListener;

import org.apache.log4j.Logger;


public class MonitoringLogReader implements MessageListener{
	private static Logger m_logger = Logger.getLogger(MonitoringLogReader.class);
	
	private MonitoringLogView m_viewer;

	public MonitoringLogReader(MonitoringLogView viewer) {
		m_viewer = viewer;
	}

	public void update(String message) {
		m_logger.info("UPDATE : "+message);
		if(!message.startsWith("RES")){
			return;
		}
		final MonitoringLog log = makeMonitoringLog(message);
		if(log!= null){
			m_viewer.getViewSite().getShell().getDisplay().syncExec(new Runnable(){
				@Override
				public void run() {
					m_viewer.add(log);					
				}		
			});
		}		
	}

	private static String DELIMITER1 = ";";
	private static String DELIMITER2 = ":";
	private static String DELIMITER3 = "@";

	private static String HEAD ="HEAD";
	private static String NUM ="num";
	private static String TIME ="time";
	private static String TARGET ="target";
	private static String DATA = "DATA";
	private static String ATTR = "ATTR";
	private static String NAME = "name";
	private static String TYPE = "type";
	private static String VALUE = "value";
	
	
	public MonitoringLog makeMonitoringLog(String message) {
		StringTokenizer st = new StringTokenizer(message, DELIMITER1);
		String num ="";
		String time = "";
		String target ="";
		String data = "";
		String name = "";
		String type = "";
		String value = "";
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if(token.startsWith(HEAD)){
				StringTokenizer st2 = new StringTokenizer(token, DELIMITER2);
				if (st2.countTokens() == 2) {
					st2.nextToken();
					String attributes = st2.nextToken();
					StringTokenizer st3 = new StringTokenizer(attributes,
							DELIMITER3);
					while (st3.hasMoreTokens()) {
						String t = st3.nextToken();
						int start = t.indexOf('[');
						int end = t.lastIndexOf(']');
						String v = t.substring(start+1,end);
						
						if(t.startsWith(NUM)){
							num = v;
						}else if(t.startsWith(TIME)){							
							time = v;
						}
						else if(t.startsWith(TARGET)){
							target = v;
						}
						else{
							m_logger.warn("Unexpected value : "+t);
						}
					}
				}
				else{
					m_logger.warn("HEAD의 토큰 개수가  2가 아닙니다.");
				}
				
			}else if (token.startsWith(DATA)) {
				StringTokenizer st2 = new StringTokenizer(token, DELIMITER2);
				if (st2.countTokens() == 2) {
					st2.nextToken();
					data = st2.nextToken();
				}
				else{
					m_logger.warn("HEAD의 토큰 개수가  2가 아닙니다.");
				}
			} else if (token.startsWith(ATTR)) {
				StringTokenizer st2 = new StringTokenizer(token, DELIMITER2);
				if (st2.countTokens() == 2) {
					st2.nextToken();
					String attributes = st2.nextToken();
					StringTokenizer st3 = new StringTokenizer(attributes,
							DELIMITER3);
					
					while (st3.hasMoreTokens()) {
						String t = st3.nextToken();
						int start = t.indexOf('[');
						int end = t.lastIndexOf(']');
						String v = t.substring(start+1,end);
						if(t.startsWith(NAME)){
							name = v;
						}
						else if(t.startsWith(TYPE)){
							type = v;
						}
						else if(t.startsWith(VALUE)){
							value = v;
						}
						else{
							m_logger.warn("Unexpected value : "+t);
						}
					}
				}
				else{
					m_logger.warn("HEAD의 토큰 개수가  2가 아닙니다.");
				}
			}
		}

		return new MonitoringLog(num, time, target,data,name,type,value);
	}
}
