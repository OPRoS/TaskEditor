package kr.re.etri.tpl.diagram.listener;


public interface IMessageProvider extends IMessageListener {
	
    public final static int NONE = 0;

    /**
     * Constant for an info message (value 1).
     */
    public final static int INFORMATION = 1;

    /**
     * Constant for a warning message (value 2).
     */
    public final static int WARNING = 2;

    /**
     * Constant for an error message (value 3).
     */
    public final static int ERROR = 3;
	
	void addMessageListener(IMessageListener listener);
	void removeMessageListener(IMessageListener listener);
}
