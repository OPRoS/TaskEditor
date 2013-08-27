package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;

public class ScriptRootNode extends ScriptNode implements IScriptRootNode {

	private boolean global;
	private IFile file;
	
	public void setGlobal(boolean global) {
		this.global = global;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setFile(IFile file) {
		this.file = file;
	}
	public IFile getFile() {
		return file;
	}

	public List<IIncludeNode> getIncludes(){
		List<IIncludeNode> result = new ArrayList<IIncludeNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IIncludeNode)
				result.add((IIncludeNode)child);
		return result;
	}
	public List<IWorkerNode> getTasks(){
		List<IWorkerNode> result = new ArrayList<IWorkerNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IWorkerNode)
				result.add((IWorkerNode) child);
		return result;
	}
	public List<IEnumNode> getEnums(){
		List<IEnumNode> result = new ArrayList<IEnumNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IEnumNode)
				result.add((IEnumNode) child);
		return result;
	}
	public List<IModelNode> getModels(){
		List<IModelNode> result = new ArrayList<IModelNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IModelNode)
				result.add((IModelNode)child);
		return result;
	}
	public List<IBehaviorNode> getBehaviors(){
		List<IBehaviorNode> result = new ArrayList<IBehaviorNode>();
		for(IScriptNode child : getChildren())
			if(child instanceof IBehaviorNode)
				result.add((IBehaviorNode) child);
		return result;
	}
	
}
