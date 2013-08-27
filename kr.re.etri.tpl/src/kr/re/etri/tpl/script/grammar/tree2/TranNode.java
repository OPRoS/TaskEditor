package kr.re.etri.tpl.script.grammar.tree2;


public class TranNode extends ScriptNode implements
		ITranNode {
	private String cond;
	private String target;
	
	public String getCond() {
		return cond;
	}
	public void setCond(String cond) {
		this.cond = cond;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	public IScriptNode getTargetNode() {
		IScriptNode parent = getParent();

		while (parent != null && !(parent instanceof IBehaviorNode))
			parent = parent.getParent();

		if (parent != null && IScriptNode.EXPAND.equals(getType())) {
			parent = parent.getParent();
		}
		
		if (parent == null)
			return null;
		
		for (IScriptNode child : parent.getChildren()) {
			if (target.equals(child.getName())) {
				return child;
			}
		}
		
		return null;
	}
	
	@Override
	public String getFullName() {
		return (new StringBuffer().append(getName()).append(" (").append(target).append(")")).toString();
	}
	
}
