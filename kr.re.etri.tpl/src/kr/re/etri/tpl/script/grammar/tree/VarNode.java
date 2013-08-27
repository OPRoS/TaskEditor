package kr.re.etri.tpl.script.grammar.tree;


public class VarNode extends TPLTree {
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
}
