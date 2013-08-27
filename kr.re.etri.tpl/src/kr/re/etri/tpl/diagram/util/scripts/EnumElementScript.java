package kr.re.etri.tpl.diagram.util.scripts;

import java.util.Hashtable;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.emf.common.util.EList;

public class EnumElementScript extends ScriptGenerator {

	public EnumElementScript(EnumElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	protected EnumElement getCastedModel() {
		return (EnumElement)getModel();
	}
	
	public void validate() throws TPLException {
		EList<EnumItemElement> itemList = getCastedModel().getEnumItem();

		if(getCastedModel().getName() == null) {
			getLogger().error("Enum 이름이 지정되지 않았습니다.", -1, -1, -1);
		}

		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		for(int idx = 0; idx < itemList.size(); idx += 1) {
			EnumItemElement item = itemList.get(idx);
			if(item.getName() == null) {
				getLogger().error(String.format("Enum %s 의 Element 이름이 지정되지 않았습니다.", getCastedModel().getName()), -1, -1, -1);
			}
			
			if(nameMap.containsKey(item.getName())) {
				getLogger().warning(String.format("Enum %s 의 Element 이름(%s)이 중복되었습니다.", getCastedModel().getName(), item.getName()), -1, -1, -1);
			}
			nameMap.put(item.getName(), item);
		}
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}

		EList<EnumItemElement> itemList = getCastedModel().getEnumItem();

		StringBuilder sb = new StringBuilder();
		sb.append(TPLUtil.getWs(getIndent())).append("enum").append(" ").append(getCastedModel().getName()).append("\r\n");
		sb.append(TPLUtil.getWs(getIndent())).append("{").append("\r\n");

		for(int idx = 0; idx < itemList.size(); idx += 1) {
			EnumItemElement item = itemList.get(idx);
			if(idx > 0) {
				sb.append(",\r\n");
			}
			sb.append(TPLUtil.getWs(getIndent()+1)).append(item.getName());
		}
		sb.append("\r\n");
		sb.append(TPLUtil.getWs(getIndent())).append("};").append("\r\n");

		return sb.toString();
	}
}
