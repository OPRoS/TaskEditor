package kr.re.etri.tpl.diagram.views.taskcontrol.dialog;

import kr.re.etri.tpl.diagram.views.taskcontrol.ITaskControlListener;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TaskListDialog extends Dialog implements ITaskControlListener,
		ISelectionChangedListener {
	private TaskControlManager manager = TaskControlManager.getDefault();
	private TableViewer taskViewer;	// KJH 20101119, ListViewer -> TableViewer 교체
	
	private final String RUNNING_COLUMN = "running";
	private final String TASK_COLUMN = "task";
	private String[] columnNames = new String[] {
			RUNNING_COLUMN,
			TASK_COLUMN
	};

	public TaskListDialog(Shell parentShell) {
		super(parentShell);
		int style = (SWT.SHELL_TRIM | SWT.MODELESS) & ~SWT.MAX;
		setShellStyle(style);
	}

	@Override
	public boolean close() {
		TaskControlManager.getDefault().removeListener(this);
		return super.close();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == ITaskListDialogConstants.DONE_ID) {
			cancelPressed();
		}
		
		ISelection selection = taskViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object fe = ((IStructuredSelection)selection).getFirstElement();
			if (fe != null) {
				String currentTask = fe.toString();
				if (buttonId == ITaskListDialogConstants.RUN_ID) {
					manager.run(currentTask);
				}
				else if (buttonId == ITaskListDialogConstants.STOP_ID) {
					manager.stop(currentTask);
				}
				else if (buttonId == ITaskListDialogConstants.UNDEPLOY_ID) {
					manager.undeploy(currentTask);
				}
			}
		}
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control buttonBar = super.createButtonBar(parent);
		
		// KJH 20101112 s, buttonBar의 button배열이 세로로 되도록 수정
		if (buttonBar instanceof Composite) {
			Composite composite = (Composite)buttonBar;
			GridLayout layout = new GridLayout(1, true);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER
					| GridData.VERTICAL_ALIGN_BEGINNING));
		}
		// KJH 20101112 e, buttonBar의 button배열이 세로로 되도록 수정
		
		return buttonBar;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, ITaskListDialogConstants.RUN_ID, ITaskListDialogConstants.RUN_LABEL, false);
		createButton(parent, ITaskListDialogConstants.STOP_ID, ITaskListDialogConstants.STOP_LABEL, false);
		createButton(parent, ITaskListDialogConstants.UNDEPLOY_ID, ITaskListDialogConstants.UNDEPLOY_LABEL, false);
		createButton(parent, ITaskListDialogConstants.DONE_ID, ITaskListDialogConstants.DONE_LABEL, true);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		
		// KJH 20101112 s, buttonBar가 viewer 오른쪽에 위치하도록 수정
		if (contents instanceof Composite) {
			Composite composite = (Composite)contents;
			composite.setLayout(new GridLayout(2, false));
		}
		// KJH 20101112 e, buttonBar가 viewer 오른쪽에 위치하도록 수정
		
		Shell shell = getShell();
		Point size = new Point(300, 200);
		Point location = getInitialLocation(size);
		shell.setBounds(getConstrainedShellBounds(new Rectangle(location.x,
				location.y, size.x, size.y)));
		
		shell.setText("Task List");
		
		update();
		TaskControlManager.getDefault().addListener(this);	// KJH 20110228
		
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new FormLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());
		
		// KJH 20101119 s, TableViewer로 교체
		Table table = createTable(composite);
		taskViewer = createTableViewer(table);
		
		taskViewer.setContentProvider(new TaskContentProvider());
		taskViewer.setLabelProvider(new TaskLabelProvider());
		taskViewer.addSelectionChangedListener(this);
		taskViewer.setInput(manager.getTaskArray());
		// KJH 20101119 e, TableViewer로 교체
	
		// KJH 20110307 s, selection 시 기존 element가 선택 가능하도록 comparer추가
		taskViewer.setComparer(new IElementComparer() {
			@Override
			public int hashCode(Object element) {
				return element.toString().hashCode();
			}
			
			@Override
			public boolean equals(Object a, Object b) {
				if (a.equals(b))
					return true;
				
				if (a instanceof IStructuredSelection) {
					a = ((IStructuredSelection)a).getFirstElement();
				}
				
				if (b instanceof IStructuredSelection) {
					b = ((IStructuredSelection)b).getFirstElement();
				}
				
				if (a != null && a.toString().equals(b.toString())) {
					return true;
				}
				
				return false;
			}
		});
		// KJH 20110307 e, selection 시 기존 element가 선택 가능하도록 comparer추가
		
		return composite;
	}
	
	private Table createTable(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION;
		Table table = new Table(parent, style);
		
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		table.setLayoutData(formData);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column.setText("!");
		column.setWidth(24);

		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("Task");
		column.setWidth(150);

		return table;
	}
	
	private TableViewer createTableViewer(Table table) {
		TableViewer tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(columnNames);
		return tableViewer;
	}
	
	private void updateButtons(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			Object element = structuredSelection.getFirstElement();
			if (element != null) {
				boolean isRunning = manager.isRunningTask(element.toString());
				getButton(ITaskListDialogConstants.UNDEPLOY_ID).setEnabled(true);
				getButton(ITaskListDialogConstants.RUN_ID).setEnabled(!isRunning);
				getButton(ITaskListDialogConstants.STOP_ID).setEnabled(isRunning);
				return;
			}
		}
		getButton(ITaskListDialogConstants.UNDEPLOY_ID).setEnabled(false);
		getButton(ITaskListDialogConstants.RUN_ID).setEnabled(false);
		getButton(ITaskListDialogConstants.STOP_ID).setEnabled(false);
	}

	@Override
	public void update() {
		Display display = getShell().getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					if (manager.isConnected() == false) {
						close();
					}
					else {
						ISelection selection = taskViewer.getSelection();
						int index = 0;
						Object fe = null;
						if (selection instanceof IStructuredSelection) {
							fe = ((IStructuredSelection)selection).getFirstElement();
							Object element;
							for (int i = 0; (element = taskViewer.getElementAt(i)) != null; i++) {
								if (element.equals(fe)) {
									index = i;
									break;
								}
							}
						}
						
						taskViewer.setInput(manager.getTaskArray());
						taskViewer.setSelection(selection);
						
						if (taskViewer.getSelection().isEmpty()) {
							Object element = taskViewer.getElementAt(index);
							while (element == null && index > -1) {
								index--;
								element = taskViewer.getElementAt(index);
							}
							if (element != null) {
								selection = new StructuredSelection(element);
								taskViewer.setSelection(selection);
							}
						}
						
						updateButtons(taskViewer.getSelection());
					}
				}
			});
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// KJH 20101220 s, modify
		if (event.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			updateButtons(selection);
		}	// KJH 20101220 e, modify
	}
}
