package kr.re.etri.tpl.diagram.views.taskcontrol;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.views.taskcontrol.actions.DeployProviderAction;
import kr.re.etri.tpl.diagram.views.taskcontrol.actions.MkdirProviderAction;
import kr.re.etri.tpl.diagram.views.taskcontrol.actions.OpenLocalFileAction;
import kr.re.etri.tpl.diagram.views.taskcontrol.actions.RemoveProviderAction;
import kr.re.etri.tpl.diagram.views.taskcontrol.actions.TaskOperateProviderAction;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.IUpdate;

public class TaskControllerView extends ViewPart implements ISelectionChangedListener,
		ITaskControlListener {
	
	public static String viewId = "kr.re.etri.tpl.diagram.TaskControllerView";	// KJH 20101113 

	private List<Action> actionList = new ArrayList<Action>();	// KJH 20101108
	private TreeViewer viewer;
	private Text host;
	private Text port;
//	private DrillDownAdapter drillDownAdapter;
	
	private TaskControlManager manager = TaskControlManager.getDefault();
	
	@Override
	public void dispose() {
		TaskMessageReader.getDefault().setView(null); // KJH 20111125
		manager.dispose();
		manager = null;
		super.dispose();
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		// KJH 20101109 s, mouse event
		final TreeCellModifier modifier = new TreeCellModifier();
		final Tree tree = new Tree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				modifier.setEnabled(false);
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				modifier.setEnabled(true);
			}
		});
		
		viewer = new TaskControllerTreeViewer(tree);
		
		getSite().setSelectionProvider(viewer);	// KJH 20101221
		
		viewer.addSelectionChangedListener(this);
//		viewer.setContentProvider(new TaskControllerContentProvider());
//		viewer.setLabelProvider(new TaskControllerLabelProvider());
//		viewer.setInput(manager.getRoot());
		manager.addListener(this);

		// KJH 20101109 e, mouse event
		viewer.setCellModifier(modifier);
		viewer.setColumnProperties(new String[] {"name"});
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(tree, SWT.BORDER) });
		// KJH 20101109 e,

//		drillDownAdapter = new DrillDownAdapter(viewer);

		host = new Text(parent, SWT.SINGLE | SWT.WRAP| SWT.BORDER);
		port = new Text(parent, SWT.SINGLE | SWT.WRAP| SWT.BORDER);
		host.setText("127.0.0.1");
		port.setText("6011");
		host.setToolTipText("HOST");
		port.setToolTipText("PORT");

		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(70, 0);
		formData.height = 12;
		host.setLayoutData(formData);
		
		formData = new FormData();
		formData.left = new FormAttachment(host, 0);
		formData.right = new FormAttachment(100, 0);
		formData.height = 12;
		port.setLayoutData(formData);
		
		formData = new FormData();
		formData.top = new FormAttachment(host, 0);
		formData.bottom = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		viewer.getControl().setLayoutData(formData);
		
		makeActions();
		hookContenxtMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		updateActions(null);

		viewer.setSelection(new StructuredSelection());	// KJH 20110216, empty selection
		
		TaskMessageReader.getDefault().setView(this);
	}
	
	private void addAction(Action action) {
		if (actionList == null) {
			actionList = new ArrayList<Action>();
		}
		actionList.add(action);
	}
	private Action getAction(String id) {
		if (actionList == null || id == null)
			return null;
		
		for (int i=0; i<actionList.size(); i++) {
			Action action = actionList.get(i);
			if (id.equals(action.getId())) {
				return action;
			}
		}
		
		return null;
	}
	
	/*
	 * KJH 20101108, actions
	 */
	private void makeActions() {
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		
		Action action = new MkdirProviderAction(viewer, "New Directory");
		action.setToolTipText("New Directory");
		action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		addAction(action);
		
		action = new RemoveProviderAction(viewer, "Remove");
		action.setToolTipText("Remove");
		action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		addAction(action);
		
		action = new DeployProviderAction(viewer, "Deploy");
		action.setToolTipText("Deploy");
		action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		addAction(action);
		
		action = new TaskOperateProviderAction(viewer, "Run");
		action.setToolTipText("Run/Stop");
		action.setImageDescriptor(TaskModelPlugin.getImageDescriptor(IconStrings.RUN_16));
		addAction(action);
		
		action = new OpenLocalFileAction(viewer, "Open");
		action.setToolTipText("Open Local File");
//		action.setImageDescriptor(TaskModelPlugin)
		addAction(action);
	}
	
	/*
	 * KJH 20101109, UpdateActions
	 */
	private void updateActions(Object[] items) {
		// KJH 20110215 s, enable을 action에서 처리
		for (int i=0; i<actionList.size(); i++) {
			Action action = actionList.get(i);
			if (action instanceof IUpdate) {
				((IUpdate)action).update();
			}
		}
		// KJH 20110215 e, enable을 action에서 처리
	}
	
	private void hookContenxtMenu() {
		MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				TaskControllerView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (manager != null && manager.isConnected()) {
					Action action = getAction(OpenLocalFileAction.actionId);
					if (action != null && action.isEnabled()) {
						action.run();
					}
				}
			}
		});
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
//		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
	
//	private void fillLocalPullDown(IMenuManager manager) {
//		Action action = getAction(MkdirProviderAction.actionId); 
//		if (action != null)	manager.add(action);
//		action = getAction(RemoveProviderAction.actionId);
//		if (action != null)	manager.add(action);
//		
//		manager.add(new Separator());
//		
//		action = getAction(DeployProviderAction.actionId);
//		if (action != null)	manager.add(action);
//		
//		// KJH 20101110
//		action = getAction(TaskOperateProviderAction.actionId);
//		if (action != null)	manager.add(action);
//	}
//	
	private void fillContextMenu (IMenuManager manager) {
		Action action = getAction(MkdirProviderAction.actionId); 
		if (action != null)	manager.add(action);
		action = getAction(RemoveProviderAction.actionId);
		if (action != null)	manager.add(action);
		
		manager.add(new Separator());
		
		action = getAction(DeployProviderAction.actionId);
		if (action != null)	manager.add(action);
		
		// KJH 20101110
		action = getAction(TaskOperateProviderAction.actionId);
		if (action != null)	manager.add(action);
		
//		drillDownAdapter.addNavigationActions(manager);
		manager.add(new Separator("Additions"));
	}
	private void fillLocalToolBar(IToolBarManager manager) {
		Action action = getAction(MkdirProviderAction.actionId); 
		if (action != null)	manager.add(action);
		action = getAction(RemoveProviderAction.actionId);
		if (action != null)	manager.add(action);
		
		manager.add(new Separator());
		
		action = getAction(DeployProviderAction.actionId);
		if (action != null)	manager.add(action);
		
		// KJH 20101110
		action = getAction(TaskOperateProviderAction.actionId);
		if (action != null)	manager.add(action);
		
//		drillDownAdapter.addNavigationActions(manager);
	}
	
	
	@Override
	public void setFocus() {
		if (viewer != null) {
			viewer.getControl().setFocus();
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		updateActions(selection.toArray());
	}
	
	private Shell getShell() {
		return (getSite() != null) ? getSite().getShell() : null;
	}
	
	private Display getDisplay() {
		return (getShell() != null) ? getShell().getDisplay() : null;
	}
	
	public void update () {
		Display display = getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					if (viewer != null) {
						// KJH 20101112 s, refresh이후 확장 및 선택 변화 없도록 수정
						Object[] elements = viewer.getExpandedElements();
						ISelection selection = viewer.getSelection();
						TreeItem2 root = manager.getRoot();
						viewer.setInput(root);
						if (elements != null && elements.length > 0) {
							viewer.setExpandedElements(elements);
						} else {
							viewer.expandToLevel(root, 2);
							viewer.update(root.getChildren(), null);
						}
						viewer.setSelection(selection);
						// KJH 20101112 e, refresh이후 확장 및 선택 변화 없도록 수정
					}
				}
			});
		}
	}
	
	public String getHost() {
		if (host == null || host.getText() == null)
			return null;
		return host.getText().trim();
	}
	
	public int getPort() {
		if (port == null || port.getText() == null)
			return -1;
		String value = port.getText().trim();
		if (value.equals(""))
			return -1;
		return Integer.valueOf(value);
	}
}
