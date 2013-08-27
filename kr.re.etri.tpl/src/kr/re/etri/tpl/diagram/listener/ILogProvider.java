package kr.re.etri.tpl.diagram.listener;


public interface ILogProvider extends ILogListener {
	
	void addLogListener(ILogListener listener);
	void removeLogListener(ILogListener listener);
}
