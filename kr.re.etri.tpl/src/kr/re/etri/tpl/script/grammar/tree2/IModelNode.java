package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

public interface IModelNode extends IScriptNode,
		IBlockNode {
	// KJH 20101116 s, IScriptNode -> �� Ÿ�Ժ��� ����
	public List<IVarNode> getVars();
	public List<IFuncNode> getFunctions();
	public List<IModelNode> getModels();
	// KJH 20101116 e, IScriptNode -> �� Ÿ�Ժ��� ����
}
