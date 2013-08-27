package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

public interface IEnumNode extends IScriptNode, IBlockNode {
	public List<IEnumElmtNode> getElements();
}
