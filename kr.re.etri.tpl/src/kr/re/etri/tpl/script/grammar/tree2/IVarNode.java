package kr.re.etri.tpl.script.grammar.tree2;

public interface IVarNode extends IScriptNode {
	public String getVMod();	
	public String getVType();
	public void setVMod(String vmod);
	public void setVType(String vtype);
}
