package kr.re.etri.tpl.script.editors.highlighting;

import kr.re.etri.tpl.script.editors.TPLWhitespaceDetector;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

public class TPLCodeScanner extends RuleBasedScanner{
	
	public TPLCodeScanner(TPLColorManager manager){
//		FontData[] fonts =Display.getCurrent().getFontList("Bitstream Vera Sans Mono", true);
		
		IToken keyword = new Token(new TextAttribute(manager.getColor(ITPLColorConstants.TASK_KEYWORD), null,SWT.BOLD));	
		IToken string = new Token(new TextAttribute(manager.getColor(ITPLColorConstants.TASK_STRING), null, SWT.ITALIC));
		IToken statics = new Token(new TextAttribute(manager.getColor(ITPLColorConstants.TASK_STATIC), null, SWT.NORMAL));	// KJH 20110914
		IToken other= new Token(new TextAttribute(manager.getColor(ITPLColorConstants.DEFAULT)));
		
		
		IRule[] rules = new IRule[4];
		
		rules[0] = new SingleLineRule("\"","\"", string);
		rules[1] =new SingleLineRule("\'","\'",string);
				
		WordRule wr = new WordRule(new TPLKeywordDetector(),other);

		for(String next : TPLKeyword.getKeywords()){
			wr.addWord(next, keyword);
		}
		for (String next : TPLKeyword.getStatics()) {	// KJH 20110914
			wr.addWord(next, statics);
		}
		rules[2] = wr;
		rules[3] = new WhitespaceRule(new TPLWhitespaceDetector());
		this.setRules(rules);
	}

}
