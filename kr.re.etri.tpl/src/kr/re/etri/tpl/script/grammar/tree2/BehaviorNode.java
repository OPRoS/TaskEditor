package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class BehaviorNode extends ScriptNode implements IBehaviorNode {
	
	private boolean mission;
	
	public boolean isMission() {
		return mission;
	}
	
	public void setMission(boolean mission) {
		this.mission = mission;
	}
	
	public List<IParamNode> getParams(){
		List<IParamNode> result = new ArrayList<IParamNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IParamNode)
				result.add((IParamNode)child);
		return result;
	}
	public List<IStateNode> getStates(){
		List<IStateNode> result = new ArrayList<IStateNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IStateNode)
				result.add((IStateNode) child);
		return result;
	}

}
