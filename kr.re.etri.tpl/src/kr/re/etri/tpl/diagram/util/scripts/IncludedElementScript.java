package kr.re.etri.tpl.diagram.util.scripts;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.IncludedElement;

public class IncludedElementScript extends ScriptGenerator {
	
	public IncludedElementScript(IncludedElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	protected IncludedElement getCastedModel() {
		return (IncludedElement)getModel();
	}
	
	public void validate() throws TPLException {
		if(getCastedModel().getIncludePath() == null) {
			getLogger().error("include 파일 이름이 지정되지 않았습니다.", -1, -1, -1);
		}
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		if(getCastedModel().getIncludePath().length() > 0) {
			sb.append(TPLUtil.getWs(getIndent())).append("#include ");
			sb.append("\"");
			sb.append(getCastedModel().getIncludePath());
			sb.append("\"");
		}
		return sb.toString();
	}
}
