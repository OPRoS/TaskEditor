package kr.re.etri.tpl.script.debug;

import kr.re.etri.tpl.MessageListener;

public class EOTMessageReader implements MessageListener {

	public EOTMessageReader() {
	}
	
	@Override
	public void update(String message) {
		if (message.equals("RES-EOT")) {
			DebugManager.getDefault().reset();
		}
	}
}
