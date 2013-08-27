package kr.re.etri.tpl.diagram.views;

import java.util.List;

import kr.re.etri.tpl.diagram.ShapeModelTreeEditPartFactory;
import kr.re.etri.tpl.diagram.editors.BehaviorDiagramContextMenuProvider;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.apache.log4j.Logger;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;

public class ModelOutlinePage extends ContentOutlinePage {
	private static Logger logger = Logger.getLogger(ModelOutlinePage.class);
	private DefaultEditDomain editDomain;
	private ActionRegistry actionRegistry;
	private SelectionSynchronizer selectionSynchronizer;
	private ModelDiagram diagram;

	public ModelOutlinePage(EditPartViewer viewer,
			DefaultEditDomain domain,
			ActionRegistry registry,
			SelectionSynchronizer ssync,
			ModelDiagram diagram) {
		super(viewer);

		this.editDomain = domain;
		this.actionRegistry = registry;
		this.selectionSynchronizer = ssync;
		this.diagram = diagram;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		// create outline viewer page
		getViewer().createControl(parent);
		// configure outline viewer
		getViewer().setEditDomain(editDomain);
		getViewer().setEditPartFactory(new ShapeModelTreeEditPartFactory());
		// configure & add context menu to viewer
		ContextMenuProvider cmProvider = new BehaviorDiagramContextMenuProvider(
				getViewer(), actionRegistry); 
		getViewer().setContextMenu(cmProvider);
		getSite().registerContextMenu(
				BehaviorDiagramContextMenuProvider.MENU_ID,
				cmProvider, getSite().getSelectionProvider());		
		// hook outline viewer
		selectionSynchronizer.addViewer(getViewer());
		// initialize outline viewer with model
		getViewer().setContents(diagram);
		
		List<ItemElement> items =  diagram.getItems();
		for(ItemElement ele : items){
			logger.debug(ele);
		}
		// show outline viewer
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose() {
		// unhook outline viewer
		selectionSynchronizer.removeViewer(getViewer());
		// dispose
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#getControl()
	 */
	public Control getControl() {
		return getViewer().getControl();
	}
	
	/**
	 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		ActionRegistry registry = actionRegistry;
		IActionBars bars = pageSite.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
	}
}
