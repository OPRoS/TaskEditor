package kr.re.etri.tpl.script.grammar.tree2;

import java.util.HashMap;
import java.util.Map;

public class ScriptAdapter {
	private static ScriptAdapter instance;
	private Map<IScriptNode, Map<Class, Object>> adapterMap = new HashMap<IScriptNode, Map<Class, Object>>();
	
	public static ScriptAdapter getInstance() {
		if (instance == null)
			instance = new ScriptAdapter();
		return instance;
	}
	
	public Object getAdapter(IScriptNode script, Class clazz) {
		Map<Class, Object> adapters = adapterMap.get(script);
		if (adapters != null) {
			return adapters.get(clazz);
		}
		return null;
	}
	
	public void setAdapter(IScriptNode script, Class clazz, Object adapter) {
		if (script != null && adapter != null) {
			Map<Class, Object> adapters = adapterMap.get(script);
			if (adapters == null) {
				adapters = new HashMap<Class, Object>();
				adapterMap.put(script, adapters);
			}
			adapters.put(clazz, adapter);
		}
	}
}
