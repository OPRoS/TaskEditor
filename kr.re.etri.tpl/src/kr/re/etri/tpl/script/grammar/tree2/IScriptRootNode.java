package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

import org.eclipse.core.resources.IFile;

public interface IScriptRootNode extends IScriptNode {
	public void setGlobal(boolean global);
	public boolean isGlobal();
	public void setFile(IFile file);
	public IFile getFile();

	public List<IIncludeNode> getIncludes();
	public List<IWorkerNode> getTasks();
	public List<IEnumNode> getEnums();
	public List<IModelNode> getModels();
	public List<IBehaviorNode> getBehaviors();
}
