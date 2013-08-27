package kr.re.etri.tpl.grammar;

import kr.re.etri.tpl.diagram.listener.IErrorListener;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedRangeException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class RTMLexer extends RFSMLexer {
	private IErrorListener listener = null;
	
	public void setErrorListener(IErrorListener listener) {
		this.listener = listener;
	}
	
	public IErrorListener getErrorListener() {
		return this.listener;
	}


	public RTMLexer(CharStream input) {
		super(input);
	}

	public RTMLexer(CharStream input, RecognizerSharedState state) {
		super(input, state);
	}
	
	@Override
	public void emitErrorMessage(String msg) {
		// System.err.println(msg);
	}
	

	@Override
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		String msg = null;

		if ( e instanceof MismatchedTokenException ) {
			MismatchedTokenException mte = (MismatchedTokenException)e;
			//msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting "+getCharErrorDisplay(mte.expecting);

			if (getCharErrorDisplay(e.c).equals("'<EOF>'")){
				msg = mte.line + "�� ���� "+ getCharErrorDisplay(mte.expecting)+ "��(��) �ʿ��մϴ�.";
			}else{
				msg = mte.line + "�� "+ mte.charPositionInLine +"���� "+ getCharErrorDisplay(mte.expecting)+ "��(��) �ʿ��մϴ�.";
			}

			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}
		else if ( e instanceof NoViableAltException ) {
			NoViableAltException nvae = (NoViableAltException)e;
			// for development, can add "decision=<<"+nvae.grammarDecisionDescription+">>"
			// and "(decision="+nvae.decisionNumber+") and
			// "state "+nvae.stateNumber
			if (getCharErrorDisplay(e.c).equals("'<EOF>'")){
				msg = nvae.line + "�� ������ ���������� �ֽ��ϴ�.";
			}else{
				msg = nvae.line + "�� "+ nvae.charPositionInLine +"���� "+ getCharErrorDisplay(e.c)+ "��(��) �߸��Ǿ����ϴ�.";
			}

			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}
		else if ( e instanceof EarlyExitException ) {
			EarlyExitException eee = (EarlyExitException)e;
			// for development, can add "(decision="+eee.decisionNumber+")"
			msg = "required (...)+ loop did not match anything at character "+getCharErrorDisplay(e.c);

			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}
		else if ( e instanceof MismatchedNotSetException ) {
//			MismatchedNotSetException mse = (MismatchedNotSetException)e;
//			msg= "�߸��� �Է� ���� "+getCharErrorDisplay(e.c)+ "��� "+ mse.expecting + "���� �ʿ��մϴ�.";
//
//			fireMessage(msg, IMessageProvider.ERROR);

			MismatchedNotSetException mse = (MismatchedNotSetException)e;
			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting set "+mse.expecting;
			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}
		else if ( e instanceof MismatchedSetException ) {
			MismatchedSetException mse = (MismatchedSetException)e;
			if( getCharErrorDisplay(e.c).equals("'<EOF>'")){
				msg = mse.line + "���� ������ " + mse.expecting + "���� �ʿ��մϴ�.";
			}else{
				msg = mse.line + "�� " + mse.charPositionInLine + "������ "+
				getCharErrorDisplay(e.c)+ " ��� " + mse.expecting + "���� �ʿ��մϴ�.";
			}

//			MismatchedSetException mse = (MismatchedSetException)e;
//			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting set "+mse.expecting;

			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}
		else if ( e instanceof MismatchedRangeException ) {
			MismatchedRangeException mre = (MismatchedRangeException)e;
			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting set "+
				  getCharErrorDisplay(mre.a)+".."+getCharErrorDisplay(mre.b);
			
			getErrorListener().error(msg, e.line, e.charPositionInLine, e.charPositionInLine+1);
		}

		return msg;
	}
}
