package kr.re.etri.tpl.script.emulator.dialogs;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.script.emulator.EmulatorManager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

public class EmulatorDialog2 extends Dialog {

	public final static String NAME_COLUMN = "Name";
	public final static String TYPE_COLUMN = "Type";
	public final static String ETYPE_COLUMN = "eType";
	public final static String VALUE_COLUMN = "Value";
	public final static String MIN_COLUMN = "Min";
	public final static String MAX_COLUMN = "Max";
	public final static String ENUM_COLUMN = "Enum";
	private String[] columnNames = new String[] {
			NAME_COLUMN,
			TYPE_COLUMN,
			ETYPE_COLUMN,
			VALUE_COLUMN,
			MIN_COLUMN,
			MAX_COLUMN,
			ENUM_COLUMN,
	};
	
	private Combo combo;
	private ContainerCheckedTreeViewer treeViewer;
	private IResource resource;
	
	public EmulatorDialog2(Shell parentShell, IResource resource) {
		super(parentShell);
		int style = (SWT.SHELL_TRIM | SWT.MODELESS) & ~SWT.MAX;
		setShellStyle(style);
		
		this.resource = resource;
	}
	
	public IResource getResource() {
		return resource;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Emulator");
	}

	@Override
	public boolean close() {
		List<EmulatorTreeItem> nonCheckedList = new ArrayList<EmulatorTreeItem>();
		List<EmulatorTreeItem> checkedList = new ArrayList<EmulatorTreeItem>();
		grouping(treeViewer.getInput(), null, checkedList, nonCheckedList);
		EmulatorManager.getDefault()
		.emul(checkedList.toArray(new EmulatorTreeItem[checkedList.size()]),
				nonCheckedList.toArray(new EmulatorTreeItem[nonCheckedList.size()]));
		EmulatorManager.getDefault().setDialog(null);
		return super.close();
	}

	@Override
	public int open() {
		EmulatorManager manager = EmulatorManager.getDefault();
		if (manager == null || manager.isConnected() == false) {
			Status status = new Status(IStatus.ERROR,
					TaskModelPlugin.PLUGIN_ID, 0, "Socket is not connected", null);
			ErrorDialog.openError(getShell(), "Engine Connection Error", "", status);
			return IDialogConstants.CANCEL_ID;
		}

		manager.setDialog(this);
		return super.open();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IEmulatorDialogConstants.SEND_ID) {
//			EmulatorManager.getDefault().send(createSendMessage());
		}
		if (buttonId == IEmulatorDialogConstants.REFRESH_ID) { // KJH 20111125
			update();
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		return super.createButtonBar(parent);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
//		createButton(parent, IEmulatorDialogConstants.SEND_ID, IEmulatorDialogConstants.SEND_LABEL, false);
		createButton(parent, IEmulatorDialogConstants.REFRESH_ID, IEmulatorDialogConstants.REFRESH_LABEL, false);
		createButton(parent, IEmulatorDialogConstants.DONE_ID, IEmulatorDialogConstants.DONE_LABEL, true);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(700, 400);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("Projects: ");
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		label.setLayoutData(gridData);
		
		combo = new Combo(composite, SWT.DROP_DOWN | SWT.SIMPLE | SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		combo.setLayoutData(gridData);

		combo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		Tree tree = createTree(composite);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		tree.setLayoutData(gridData);

		treeViewer = createTreeViewer(tree);
		treeViewer.setLabelProvider(new ModelTreeViewerLabelProvider());
		treeViewer.setContentProvider(new ModelTreeViewerContentProvider());
		treeViewer.setCellEditors(getTreeViewerCellEditors(tree));
		treeViewer.setCellModifier(getTreeViewerCellModifier());
//		treeViewer.setInput(EmulatorManager.getDefault().getInput(null));
//		treeViewer.expandAll();
		
		treeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getCheckable().equals(treeViewer)) {
					List<EmulatorTreeItem> nonCheckedList = new ArrayList<EmulatorTreeItem>();
					List<EmulatorTreeItem> checkedList = new ArrayList<EmulatorTreeItem>();
					Object[] checkedElements = treeViewer.getCheckedElements();
					grouping(treeViewer.getInput(), checkedElements, checkedList, nonCheckedList);
					EmulatorManager.getDefault()
					.emul(checkedList.toArray(new EmulatorTreeItem[checkedList.size()]),
							nonCheckedList.toArray(new EmulatorTreeItem[nonCheckedList.size()]));
				}
			}
		});
		
		updateButtons();
		update();
		
		return composite;
	}

	private void grouping(Object input,
			Object[] elements,
			List<EmulatorTreeItem> checkedList,
			List<EmulatorTreeItem> nonCheckedList) {
		Assert.isNotNull(checkedList);
		Assert.isNotNull(nonCheckedList);

		if (input instanceof EmulatorTreeItem) {
			EmulatorTreeItem item = (EmulatorTreeItem)input;
			int itemType = item.getItemType();
			if (EmulatorTreeItem.FUNCTION == itemType ||
					EmulatorTreeItem.SYMBOL == itemType) {
				boolean isChecked = false;
				if (elements != null) {
					for (Object element : elements) {
						if (item.equals(element)) {
							isChecked = true;
							break;
						}
					}
				}
				if (isChecked) {
					checkedList.add(item);
				}
				else {
					nonCheckedList.add(item);
				}
			}
			else {
				for (EmulatorTreeItem child : item.getChildren()) {
					grouping(child, elements, checkedList, nonCheckedList);
				}
			}
		}
	}
	
	private Tree createTree(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK;
		Tree tree = new Tree(parent, style);
		
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		int columnWidths[] = new int[] {
			170, 60, 70, 80, 80, 80, 80
		};
		
		for (int i = 0; i < columnNames.length; i++) {
			TreeColumn column = new TreeColumn(tree, SWT.LEFT);
			column.setText(columnNames[i]);
			column.setWidth(columnWidths[i]);
		}
		
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
				null,
				new ComboBoxCellEditor(parent, EType.getETypes()),
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				new TextCellEditor(parent),
		};
	}
	
	private ICellModifier getTreeViewerCellModifier() {
		return new ICellModifier() {
			
			private int getColumnIndex(String property) {
				for (int i = 0; i < columnNames.length; i++) {
					if (columnNames[i].equals(property)) {
						return i;
					}
				}
				return -1;
			}
			
			private void setChildrenEType(EmulatorTreeItem item, String value) {
				item.setEtype(value);
				for (EmulatorTreeItem child : item.getChildren()) {
					setChildrenEType(child, value);
				}
			}
			
			private void setParentEtype(EmulatorTreeItem item, String value) {
				item.setEtype(value);
				EmulatorTreeItem parent = item.getParent();
				if (parent != null) {
					for (EmulatorTreeItem child : parent.getChildren()) {
						if (value.equals(child.getEtype()) == false) {
							parent.setEtype("");
							return;
						}
					}
					parent.setEtype(value);
				}
			}
			
			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof TreeItem == false) {
					return;
				}
				
				TreeItem treeItem = (TreeItem)element; 
				Object data = treeItem.getData();
				if (data instanceof EmulatorTreeItem) {
					EmulatorTreeItem item = (EmulatorTreeItem)data;
					if (property.equals(NAME_COLUMN)) {
						item.setName((String)value);
						treeItem.setText(getColumnIndex(property), (String)value);
					}
					else if (property.equals(TYPE_COLUMN)) {
						item.setType((String)value);
						treeItem.setText(getColumnIndex(property), (String)value);
					}
					else if (property.equals(ETYPE_COLUMN)) {
						EType etype = EType.get((Integer)value);
						if (etype != null) {
							setChildrenEType(item, etype.getName());
							setParentEtype(item, etype.getName());
						}
						treeViewer.refresh();
						EmulatorManager.getDefault().set(item);
					}
					else if (property.equals(VALUE_COLUMN)) {
						item.setValue((String)value);
						treeItem.setText(getColumnIndex(property), (String)value);
						if (EType.VALUE == EType.get(item.getEtype())) {
							EmulatorManager.getDefault().set(item);
						}
					}
					else if (property.equals(MIN_COLUMN)) {
						item.setMin((String)value);
						treeItem.setText(getColumnIndex(property), (String)value);
						if (EType.UNIFURM == EType.get(item.getEtype()) ||
								EType.GAUSSIAN == EType.get(item.getEtype())) {
							EmulatorManager.getDefault().set(item);
						}
					}
					else if (property.equals(MAX_COLUMN)) {
						item.setMax((String)value);
						treeItem.setText(getColumnIndex(property), (String)value);
						if (EType.UNIFURM == EType.get(item.getEtype()) ||
								EType.GAUSSIAN == EType.get(item.getEtype())) {
							EmulatorManager.getDefault().set(item);
						}
					}
					else if (property.equals(ENUM_COLUMN)) {
						item.setEnumm((String)value);
						treeItem.setText(getColumnIndex(property), (String)value);
						if (EType.ENUM == EType.get(item.getEtype())) {
							EmulatorManager.getDefault().set(item);
						}
					}
				}
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof EmulatorTreeItem == false) {
					return null;
				}
				
				EmulatorTreeItem item = (EmulatorTreeItem)element;
				if (property.equals(NAME_COLUMN)) {
					return item.getName();
				}
				if (property.equals(TYPE_COLUMN)) {
					return item.getType();
				}
				if (property.equals(ETYPE_COLUMN)) {
					EType etype = EType.get(item.getEtype());
					if (etype != null) {
						return etype.getValue();
					}
					return -1;
				}
				if (property.equals(VALUE_COLUMN)) {
					return item.getValue();
				}
				if (property.equals(MIN_COLUMN)) {
					return item.getMin();
				}
				if (property.equals(MAX_COLUMN)) {
					return item.getMax();
				}
				if (property.equals(ENUM_COLUMN)) {
					return item.getEnumm();
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
				if (property.equals(ETYPE_COLUMN)) {
					return true;
				}
				if (property.equals(VALUE_COLUMN) ||
						property.equals(MIN_COLUMN) ||
						property.equals(MAX_COLUMN) ||
						property.equals(ENUM_COLUMN)) {
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
	
	private void updateButtons() {
		boolean isConnected = EmulatorManager.getDefault().isConnected();
		
		Button button = getButton(IEmulatorDialogConstants.SEND_ID);
		if (button != null) {
			button.setEnabled(isConnected);
		}
	}
	
	public void update() {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		String text = null;
		if (combo.getSelectionIndex() >= 0) {
			text = combo.getItem(combo.getSelectionIndex());
		}
		if (text == null || text.equals("")) {
			text = resource != null ? resource.getName() : "";
		}
		
		if (workspaceRoot != null) {
			int select = -1;
			IProject project = null;
			IProject[] projects = workspaceRoot.getProjects();
			String[] names = new String[projects.length];
			for (int i = 0; i < projects.length; i++) {
				names[i] = projects[i].getName();
				if (names[i].equals(text)) {
					select = i;
					project = projects[i];
				}
			}
			
			combo.setItems(names);
			combo.select(select);
			resource = project;
			
			if (project != null) {
				treeViewer.setInput(EmulatorManager.getDefault().getInput(project));
				treeViewer.expandAll();
			}
		}
	}
	
}
