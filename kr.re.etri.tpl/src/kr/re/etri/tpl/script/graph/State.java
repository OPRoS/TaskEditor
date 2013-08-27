package kr.re.etri.tpl.script.graph;

import java.util.ArrayList;
import java.util.List;

public class State {
	private String mode;
	private String name;
	private List<GoTo> gotos;
	public State( ){
		gotos = new ArrayList<GoTo>();
	}
	public void setMode(String mode){
		this.mode = mode;
	}
	
	public String getMode(){
		return mode;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void addGoTo(GoTo goTo){
		gotos.add(goTo);
	}
	
	public GoTo getGoTo(int index){
		return gotos.get(index);
	}
	
	public String getName(){
		return name;
	}
	public int getGoToListSize(){
		return gotos.size();
	}
}
