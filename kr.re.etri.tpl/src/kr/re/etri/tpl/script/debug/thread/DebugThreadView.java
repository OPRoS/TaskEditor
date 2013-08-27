package kr.re.etri.tpl.script.debug.thread;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.variables.DebugVariableView;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.ViewPart;

public class DebugThreadView extends ViewPart implements ISelectionChangedListener, IDoubleClickListener {

	private TreeViewer treeViewer;
	
	public static final String VIEW_ID = "kr.re.etri.tpl.diagram.DebugThreadView";
	
	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.MULTI);
		
		treeViewer.addSelectionChangedListener(this);
		treeViewer.addDoubleClickListener(this);

		treeViewer.setContentProvider(new DebugThreadContentProvider());
		treeViewer.setLabelProvider(new DebugThreadLabelProvider());
		
		update();

		ThreadMessageReader.getDefault().setView(this);
		VariableMessageReader.getDefault().setView(this);
	}

	@Override
	public void dispose() {
		ThreadMessageReader.getDefault().setView(null); // KJH 20111125
		VariableMessageReader.getDefault().setView(null); // KJH 20111125
		super.dispose();
	}

	@Override
	public void setFocus() {
		if (treeViewer != null) {
			treeViewer.getControl().setFocus();
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();

			IWorkbenchPage wp = getSite().getPage();
			if (selection.getFirstElement() instanceof ThreadNode) {
				ThreadNode thread = (ThreadNode)selection.getFirstElement();
				DebugManager dManager = DebugManager.getDefault();
				dManager.setSelectedThread(thread);
				dManager.selectAndReveal(wp, thread, false);
			}
			
			IViewPart vp = wp.findView(DebugVariableView.VIEW_ID);
			if (vp instanceof DebugVariableView) {
				DebugVariableView variableView = (DebugVariableView)vp;
				variableView.update();
			}
		}
	}
	
	@Override
	public void doubleClick(DoubleClickEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection.getFirstElement() instanceof ThreadNode) {
				ThreadNode thread = (ThreadNode)selection.getFirstElement();
				IWorkbenchPage page = getSite().getPage();
				DebugManager.getDefault().selectAndReveal(page, thread, true);
				
				DebugManager.getDefault().toggleDialogVisible(thread);
			}
		}
	}
	
	public void update() {	// KJH 20110220
		DebugManager dManager = DebugManager.getDefault();
		treeViewer.setInput(dManager.getThreadRoot());
		treeViewer.expandAll();
		treeViewer.setSelection(new StructuredSelection(dManager.getSelectedThread()));
	}

}
