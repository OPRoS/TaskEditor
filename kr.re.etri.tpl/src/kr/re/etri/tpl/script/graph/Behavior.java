package kr.re.etri.tpl.script.graph;

import java.util.ArrayList;
import java.util.List;

public class Behavior {
	private String name;
	private List<State> states;
	private boolean printed;
	
	public Behavior(){
		states = new ArrayList<State>();
		printed = false;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public void add(State t){
		states.add(t);
	}
	
	public State get(int index){
		return states.get(index);
	}
	public int getStateListSize(){
		return states.size();
	}
	public boolean isPrinted(){
		return printed;
	}
	public void setPrinted(boolean printed){
		this.printed = printed;
	}
}
