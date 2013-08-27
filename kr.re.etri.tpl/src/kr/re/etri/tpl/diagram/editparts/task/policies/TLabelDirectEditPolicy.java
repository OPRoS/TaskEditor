/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package kr.re.etri.tpl.diagram.editparts.task.policies;

import kr.re.etri.tpl.diagram.commands.ItemElementRenameCommand;
import kr.re.etri.tpl.diagram.editparts.task.TActionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TConnectionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TStateElementEditPart;
import kr.re.etri.tpl.diagram.figures.ActionElementFigure;
import kr.re.etri.tpl.diagram.figures.ConnectionElementFigure;
import kr.re.etri.tpl.diagram.figures.StateElementFigure;
import kr.re.etri.tpl.diagram.figures.BehaviorElementFigure;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

/**
 * ���õ� ���� �̸��� �����ϱ� ���� Policy Ŭ�����̴�.
 * 
 * @author sfline
 */
public class TLabelDirectEditPolicy extends DirectEditPolicy {
	
	/**
	 * ���õ� ���� �̸��� ������ command �� ��ȯ�Ѵ�.
	 * @see DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		ItemElementRenameCommand cmd = new ItemElementRenameCommand();
		cmd.setSource((ItemElement)getHost().getModel());
		cmd.setOldName(((ItemElement)getHost().getModel()).getName());
		cmd.setName((String)request.getCellEditor().getValue());
		return cmd;
	}
	
	/**
	 * CellEditor �� �Էµ� ������ ���õ� ���� �𵨿� �����Ѵ�. 
	 * @see DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String)request.getCellEditor().getValue();
		EditPart editPart = getHost();
		if(editPart instanceof TBehaviorElementEditPart) {
			IFigure figure = getHostFigure();
			((BehaviorElementFigure)figure).getLabelFigure().setText(value);
		}
		if(editPart instanceof TStateElementEditPart) {
			IFigure figure = getHostFigure();
			((StateElementFigure)figure).getLabelFigure().setText(value);
		}
		if(editPart instanceof TActionElementEditPart) {
			IFigure figure = getHostFigure();
			((ActionElementFigure)figure).getLabelFigure().setText(value);
		}
		if(editPart instanceof TConnectionElementEditPart) {
			IFigure figure = getHostFigure();
			((ConnectionElementFigure)figure).getLabelFigure().setText(value);
		}
	//	((Label)getHostFigure()).setText(value);
		//hack to prevent async layout from placing the cell editor twice.
	//	getHostFigure().getUpdateManager().performUpdate();
	}

}
