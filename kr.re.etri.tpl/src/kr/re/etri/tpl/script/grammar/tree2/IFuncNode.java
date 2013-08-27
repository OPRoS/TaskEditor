package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

public interface IFuncNode extends IScriptNode {
	public boolean isAction();
	public String getRtn();
	public void setAction(boolean action);
	public void setRtn(String rtn);
	public List<IParamNode> getParams();	// KJH 20101116, IScriptNode -> IParamNode
}
