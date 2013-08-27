package kr.re.etri.tpl.script.editors.marking;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.utils.TPLLogger;

import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class TPLParserForErrorMarking extends RFSMParser {
	private IFile file;
	private IDocument doc;

	public TPLParserForErrorMarking(TokenStream input, IFile file,
			IDocument doc) {
		super(input);
		this.file = file;
		this.doc = doc;
	}

	

	public void removeExistingMarkers() {
		try {
			file.deleteMarkers(MarkerLogger.TYPE_SCRIPT, true, IResource.DEPTH_ZERO);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
	}

//	public static final String ERROR_MARKER_ID = "kr.re.etri.tpl.syntaxerrormarker";

	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		String msg = super.getErrorMessage(e, tokenNames);
		
		// KJH 20100715 s, mismatch일 경우 다음토큰에 에러가 표시되는 문제
		Token t = e.token;
		if(e instanceof MismatchedTokenException)
			t = ((TokenStream)e.input).get(e.index-1);
		// KJH 20100715 e, mismatch일 경우 다음토큰에 에러가 표시되는 문제
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IMarker.LOCATION, file.getFullPath().toString());
		map.put(IMarker.MESSAGE, msg);

//		Integer charStart = getCharStart(e.line, e.charPositionInLine);
		Integer charStart = getCharStart(t.getLine(), t.getCharPositionInLine());
		if (charStart != null) {
			map.put(IMarker.CHAR_START, charStart);
		}

//		Integer charEnd = getCharEnd(e.line, e.charPositionInLine, e.token);
		Integer charEnd = getCharEnd(t.getLine(), t.getCharPositionInLine(), t);
		if (charEnd != null)
			map.put(IMarker.CHAR_END, charEnd);

		map.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);

		try {
			MarkerUtilities.createMarker(file, map, MarkerLogger.TYPE_SCRIPT);
		} catch (CoreException ee) {
			ee.printStackTrace();
		}

		return msg;
	}

	private Integer getCharStart(int lineNumber, int columnNumber) {
		try {
			return new Integer(doc.getLineOffset(lineNumber - 1) + columnNumber);
		} catch (BadLocationException e) {
			TPLLogger.logInfo("TPLParserForErrorMarking.getCharStart()\t: BadLocation.. Line="+lineNumber+", Column="+columnNumber);
			return new Integer(0);
		}
	}
	
	private Integer getCharEnd(int lineNumber, int columnNumber, Token t) {
		int lineStartChar = getCharStart(lineNumber, columnNumber);
		// KJH 20100715, t.getText() null일경우 에러
		if(t.getText() != null)
			lineStartChar += t.getText().length();
		return new Integer(lineStartChar);
		
	}

}
