package kr.re.etri.tpl.script.editors.highlighting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPLKeyword {
	public static List<String> TaskKeywords=Arrays.asList( "#", "include", "#include", "enum", "model", "worker", "task", "target", "state", "transition", "initial", "entry", "stay", "exit", "action", "if", "else", "const", "in", "out", "goto", "sequential","stay","parallel","with","wvar",
			// KJH 20100506, add
			"moveto", "expand", "orjoin", "andjoin", "construct", "destruct", "synch", "asynch", "mission", "conexer", "seqexer", "dispoable", "goal", "var", "gvar", "context", "waitfor", "recall", "initializer", "run", "finalizer",
			// KJH 20101220, add
			"connector",
			// KJH 20110829, cycle 키워드 등록
			"@cycle");

	public static List<String> TaskTypes= Arrays.asList( "behavior","void", "bool", "byte", "char", "string", "byte", "short", "int", "long", "float", "double" );

	public static List<String> TaskConstants= Arrays.asList( "false", "null", "true", "isSubTaskFinal", "stateTime", "taskTime" );
	
	// KJH 20110914
	public static List<String> TaskStatics = Arrays.asList("BEHAVIOR_TIME", "STATE_TIME", "TASK_TIME", "SLEEP", "SYNCH");
	
	public static List<String> getKeywords(){
		List<String> keywords = new ArrayList<String>();
		keywords.addAll(TaskKeywords);
		keywords.addAll(TaskTypes);
		keywords.addAll(TaskConstants);
		return keywords;
	}
	
	// KJH 20110914
	public static List<String> getStatics() {
		return TaskStatics;
	}
}
