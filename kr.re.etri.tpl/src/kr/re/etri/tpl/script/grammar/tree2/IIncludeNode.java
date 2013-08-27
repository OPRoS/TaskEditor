package kr.re.etri.tpl.script.grammar.tree2;


public interface IIncludeNode extends IScriptRootNode,
		IBlockNode {
	public IScriptNode getRealNode();
	public void setRealNode(IScriptNode realNode);
}
