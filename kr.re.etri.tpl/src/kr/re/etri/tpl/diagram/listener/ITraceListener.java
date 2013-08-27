package kr.re.etri.tpl.diagram.listener;

import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;

public interface ITraceListener {

	void enterRule(int rule, Parser parser, ParserRuleReturnScope returnScope);

	void exitRule(int rule, Parser parser, ParserRuleReturnScope returnScope);

	void enterSubRule(int subRule, Parser parser, ParserRuleReturnScope returnScope);

	void exitSubRule(int subRule, Parser parser, ParserRuleReturnScope returnScope);
}
