package kr.re.etri.tpl.script.grammar.tree2;

public interface ITranNode extends IScriptNode {
	public String getCond();
	public void setCond(String cond);
	public String getTarget();
	public void setTarget(String target);
	public IScriptNode getTargetNode();
}
