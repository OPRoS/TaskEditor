package kr.re.etri.tpl.script.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import kr.re.etri.tpl.MessageListener;

public abstract class ResponseMessageReader implements MessageListener {

	protected static final String ATTR_THID = "thid";
	protected static final String ATTR_PARENT = "parent";
	protected static final String ATTR_CMD = "cmd";
	protected static final String ATTR_MSGID = "msgid";
	protected static final String ATTR_THREAD = "thread";
	protected static final String ATTR_TASK = "task";
	protected static final String ATTR_TIME = "time";
	protected static final String ATTR_TARGET = "target";
	protected static final String ATTR_FILE = "file";
	protected static final String ATTR_LINE = "line";
	protected static final String ATTR_NAME = "name";
	protected static final String ATTR_TYPE = "type";
	protected static final String ATTR_VALUE = "value";
	protected static final String ATTR_DBGLINE = "dbgline";
	protected static final String ATTR_THLINE = "threadLine";

	private boolean isValid;
	private boolean isEOB;
	private String command;
	private Map<String, String> attributes = new HashMap<String, String>();
	
	@Override
	public void update(String message) {

		String[] tokens = message.split(";");
		for (int i=0; i<tokens.length; i++) {
			tokens[i] = tokens[i].trim();
		}
		
		isEOB = false;

		if (tokens[0].equals("RES-EOB")) {
			if (tokens.length != 1) {
				isValid = false;
			} else {
				isValid = true;	// KJH 20110314, RES-EOBÀÎ °æ¿ì valid
			}
			isEOB = true;
			return;
		}

		if ((tokens.length != 4) ||
				(!tokens[0].equals("RES-start")) ||
				(!tokens[3].equals("RES-end"))) {
			isValid = false;
			return;
		}

		if (isValidCommand(tokens[1])) {
			command = tokens[1];

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
			return;
		}
		
		isValid = false;
	}
	
	protected void clear() {
		attributes.clear();
		command = null;
		isValid = false;
		isEOB = false;
	}

	protected boolean isValid() {
		return isValid;
	}
	
	protected boolean isEOB() {
		return isEOB;
	}
	
	protected String getCommand() {
		return command;
	}

	protected String getAttribute(String key) {
		return attributes.get(key);
	}
	
	protected abstract boolean isValidCommand(String command);

}
