package kr.re.etri.tpl.script.grammar.tree2;


public class StateActionNode extends ScriptNode implements
		IStateActionNode, IBreakableNode {
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
