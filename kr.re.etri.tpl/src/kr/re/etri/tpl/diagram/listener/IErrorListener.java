package kr.re.etri.tpl.diagram.listener;


public interface IErrorListener {
	
	boolean hasInfo();
	
	boolean hasWarning();
	
	boolean hasError();
	
	void info(String message, int lineNum, int charStart, int charEnd);

	void warning(String message, int lineNum, int charStart, int charEnd);
	
	void error(String message, int lineNum, int charStart, int charEnd);
}
