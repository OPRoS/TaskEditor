package kr.re.etri.tpl.script.grammar.tree;


public class FuncNode extends TPLTree {
	private String action;
	private String rtn;
	
	public String getAction(){
		return action;
	}
	
	public String getRtn(){
		return rtn;
	}
	
	public void setAction(String action){
		this.action = action;
	}
	
	public void setRtn(String rtn){
		this.rtn = rtn;
	}
}
