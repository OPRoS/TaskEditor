package kr.re.etri.tpl.script.grammar.tree2;

public class ConnectorNode extends ScriptNode implements IConnectorNode {
	
	private String synMod;
	private String joinType;
	private String conMod;

	@Override
	public String getSynMod() {
		return synMod;
	}
	@Override
	public void setSynMod(String synMod) {
		this.synMod = synMod;
	}
	@Override
	public String getJoinType() {
		return joinType;
	}
	@Override
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	@Override
	public String getConMod() {
		return conMod;
	}
	@Override
	public void setConMod(String conMod) {
		this.conMod = conMod;
	}

	@Override
	public IScriptNode getConstruct() {
		for(IScriptNode child : getChildren()){
			if(child instanceof IStructNode && child.getName().equals("construct"))
				return child;
		}
		return null;
	}

	@Override
	public IScriptNode getDestruct() {
		for(IScriptNode child : getChildren()){
			if(child instanceof IStructNode && child.getName().equals("destruct"))
				return child;
		}
		return null;
	}

	@Override
	public IScriptNode getExer() {
		for(IScriptNode child : getChildren()){
			if(child instanceof IRunNode && child.getName().equals("conexer"))
				return child;
		}
		return null;
	}

}
