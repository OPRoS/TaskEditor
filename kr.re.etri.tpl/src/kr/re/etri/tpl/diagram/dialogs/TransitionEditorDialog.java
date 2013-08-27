package kr.re.etri.tpl.diagram.dialogs;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.MarkerProvider;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.antlr.runtime.RecognitionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TransitionEditorDialog extends TitleAreaDialog{

	public static final String LABEL_BUTTON_OK = "OK";

	private Button okBtn;

	private StyledText textEdit;

	private String contentText;

	private String title;

	private String message;
	private String inputStr;

	private boolean editable;

	private ParserUtil parser = new ParserUtil();

	private StringBuilder messageBuilder = new StringBuilder();
	private int msgType;

	private ModelDiagram rootModel;
	
	private IErrorListener errorLogger;

	public TransitionEditorDialog(Shell shell, String title, String message, String contents){
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

		errorLogger = new MarkerProvider() {

			@Override
			public void info(String message, int lineNum, int charStart, int charEnd) {
				super.info(message, lineNum, charStart, charEnd);
				
				if(message == null || message.length() == 0) {
					return;
				}
				
				messageBuilder.append(message).append("\r\n");

				if(msgType < IMessageProvider.INFORMATION) msgType = IMessageProvider.INFORMATION;

				setMessage(messageBuilder.toString(), msgType);
			}

			@Override
			public void warning(String message, int lineNum, int charStart, int charEnd) {
				super.warning(message, lineNum, charStart, charEnd);
				
				if(message == null || message.length() == 0) {
					return;
				}
				
				messageBuilder.append(message).append("\r\n");

				if(msgType < IMessageProvider.WARNING) msgType = IMessageProvider.WARNING;

				setMessage(messageBuilder.toString(), msgType);
			}

			@Override
			public void error(String message, int lineNum, int charStart, int charEnd) {
				super.error(message, lineNum, charStart, charEnd);
				
				if(message == null || message.length() == 0) {
					return;
				}
				
				messageBuilder.append(message).append("\r\n");

				if(msgType < IMessageProvider.ERROR) msgType = IMessageProvider.ERROR;

				setMessage(messageBuilder.toString(), msgType);
			}
		};

		
		composite.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);


		textEdit = new StyledText(composite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		textEdit.setLayoutData(gridData);

		if(contentText != null) {
			textEdit.setText(contentText);
		}

		textEdit.setEnabled(editable);

		textEdit.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				setMessage(message);

				inputStr = textEdit.getText().trim();

				if(inputStr.length() <= 0){
					setMessage(message);
					return;
				}

				try {
					messageBuilder.setLength(0);
					msgType = IMessageProvider.NONE;
					parser.checkExpression(rootModel, inputStr, errorLogger);

				} catch (RecognitionException re) {
					System.out.println(re.getMessage());
				}

			}

		});

		textEdit.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

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
		Button cancelBtn = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,
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

	public void setRootModel(ModelDiagram rootModel) {
		this.rootModel = rootModel;
	}
}