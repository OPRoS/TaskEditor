package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

public class IncludeNode extends ScriptRootNode implements
		IIncludeNode, IScriptRootNode {
	private IScriptNode realNode;
	
	public IScriptNode getRealNode() {
		return realNode;
	}
	public void setRealNode(IScriptNode realNode) {
		this.realNode = realNode;
	}
	@Override
	public List<IScriptNode> getChildren() {
		if (realNode != null) {
			return realNode.getChildren();
		}
		return super.getChildren();
	}
	@Override
	public int getChildrenCount() {
		if (realNode != null) {
			return realNode.getChildrenCount();
		}
		return super.getChildrenCount();
	}
	
}
