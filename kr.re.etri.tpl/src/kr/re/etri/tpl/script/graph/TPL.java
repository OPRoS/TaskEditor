package kr.re.etri.tpl.script.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TPL {
	private List<Task> tasks;
	private Map<String, Behavior> behaviorMap;
	
	public TPL(){
		tasks = new ArrayList<Task>();
		behaviorMap = new HashMap<String, Behavior>();
	}

	public void addTask(Task t){
		tasks.add(t);
	}
	
	public Task getTask(int index){
		return tasks.get(index);
	}
	public int getTaskListSize(){
		return tasks.size();
	}
	
	public void addBehavior(Behavior t){
		behaviorMap.put(t.getName(),t);
	}
	
	public Behavior getBehavior(String name){
		return behaviorMap.get(name);
	}
	
	public int getBehaviorListSize(){
		return behaviorMap.size();
	}
	public void printedReset(){
		if(behaviorMap == null){
			return;
		}
		for(Behavior next : behaviorMap.values()){
			next.setPrinted(false);
		}
	}
}
