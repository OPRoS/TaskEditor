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
package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import kr.re.etri.tpl.diagram.commands.ItemElementRenameCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.BActionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BTaskElementEditPart;
import kr.re.etri.tpl.diagram.figures.ActionElementFigure;
import kr.re.etri.tpl.diagram.figures.ConnectionElementFigure;
import kr.re.etri.tpl.diagram.figures.ConnectorElementFigure;
import kr.re.etri.tpl.diagram.figures.StateElementFigure;
import kr.re.etri.tpl.diagram.figures.BehaviorElementFigure;
import kr.re.etri.tpl.diagram.figures.TaskElementFigure;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

/**
 * 선택된 모델의 이름을 편집하기 위한 Policy 클래스이다.
 * 
 * @author sfline
 */
public class BLabelDirectEditPolicy extends DirectEditPolicy {
	private static Logger logger = Logger.getLogger(BLabelDirectEditPolicy.class);
	
	/**
	 * 선택된 모델의 이름을 변경할 command 를 반환한다.
	 * @see DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		logger.debug("");
		ItemElementRenameCommand cmd = new ItemElementRenameCommand();
		cmd.setSource((ItemElement)getHost().getModel());
		cmd.setOldName(((ItemElement)getHost().getModel()).getName());
		cmd.setName((String)request.getCellEditor().getValue());
		return cmd;
	}
	
	/**
	 * CellEditor 에 입력된 내용을 선택된 모델의 모델에 적용한다. 
	 * @see DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String)request.getCellEditor().getValue();
		EditPart editPart = getHost();
		if(editPart instanceof BBehaviorElementEditPart) {
			IFigure figure = getHostFigure();
			((BehaviorElementFigure)figure).getLabelFigure().setText(value);
		}
		else if(editPart instanceof BStateElementEditPart) {
			IFigure figure = getHostFigure();
			((StateElementFigure)figure).getLabelFigure().setText(value);
		}
		else if(editPart instanceof BActionElementEditPart) {
			IFigure figure = getHostFigure();
			((ActionElementFigure)figure).getLabelFigure().setText(value);
		}
		else if(editPart instanceof BConnectionElementEditPart) {
			IFigure figure = getHostFigure();
			((ConnectionElementFigure)figure).getLabelFigure().setText(value);
		}
		else if (editPart instanceof BConnectorElementEditPart) {	// KJH 20101130 s, connector label
			IFigure figure = getHostFigure();
			((ConnectorElementFigure)figure).getLabelFigure().setText(value);
		}														// KJH 20101130 e, connector label
		else if (editPart instanceof BTaskElementEditPart) {	// KJH 20110420 s, task label
			IFigure figure = getHostFigure();
			((TaskElementFigure)figure).getLabelFigure().setText(value);
		}														// KJH 201420 e, task label
	//	((Label)getHostFigure()).setText(value);
		//hack to prevent async layout from placing the cell editor twice.
	//	getHostFigure().getUpdateManager().performUpdate();
	}

}
