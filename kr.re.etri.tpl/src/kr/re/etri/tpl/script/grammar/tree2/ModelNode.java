package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class ModelNode extends ScriptNode implements
		IModelNode {
	// KJH 20101116 s, IScriptNode -> 각 타입별로 수정
	public List<IVarNode> getVars(){
		List<IVarNode> result = new ArrayList<IVarNode>();
		for(IScriptNode child : getChildren()){
			if(child instanceof IVarNode)
				result.add((IVarNode)child);
		}
		return result;
	}
	public List<IFuncNode> getFunctions(){
		List<IFuncNode> result = new ArrayList<IFuncNode>();
		for(IScriptNode child : getChildren()){
			if(child instanceof IFuncNode)
				result.add((IFuncNode)child);
		}
		return result;
	}
	public List<IModelNode> getModels(){
		List<IModelNode> result = new ArrayList<IModelNode>();
		for(IScriptNode child : getChildren()){
			if(child instanceof IModelNode)
				result.add((IModelNode)child);
		}
		return result;
	}
	// KJH 20101116 e, IScriptNode -> 각 타입별로 수정
}
