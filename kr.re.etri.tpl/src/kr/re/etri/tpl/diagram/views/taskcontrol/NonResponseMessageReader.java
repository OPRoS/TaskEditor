package kr.re.etri.tpl.diagram.views.taskcontrol;

import kr.re.etri.tpl.MessageListener;

public class NonResponseMessageReader implements MessageListener {

	private boolean isValid;
	
	@Override
	public void update(String message) {
		isValid = true;
		if (message.startsWith("RES")) {
			isValid = false;
		}
	}

	protected boolean isValid() {
		return isValid;
	}
	
}
