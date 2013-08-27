package kr.re.etri.tpl.actions;

import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

public class TPLActionBarContributor extends ActionBarContributor {

	@Override
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
		
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
		
		addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY,
				GEFMessages.ToggleGrid_Label, IAction.AS_CHECK_BOX));
		
		addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY,
				GEFMessages.ToggleSnapToGeometry_Label, IAction.AS_CHECK_BOX));
		
		addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY,
				GEFMessages.ToggleRulerVisibility_Label, IAction.AS_CHECK_BOX));
	}

	@Override
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);
		
		MenuManager viewMenu = new MenuManager("View");
		viewMenu.add(getAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY));
		viewMenu.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));
		viewMenu.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
		viewMenu.add(new Separator());
		viewMenu.add(getAction(GEFActionConstants.ZOOM_IN));
		viewMenu.add(getAction(GEFActionConstants.ZOOM_OUT));
		
		menuManager.insertAfter(IWorkbenchActionConstants.M_EDIT, viewMenu);
		
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);

		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));

		toolBarManager.add(new Separator());
		toolBarManager.add(new ZoomComboContributionItem(getPage()));
	}

	@Override
	protected void declareGlobalActionKeys() {
	}

}
