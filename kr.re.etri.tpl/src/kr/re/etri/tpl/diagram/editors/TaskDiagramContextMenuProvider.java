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

import kr.re.etri.tpl.diagram.actions.RTMActionFactory;
import kr.re.etri.tpl.diagram.editors.actions.WorkerDiagramCloseAction;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.UIPlugin;


/**
 * Provides context menu actions for the ShapesEditor.
 * @author Elias Volanakis
 */
public class TaskDiagramContextMenuProvider extends TPLDiagramContextMenuProvider {
	
	public static final String MENU_ID = "kr.re.etri.tpl.diagram.editors.WorkerDiagramContextMenu";
	
	/**
	 * Instantiate a new menu context provider for the specified EditPartViewer 
	 * and ActionRegistry.
	 * @param viewer	the editor's graphical viewer
	 * @param registry	the editor's action registry
	 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
	 */
	public TaskDiagramContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer, registry);
	}
	
	/**
	 * Called when the context menu is about to show. Actions, 
	 * whose state is enabled, will appear in the context menu.
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void buildContextMenu(IMenuManager menu) {
		super.buildContextMenu(menu);
		
		// Add actions to the menu
		menu.appendToGroup(
				GEFActionConstants.GROUP_EDIT,
				getAction(WorkerDiagramCloseAction.actionId));
	}

}
