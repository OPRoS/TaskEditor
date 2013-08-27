package kr.re.etri.tpl.script.emulator.dialogs;
import java.io.IOException;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.script.emulator.EmulatorMessageClient;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;


public class EmulatorDialog extends Dialog {

	public final static String NAME_COLUMN = "Name";
	public final static String TYPE_COLUMN = "Type";
	public final static String VALUE_COLUMN = "Value";
	private String[] columnNames = new String[] {
			NAME_COLUMN,
			TYPE_COLUMN,
			VALUE_COLUMN,
	};

	private final static String DEFAULT_IP = "127.0.0.1";
	private final static Integer DEFAULT_PORT = 6011;
	
	private Text ipText;
	private Text portText;
	private ContainerCheckedTreeViewer treeViewer;
	private EmulatorTreeItem input;
	
	private EmulatorMessageClient client;
	
	
	public EmulatorDialog(Shell parentShell) {
		super(parentShell);
		int style = (SWT.SHELL_TRIM | SWT.MODELESS) & ~SWT.MAX;
		setShellStyle(style);
	}

	@Override
	public boolean close() {
		if (client != null) {
			client.stop();
			client = null;
		}
		return super.close();
	}

	@Override
	public void create() {
		super.create();
		
		String ip;
		int port;
		if (ipText != null && !ipText.isDisposed()) {
			ip = ipText.getText();
		}
		else {
			ip = DEFAULT_IP;
		}
		
		if (portText != null && !portText.isDisposed()) {
			try {
			port = Integer.parseInt(portText.getText());
			} catch(NumberFormatException e) {
				port = DEFAULT_PORT;
			}
		}
		else {
			port = DEFAULT_PORT;
		}
		
		try {
			client = EmulatorMessageClient.open(ip, port);
			if (client != null && client.isConnected()) {
				new Thread(client).start();
			}
		} catch (IOException e) {
			client = null;
			Status status = new Status(IStatus.ERROR,
					TaskModelPlugin.PLUGIN_ID, 0, e.getMessage(), null);
			ErrorDialog.openError(getShell(), "Engine Connection Error", "", status);
		} finally {
			updateButtons();
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IEmulatorDialogConstants.CONNECT_ID) {
			String ip;
			int port;
			if (ipText != null && !ipText.isDisposed()) {
				ip = ipText.getText();
			}
			else {
				ip = DEFAULT_IP;
			}
			
			if (portText != null && !portText.isDisposed()) {
				try {
				port = Integer.parseInt(portText.getText());
				} catch(NumberFormatException e) {
					port = DEFAULT_PORT;
				}
			}
			else {
				port = DEFAULT_PORT;
			}
			
			try {
				client = EmulatorMessageClient.open(ip, port);
			} catch (IOException e) {
				client = null;
				Status status = new Status(IStatus.ERROR,
						TaskModelPlugin.PLUGIN_ID, 0, e.getMessage(), null);
				ErrorDialog.openError(getShell(), "Engine Connection Error", "", status);
			} finally {
				updateButtons();
			}
		}
		else if (buttonId == IEmulatorDialogConstants.DISCONNECT_ID) {
			if (client != null) {
				client.stop();
				client = null;
			}
			updateButtons();
		}
		else if (buttonId == IEmulatorDialogConstants.SEND_ID) {
			if (client != null) {
				client.sendMessage(createSendMessage());
			}
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IEmulatorDialogConstants.SEND_ID, IEmulatorDialogConstants.SEND_LABEL, false);
		createButton(parent, IEmulatorDialogConstants.DONE_ID, IEmulatorDialogConstants.DONE_LABEL, true);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 400);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		
		// connect panel
		Composite connectPanel = new Composite(composite, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		connectPanel.setLayoutData(gridData);
		layout = new GridLayout(6, false);
		connectPanel.setLayout(layout);
		
		Label label = new Label(connectPanel, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		label.setLayoutData(gridData);
		label.setText("&IP:");
		
		ipText = new Text(connectPanel, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		ipText.setLayoutData(gridData);
		ipText.setText(DEFAULT_IP);
		
		label = new Label(connectPanel, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		label.setLayoutData(gridData);
		label.setText("&POPT:");
		
		portText = new Text(connectPanel, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData();
		gridData.widthHint = 40;
		portText.setLayoutData(gridData);
		portText.setText(Integer.toString(DEFAULT_PORT));
		
//		Button button = new Button(connectPanel, SWT.PUSH);
//		button.setText(IEmulatorDialogConstants.CONNECT_LABEL);
//		button.setData(new Integer(IEmulatorDialogConstants.CONNECT_ID));
		Button button = createButton(connectPanel,
				IEmulatorDialogConstants.CONNECT_ID,
				IEmulatorDialogConstants.CONNECT_LABEL,
				false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.horizontalIndent = 50;
		button.setLayoutData(gridData);

//		button = new Button(connectPanel, SWT.PUSH);
//		button.setText(IEmulatorDialogConstants.DISCONNECT_LABEL);
//		button.setData(new Integer(IEmulatorDialogConstants.DISCONNECT_ID));
		button = createButton(connectPanel,
				IEmulatorDialogConstants.DISCONNECT_ID,
				IEmulatorDialogConstants.DISCONNECT_LABEL,
				false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		button.setLayoutData(gridData);

		// content panel
		Composite contentPanel = new Composite(composite, SWT.NONE);
		gridData = new GridData(GridData.FILL_BOTH);
		contentPanel.setLayoutData(gridData);
		layout = new GridLayout();
		contentPanel.setLayout(layout);
		
		Tree tree = createTree(contentPanel);
		gridData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gridData);

		treeViewer = createTreeViewer(tree);
		treeViewer.setLabelProvider(new ModelTreeViewerLabelProvider());
		treeViewer.setContentProvider(new ModelTreeViewerContentProvider());
		treeViewer.setCellEditors(getTreeViewerCellEditors(tree));
		treeViewer.setCellModifier(getTreeViewerCellModifier());
		treeViewer.setInput(getInput());
		treeViewer.expandAll();
		treeViewer.setCheckedElements(getInput().getChildren());
		
		return composite;
	}

	public EmulatorTreeItem getInput() {
		if (input == null) {
			input = createInput();
		}
		return input;
	}
	
	private EmulatorTreeItem createInput() {
		EmulatorTreeItem root = new EmulatorTreeItem("root");
		
		EmulatorTreeItem model = new EmulatorTreeItem(root, "obstacle", EmulatorTreeItem.MODEL);
		EmulatorTreeItem child = new EmulatorTreeItem(model, "seeing", EmulatorTreeItem.SYMBOL);
		child.setType("int");
		child = new EmulatorTreeItem(model, "id", EmulatorTreeItem.SYMBOL);
		child.setType("int");
		child = new EmulatorTreeItem(model, "location", EmulatorTreeItem.SYMBOL);
		child.setType("int");
		child = new EmulatorTreeItem(model, "accuracy", EmulatorTreeItem.SYMBOL);
		child.setType("int");
		child = new EmulatorTreeItem(model, "getPersonName", EmulatorTreeItem.FUNCTION);
		child.setType("string");
		
		model = new EmulatorTreeItem(root, "robot", EmulatorTreeItem.MODEL);
		child = new EmulatorTreeItem(model, "turnLeft", EmulatorTreeItem.FUNCTION);
		child.setType("void");
		child = new EmulatorTreeItem(model, "turnRight", EmulatorTreeItem.FUNCTION);
		child.setType("void");
		child = new EmulatorTreeItem(model, "forward", EmulatorTreeItem.FUNCTION);
		child.setType("void");
		child = new EmulatorTreeItem(model, "backward", EmulatorTreeItem.FUNCTION);
		child.setType("void");
		child = new EmulatorTreeItem(model, "stop", EmulatorTreeItem.FUNCTION);
		child.setType("void");
		child = new EmulatorTreeItem(model, "stop", EmulatorTreeItem.FUNCTION);
		child.setType("void");
		child = new EmulatorTreeItem(model, "tts", EmulatorTreeItem.FUNCTION);
		child.setType("void");

		return root;
	}
	
	private Tree createTree(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK;
		Tree tree = new Tree(parent, style);
		
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(NAME_COLUMN);
		column.setWidth(150);

		column = new TreeColumn(tree, SWT.LEFT);
		column.setText(TYPE_COLUMN);
		column.setWidth(100);

		column = new TreeColumn(tree, SWT.LEFT);
		column.setText(VALUE_COLUMN);
		column.setWidth(150);

		return tree;
	}
	
	private ContainerCheckedTreeViewer createTreeViewer(Tree tree) {
		ContainerCheckedTreeViewer treeViewer = new ContainerCheckedTreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		treeViewer.setColumnProperties(columnNames);
		return treeViewer;
	}
	
	private CellEditor[] getTreeViewerCellEditors(Composite parent) {
		return new CellEditor[] {
				null,
				new TextCellEditor(parent),
				new TextCellEditor(parent),
		};
	}
	
	private ICellModifier getTreeViewerCellModifier() {
		return new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof TreeItem == false) {
					return;
				}
				
				Object data = ((TreeItem)element).getData();
				
				if (property.equals(NAME_COLUMN)) {
					if (data instanceof EmulatorTreeItem) {
						((EmulatorTreeItem)data).setName((String)value);
						((TreeItem)element).setText(0, (String)value);
					}
				}
				if (property.equals(TYPE_COLUMN)) {
					if (data instanceof EmulatorTreeItem) {
						((EmulatorTreeItem)data).setType((String)value);
						((TreeItem)element).setText(1, (String)value);
					}
				}
				if (property.equals(VALUE_COLUMN)) {
					if (data instanceof EmulatorTreeItem) {
						((EmulatorTreeItem)data).setValue((String)value);
						((TreeItem)element).setText(2, (String)value);
					}
				}
				
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if (property.equals(NAME_COLUMN)) {
					if (element instanceof EmulatorTreeItem) {
						return ((EmulatorTreeItem)element).getName();
					}
				}
				if (property.equals(TYPE_COLUMN)) {
					if (element instanceof EmulatorTreeItem) {
						return ((EmulatorTreeItem)element).getType();
					}
				}
				if (property.equals(VALUE_COLUMN)) {
					if (element instanceof EmulatorTreeItem) {
						return ((EmulatorTreeItem)element).getValue();
					}
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if (property.equals(NAME_COLUMN)) {
					return false;
				}
				if (property.equals(TYPE_COLUMN)) {
					return false;
				}
				if (property.equals(VALUE_COLUMN)) {
					if (element instanceof EmulatorTreeItem) {
						EmulatorTreeItem item = (EmulatorTreeItem)element;
						if (EmulatorTreeItem.FUNCTION == item.getItemType() &&
								RTMType.VOID.getName().equals(item.getType()) == false) {
							return true;
						}
						if (EmulatorTreeItem.SYMBOL == item.getItemType()) {
							return true;
						}
					}
					return false;
				}
				return false;
			}
		};
	}
	
	private String createSendMessage() {
		StringBuilder sb = new StringBuilder();
		
		Object[] checkedElements = treeViewer.getCheckedElements();
		for (int i = 0; i < checkedElements.length; i++) {
			if (checkedElements[i] instanceof EmulatorTreeItem) {
				EmulatorTreeItem treeItem = (EmulatorTreeItem)checkedElements[i];
				if (treeItem.getItemType() == EmulatorTreeItem.FUNCTION ||
						treeItem.getItemType() == EmulatorTreeItem.SYMBOL) {
					if (treeItem.getValue().length() > 0) {
						sb.append("REQ-start;set;");
						sb.append(treeItem.toString());
						sb.append("REQ-end\r\n");
					}
				}
			}
		}
		
		return sb.toString();
	}
	
	private void updateButtons() {
		boolean isConnected = client != null && client.isConnected();
		
		Button button = getButton(IEmulatorDialogConstants.CONNECT_ID);
		if (button != null) {
			button.setEnabled(!isConnected);
		}
		
		button = getButton(IEmulatorDialogConstants.DISCONNECT_ID);
		if (button != null) {
			button.setEnabled(isConnected);
		}
		
		button = getButton(IEmulatorDialogConstants.SEND_ID);
		if (button != null) {
			button.setEnabled(isConnected);
		}
	}
}
