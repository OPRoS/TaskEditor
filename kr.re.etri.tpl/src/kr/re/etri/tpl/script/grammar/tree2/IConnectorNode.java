package kr.re.etri.tpl.script.grammar.tree2;

public interface IConnectorNode extends IScriptNode, IBlockNode {	// KJH 20110208, new
	public String getSynMod();
	public void setSynMod(String synMod);
	public String getJoinType();
	public void setJoinType(String joinType);
	public String getConMod();
	public void setConMod(String conMod);
	public IScriptNode getConstruct();
	public IScriptNode getDestruct();
	public IScriptNode getExer();
}

