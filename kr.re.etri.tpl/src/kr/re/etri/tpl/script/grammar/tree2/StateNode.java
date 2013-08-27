package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class StateNode extends ScriptNode implements
		IStateNode {
	private String modifier;
	
	public String getModifier(){
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}

	public IScriptNode getEntry() {
		for(IScriptNode child : getChildren()){
			if(child instanceof IStateActionNode && child.getName().equals("entry"))
				return child;
		}
		return null;
	}
	public IScriptNode getStay() {
		for(IScriptNode child : getChildren()){
			if(child instanceof IStateActionNode && child.getName().equals("stay"))
				return child;
		}
		return null;
	}
	public IScriptNode getExit() {
		for(IScriptNode child : getChildren()){
			if(child instanceof IStateActionNode && child.getName().equals("exit"))
				return child;
		}
		return null;
	}
	public List<IScriptNode> getTransitions(){
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		for(IScriptNode child : getChildren()){
			if(child instanceof ITranNode)
				list.add(child);
		}
		return list;
	}
	
}
