package kr.re.etri.tpl.script.grammar.tree2;

public class RunNode extends ScriptNode implements IRunNode, IBreakableNode {

//	@Override
//	public IScriptNode getChildByOffset(int offset) {
//		if (offset >= start && offset <= end)
//			return this;
//		return null;
//	}

	@Override
	public IScriptNode getNextChildByOffset(int offset) {
		if (offset >= start && offset <= end)
			return this;
		else if (offset < start)
			return this;
		return null;
	}

}
