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
package kr.re.etri.tpl.diagram.editors;

import kr.re.etri.tpl.diagram.monitoring.controller.MonitoringAction;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;


/**
 * Provides context menu actions for the ShapesEditor.
 * @author Elias Volanakis
 */
public class TPLDiagramContextMenuProvider extends ContextMenuProvider {

/** The editor's action registry. */
private ActionRegistry actionRegistry;

/**
 * Instantiate a new menu context provider for the specified EditPartViewer 
 * and ActionRegistry.
 * @param viewer	the editor's graphical viewer
 * @param registry	the editor's action registry
 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
 */
public TPLDiagramContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
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
	
//	IWorkbench wb = PlatformUI.getWorkbench();
//	IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
//	
//	IWorkbenchPage wp = wbw.getActivePage();
//	IEditorPart editPart= wp.getActiveEditor();
//	
//	menu.appendToGroup(
//			GEFActionConstants.GROUP_UNDO,new MonitoringAction(editPart));	
	menu.appendToGroup(
			GEFActionConstants.GROUP_EDIT,
			getAction(ActionFactory.DELETE.getId()));
	
	
	
}

protected IAction getAction(String actionId) {
	return actionRegistry.getAction(actionId);
}

}
