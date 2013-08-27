package kr.re.etri.tpl.diagram.dialogs;

import java.util.List;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.listener.IMessageProvider;
import kr.re.etri.tpl.diagram.util.MarkerProvider;
import kr.re.etri.tpl.grammar.GrammaUtil;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.antlr.runtime.RecognitionException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ActionStatementEditorDialog extends TitleAreaDialog{

	public static final String LABEL_BUTTON_OK = "OK";

	private Button okBtn, cancelBtn;

	private Text textEdit, errorState;
	
	private TableViewer paramTable;
	private Text syntaxEdit;

	private String contentText;

	private String title;

	private String message;

	private boolean editable;

	private ParserUtil parser = new ParserUtil();
	
	private Composite panel;
	
	private Composite actionPanel;
	
	private ModelDiagram rootModel;
	
	private ItemElement target;

	StringBuilder messageBuilder = new StringBuilder();
	private int msgType;

	private IErrorListener errorLogger;

	Listener listener;
	int[] events = new int[] {
			SWT.Dispose,
			SWT.Resize,  
			SWT.FocusIn,
			SWT.FocusOut,
			SWT.Activate,
			SWT.Deactivate};

	public ActionStatementEditorDialog(Shell shell, String title, String message, String contents){
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

		EList<Parameter> itemList = null;
		StringBuilder sb = new StringBuilder();
		if(target instanceof BehaviorElement) {
			itemList = ((BehaviorElement)target).getParams();
			sb.append("task").append(" ").append(target.getName()).append("(");
		}
		else if(target instanceof ActionElement) {
			itemList = ((ActionElement)target).getParams();
			sb.append("action").append(" ").append(target.getName()).append("(");
		}
		if(itemList != null) {
			for(int idx = 0; idx < itemList.size(); idx += 1) {
				Parameter param = itemList.get(idx);
				if(idx > 0) {
					sb.append(",");
				}
				sb.append(" ").append(param.getType());
				sb.append(" ").append(param.getName());

				TableItem item_ = new TableItem(paramTable.getTable(), SWT.NONE);
				item_.setText(new String[]{param.getType(), param.getName(), param.getValue()});
				item_.setData(param);
			}
			sb.append(");");
		}
		
		syntaxEdit.setText(sb.toString());
		syntaxEdit.setEditable(false);

		if(contentText != null) {
			textEdit.setText(contentText);
			try {
				messageBuilder.setLength(0);
				msgType = IMessageProvider.NONE;
				parser.checkStayAction(rootModel, contentText, errorLogger);

			} catch (RecognitionException re) {
//				System.out.println(re.getMessage());
			}
		}
		
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

		
		// 무조건 GridLayout이어야 하내?
		composite.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);


		FormData formData;
		panel = new Composite(composite, SWT.RESIZE);
		panel.setLayoutData(gridData);
		panel.setLayout(new FormLayout());

		actionPanel = new Composite(panel, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		formData.height = 130;
		actionPanel.setLayoutData(formData);
		actionPanel.setLayout(new GridLayout(1, true));

		textEdit = new Text(panel, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		formData.right = new FormAttachment(100, -5);
		formData.top = new FormAttachment(0, 5);
		formData.bottom = new FormAttachment(actionPanel, 0);
		textEdit.setLayoutData(formData);

		textEdit.setEnabled(editable);

		textEdit.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				setMessage(message);
				String inputStr = textEdit.getText();
				try {
					messageBuilder.setLength(0);
					if(parser.checkStayAction(rootModel, inputStr, errorLogger)) {
						if(paramTable == null) {
							return;
						}
						if(GrammaUtil.updateCallParameter(target, inputStr, errorLogger)) {
							Table table = (Table)paramTable.getControl();
							table.removeAll();

							EList<Parameter> itemList = null;
							if(target instanceof BehaviorElement) {
								itemList = ((BehaviorElement)target).getParams();
							}
							else if(target instanceof ActionElement) {
								itemList = ((ActionElement)target).getParams();
							}
							if(itemList != null) {
								for(int idx = 0; idx < itemList.size(); idx += 1) {
									Parameter param = itemList.get(idx);

									TableItem item_ = new TableItem(paramTable.getTable(), SWT.NONE);
									item_.setText(new String[]{param.getType(), param.getName(), param.getValue()});
									item_.setData(param);
								}
							}
						}
					}

				} catch (RecognitionException re) {
//					System.out.println(re.getMessage());
				}
			}
		});
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

		if(contentText != null) {
			textEdit.setText(contentText);
		}

		listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Dispose: onDispose(); break;
					case SWT.Resize: onResize(); break;
				}
			}
		};

		syntaxEdit = new Text(actionPanel, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;//.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		syntaxEdit.setLayoutData(gridData);

		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;
		paramTable = new TableViewer(actionPanel, style);
		Table table = (Table)paramTable.getControl();

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = 80;
		table.setLayoutData(gridData);
		
		String[] columnNames = new String[] {"Type", "Name", "Value"};
		int[] columnWidths = new int[] {80, 100, 270};
		int[] columnAlignments = new int[] {SWT.RIGHT, SWT.RIGHT, SWT.LEFT};

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		CellEditor[] editors = new CellEditor[3];
		editors[0] = null;
		editors[1] = null;
		editors[2] = new TextCellEditor(table);

		paramTable.setColumnProperties(new String[]{"Type", "Name", "Value"});
		paramTable.setCellModifier(new ParameterCellModifier(paramTable));
		paramTable.setCellEditors(editors);
		
		for (int i = 0; i < events.length; i++) {
			panel.addListener(events[i], listener);
		}
		
		textEdit.setFocus();

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
	
	public void setRootModel(ModelDiagram rootModel, ItemElement target) {
		this.rootModel = rootModel;
		this.target = target;
	}

	public String getText() {
		return contentText;
	}

	protected void onDispose() {
		for (int i = 0; i < events.length; i++) {
			panel.removeListener(events[i], listener);
		}
	}

	protected void onResize() {
		Point size = panel.getSize();
	}


	class ParameterCellModifier implements ICellModifier
	{
		private Viewer viewer;

		public ParameterCellModifier(Viewer viewer)
		{
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(Object element, String property)
		{
//			Object model = getModel();
//			if(model == null || (model instanceof ItemElement) == false) {
//				return false;
//			}
//
//			if(((ItemElement)getModel()).isIncluded()) {
//				return false;
//			}
			if("Value".equals(property)) {
				return true;
			}
			return false;
		}

		@Override
		public Object getValue(Object element, String property)
		{
			if(null != element && element instanceof Parameter)
			{
				Parameter param = (Parameter)element;

				if("Value".equals(property))
				{
					if(param.getValue() == null) {
						return "";
					}
					
					return param.getValue();
				}
			}

			return null;
		}

		@Override
		public void modify(Object element, String property, Object value)
		{
			if(element instanceof TableItem)
			{
				Parameter param_ = (Parameter)((TableItem)element).getData();

				if("Value".equals(property)) {
					((TableItem)element).setText(2, value.toString());
					param_.setValue(value.toString());

					StringBuilder sb = new StringBuilder();
					String inputStr = textEdit.getText();
					if(GrammaUtil.updateCallExpression(target, inputStr, sb, errorLogger)) {
						textEdit.setText(sb.toString());
					}
					else {
						sb.setLength(0);
						sb.append(inputStr).append("\r\n");
						
						sb.append(target.getName()).append("(");
						List<Parameter> paramList = null;
						if(target instanceof BehaviorElement) {
							paramList = ((BehaviorElement)target).getParams();
						}
						else if(target instanceof ActionElement) {
							paramList = ((ActionElement)target).getParams();
						}
						else {
							return;
						}

						int idx = 0;
						// 먼저 parameter의 순서와 이름이 같다면, Value를 변경한다.
						for(Parameter parameter : paramList) {
							if(idx > 0) {
								sb.append(", ");
							}
							sb.append(parameter.getName()).append("=").append(parameter.getValue());
							
							idx += 1;
						}
						sb.append(");");
						
						textEdit.setText(sb.toString());
					}
				}
			}
		}
	}
}