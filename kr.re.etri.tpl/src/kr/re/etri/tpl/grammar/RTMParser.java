package kr.re.etri.tpl.grammar;

import java.util.ArrayList;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.listener.ITraceListener;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.MissingTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.UnwantedTokenException;

public class RTMParser extends RFSMParser {

	private ArrayList<ITraceListener> listeners = new ArrayList<ITraceListener>();
	
	public void setErrorListener(IErrorListener listener) {
		super.setErrorListener(listener);
	}
	
	public IErrorListener getErrorListener() {
		return super.getErrorListener();
	}

	public RTMParser(TokenStream input) {
		super(input);
	}

	public RTMParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	public void addTraceListener(ITraceListener listener) {
		if(listeners.contains(listener)) {
			return;
		}

		listeners.add(listener);
	}

	public void removeTraceListener(ITraceListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void emitErrorMessage(String msg) {
		// System.err.println(msg);
	}


	protected void enterRule(int rule, Parser parser, ParserRuleReturnScope returnScope){
		int size = listeners.size();
		for(int idx = 0; idx < size; idx += 1) {
			ITraceListener listener = listeners.get(idx);
			listener.enterRule(rule, parser, returnScope);
		}
	};


	protected void exitRule(int rule, Parser parser, ParserRuleReturnScope returnScope){
		int size = listeners.size();
		for(int idx = 0; idx < size; idx += 1) {
			ITraceListener listener = listeners.get(idx);
			listener.exitRule(rule, parser, returnScope);
		}
	};


	protected void enterSubRule(int subRule, Parser parser, ParserRuleReturnScope returnScope){
		int size = listeners.size();
		for(int idx = 0; idx < size; idx += 1) {
			ITraceListener listener = listeners.get(idx);
			listener.enterSubRule(subRule, parser, returnScope);
		}
	};


	protected void exitSubRule(int subRule, Parser parser, ParserRuleReturnScope returnScope){
		int size = listeners.size();
		for(int idx = 0; idx < size; idx += 1) {
			ITraceListener listener = listeners.get(idx);
			listener.exitSubRule(subRule, parser, returnScope);
		}
	};
	
	public String getErrorHeader(RecognitionException e) {
		return String.format("line %d:%d", e.line, e.charPositionInLine);
	}

	@Override
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		String msg = null, lineText = null;

		if ( e instanceof UnwantedTokenException ) {
			UnwantedTokenException ute = (UnwantedTokenException)e;
			String tokenName="<unknown>";
			if ( ute.expecting== Token.EOF ) {
				tokenName = "EOF";
			}
			else {
				tokenName = tokenNames[ute.expecting];
			}
			msg = "extraneous input "+getTokenErrorDisplay(ute.getUnexpectedToken())+
				" expecting "+tokenName;
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 0);
			}
		}
		else if ( e instanceof MissingTokenException ) {
			MissingTokenException mte = (MissingTokenException)e;
			String tokenName="<unknown>";
			if ( mte.expecting== Token.EOF ) {
				tokenName = "EOF";
			}
			else {
				tokenName = tokenNames[mte.expecting];
			}
			msg = "missing "+tokenName+" at "+getTokenErrorDisplay(e.token);
			
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 0);
			}
		}
		else if ( e instanceof MismatchedTokenException ) {
			MismatchedTokenException mte = (MismatchedTokenException)e;
			String tokenName="<unknown>";
			if ( mte.expecting == Token.EOF ) {
				tokenName = "EOF";
			}
			else {
				tokenName = tokenNames[mte.expecting];
			}
//			msg = "mismatched input "+getTokenErrorDisplay(e.token)+" expecting "+tokenName;

			if( getTokenErrorDisplay(e.token).equals("'<EOF>'")){
				msg ="���� �������� "+ tokenNames[mte.expecting] + "��(��) �ʿ��մϴ�.";
			}else{
				StringBuilder sb_ = new StringBuilder();
				sb_.append(getTokenErrorDisplay(e.token));
				sb_.append("�� ���� �����Դϴ�. ");
				if(mte.expecting != Token.EOF) {
					sb_.append("'").append(tokenName).append("'");
					sb_.append("��(��) �ʿ��մϴ�.");
				}
				msg = sb_.toString();
				
			}
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 1);
			}
		}
		else if ( e instanceof MismatchedTreeNodeException ) {

			MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
			String tokenName="<unknown>";
			if ( mtne.expecting==Token.EOF ) {
				tokenName = "EOF";
			}

			else {
				tokenName = tokenNames[mtne.expecting];
			}
			msg = "mismatched tree node: "+mtne.node+
				" expecting "+tokenName;
			
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 1);
			}
		}
		else if ( e instanceof NoViableAltException ) {
			NoViableAltException nvae = (NoViableAltException)e;
			// for development, can add "decision=<<"+nvae.grammarDecisionDescription+">>"
			// and "(decision="+nvae.decisionNumber+") and
			// "state "+nvae.stateNumber
			msg = "no viable alternative at input "+getTokenErrorDisplay(e.token);

			if( getTokenErrorDisplay(e.token).equals("'<EOF>'")){
				// �Է��� �� �ʿ��� ���
				msg = "������ �ϼ����� �ʾҽ��ϴ�.";

			}else{
//				lineText = textEdit.getLine(nvae.line-1);
				lineText = e.token.getText();

				StringBuilder sb_ = new StringBuilder();
				sb_.append(nvae.line).append("��");
				sb_.append(nvae.charPositionInLine +1).append("����  ����(��) \"");
				sb_.append(lineText).append("\"��  �߸��Ǿ����ϴ�.");
				msg = sb_.toString();
			}
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 1);
			}
		}
		else if ( e instanceof EarlyExitException ) {
			EarlyExitException eee = (EarlyExitException)e;
			// for development, can add "(decision="+eee.decisionNumber+")"
			//msg = "required (...)+ loop did not match anything at input "+ getTokenErrorDisplay(e.token);

			lineText = getTokenErrorDisplay(eee.token);
			
			StringBuilder sb_ = new StringBuilder();
			sb_.append(eee.line).append("��");
			sb_.append(eee.charPositionInLine +1).append("����  ����(��) ");
			sb_.append(lineText).append(" ������ ������ �߻��Ͽ����ϴ�.");
			msg = sb_.toString();
			
			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}
		else if ( e instanceof MismatchedSetException ) {
			MismatchedSetException mse = (MismatchedSetException)e;
			if( getTokenErrorDisplay(e.token).equals("'<EOF>'")){
				msg = mse.line + "���� ������ " + mse.expecting + "���� �ʿ��մϴ�.";
			}else{
				msg = mse.line + "�� " + mse.charPositionInLine + "������ "+
					getTokenErrorDisplay(e.token)+ " ��� " + mse.expecting + "���� �ʿ��մϴ�.";
			}
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 1);
			}
		}
		else if ( e instanceof MismatchedNotSetException ) {
			MismatchedNotSetException mse = (MismatchedNotSetException)e;
			msg= "�߸��� �Է� ���� "+getTokenErrorDisplay(e.token)+ "��� "+ mse.expecting + "���� �ʿ��մϴ�.";
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 1);
			}
		}
		else if ( e instanceof FailedPredicateException ) {
			/*FailedPredicateException fpe = (FailedPredicateException)e;
			msg = "rule "+fpe.ruleName+" failed predicate: {"+
				fpe.predicateText+"}?";*/
			msg = "������ ������ �߻��Ͽ����ϴ� : FailedPredicateException";
			
			if(e.token instanceof CommonToken) {
				CommonToken token = (CommonToken)e.token;
				getErrorListener().error(msg, e.line, token.getStartIndex(), token.getStopIndex()+1);
			}
			else {
				getErrorListener().error(msg, e.line, 0, 1);
			}
		}
		else {
			super.getErrorMessage(e, tokenNames);
		}

		return msg;
	}
}
