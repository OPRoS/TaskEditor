package kr.re.etri.tpl.diagram.monitoring;

import kr.re.etri.tpl.MessageListener;

import org.apache.log4j.Logger;

public class MessageReader implements MessageListener {
	private static Logger m_logger = Logger.getLogger(MessageReader.class);
	@Override
	public void update(String message) {
		m_logger.info(message);
	}
}
