package kr.re.etri.tpl.diagram.dialogs;

import java.util.List;

import kr.re.etri.tpl.diagram.commands.SetValueCommand;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class WorkerInitTaskDialog extends TitleAreaDialog{

	public static final String LABEL_BUTTON_APPLY = "Apply";

	public static final String LABEL_BUTTON_CLOSE = "Close";

	private Button applyBtn, cancelBtn;

	private Label workerLabel;
	
	private Label taskLabel;
	
	private Text descEdit;

	private TableViewer workerViewer;

	private TableViewer taskViewer;

	private String title;

	private String message;
	
	private Composite composite;
	private Composite horzPanel;
	
	private Composite vertPanel;

	private DefaultEditDomain editDomain;
	
	private ModelDiagram modelDiagram;
	
	private BehaviorElement taskElement;
	
	private TaskElement workerElement;

	Listener listener;
	int[] events = new int[] {
			SWT.Dispose,
			SWT.Resize,  
			SWT.FocusIn,
			SWT.FocusOut,
			SWT.Activate,
			SWT.Deactivate};

	public WorkerInitTaskDialog(Shell shell, String title, String message){
		super(shell);
		setShellStyle(SWT.SHELL_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL | getDefaultOrientation());

		this.title = title;
		this.message = message;
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

		if(modelDiagram == null) {
			return contents;
		}
		
		List<ItemElement> itemList = this.modelDiagram.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof TaskElement) {
				Table workerTable = workerViewer.getTable();
				TableItem tableItem = new TableItem(workerTable, SWT.NONE);
				tableItem.setText(new String[]{item.getName()});
				tableItem.setData(item);
				if(item == workerElement) {
					workerTable.setSelection(tableItem);
				}
			}
			else if(item instanceof BehaviorElement) {
				Table taskTable = taskViewer.getTable();
				TableItem tableItem = new TableItem(taskTable, SWT.NONE);
				tableItem.setText(new String[]{item.getName()});
				tableItem.setData(item);
				if(item == taskElement) {
					taskTable.setSelection(tableItem);
				}
			}
		}
		
		return contents;

	}

	@Override
	public Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		
		// 무조건 GridLayout이어야 하내?
		composite.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);

		vertPanel = new Composite(composite, SWT.NONE);
		gridData = new GridData(GridData.FILL_BOTH);
		vertPanel.setLayoutData(gridData);

		GridLayout gridLayout;
		gridLayout = new GridLayout(1, false);
		gridLayout.numColumns = 1;
		vertPanel.setLayout(gridLayout);

		horzPanel = new Composite(vertPanel, SWT.NONE);
		gridData = new GridData(GridData.FILL_BOTH);
		horzPanel.setLayoutData(gridData);

		gridLayout = new GridLayout(1, false);
		gridLayout.numColumns = 2;
		horzPanel.setLayout(gridLayout);

		descEdit = new Text(vertPanel, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		gridData = new GridData(GridData.FILL_BOTH);
		descEdit.setLayoutData(gridData);

		workerLabel = new Label(horzPanel, SWT.NONE);
		gridData = new GridData();
		workerLabel.setLayoutData(gridData);
		workerLabel.setText("Task");

		taskLabel = new Label(horzPanel, SWT.NONE);
		gridData = new GridData();
		taskLabel.setLayoutData(gridData);
		taskLabel.setText("Behavior");
		
		int style = SWT.SELECTED | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;
		workerViewer = new TableViewer(horzPanel, style);
		Table workerTable = (Table)workerViewer.getControl();
		gridData = new GridData(GridData.FILL_BOTH);
		workerTable.setLayoutData(gridData);

		TableColumn tableColumn;
		tableColumn = new TableColumn(workerTable, SWT.NONE);
		tableColumn.setWidth(150);
		workerTable.setLinesVisible(true);
		
		workerTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(applyBtn == null) {
					return;
				}
				
				TableItem item = (TableItem)e.item;
				if(workerElement == item.getData()) {
					return;
				}

				applyBtn.setEnabled(true);
			}
		});

		taskViewer = new TableViewer(horzPanel, style);
		Table taskTable = (Table)taskViewer.getControl();
		gridData = new GridData(GridData.FILL_BOTH);
		taskTable.setLayoutData(gridData);

		tableColumn = new TableColumn(taskTable, SWT.NONE);
		tableColumn.setWidth(150);

		taskTable.setLinesVisible(true);
		
		taskTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(applyBtn == null) {
					return;
				}
				
				TableItem item = (TableItem)e.item;
				if(taskElement == item.getData()) {
					return;
				}
				
				applyBtn.setEnabled(true);
			}
		});

		listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Dispose: onDispose(); break;
					case SWT.Resize: onResize(); break;
				}
			}
		};

		for (int i = 0; i < events.length; i++) {
			vertPanel.addListener(events[i], listener);
		}

		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		applyBtn = createButton(parent, IDialogConstants.YES_ID, LABEL_BUTTON_APPLY, false);
		applyBtn.setEnabled(false);
		cancelBtn = createButton(parent, IDialogConstants.CLOSE_ID, LABEL_BUTTON_CLOSE,
				false);
	}

	protected void buttonPressed(int buttonId){
		this.setReturnCode(buttonId);

		if(buttonId== IDialogConstants.YES_ID){
			apply();
			return;
		}

		close();
	}

	protected void apply() {
		CommandStack commandStack = this.editDomain.getCommandStack();
		SetValueCommand cmd;

		applyBtn.setEnabled(false);
		
		Table workerTable = workerViewer.getTable();
		Table taskTable = taskViewer.getTable();
		
		TableItem selItem;
		int selIndex;
		selIndex = workerTable.getSelectionIndex();
		if(selIndex < 0) {
			setMessage("Worker를 선택하세요.");
			return;
		}
		
		selItem = workerTable.getItem(selIndex);
		workerElement = (TaskElement)selItem.getData();

		TaskElementPropertySource propertySource = new TaskElementPropertySource(workerElement);
		cmd = new SetValueCommand("set description");
		cmd.setTarget(propertySource);
		cmd.setPropertyId(TaskModelPackage.TASK_ELEMENT__DESCRIPTION);
		cmd.setPropertyValue(this.descEdit.getText());

		commandStack.execute(cmd);

		selIndex = taskTable.getSelectionIndex();
		if(selIndex < 0) {
			setMessage("Task를 선택하세요.");
			return;
		}
		selItem = taskTable.getItem(selIndex);
		taskElement = (BehaviorElement)selItem.getData();

		cmd = new SetValueCommand("set inittask");
		cmd.setTarget(propertySource);
		cmd.setPropertyId(TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK);
		cmd.setPropertyValue(taskElement.getName());

		commandStack.execute(cmd);

	}

	public void setEditDomain(DefaultEditDomain editDomain) {
		this.editDomain = editDomain;
	}

	public void setModelDiagram(ModelDiagram modelDiagram) {
		this.modelDiagram = modelDiagram;
	}

	public void setWorkerElement(TaskElement worker) {
		this.workerElement = worker;
	}

	public void setTaskElement(BehaviorElement task) {
		this.taskElement = task;
	}

	protected void onDispose() {
		for (int i = 0; i < events.length; i++) {
			vertPanel.removeListener(events[i], listener);
		}
	}

	protected void onResize() {
		Point size = vertPanel.getSize();
	}
}