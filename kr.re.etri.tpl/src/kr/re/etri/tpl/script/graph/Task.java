package kr.re.etri.tpl.script.graph;

public class Task {
	private String name;
	private String description;
	private GoTo startBehavior;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String dec){
		description = dec;
	}
	
	public void setStartBehavior(GoTo behavior){
		startBehavior = behavior;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public GoTo getStartBehavior(){
		return startBehavior;
	}
}
