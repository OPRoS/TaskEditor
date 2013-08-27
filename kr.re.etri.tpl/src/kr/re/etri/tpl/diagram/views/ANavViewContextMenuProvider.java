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

import kr.re.etri.tpl.diagram.views.actionparts.ANavActionElementEditPart;
import kr.re.etri.tpl.diagram.views.actions.NewActionAction;
import kr.re.etri.tpl.diagram.views.actions.NewParameterAction;
import kr.re.etri.tpl.taskmodel.ItemElement;

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
public class ANavViewContextMenuProvider extends ContextMenuProvider {

/** The editor's action registry. */
private ActionRegistry actionRegistry;

public static final String MENU_ID = "kr.re.etri.tpl.views.ANavViewContextMenu";

/**
 * Instantiate a new menu context provider for the specified EditPartViewer 
 * and ActionRegistry.
 * @param viewer	the editor's graphical viewer
 * @param registry	the editor's action registry
 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
 */
public ANavViewContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
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
	
	String actionId;
	
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
			getAction(NewActionAction.actionId));
	
	ISelection sel = this.getViewer().getSelection();

	NewParameterAction paramAction = (NewParameterAction)getAction(NewParameterAction.actionId);
	paramAction.setParent(null);

	// 선택된 노드가 ActionElement인 경우만 Parent가 될 수 있다.
	// Include된 모델들은 Parent가 될 수 없다.
	sel = this.getViewer().getSelection();
	if(sel instanceof StructuredSelection && paramAction != null) {
		if(((StructuredSelection)sel).size() > 0) {
			Object selItem = ((StructuredSelection)sel).getFirstElement();
			if(selItem instanceof ANavActionElementEditPart) {
				ItemElement parentModel = ((ANavActionElementEditPart)selItem).getCastedModel();
				if(parentModel.isIncluded() == false) {
					paramAction.setParent(parentModel);
	
					menu.appendToGroup(
							GEFActionConstants.GROUP_ADD,
							paramAction);
				}
			}
		}
	}
}

private IAction getAction(String actionId) {
	return actionRegistry.getAction(actionId);
}

}
