package kr.re.etri.tpl.script.editors.highlighting;


import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

public class TPLCommentScanner extends RuleBasedScanner{
	public TPLCommentScanner(TPLColorManager manager){
		IToken comment = new Token(new TextAttribute(manager.getColor(ITPLColorConstants.TASK_COMMENT)));
		
		IRule[] rules = new IRule[2];
		
		rules[0] = new EndOfLineRule("//",comment);
		rules[1] = new MultiLineRule("/*", "*/", comment);
			
		this.setRules(rules);
	}

}
