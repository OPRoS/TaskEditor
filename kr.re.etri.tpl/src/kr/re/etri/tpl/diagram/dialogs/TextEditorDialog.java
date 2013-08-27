package kr.re.etri.tpl.diagram.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextEditorDialog extends TitleAreaDialog {
	
	public static final String LABEL_BUTTON_OK = "OK";

	private Button okBtn, cancelBtn;

	private Text textEdit;

	private String contentText;

	private String title;
	
	private String message;
	
	private boolean editable;
	
	public TextEditorDialog(Shell shell, String title, String message, String contents){
		super(shell);
		setShellStyle(SWT.SHELL_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL | getDefaultOrientation());
		
		this.title = title;
		this.message = message;
		this.contentText = contents;
		this.editable = true;
	}

	protected Control createContents(Composite parent){
		Control contents = super.createContents(parent);
		
		Shell shell = getShell();
		Point size = new Point(500, 400);
		Point location = getInitialLocation(size);
		shell.setBounds(getConstrainedShellBounds(new Rectangle(location.x,
				location.y, size.x, size.y)));

		setTitle(title);
		setMessage(message);

		return contents;
		
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean getEditable() {
		return this.editable;
	}
	
	@Override
	public Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		
		composite.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		
		textEdit = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		textEdit.setLayoutData(gridData);

		if(contentText != null) {
			textEdit.setText(contentText);
		}
		
		textEdit.setEnabled(editable);
		
		textEdit.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				okBtn.setEnabled(editable);
			}
			
		});

		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		okBtn = createButton(parent, IDialogConstants.OK_ID, LABEL_BUTTON_OK, false);
		okBtn.setEnabled(false);
		cancelBtn = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,
				false);
	}

	protected void buttonPressed(int buttonId){
		this.setReturnCode(buttonId);
		if(buttonId== IDialogConstants.CANCEL_ID){
			close();
			return;
		}
		
		contentText = textEdit.getText();

		close();
	}
	
	public String getText() {
		return contentText;
	}


}