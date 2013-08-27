package kr.re.etri.tpl.diagram.util.scripts;

import java.util.Hashtable;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;

import org.eclipse.emf.common.util.EList;

public class ActionElementScript extends ScriptGenerator {

	public ActionElementScript(ActionElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	protected ActionElement getCastedModel() {
		return (ActionElement)getModel();
	}

	public void validate() throws TPLException {

		if(getCastedModel().getName() == null) {
			getLogger().error("Action 이름이 지정되지 않았습니다.", -1, -1, -1);
		}
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();

		EList<Parameter> itemList = getCastedModel().getParams();
		for(int idx = 0; idx < itemList.size(); idx += 1) {
			Parameter item = itemList.get(idx);
			if(item.getType() == null) {
				getLogger().error(String.format("Action %s 의 parameter 타입이 지정되지 않았습니다.", getCastedModel().getName()), -1, -1, -1);
			}
			if(item.getName() == null) {
				getLogger().error(String.format("Action %s 의 parameter 이름이 지정되지 않았습니다.", getCastedModel().getName()), -1, -1, -1);
			}
			
			if(nameMap.containsKey(item.getName())) {
				getLogger().warning(String.format("Action %s 의 parameter 이름(%s)이 중복되었습니다.", getCastedModel().getName(), item.getName()), -1, -1, -1);
			}
			nameMap.put(item.getName(), item);
		}
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}

		EList<Parameter> itemList = getCastedModel().getParams();

		StringBuilder sb = new StringBuilder();
		sb.append(TPLUtil.getWs(getIndent())).append("action").append(" ").append(getCastedModel().getName()).append("(");
		for(int idx = 0; idx < itemList.size(); idx += 1) {
			Parameter item = itemList.get(idx);
			if(idx > 0) {
				sb.append(",");
			}
			sb.append(" ").append(item.getType());
			sb.append(" ").append(item.getName());
		}
		sb.append(");");

		return sb.toString();
	}
}
