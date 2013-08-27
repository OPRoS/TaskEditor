package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

public interface IBehaviorNode extends IScriptNode, IBlockNode {
	public boolean isMission();
	public void setMission(boolean mission);
	public List<IParamNode> getParams();
	public List<IStateNode> getStates();
}
