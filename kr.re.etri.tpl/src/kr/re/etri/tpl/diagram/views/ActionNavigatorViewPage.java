package kr.re.etri.tpl.diagram.views;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.diagram.views.actionparts.ActionNavigatorEditPartFactory;
import kr.re.etri.tpl.diagram.views.actions.NewActionAction;
import kr.re.etri.tpl.diagram.views.actions.NewParameterAction;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;


public class ActionNavigatorViewPage extends ContentOutlinePage { 
	private Object input;
	private EditPartViewer viewer;
	private DefaultEditDomain editDomain;
	private ActionRegistry actionRegistry;
	private SelectionSynchronizer selectionSynchronizer;
	private ModelDiagram diagram;
	private List selectionActions = new ArrayList<String>();
	private List stackActions = new ArrayList<String>();

	public ActionNavigatorViewPage( EditPartViewer viewer,
			DefaultEditDomain domain,
			SelectionSynchronizer selectionSynchronizer,
			ModelDiagram diagram ){
		super(viewer);

		this.viewer = viewer;
		this.editDomain = domain;
		this.selectionSynchronizer = selectionSynchronizer;
		this.diagram = diagram;
	}

	@Override
	public void createControl(Composite parent) {
		// create outline viewer page
		viewer.createControl(parent);
		// configure outline viewer
		viewer.setEditDomain(getEditDomain());
		viewer.setEditPartFactory(new ActionNavigatorEditPartFactory());
		// configure & add context menu to viewer
		ContextMenuProvider cmProvider = new ANavViewContextMenuProvider(
				viewer, getActionRegistry()); 
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(
				ANavViewContextMenuProvider.MENU_ID,
				cmProvider, getSite().getSelectionProvider());		
		// hook outline viewer
		selectionSynchronizer.addViewer(viewer);
		// initialize outline viewer with model
		viewer.setContents(diagram);
		// show outline viewer

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateActions(selectionActions);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose() {
		// unhook outline viewer
		selectionSynchronizer.removeViewer(viewer);
		// dispose
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#getControl()
	 */
	@Override
	public Control getControl() {
		if (getViewer() == null) {
			return null;
		}
		return getViewer().getControl();
	}
	
	/**
	 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		
		createActions();
		updateActions(stackActions);

		ActionRegistry registry = getActionRegistry();
		IActionBars bars = pageSite.getActionBars();
		String id;
		id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, getActionRegistry().getAction(id));
		id = NewActionAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = NewParameterAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		
		getViewer().addDragSourceListener(new TemplateTransferDragSourceListener(getViewer()) {

			/**
			 * A helper method that returns <code>null</code> or the <i>template</i> Object from the
			 * currently selected EditPart.
			 * @return the template
			 */
			protected Object getTemplate() {
				List selection = getViewer().getSelectedEditParts();
				if (selection.size() == 1) {
					EditPart editpart = (EditPart)getViewer().getSelectedEditParts().get(0);
					Object model = editpart.getModel(); 
					if (model instanceof ActionElement) {
						return model;
					}
					else if (model instanceof BehaviorElement) {
						return BehaviorElement.class;
					}
				}
				return null;
			}
		});
	}

	/**
	 * Creates actions for this editor.  Subclasses should override this method to create
	 * and register actions with the {@link ActionRegistry}.
	 */
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		
		IAction action;
		
		action = new UndoAction(getEditDomain().getEditorPart());
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(getEditDomain().getEditorPart());
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new SelectAllAction(getEditDomain().getEditorPart());
		registry.registerAction(action);
		
		action = new DeleteAction((IWorkbenchPart)getEditDomain().getEditorPart());
		((DeleteAction)action).setSelectionProvider(this.viewer);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NewActionAction("&New Action", diagram, getCommandStack());
		registry.registerAction(action);

		action = new NewParameterAction("&Add Parameter", diagram, getCommandStack());
		registry.registerAction(action);
	}

	protected ActionRegistry getActionRegistry() {
		if(actionRegistry == null) {
			actionRegistry = new ActionRegistry();
		}
		
		return actionRegistry;
	}

	/**
	 * Returns the command stack.
	 * @return the command stack
	 */
	protected CommandStack getCommandStack() {
		return getEditDomain().getCommandStack();
	}

	/**
	 * Returns the edit domain.
	 * @return the edit domain
	 */
	protected DefaultEditDomain getEditDomain() {
		return editDomain;
	}

	/**
	 * Returns the list of <em>IDs</em> of Actions that are dependant on the CommmandStack's
	 * state. The associated Actions can be found in the action registry. These actions should
	 * implement the {@link UpdateAction} interface so that they can be updated in response to
	 * command stack changes.  An example is the "undo" action.
	 * @return the list of stack-dependant action IDs
	 */
	protected List getStackActions() {
		return stackActions;
	}

	/**
	 * Returns the list of <em>IDs</em> of Actions that are dependant on changes in the
	 * workbench's {@link ISelectionService}. The associated Actions can be found in the
	 * action registry. Such actions should implement the {@link UpdateAction} interface so
	 * that they can be updated in response to selection changes.
	 * @see #updateActions(List)
	 * @return the list of selection-dependant action IDs
	 */
	protected List getSelectionActions() {
		return selectionActions;
	}

	// 현재 선택된 Item에 대하여 action을 활성/비활성화 시킨다.
	protected void updateActions(List actionIds) {
		ActionRegistry registry = getActionRegistry();
		Iterator iter = actionIds.iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			IAction action = registry.getAction(key);
			if (action instanceof UpdateAction)
				((UpdateAction)action).update();
			else
				action.setEnabled(true);
		}
	}
}