package kr.re.etri.tpl.script.debug.dialog;

import kr.re.etri.tpl.script.debug.thread.ThreadNode;
import kr.re.etri.tpl.script.debug.variables.DebugVariableContentProvider;
import kr.re.etri.tpl.script.debug.variables.DebugVariableLabelProvider;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class MonitoringDialog extends Dialog {

	private TableViewer tableViewer;
	private ThreadNode thread;
	private int position;
	
	public MonitoringDialog(Shell parentShell, ThreadNode thread, int position) {
		super(parentShell);
		int style = (SWT.SHELL_TRIM | SWT.MODELESS) & ~SWT.MAX;	// KJH 20110412, min, resize 추가
		setShellStyle(style);
		this.thread = thread;
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
	
	@Override
	protected void handleShellCloseEvent() {
		setReturnCode(CANCEL);
		this.getShell().setVisible(false);
	}

	@Override
	public boolean close() {
		return super.close();
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		return null;
	}

	@Override
	protected Control createContents(Composite parent) {
		Shell shell = getShell();
		Point size = new Point(300, 200);
		
		Monitor monitor = shell.getDisplay().getPrimaryMonitor();
		Rectangle monitorBounds = monitor.getClientArea();

		int rows = monitorBounds.height / size.y;
		int row = position / rows;
		int column = position % rows;
		
		Point location = new Point(size.x*column, monitorBounds.height - size.y*(row+1));
		shell.setBounds(getConstrainedShellBounds(new Rectangle(location.x,
				location.y, size.x, size.y)));
		
		// KJH 20110412, 창제목에 Thread ID 추가
		shell.setText(String.format("%s:%s (%s)", thread.getName(), thread.getThid(),
				thread.isRunning() ? "Running" : "Suspend"));
		
		return super.createContents(parent);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		composite.setLayout(new FillLayout());
		
		tableViewer = new TableViewer(composite, SWT.FULL_SELECTION);
		
		Table table = tableViewer.getTable();
		
		String[] columnNames = new String[] {"Line", "Name", "Value", "Path"};
		int[] weights = new int[] {50, 50, 80, 100};
		int[] minimunWidths = new int[] {30, 50, 60, 70};
		int size = Math.min(columnNames.length, Math.min(weights.length, minimunWidths.length));
		
		TableLayout layout = new TableLayout();
		for (int i = 0; i < size; i++) {
			layout.addColumnData(new ColumnWeightData(weights[i], minimunWidths[i], true));

			TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
			tableColumn.setText(columnNames[i]);
		}
		table.setLayout(layout);
		table.setHeaderVisible(true);
		
		tableViewer.setContentProvider(new DebugVariableContentProvider());
		tableViewer.setLabelProvider(new DebugVariableLabelProvider());
		
		update();
		
		return composite;

	}
	
	
	public void update() {
		if (thread != null) {
			this.getShell().setRedraw(false);
			tableViewer.getTable().setRedraw(false);
			tableViewer.setInput(thread.getVariables());
			// KJH 20110412, 창제목에 Thread ID 추가
			getShell().setText(thread.getIdentify());
			
			tableViewer.getControl().setRedraw(true);
			this.getShell().setRedraw(true);
		}
	}

	public void setThread(ThreadNode thread) {	// KJH 20111130, thread의 재설정이 가능하도록 추가
		this.thread = thread;
	}
}
