package kr.re.etri.tpl.script.debug.dialog;

import java.util.Map;

import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;
import kr.re.etri.tpl.script.grammar.tree2.IWorkerNode;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class TaskSelectionDialog extends Dialog implements ISelectionChangedListener {

	private ListViewer projectViewer;
	private ListViewer taskViewer;
	
	private Map taskMap;
	private IProject project;
	private IScriptNode task;
	
	public TaskSelectionDialog(Shell parentShell, Map map) {
		super(parentShell);
		int style = SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE;
		setShellStyle(style);
		
		this.taskMap = map;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Task Selection");
	}

	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
	}

	@Override
	protected Control createContents(Composite parent) {
		Shell shell = getShell();
		Point size = new Point(400, 300);
		Point location = getInitialLocation(size);
		shell.setBounds(getConstrainedShellBounds(new Rectangle(location.x,
				location.y, size.x, size.y)));
		
		return super.createContents(parent);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		composite.setLayout(new FormLayout());
		
		// group1
		Group group1 = new Group(composite, SWT.SHADOW_ETCHED_IN);
		group1.setText("Projects");
		group1.setLayout(new FillLayout());

		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 5);
		formData.bottom = new FormAttachment(100, -5);
		formData.left = new FormAttachment(0, 5);
		formData.right = new FormAttachment(30, 0);
		group1.setLayoutData(formData);
		
		// project viewer
		projectViewer = new ListViewer(group1, SWT.BORDER | SWT.SINGLE);
		
		projectViewer.addSelectionChangedListener(this);
		projectViewer.setContentProvider(makeContentProvider());
		projectViewer.setLabelProvider(makeLabelProvider());
		
		
		// group2
		Group group2 = new Group(composite, SWT.SHADOW_ETCHED_IN);
		group2.setText("Tasks");
		group2.setLayout(new FillLayout());

		formData = new FormData();
		formData.top = new FormAttachment(0, 5);
		formData.bottom = new FormAttachment(100, -5);
		formData.left = new FormAttachment(group1, 5);
		formData.right = new FormAttachment(100, -5);
		group2.setLayoutData(formData);

		// task viewer
		taskViewer = new ListViewer(group2, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		taskViewer.addSelectionChangedListener(this);
		taskViewer.setContentProvider(makeContentProvider());
		taskViewer.setLabelProvider(makeLabelProvider());
		
		projectViewer.setInput(taskMap.keySet().toArray());
		
		return composite;
	}
	
	private IStructuredContentProvider makeContentProvider() {
		return new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			
			@Override
			public void dispose() {
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				return (Object[])inputElement;
			}
		};
	}
	
	private ILabelProvider makeLabelProvider() {
		return new LabelProvider() {

			@Override
			public Image getImage(Object element) {
				return null;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof IWorkerNode) {
					IWorkerNode worker = (IWorkerNode)element;
					IScriptNode parent = worker.getParent();
					if (parent instanceof IScriptRootNode) {
						return worker.getName() + "[" + parent.getName() + "]";
					}
				}
				else if (element instanceof IProject) {
					return ((IProject)element).getName();
				}
				return element.toString();
			}
			
		};
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			Object source = event.getSource();
			Object object = selection.getFirstElement();

			if (source != null) {
//				if (source.equals(projectViewer)) {
//					taskViewer.setInput(taskMap.get(object));
//					project = (IProject)object;
//				} else if (source.equals(taskViewer)) {
//					task = (IScriptNode)object;
//				}
				if (object instanceof IProject) {
					taskViewer.setInput(taskMap.get(object));
					project = (IProject)object;
				} else if (object instanceof IScriptNode) {
					task = (IScriptNode)object;
				}
			}
//			if (object instanceof String) {
//				taskViewer.setInput(taskMap.get((String)object));
//			} else if (object instanceof IScriptNode) {
//				task = (IScriptNode)object;
//			}
		}
	}

	public IScriptNode getTask() {
		return task;
	}
	
	public IProject getProject() {
		return project;
	}
}
