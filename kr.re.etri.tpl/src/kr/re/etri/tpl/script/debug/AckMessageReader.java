package kr.re.etri.tpl.script.debug;



public class AckMessageReader extends ResponseMessageReader {

	public static String ATTR_REQID = "reqid";
	public static String ATTR_ERROR = "error";
	
	@Override
	public void update(String message) {
		super.update(message);
		if (!isValid())
			return;
		
		String reqid = getAttribute(ATTR_REQID);
		String error = getAttribute(ATTR_ERROR);
		DebugManager.getDefault().putAckMessage(Integer.parseInt(reqid), error);
		
		clear();
	}

	@Override
	protected boolean isValidCommand(String command) {
		return "msgack".equals(command);
	}

}
