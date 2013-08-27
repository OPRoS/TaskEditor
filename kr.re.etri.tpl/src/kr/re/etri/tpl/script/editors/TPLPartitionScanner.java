package kr.re.etri.tpl.script.editors;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class TPLPartitionScanner extends RuleBasedPartitionScanner {
	public final static String TASK_COMMENT ="__task_comment";
	public final static String TASK_STRING ="__task_string";
	public final static String TASK_KEYWORD ="__task_keyword";
	
	public final static String[] TASK_PARTITION_TYPES = new String[] {
		TASK_COMMENT };
	
	public TPLPartitionScanner() {
		IToken taskComment = new Token(TASK_COMMENT);

		List<IPredicateRule> rules = new ArrayList<IPredicateRule>();
		rules.add(new EndOfLineRule("//",taskComment));
		rules.add(new MultiLineRule("/*", "*/", taskComment));

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);	
	}
}
