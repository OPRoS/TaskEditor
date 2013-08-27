package kr.re.etri.tpl.script.debug.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import kr.re.etri.tpl.MessageListener;

public abstract class DebuggingMessageReader implements MessageListener {

	private boolean isValid;
	private String command;
	private Map<String, String> attributes = new HashMap<String, String>();
	
	@Override
	public void update(String message) {
		String[] tokens = message.split(";");
		for (int i=0; i<tokens.length; i++) {
			tokens[i] = tokens[i].trim();
		}
		
		if (tokens.length != 4) {
			isValid = false;
			return;
		}
		else if (!tokens[0].equals("RES-start")) {
			isValid = false;
			return;
		}
		else if (!tokens[3].equals("RES-end")) {
			isValid = false;
			return;
		}
		
		command = tokens[1];
		
//		String[] attrs = tokens[2].split("@|[|]");
//		for (int i=1; i<attrs.length; i+=2) {
//			attributes.put(attrs[i].trim(), attrs[i+1].trim());
//		}
		StringTokenizer st = new StringTokenizer(tokens[2], "@");
		while (st.hasMoreTokens()) {
			String token = st.nextToken().trim();
			int idx = token.indexOf('[');
			int idx2 = token.indexOf(']');
			
			if (idx > 0 && idx < idx2) {
				String key = token.substring(0, idx).trim();
				String value = token.substring(idx+1, idx2).trim();
				attributes.put(key, value);
			}
		}
		
		isValid = true;
	}

	protected boolean isValid() {
		return isValid && isValidCommand();
	}
	
	protected String getCommand() {
		return command;
	}

	protected String getAttribute(String key) {
		String res = attributes.get(key);
		return res != null ? res : "";
	}
	
	protected abstract boolean isValidCommand();

}
