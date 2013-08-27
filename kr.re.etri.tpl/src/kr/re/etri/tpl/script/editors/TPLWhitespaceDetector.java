package kr.re.etri.tpl.script.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class TPLWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
