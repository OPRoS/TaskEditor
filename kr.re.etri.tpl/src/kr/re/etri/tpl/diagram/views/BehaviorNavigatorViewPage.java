package kr.re.etri.tpl.diagram.views;

import java.util.List;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.diagram.views.behaviorparts.BehaviorNavigatorEditPartFactory;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;

public class BehaviorNavigatorViewPage extends ContentOutlinePage {
	private DefaultEditDomain editDomain;
	private ActionRegistry actionRegistry;
	private SelectionSynchronizer selectionSynchronizer;
	private ModelDiagram diagram;

	public BehaviorNavigatorViewPage(EditPartViewer viewer,
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
		getViewer().setEditPartFactory(new BehaviorNavigatorEditPartFactory());
		// configure & add context menu to viewer
		ContextMenuProvider cmProvider = new BNavViewContextMenuProvider(
				getViewer(), actionRegistry); 
		getViewer().setContextMenu(cmProvider);
		getSite().registerContextMenu(
				BNavViewContextMenuProvider.MENU_ID,
				cmProvider, getSite().getSelectionProvider());		
		// hook outline viewer
		selectionSynchronizer.addViewer(getViewer());
		// initialize outline viewer with model
		getViewer().setContents(diagram);
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
		ActionRegistry registry = actionRegistry;
		IActionBars bars = pageSite.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
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
					if (model instanceof BehaviorElement) {
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
}
