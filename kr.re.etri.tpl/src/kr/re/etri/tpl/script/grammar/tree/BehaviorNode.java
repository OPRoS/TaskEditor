package kr.re.etri.tpl.script.grammar.tree;

import java.util.LinkedHashMap;
import java.util.Map;

public class BehaviorNode extends TPLBlock {
	private Map<String, String> params = new LinkedHashMap<String, String>();
	
	public String[] getParams() {
		return params.keySet().toArray(new String[params.size()]);
	}
	
	public String getType(String param){
		return params.get(param);
	}
	
	public void addParam(String name, String type){
		params.put(name, type);
	}
}
