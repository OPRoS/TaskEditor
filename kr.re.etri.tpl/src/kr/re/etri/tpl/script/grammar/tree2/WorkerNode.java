package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class WorkerNode extends ScriptNode implements
		IWorkerNode {
	private List<String> targets;
	
	public void addTarget(String target) {
		getTargets().add(target);
	}
	
	public void removeTarget(String target) {
		getTargets().remove(target);
	}
	
	public List<String> getTargets() {
		if (targets == null) {
			targets = new ArrayList<String>();
		}
		return targets;
	}
}
