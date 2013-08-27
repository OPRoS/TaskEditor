package kr.re.etri.tpl.script.debug.variables;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.thread.ThreadNode;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

public class DebugVariableView extends ViewPart implements ISelectionChangedListener {

	private TableViewer tableViewer;
	
	public static final String VIEW_ID = "kr.re.etri.tpl.diagram.DebugValueView";
	
	@Override
	public void createPartControl(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		
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
	}

	@Override
	public void setFocus() {
		if (tableViewer != null) {
			tableViewer.getControl().setFocus();
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			
			if (selection.getFirstElement() instanceof ThreadNode) {
				ThreadNode threadNode = (ThreadNode)selection.getFirstElement();
				tableViewer.setInput(threadNode.getVariables());
			}
			else if (selection.getFirstElement() instanceof Variable) {
				
			}
		}
	}

	public void update() {
		ThreadNode thread = DebugManager.getDefault().getSelectedThread();
		if (thread != null) {
			tableViewer.setInput(thread.getVariables());
		}
	}
}
