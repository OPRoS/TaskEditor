package kr.re.etri.tpl.grammar;

import java.util.List;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.listener.ILogListener;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.Tree;

public class GrammaUtil {

	public GrammaUtil() {
		// TODO Auto-generated constructor stub
	}

	public static boolean updateCallParameter(ItemElement item, String expression, IErrorListener listener) {
		expression = expression.trim();
		if(expression.length() == 0) {
			return true;
		}

		ANTLRStringStream antlrStream = new ANTLRStringStream(expression);

		RTMLexer lexer = new RTMLexer(antlrStream);
		lexer.setErrorListener(listener);

		RTMParser.rfsm_return def;

		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokenStream);
		parser.setErrorListener(listener);

		boolean result = false;
		RTMTokenListener rtmTracer = new RTMTokenListener();
		parser.addTraceListener(rtmTracer);
		try {
			def = parser.rfsm();
			
			result = updateCallParameterValue(item, (Tree)def.getTree(), tokenStream, listener);
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		parser.removeTraceListener(rtmTracer);
		
		return result;
	}

	public static boolean updateCallExpression(ItemElement item, String expression, StringBuilder outBuf, IErrorListener listener) {
		expression = expression.trim();
		if(expression.length() == 0) {
			return true;
		}

		ANTLRStringStream antlrStream = new ANTLRStringStream(expression);

		RTMLexer lexer = new RTMLexer(antlrStream);
		lexer.setErrorListener(listener);

		RTMParser.rfsm_return def;

		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokenStream);
		parser.setErrorListener(listener);

		boolean result = false;
		RTMTokenListener rtmTracer = new RTMTokenListener();
		parser.addTraceListener(rtmTracer);
		try {
			def = parser.rfsm();
			
			result = updateCallParameterExpression(item, (Tree)def.getTree(), outBuf, tokenStream, listener);
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		parser.removeTraceListener(rtmTracer);
		
		return result;
	}

	protected static boolean updateCallParameterValue(ItemElement item, Tree parentTree, TokenStream tokenStream, IErrorListener listener) {
		boolean result = false;

		// Top Member
		if("INCLUDESTATEMENT".equals(parentTree.getText()) ||
			"ENUMDECL".equals(parentTree.getText()) ||
			"MODELDECL".equals(parentTree.getText()) ||
			"ACTIONDECL".equals(parentTree.getText()) ||
			"TASKDECL".equals(parentTree.getText()) ||
			"WORKERDECL".equals(parentTree.getText()) ||
			"TCONSTANT".equals(parentTree.getText()) ||
			"TSYMBOL".equals(parentTree.getText()) ||
			"TFUNC".equals(parentTree.getText()) ||
			"MODELDECL".equals(parentTree.getText()) ||
			"IFSTATEMENT".equals(parentTree.getText()) ||
			"GOTOSTATEMENT".equals(parentTree.getText()) ||
			"STAYSTATEMENT".equals(parentTree.getText()) ||
//			"CALLSTATEMENT".equals(parentTree.getText()) ||
			"STATEMENTSINGLE".equals(parentTree.getText()) ||
			"STATEMENTBLOCK".equals(parentTree.getText()) ||
			"LOCALVARSTATEMENT".equals(parentTree.getText()) ||
//			"EXPRCALL".equals(parentTree.getText()) ||
			"EXPRCOND".equals(parentTree.getText()) ||
			"EXPRESSION".equals(parentTree.getText()) ||
			"EXPRCOND".equals(parentTree.getText()) ||
			"EXPROR".equals(parentTree.getText()) ||
			"EXPRAND".equals(parentTree.getText()) ||
			"EXPRNOT".equals(parentTree.getText()) ||
			"EXPBOOL".equals(parentTree.getText()) ||
			"EXPRADD".equals(parentTree.getText()) ||
			"EXPRMUL".equals(parentTree.getText()) ||
			"EXPRVALUE".equals(parentTree.getText()) ||
			"EXPRSYMBOL".equals(parentTree.getText())) {
			return false;
		}
		else if("CALLSTATEMENT".equals(parentTree.getText())) {
			return processCallParameter(item, parentTree, tokenStream, listener);
		}
		else if("EXPRCALL".equals(parentTree.getText())) {
			return processExprCallParameter(item, parentTree, tokenStream, listener);
		}
		else {
			int childNum = parentTree.getChildCount();
			for (int i = 0; i < childNum; i++) {
				Tree child = (Tree) parentTree.getChild(i);
				if(updateCallParameterValue(item, child, tokenStream, listener)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	protected static boolean processCallParameter(ItemElement item, Tree parentTree, TokenStream tokenStream, IErrorListener listener) {
		Tree callTree = parentTree.getChild(0);
		
		if("EXPRCALL".equals(callTree.getText())) {
			return processExprCallParameter(item, callTree, tokenStream, listener);
		}
		else {
			unknownCase(parentTree, tokenStream, listener);
		}
		
		return false;
	}

	protected static boolean processExprCallParameter(ItemElement item, Tree tree, TokenStream tokenStream, IErrorListener listener) {
		Tree symbolTree = tree.getChild(0);
		Tree paramTree = tree.getChild(1);

		List<Parameter> paramList = null;
		if(item instanceof BehaviorElement) {
			paramList = ((BehaviorElement)item).getParams();
		}
		else if(item instanceof ActionElement) {
			paramList = ((ActionElement)item).getParams();
		}
		else {
			return false;
		}
		
		Tree tokenTree = symbolTree.getChild(0);
		String token = tokenTree.getText();
		if(item.getName().equals(token) == false) {
			return false;
		}

		// symbol 토큰의 수가 2개 이상이면 Model의 Function호출
		int tokenCount = symbolTree.getChildCount();
		if(tokenCount > 1) {
			return false;
		}

		if(paramList != null) {
			int paramCount = paramTree.getChildCount();
			
			// 먼저 parameter의 순서와 이름이 같은지 검사한다. 
			for(Parameter parameter : paramList) {
				boolean find = false;
				for(int idx = 0; idx < paramCount; idx += 1) {
					Tree param = paramTree.getChild(idx);
					if("EXPRESSION".equals(param.getText())) {
						int dataCount = param.getChildCount();
						// EXPRCOND, EXPRESSIONACCOMP
						if(dataCount != 2) {
							return false;
						}
						Tree exprName = param.getChild(0);
						String paramToken = getSymbolName(exprName);
						if(paramToken.equals(parameter.getName()) == false) {
							continue;
						}
						find = true;
					}
					else {
						unknownCase(param, tokenStream, listener);
						break;
					}
				}
				
				if(find == false) {
					return false;
				}
			}

			// 먼저 parameter의 순서와 이름이 같다면, Value를 변경한다.
			for(Parameter parameter : paramList) {
				boolean find = false;
				for(int idx = 0; idx < paramCount; idx += 1) {
					Tree param = paramTree.getChild(idx);
					if("EXPRESSION".equals(param.getText())) {
						int dataCount = param.getChildCount();
						// EXPRCOND, EXPRESSIONACCOMP
						if(dataCount != 2) {
							return false;
						}
						Tree exprName = param.getChild(0);
						String paramToken = getSymbolName(exprName);
						if(paramToken.equals(parameter.getName()) == false) {
							continue;
						}
						find = true;
						Tree exprAccomp = param.getChild(1);
						// =, EXPRADD
						Tree exprValue = exprAccomp.getChild(1);
						parameter.setValue(tokenStream.toString(exprValue.getTokenStartIndex(), exprValue.getTokenStopIndex()));
					}
					else {
						unknownCase(param, tokenStream, listener);
						break;
					}
				}
				
				if(find == false) {
					return false;
				}
			}
		}
		
		return true;
	}

	protected static boolean updateCallParameterExpression(ItemElement item, Tree parentTree, StringBuilder outBuf, TokenStream tokenStream, IErrorListener listener) {
		boolean result = false;

		// Top Member
		if("INCLUDESTATEMENT".equals(parentTree.getText()) ||
			"ENUMDECL".equals(parentTree.getText()) ||
			"MODELDECL".equals(parentTree.getText()) ||
			"ACTIONDECL".equals(parentTree.getText()) ||
			"TASKDECL".equals(parentTree.getText()) ||
			"WORKERDECL".equals(parentTree.getText()) ||
			"TCONSTANT".equals(parentTree.getText()) ||
			"TSYMBOL".equals(parentTree.getText()) ||
			"TFUNC".equals(parentTree.getText()) ||
			"MODELDECL".equals(parentTree.getText()) ||
			"IFSTATEMENT".equals(parentTree.getText()) ||
			"GOTOSTATEMENT".equals(parentTree.getText()) ||
			"STAYSTATEMENT".equals(parentTree.getText()) ||
//			"CALLSTATEMENT".equals(parentTree.getText()) ||
			"STATEMENTSINGLE".equals(parentTree.getText()) ||
			"STATEMENTBLOCK".equals(parentTree.getText()) ||
			"LOCALVARSTATEMENT".equals(parentTree.getText()) ||
//			"EXPRCALL".equals(parentTree.getText()) ||
			"EXPRCOND".equals(parentTree.getText()) ||
			"EXPRESSION".equals(parentTree.getText()) ||
			"EXPRCOND".equals(parentTree.getText()) ||
			"EXPROR".equals(parentTree.getText()) ||
			"EXPRAND".equals(parentTree.getText()) ||
			"EXPRNOT".equals(parentTree.getText()) ||
			"EXPBOOL".equals(parentTree.getText()) ||
			"EXPRADD".equals(parentTree.getText()) ||
			"EXPRMUL".equals(parentTree.getText()) ||
			"EXPRVALUE".equals(parentTree.getText()) ||
			"EXPRSYMBOL".equals(parentTree.getText())) {
			outBuf.append(tokenStream.toString(parentTree.getTokenStartIndex(), parentTree.getTokenStopIndex()));
			outBuf.append("\r\n");
			return false;
		}
		else if("CALLSTATEMENT".equals(parentTree.getText())) {
			return processCallExpression(item, parentTree, outBuf, tokenStream, listener);
		}
		else if("EXPRCALL".equals(parentTree.getText())) {
			return processExprCallExpression(item, parentTree, outBuf, tokenStream, listener);
		}
		else {
			int childNum = parentTree.getChildCount();
			for (int i = 0; i < childNum; i++) {
				Tree child = (Tree) parentTree.getChild(i);
				if(updateCallParameterExpression(item, child, outBuf, tokenStream, listener)) {
					result = true;
				}
			}
		}

		return result;
	}

	protected static boolean processCallExpression(ItemElement item, Tree parentTree, StringBuilder outBuf, TokenStream tokenStream, IErrorListener listener) {
		Tree callTree = parentTree.getChild(0);
		
		if("EXPRCALL".equals(callTree.getText())) {
			return processExprCallExpression(item, callTree, outBuf, tokenStream, listener);
		}
		else {
			unknownCase(parentTree, tokenStream, listener);
		}

		return false;
	}

	protected static boolean processExprCallExpression(ItemElement item, Tree tree, StringBuilder outBuf, TokenStream tokenStream, IErrorListener listener) {
		Tree symbolTree = tree.getChild(0);
		Tree paramTree = tree.getChild(1);

		List<Parameter> paramList = null;
		if(item instanceof BehaviorElement) {
			paramList = ((BehaviorElement)item).getParams();
		}
		else if(item instanceof ActionElement) {
			paramList = ((ActionElement)item).getParams();
		}
		else {
			return false;
		}
		
		Tree tokenTree = symbolTree.getChild(0);
		String token = tokenTree.getText();
		if(item.getName().equals(token) == false) {
			return false;
		}

		// symbol 토큰의 수가 2개 이상이면 Model의 Function호출
		int tokenCount = symbolTree.getChildCount();
		if(tokenCount > 1) {
			return false;
		}

		outBuf.append(item.getName()).append("(");

		if(paramList != null) {
			int paramCount = paramTree.getChildCount();

			boolean find = false;
			// 먼저 parameter의 순서와 이름이 같은지 검사한다. 
			for(Parameter parameter : paramList) {
				for(int idx = 0; idx < paramCount; idx += 1) {
					Tree param = paramTree.getChild(idx);
					if("EXPRESSION".equals(param.getText())) {
						int dataCount = param.getChildCount();
						// EXPRCOND, EXPRESSIONACCOMP
						if(dataCount != 2) {
							return false;
						}
						Tree exprName = param.getChild(0);
						String paramToken = getSymbolName(exprName);
						if(paramToken.equals(parameter.getName()) == false) {
							continue;
						}
						find = true;
					}
					else {
						unknownCase(param, tokenStream, listener);
						break;
					}
				}

				if(find == false) {
					return false;
				}
			}
			
			int idx = 0;
			// 먼저 parameter의 순서와 이름이 같다면, Value를 변경한다.
			for(Parameter parameter : paramList) {
				if(idx> 0) {
					outBuf.append(", ");
				}
				outBuf.append(parameter.getName()).append("=").append(parameter.getValue());
				idx += 1;
			}
		}
		
		outBuf.append(");");
		
		return true;
	}

	protected static void unknownCase(Tree tree, TokenStream tokenStream, IErrorListener listener) {
		if(listener != null) {
			String lineString = tokenStream.toString(tree.getTokenStartIndex(), tree.getTokenStopIndex());
			int line = tree.getLine();
			int charStart = tree.getCharPositionInLine();
			int charEnd = charStart + tree.getText().length();
			listener.error(String.format("예상치 못한 토큰 : %s", lineString), line, charStart, charEnd);
		}
	}
	
	protected static String getSymbolName(Tree paramTree) {
		if("EXPRESSION".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRCOND".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPROR".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRAND".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRNOT".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPBOOL".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRADD".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRMUL".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRSIGN".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRVALUE".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = getSymbolName(childTree);
			
			return symbolName;
		}
		else if("EXPRSYMBOL".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = childTree.getText();
			
			return symbolName;
		}
		else if("EXPRPARAMASSIGN".equals(paramTree.getText())) {
			Tree childTree = paramTree.getChild(0);
			String symbolName = childTree.getText();
			
			return symbolName;
		}
		return "";
	}
}
