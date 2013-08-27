package kr.re.etri.tpl.diagram.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.diagram.views.actions.ExportModelAction;
import kr.re.etri.tpl.diagram.views.actions.ImportModelAction;
import kr.re.etri.tpl.diagram.views.actions.NewConstantAction;
import kr.re.etri.tpl.diagram.views.actions.NewEnumerationAction;
import kr.re.etri.tpl.diagram.views.actions.NewEnumerationItemAction;
import kr.re.etri.tpl.diagram.views.actions.NewFunctionAction;
import kr.re.etri.tpl.diagram.views.actions.NewModelAction;
import kr.re.etri.tpl.diagram.views.actions.NewParameterAction;
import kr.re.etri.tpl.diagram.views.actions.NewSymbolAction;
import kr.re.etri.tpl.diagram.views.actions.RefreshModelViewAction;
import kr.re.etri.tpl.diagram.views.symbolparts.SymbolNavigatorEditPartFactory;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.apache.log4j.Logger;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;

public class ModelNavigatorViewPage extends ContentOutlinePage implements IModelNavigatorViewPage {
	private static Logger logger = Logger.getLogger(ModelNavigatorViewPage.class);
	private Object input;
	private EditPartViewer viewer;
	private DefaultEditDomain editDomain;
	private ActionRegistry actionRegistry;
	private SelectionSynchronizer selectionSynchronizer;
	private ModelDiagram diagram;
	private List selectionActions = new ArrayList<String>();
	private List stackActions = new ArrayList<String>();

	public ModelNavigatorViewPage( EditPartViewer viewer,
			DefaultEditDomain domain,
			SelectionSynchronizer selectionSynchronizer,
			ModelDiagram diagram ){
		super(viewer);
		
		this.viewer = viewer;
		this.editDomain = domain;
		this.selectionSynchronizer = selectionSynchronizer;
		this.diagram = diagram;
	}
	
	public EditPartViewer getEditPartViewer(){
		return getViewer();
	}
	
	@Override
	public void createControl(Composite parent) {
		// create outline viewer page
		viewer.createControl(parent);
		// configure outline viewer
		viewer.setEditDomain(getEditDomain());
		viewer.setEditPartFactory(new SymbolNavigatorEditPartFactory());
		// configure & add context menu to viewer
		ContextMenuProvider cmProvider = new SNavViewContextMenuProvider(
				viewer, getActionRegistry()); 
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(
				SNavViewContextMenuProvider.MENU_ID,
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
	 * @see kr.re.etri.tpl.views.vies.SNavViewContextMenuProvider#buildContextMenu(IMenuManager menu)
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);

		// 뷰에서 동작할 Action을 생성하여
		createActions();
		updateActions(stackActions);
		
		ActionRegistry registry = getActionRegistry();
		// ActionBar에 등록한다.
		IActionBars bars = pageSite.getActionBars();
		String id;
		id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, getActionRegistry().getAction(id));
		
		id = NewEnumerationAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = NewEnumerationItemAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		
		id = NewFunctionAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = NewSymbolAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = NewFunctionAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = NewModelAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = NewParameterAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		
		id = RefreshModelViewAction.ID;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		
		// KJH 20100917, import model
		id = ImportModelAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
		
		// KJH 20100917, export model
		id = ExportModelAction.actionId;
		bars.setGlobalActionHandler(id, registry.getAction(id));
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
		
		action = new RefreshModelViewAction("Refesh", diagram, getCommandStack());
		registry.registerAction(action);
		
		action = new NewEnumerationAction("&New Enumeration", diagram, getCommandStack());
		registry.registerAction(action);

		action = new NewEnumerationItemAction("&New Element", diagram, getCommandStack());
		registry.registerAction(action);
		
		action = new NewConstantAction("&New Constant", diagram, getCommandStack());
		registry.registerAction(action);

		action = new NewSymbolAction("&New Symbol", diagram, getCommandStack());
		registry.registerAction(action);

		action = new NewFunctionAction("&New Function", diagram, getCommandStack());
		registry.registerAction(action);

		action = new NewModelAction("&New Model", diagram, getCommandStack());
		registry.registerAction(action);

		action = new NewParameterAction("&New Parameter", diagram, getCommandStack());
		registry.registerAction(action);

		// KJH 20110630 s, model view에서 Import/Export Model 명령 삭제
//		// KJH 20100917, import model
//		action = new ImportModelAction("&Import Model", diagram, getCommandStack(), getSite().getShell());
//		registry.registerAction(action);
//		
//		// KJH 20100917, export model
//		action = new ExportModelAction("Exp&ort Model", diagram, getCommandStack(), getSite().getShell());
//		registry.registerAction(action);
		// KJH 20110630 e, model view에서 Import/Export Model 명령 삭제
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
