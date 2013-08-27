package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class FuncNode extends ScriptNode implements
		IFuncNode {
	private boolean action;
	private String rtn;
	
	public boolean isAction(){
		return action;
	}
	
	public String getRtn(){
		return rtn;
	}
	
	public void setAction(boolean action){
		this.action = action;
	}
	
	public void setRtn(String rtn){
		this.rtn = rtn;
	}

	// KJH 20101116 s, add
	public List<IParamNode> getParams() {
		List<IParamNode> result = new ArrayList<IParamNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IParamNode)
				result.add((IParamNode)child);
		return result;
	}	// KJH 20101116 e, add
	
	@Override
	public String getFullName() {
		return (new StringBuffer().append(rtn).append(" ").append(getName())).toString();
	}
	
}
