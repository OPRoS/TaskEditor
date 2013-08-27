package kr.re.etri.tpl.diagram.util;

import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Error Logs �信 �α׸� ����ϴ� ��ƿ��Ƽ Ŭ�����̴�.
 * @author sfline
 *
 */
public class TPLErrorLogger {
	
	/**
	 * Error Log Id;
	 */
	public static final String logId = "kr.re.etri.tpl.errorLog";

	/**
	 * Error Log �信 info �޽����� ����Ѵ�.
	 * @param message �α� �޽���
	 */
	public static void info(String message) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.INFO, TPLErrorLogger.logId, IStatus.OK, message, null);
		log.log(logStatus);
	}

	/**
	 * Error Log �信 Exception�� �Բ� info �޽����� ����Ѵ�.
	 * @param message �α� �޽���
	 * @param t Exception
	 */
	public static void info(String message, Throwable t) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.INFO, TPLErrorLogger.logId, IStatus.OK, message, t);
		log.log(logStatus);
	}

	/**
	 * Error Log �信 warning �޽����� ����Ѵ�.
	 * @param message �α� �޽���
	 */
	public static void warning(String message) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.WARNING, TPLErrorLogger.logId, IStatus.OK, message, null);
		log.log(logStatus);
	}

	/**
	 * Error Log �信 Exception�� �Բ� warning �޽����� ����Ѵ�.
	 * @param message �α� �޽���
	 * @param t Exception
	 */
	public static void warning(String message, Throwable t) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.WARNING, TPLErrorLogger.logId, IStatus.OK, message, t);
		log.log(logStatus);
	}

	/**
	 * Error Log �信 error �޽����� ����Ѵ�.
	 * @param message �α� �޽���
	 */
	public static void error(String message) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.ERROR, TPLErrorLogger.logId, IStatus.OK, message, null);
		log.log(logStatus);
	}
	

	/**
	 * Error Log �信 Exception�� �Բ� error �޽����� ����Ѵ�.
	 * @param message �α� �޽���
	 * @param t Exception
	 */
	public static void error(String message, Throwable t) {
		TaskModelPlugin plguin = TaskModelPlugin.getDefault();
		ILog log = plguin.getLog();
		IStatus logStatus = new Status(IStatus.ERROR, TPLErrorLogger.logId, IStatus.OK, message, t);
		log.log(logStatus);
	}
}
