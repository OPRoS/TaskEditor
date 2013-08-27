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

import kr.re.etri.tpl.diagram.views.actions.NewConstantAction;
import kr.re.etri.tpl.diagram.views.actions.NewEnumerationAction;
import kr.re.etri.tpl.diagram.views.actions.NewEnumerationItemAction;
import kr.re.etri.tpl.diagram.views.actions.NewFunctionAction;
import kr.re.etri.tpl.diagram.views.actions.NewModelAction;
import kr.re.etri.tpl.diagram.views.actions.NewParameterAction;
import kr.re.etri.tpl.diagram.views.actions.NewSymbolAction;
import kr.re.etri.tpl.diagram.views.actions.RefreshModelViewAction;
import kr.re.etri.tpl.diagram.views.symbolparts.SNavEnumElementEditPart;
import kr.re.etri.tpl.diagram.views.symbolparts.SNavFunctionEditPart;
import kr.re.etri.tpl.diagram.views.symbolparts.SNavModelElementEditPart;
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
public class SNavViewContextMenuProvider extends ContextMenuProvider {

	/** The editor's action registry. */
	private ActionRegistry actionRegistry;

	public static final String MENU_ID = "kr.re.etri.tpl.views.SNavViewContextMenu";

	/**
	 * Instantiate a new menu context provider for the specified EditPartViewer 
	 * and ActionRegistry.
	 * @param viewer	the editor's graphical viewer
	 * @param registry	the editor's action registry
	 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
	 */
	public SNavViewContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
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
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction(RefreshModelViewAction.ID));
		ISelection sel = this.getViewer().getSelection();

		NewEnumerationAction enumAction = (NewEnumerationAction)getAction(NewEnumerationAction.actionId);
		enumAction.setParent(null);

		if(enumAction != null) {
			menu.appendToGroup(
					GEFActionConstants.GROUP_ADD,
					enumAction);
		}

		NewEnumerationItemAction enumItemAction = (NewEnumerationItemAction)getAction(NewEnumerationItemAction.actionId);
		enumItemAction.setParent(null);

		// 선택된 노드가 ModelElement인 경우만 Parent가 될 수 있다.
		// Include된 모델들은 Parent가 될 수 없다.
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && enumItemAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof SNavEnumElementEditPart) {
					ItemElement parentModel = ((SNavEnumElementEditPart)selItem).getCastedModel();
					if(parentModel.isIncluded() == false) {
						enumItemAction.setParent(parentModel);

						menu.appendToGroup(
								GEFActionConstants.GROUP_ADD,
								enumItemAction);
					}
				}
			}
		}

		NewModelAction modelAction = (NewModelAction)getAction(NewModelAction.actionId);
		modelAction.setParent(null);

		// 선택된 노드가 Parent가 가능한 노드인지 확인하여 가능하다면 Parent로 설정한다.
		// Include된 모델들은 Parent가 될 수 없다.
		if(sel instanceof StructuredSelection && modelAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof SNavModelElementEditPart) {
					ItemElement parentModel = ((SNavModelElementEditPart)selItem).getCastedModel();
					if(parentModel.isIncluded() == false) {
						modelAction.setParent(parentModel);
					}
				}
			}
		}

		if(modelAction != null) {
			menu.appendToGroup(
					GEFActionConstants.GROUP_ADD,
					modelAction);
		}

		NewConstantAction constAction = (NewConstantAction)getAction(NewConstantAction.actionId);
		constAction.setParent(null);

		// 선택된 노드가 ModelElement인 경우만 Parent가 될 수 있다.
		// Include된 모델들은 Parent가 될 수 없다.
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && constAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof SNavModelElementEditPart) {
					ItemElement parentModel = ((SNavModelElementEditPart)selItem).getCastedModel();
					if(parentModel.isIncluded() == false) {
						constAction.setParent(parentModel);

						menu.appendToGroup(
								GEFActionConstants.GROUP_ADD,
								constAction);
					}
				}
			}
		}

		NewSymbolAction symbolAction = (NewSymbolAction)getAction(NewSymbolAction.actionId);
		symbolAction.setParent(null);

		// 선택된 노드가 ModelElement인 경우만 Parent가 될 수 있다.
		// Include된 모델들은 Parent가 될 수 없다.
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && symbolAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof SNavModelElementEditPart) {
					ItemElement parentModel = ((SNavModelElementEditPart)selItem).getCastedModel();
					if(parentModel.isIncluded() == false) {
						symbolAction.setParent(parentModel);

						menu.appendToGroup(
								GEFActionConstants.GROUP_ADD,
								symbolAction);
					}
				}
			}
		}

		NewFunctionAction funcAction = (NewFunctionAction)getAction(NewFunctionAction.actionId);
		funcAction.setParent(null);

		// 선택된 노드가 ModelElement인 경우만 Parent가 될 수 있다.
		// Include된 모델들은 Parent가 될 수 없다.
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && funcAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof SNavModelElementEditPart) {
					ItemElement parentModel = ((SNavModelElementEditPart)selItem).getCastedModel();
					if(parentModel.isIncluded() == false) {
						funcAction.setParent(parentModel);

						menu.appendToGroup(
								GEFActionConstants.GROUP_ADD,
								funcAction);
					}
				}
			}
		}

		NewParameterAction paramAction = (NewParameterAction)getAction(NewParameterAction.actionId);
		paramAction.setParent(null);

		// 선택된 노드가 Function인 경우만 Parent가 될 수 있다.
		// Include된 모델들은 Parent가 될 수 없다.
		sel = this.getViewer().getSelection();
		if(sel instanceof StructuredSelection && paramAction != null) {
			if(((StructuredSelection)sel).size() > 0) {
				Object selItem = ((StructuredSelection)sel).getFirstElement();
				if(selItem instanceof SNavFunctionEditPart) {
					ItemElement parentModel = ((SNavFunctionEditPart)selItem).getCastedModel();
					if(parentModel.isIncluded() == false) {
						paramAction.setParent(parentModel);

						menu.appendToGroup(
								GEFActionConstants.GROUP_ADD,
								paramAction);
					}
				}
			}
		}
		
		// KJH 20100920, import model
//		ImportModelAction importAction = (ImportModelAction)getAction(ImportModelAction.actionId);
//		importAction.setParent(null);
//		importAction.setEnabled(true);
//		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, importAction);
//
//		ExportModelAction exportAction = (ExportModelAction)getAction(ExportModelAction.actionId);
//		exportAction.setModelElement(null);
//		
//		sel = this.getViewer().getSelection();
//		if (sel instanceof StructuredSelection) {
//			if (((StructuredSelection)sel).size() > 0) {
//				Object selItem = ((StructuredSelection)sel).getFirstElement();
//				if (selItem instanceof SNavModelElementEditPart) {
//					ItemElement itemElement = ((SNavModelElementEditPart)selItem).getCastedModel();
//					ItemElement parentElement = itemElement.getParent();
//					if (itemElement instanceof ModelElement) {
//						exportAction.setModelElement((ModelElement)itemElement);
//						menu.appendToGroup(GEFActionConstants.GROUP_EDIT, exportAction);
//					}
//					
//					exportAction.setEnabled(parentElement instanceof ModelDiagram);
//				}
//			}
//		}
	}

	private IAction getAction(String actionId) {
		return actionRegistry.getAction(actionId);
	}

}
