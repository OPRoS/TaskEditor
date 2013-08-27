package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

public interface IStateNode extends IScriptNode, IBlockNode {
	public String getModifier();
	public void setModifier(String Modifier);
	public IScriptNode getEntry();
	public IScriptNode getStay();
	public IScriptNode getExit();
	public List<IScriptNode> getTransitions();
}
