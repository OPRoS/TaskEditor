/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *??Elias Volanakis - initial API and implementation
 *    Chris Aniszczyk - updated to work with EMF
 *    IBM Corporation
 ?******************************************************************************/
package kr.re.etri.tpl.diagram.views;

import kr.re.etri.tpl.diagram.views.actions.NewTaskAction;
import kr.re.etri.tpl.diagram.views.actions.OpenWorkerAction;
import kr.re.etri.tpl.diagram.views.taskparts.TNavTaskElementEditPart;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.ActionFactory;


/**
 * Provides context menu actions for the ShapesEditor.
 * @author Elias Volanakis
 */
public class TNavViewContextMenuProvider extends ContextMenuProvider {

/** The editor's action registry. */
private ActionRegistry actionRegistry;

public static final String MENU_ID = "kr.re.etri.tpl.views.WNavViewContextMenu";

/**
 * Instantiate a new menu context provider for the specified EditPartViewer 
 * and ActionRegistry.
 * @param viewer	the editor's graphical viewer
 * @param registry	the editor's action registry
 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
 */
public TNavViewContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
	super(viewer);
	if (registry == null) {
		throw new IllegalArgumentException();
	}
	actionRegistry = registry;
}

/**
 * Called when the context menu is about to show. Actions, 
 * whose state is enabled, will appear in the context menu.
 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
 */
public void buildContextMenu(IMenuManager menu) {
	// Add standard action groups to the menu
	GEFActionConstants.addStandardActionGroups(menu);
	
	// Add actions to the menu
	menu.appendToGroup(
			GEFActionConstants.GROUP_UNDO, // target group id
			getAction(ActionFactory.UNDO.getId())); // action to add
	menu.appendToGroup(
			GEFActionConstants.GROUP_UNDO, 
			getAction(ActionFactory.REDO.getId()));
	menu.appendToGroup(
			GEFActionConstants.GROUP_EDIT,
			getAction(ActionFactory.DELETE.getId()));
	menu.appendToGroup(
			GEFActionConstants.GROUP_ADD,
			getAction(NewTaskAction.actionId));
	
	
	OpenWorkerAction action = (OpenWorkerAction)getAction(OpenWorkerAction.actionId);
	
	ISelection sel = this.getViewer().getSelection();
	if(sel instanceof StructuredSelection && action != null) {
		if(((StructuredSelection)sel).size() > 0) {
			Object selItem = ((StructuredSelection)sel).getFirstElement();
			if(selItem instanceof TNavTaskElementEditPart) {
				action.setWorker((TaskElement)((TNavTaskElementEditPart)selItem).getModel());

				menu.appendToGroup(
						GEFActionConstants.GROUP_EDIT,
						action);
			}
		}
	}
}

private IAction getAction(String actionId) {
	return actionRegistry.getAction(actionId);
}

}
