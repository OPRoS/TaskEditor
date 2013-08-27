package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class EnumNode extends ScriptNode implements
		IEnumNode {
	public List<IEnumElmtNode> getElements(){
		List<IEnumElmtNode> result = new ArrayList<IEnumElmtNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IEnumElmtNode)
				result.add((IEnumElmtNode) child);
		return result;
	}
}
