package kr.re.etri.tpl.diagram.util.scripts;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;

public abstract class ScriptGenerator {

	private Object model;
	private int indent;
	private IErrorListener logger;

	public ScriptGenerator(Object model, int indent, IErrorListener logger) {
		this.model = model;
		this.indent = indent;
		this.logger = logger;
	}
	
	public abstract void validate() throws TPLException;

	public Object getModel() {
		return model;
	}
	
	public int getIndent() {
		return indent;
	}
	
	public IErrorListener getLogger() {
		return logger;
	}
}
