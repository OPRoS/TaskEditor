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

import kr.re.etri.tpl.diagram.editors.actions.ExternalBehaviorSetAction;
import kr.re.etri.tpl.diagram.editors.actions.WorkerInitTaskOpenAction;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.monitoring.controller.MonitoringAction;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;


/**
 * Provides context menu actions for the ShapesEditor.
 * @author Elias Volanakis
 */
public class BehaviorDiagramContextMenuProvider extends TPLDiagramContextMenuProvider {
	private static Logger logger = Logger.getLogger(BehaviorDiagramContextMenuProvider.class);
	
	public static final String MENU_ID = "kr.re.etri.tpl.diagram.editors.TaskDiagramContextMenu";
	
	/**
	 * Instantiate a new menu context provider for the specified EditPartViewer 
	 * and ActionRegistry.
	 * @param viewer	the editor's graphical viewer
	 * @param registry	the editor's action registry
	 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
	 */
	public BehaviorDiagramContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
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
		ISelection sel;
//		StateDiagramOpenAction action = (StateDiagramOpenAction)getAction(StateDiagramOpenAction.actionId);
//		
//		ISelection sel = this.getViewer().getSelection();
//		if(sel instanceof StructuredSelection && action != null) {
//			if(((StructuredSelection)sel).size() > 0) {
//				Object selItem = ((StructuredSelection)sel).getFirstElement();
//				if(selItem instanceof TDgmTaskElementEditPart) {
//					ReferElement refItem = ((TDgmTaskElementEditPart)selItem).getCastedModel();
//					TaskElement realItem = (TaskElement)refItem.getRealModel();
//					action.setTask(realItem);
//
//					menu.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
//				}
//			}
//		}
		
		MonitoringAction monitoringAction = (MonitoringAction)getAction(MonitoringAction.actionId);
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, monitoringAction);
//		
//		sel = this.getViewer().getSelection();
//		if(sel instanceof StructuredSelection && monitoringAction != null) {
//			if(((StructuredSelection)sel).size() > 0) {
//				logger.debug("selection : "+((StructuredSelection)sel).getFirstElement());
//				menu.appendToGroup(GEFActionConstants.GROUP_UNDO, monitoringAction);
//				monitoringAction.setEditPart((BItemElementEditPart)((StructuredSelection)sel).getFirstElement());
//				
//			}
//		}
		
		WorkerInitTaskOpenAction taskSetAction = (WorkerInitTaskOpenAction)getAction(WorkerInitTaskOpenAction.actionId);
		
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && taskSetAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof BBehaviorElementEditPart) {
					ReferElement refItem = ((BBehaviorElementEditPart)selItem).getCastedModel();
					BehaviorElement realItem = (BehaviorElement)refItem.getRealModel();
					if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
						ItemElement parent = refItem.getParent();
						while (parent != null && (parent instanceof ModelDiagram) == false) {
							parent = parent.getParent();
						}
						
						if (parent instanceof ModelDiagram) {
							taskSetAction.setModelDiagram((ModelDiagram)parent);
							taskSetAction.setTaskElement(realItem);
						}
						else {
							taskSetAction.setEnabled(false);
						}
					}
					else {
						taskSetAction.setModelDiagram((ModelDiagram)TPLUtil.getRootModel(realItem));
						taskSetAction.setTaskElement(realItem);
					}

					menu.appendToGroup(GEFActionConstants.GROUP_VIEW, taskSetAction);
				}
			}
		}
		
		// KJH 20100825 s, external behavior selection
		ExternalBehaviorSetAction behaviorSetAction = (ExternalBehaviorSetAction)getAction(ExternalBehaviorSetAction.actionId);
		
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && behaviorSetAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof BBehaviorElementEditPart) {
					ReferElement refItem = ((BBehaviorElementEditPart)selItem).getCastedModel();
					behaviorSetAction.setReferElement(refItem);
					behaviorSetAction.setEnabled(refItem.getAttribute() == ReferAttribute.EXTERNAL);
					menu.appendToGroup(GEFActionConstants.GROUP_VIEW, behaviorSetAction);
				}
			}
		}
	}

}
