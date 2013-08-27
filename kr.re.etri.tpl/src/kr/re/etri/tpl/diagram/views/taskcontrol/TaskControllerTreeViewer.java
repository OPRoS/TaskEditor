package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

public class TaskControllerTreeViewer extends TreeViewer {

	public TaskControllerTreeViewer(Tree tree) {
		super(tree);
		init();
	}
	
	protected void init() {
		setContentProvider(new TaskControllerContentProvider());
		TaskControllerLabelProvider labelProvider = new TaskControllerLabelProvider(this);
		setLabelProvider(labelProvider);
		this.getTree().addTreeListener(labelProvider);
		setComparator(new TaskControllerComparator());
		setComparer(new IElementComparer() {
			
			@Override
			public int hashCode(Object element) {
				int result = element.toString().hashCode();
				return result;
			}
			
			@Override
			public boolean equals(Object a, Object b) {
				if (a == null)
					return false;
				
				if (a.equals(b)) {
					return true;
				} else {
					String a1 = a.toString();
					String b1 = b.toString();
					if (a1 != null && a1.equals(b1))
						return true;
				}
				
				return false;
			}
		});
		initDragAndDrop();
	}
	
	protected void initDragAndDrop() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;

		TaskControllerDragAdapter dragAdapter = createDragAdapter();
		addDragSupport(operations, dragAdapter.getSupportedDragTransfers(),
				dragAdapter);
		
		TaskControllerDropAdapter dropAdapter = createDropAdapter();
		addDropSupport(operations, dropAdapter.getSupportedDropTransfers(),
				dropAdapter);
	}
	
	protected TaskControllerDragAdapter createDragAdapter() {
		return new TaskControllerDragAdapter(this);
	}
	
	protected TaskControllerDropAdapter createDropAdapter() {
		return new TaskControllerDropAdapter(this);
	}
	
	public void update() {
		TaskControlManager.getDefault().dirall();
//		view.update();
	}

	// KJH 20101109 s, mousedown시 modifier가 작동되지 않도록 함 
	@Override
	protected void hookEditingSupport(Control control) {
		if (getColumnViewerEditor() != null) {
			control.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					// KJH 20101109, remove all
//					if (e.count != 2) {
//						handleMouseDown(e);
//					}
				}

				public void mouseDoubleClick(MouseEvent e) {
					if (e.count >= 2) {
						e.count = 1;
					}
					handleMouseDown(e);
				}
			});
		}
	}
	
	private void handleMouseDown(MouseEvent e) {
		ViewerCell cell = getCell(new Point(e.x, e.y));

		if (cell != null) {
			triggerEditorActivationEvent(new ColumnViewerEditorActivationEvent(
					cell, e));
		}
	}
	// KJH 20101109 e, 

}
