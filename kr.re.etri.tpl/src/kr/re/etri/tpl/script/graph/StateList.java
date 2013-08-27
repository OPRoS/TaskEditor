package kr.re.etri.tpl.script.graph;

import java.util.ArrayList;
import java.util.List;

public class StateList {
	private List<State> states;
	
	public StateList(){
		states = new ArrayList<State>();
	}

	public void add(State t){
		states.add(t);
	}
	
	public State get(int index){
		return states.get(index);
	}
}