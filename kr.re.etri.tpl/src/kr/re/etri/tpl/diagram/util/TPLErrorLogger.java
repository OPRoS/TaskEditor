package kr.re.etri.tpl.diagram.util;

import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Error Logs 뷰에 로그를 기록하는 유틸리티 클래스이다.
 * @author sfline
 *
 */
public class TPLErrorLogger {
	
	/**
	 * Error Log Id;
	 */
	public static final String logId = "kr.re.etri.tpl.errorLog";

	/**
	 * Error Log 뷰에 info 메시지를 기록한다.
	 * @param message 로그 메시지
	 */
	public static void info(String message) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.INFO, TPLErrorLogger.logId, IStatus.OK, message, null);
		log.log(logStatus);
	}

	/**
	 * Error Log 뷰에 Exception과 함께 info 메시지를 기록한다.
	 * @param message 로그 메시지
	 * @param t Exception
	 */
	public static void info(String message, Throwable t) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.INFO, TPLErrorLogger.logId, IStatus.OK, message, t);
		log.log(logStatus);
	}

	/**
	 * Error Log 뷰에 warning 메시지를 기록한다.
	 * @param message 로그 메시지
	 */
	public static void warning(String message) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.WARNING, TPLErrorLogger.logId, IStatus.OK, message, null);
		log.log(logStatus);
	}

	/**
	 * Error Log 뷰에 Exception과 함께 warning 메시지를 기록한다.
	 * @param message 로그 메시지
	 * @param t Exception
	 */
	public static void warning(String message, Throwable t) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.WARNING, TPLErrorLogger.logId, IStatus.OK, message, t);
		log.log(logStatus);
	}

	/**
	 * Error Log 뷰에 error 메시지를 기록한다.
	 * @param message 로그 메시지
	 */
	public static void error(String message) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.ERROR, TPLErrorLogger.logId, IStatus.OK, message, null);
		log.log(logStatus);
	}
	

	/**
	 * Error Log 뷰에 Exception과 함께 error 메시지를 기록한다.
	 * @param message 로그 메시지
	 * @param t Exception
	 */
	public static void error(String message, Throwable t) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.ERROR, TPLErrorLogger.logId, IStatus.OK, message, t);
		log.log(logStatus);
	}
}
