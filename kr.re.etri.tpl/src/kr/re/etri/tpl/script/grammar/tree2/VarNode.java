package kr.re.etri.tpl.script.grammar.tree2;

public class VarNode extends ScriptNode implements
		IVarNode {
	private String vmod ;
	private String vtype;
	
	public String getVMod(){
		return vmod;
	}
	
	public String getVType(){
		return vtype;
	}
	
	public void setVMod(String vmod){
		this.vmod = vmod;
	}
	
	public void setVType(String vtype){
		this.vtype = vtype;
	}

	@Override
	public String getFullName() {
		return (new StringBuffer().append(vmod).append(" ").append(vtype).append(" ").append(getName())).toString();
	}
	
}
