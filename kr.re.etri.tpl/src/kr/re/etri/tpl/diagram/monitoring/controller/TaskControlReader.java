package kr.re.etri.tpl.diagram.monitoring.controller;

import org.apache.log4j.Logger;

import kr.re.etri.tpl.MessageListener;

public class TaskControlReader implements MessageListener{
	private static Logger m_logger = Logger.getLogger(TaskControlReader.class);
	private TaskController m_controller;

	public TaskControlReader(TaskController controller) {
		m_controller = controller;
	}
	@Override
	public void update(final String message) {
		if(m_controller == null){
			m_logger.warn("Controller is null.");
			return;
		}
		m_controller.getDisplay().asyncExec(new Runnable(){
			@Override
			public void run() {
				String s = m_controller.getResultsText().getText();
				m_controller.getResultsText().setText(s+"\n"+message);				
			}
		});	
	}
}
