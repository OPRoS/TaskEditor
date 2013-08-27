package kr.re.etri.tpl.diagram.util.scripts;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.emf.common.util.EList;

public class StructBlockScript extends ScriptGenerator {
	ItemElement parent;
	
	public StructBlockScript(ItemElement parent, StructBlockElement model,
			int indent, IErrorListener logger) {
		super(model, indent, logger);
		this.parent = parent;
	}
	
	protected StructBlockElement getCastedModel() {
		return (StructBlockElement)getModel();
	}

	@Override
	public void validate() throws TPLException {
		if (getCastedModel() == null) {
			return;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (getCastedModel() == null) {
			return "";
		}
		
		EList<String> stmtList = getCastedModel().getStatements();
		if (stmtList == null || stmtList.size() == 0) {
//			return "";	// KJH 20110722, empty structblock
		}
		
		if (StructType.CONSTRUCT == getCastedModel().getStructType()) {
			if (parent instanceof TaskElement) {
				sb.append(TPLUtil.getWs(getIndent())).append("initializer {\r\n");
			}
			else {
				sb.append(TPLUtil.getWs(getIndent())).append("construct {\r\n");
			}
		}
		else if (StructType.DESTRUCT == getCastedModel().getStructType()) {
			if (parent instanceof TaskElement) {
				sb.append(TPLUtil.getWs(getIndent())).append("finalizer {\r\n");
			}
			else {
				sb.append(TPLUtil.getWs(getIndent())).append("destruct { \r\n");
			}
		}
		else {
			sb.append(TPLUtil.getWs(getIndent())).append(getCastedModel().getName())
												 .append(" {\r\n");
		}
		
		for (String stmt : stmtList) {
			sb.append(TPLUtil.getWs(getIndent() + 1)).append(stmt)
													 .append("\r\n");
		}
		
		sb.append(TPLUtil.getWs(getIndent())).append("}\r\n");
		
		return sb.toString();
	}		
}
