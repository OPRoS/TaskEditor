package kr.re.etri.tpl.diagram.listener;



public interface ILogListener {
	
	boolean hasInfo();

	void info(String message);
	void info(String message, String location);

	boolean hasWarning();
	
	void warning(String message);
	void warning(String message, String location);

	boolean hasError();
	
	void error(String message);
	void error(String message, String location);
}
