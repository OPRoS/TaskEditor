package kr.re.etri.tpl.diagram.monitoring.controller;

import org.apache.log4j.Logger;

public class MessageMaker {
	/*
		Request
		REQ-start;CMD:$command;ATTR:$attribute;REQ-end
		¿¹: REQ-start;CMD:req;ATTR:@target[tsk1.bhv1.s1.transition.2]@after[10]@iteration[3];REQ-end
		
		Suspend
		REQ-start;CMD:stop;ATTR:@target[tsk1.bhv1.s1.transition.2];REQ-end
		
		Resume
		REQ-start;CMD:resume;ATTR:@target[tsk1.bhv1.s1.do]@after[10]@iteration[3];REQ-end
		
		End
		REQ-start;CMD:end;ATTR:@target[tsk1.bhv1.s1.transition.2];REQ-end
		
		Status
		REQ-start;CMD:status;;REQ-end
	 */
	private static Logger logger = Logger.getLogger(MessageMaker.class);
	
	private static final String REQ_START="REQ-start";
	private static final String REQ_END="REQ-end";
	
	private static final String CMD_REQUEST="CMD:req";
	private static final String CMD_STOP="CMD:stop";
	private static final String CMD_RESUME="CMD:resume";
	private static final String CMD_END="CMD:end";
	private static final String CMD_STATUS="CMD:status";
	
	private static final String SEPARATOR=";";
	private static final String BRACKET_START="[";
	private static final String BRACKET_END="]";
	
	private static final String ATTR_TARGET="ATTR:@target";
	private static final String ATTR_AFTER="@after";
	private static final String ATTR_ITERATION="@iteration";
	
	private static final String ZERO ="0";
	private static final String UNLIMIT ="unlimit";
	
	public static String makeRequestMessage(String target){
		String message = REQ_START+SEPARATOR
		+CMD_REQUEST+SEPARATOR
		+ATTR_TARGET+BRACKET_START+target+BRACKET_END
		+SEPARATOR+REQ_END;
		logger.info("Make Messager : "+message);
		return message;
	}
	public static String makeRequestMessage(String target, String after, String iteration){
		String message =REQ_START+SEPARATOR
		+CMD_REQUEST+SEPARATOR
		+ATTR_TARGET+BRACKET_START+target+BRACKET_END;
		if(after.equals(ZERO) && (iteration.equals(ZERO)||iteration.equals(UNLIMIT)) ){
			
		}
		else if(!after.equals(ZERO) && (iteration.equals(ZERO)||iteration.equals(UNLIMIT))){
			message += ATTR_AFTER+BRACKET_START+after+BRACKET_END;
		}
		else if(after.equals(ZERO) && !(iteration.equals(ZERO)||iteration.equals(UNLIMIT))){
			message += ATTR_ITERATION+BRACKET_START+iteration+BRACKET_END;
		}	
		else if(!after.equals(ZERO) && !(iteration.equals(ZERO)||iteration.equals(UNLIMIT))){
			message +=ATTR_AFTER+BRACKET_START+after+BRACKET_END
			+ATTR_ITERATION+BRACKET_START+iteration+BRACKET_END;
		}
		else{
			logger.error("Unexpected condition");
		}
		
		message +=SEPARATOR+REQ_END;
		logger.info("Make Messager : "+message);
		return message;
	}
	public static String makeStopMessage(String target){
		return REQ_START+SEPARATOR
		+CMD_STOP+SEPARATOR
		+ATTR_TARGET+BRACKET_START+target+BRACKET_END
		+SEPARATOR+REQ_END;
	}
	
	public static String makeResumeMessage(String target, String after, String iteration){
		String message =REQ_START+SEPARATOR
		+CMD_RESUME+SEPARATOR
		+ATTR_TARGET+BRACKET_START+target+BRACKET_END;
		if(after.equals(ZERO) && (iteration.equals(ZERO)||iteration.equals(UNLIMIT)) ){
			
		}
		else if(!after.equals(ZERO) && (iteration.equals(ZERO)||iteration.equals(UNLIMIT))){
			message += ATTR_AFTER+BRACKET_START+after+BRACKET_END;
		}
		else if(after.equals(ZERO) && !(iteration.equals(ZERO)||iteration.equals(UNLIMIT))){
			message += ATTR_ITERATION+BRACKET_START+iteration+BRACKET_END;
		}	
		else if(!after.equals(ZERO) && !(iteration.equals(ZERO)||iteration.equals(UNLIMIT))){
			message +=ATTR_AFTER+BRACKET_START+after+BRACKET_END
			+ATTR_ITERATION+BRACKET_START+iteration+BRACKET_END;
		}
		else{
			logger.error("Unexpected condition");
		}
		
		message +=SEPARATOR+REQ_END;
		logger.info("Make Messager : "+message);
		return message;
	}
	
	public static String makeEndMessage(String target){
		return REQ_START+SEPARATOR
		+CMD_END+SEPARATOR
		+ATTR_TARGET+BRACKET_START+target+BRACKET_END
		+SEPARATOR+REQ_END;
	}
	
	public static String makeStatusMessage(){
		return REQ_START+SEPARATOR
		+CMD_STATUS+SEPARATOR
		+SEPARATOR+REQ_END;
	}
}
