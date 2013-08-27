package kr.re.etri.tpl.diagram.views.controls;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Text;

/*
 * KJH 20101203, FocusAdapter for org.eclipse.swt.widgets.Text;
 */
public abstract class TextFocusAdapter implements FocusListener {

	private Text control;
	private String text;
	
	public TextFocusAdapter(Text control) {
		this.control = control;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		text = control != null ? control.getText() : "";
	}

	@Override
	public abstract void focusLost(FocusEvent e);

	public boolean isDirty() {
		return (control.getText().equals(text)) == false;
	}
}
