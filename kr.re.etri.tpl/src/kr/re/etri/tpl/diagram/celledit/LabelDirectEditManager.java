/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package kr.re.etri.tpl.diagram.celledit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;

/**
 * Label 에 대한 CellEditor 을 관리할 DirectEditManager 클래스이다.
 * @author sfline
 */
public class LabelDirectEditManager extends DirectEditManager {

	/** Label 의 폰트 */
	Font scaledFont;
	/**  검증 리스너 */
	protected VerifyListener verifyListener;
	protected Label activityLabel;
	
	/**
	 * 주어진 Parameter 로 LabelDirectEditManager 를 생성한다.
	 * @param source Source EditPart
	 * @param editorType Editor 의 타입
	 * @param locator the CellEditor 의 위치
	 */
	public LabelDirectEditManager(GraphicalEditPart source, Class editorType,
										CellEditorLocator locator, Label label) {
		super(source, editorType, locator);
		activityLabel = label;
	}
	
	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 * @Override
	 */
	protected void bringDown() {
		//This method might be re-entered when super.bringDown() is called.
		Font disposeFont = scaledFont;
		scaledFont = null;
		super.bringDown();
		if (disposeFont != null)
			disposeFont.dispose();	
	}
	
	/**
	 * CellEditor 를 초기화 한다.
	 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
	 * @Override
	 */
	protected void initCellEditor() {
		Text text = (Text)getCellEditor().getControl();
		verifyListener = new VerifyListener() {
			public void verifyText(VerifyEvent event) {
				Text text = (Text)getCellEditor().getControl();
				String oldText = text.getText();
				String leftText = oldText.substring(0, event.start);
				String rightText = oldText.substring(event.end, oldText.length());
				GC gc = new GC(text);
				Point size = gc.textExtent(leftText + event.text + rightText);
				gc.dispose();
				if (size.x != 0)
					size = text.computeSize(size.x, SWT.DEFAULT);
				getCellEditor().getControl().setSize(size.x, size.y);
			}
		};
		text.addVerifyListener(verifyListener);
		
		String initialLabelText = activityLabel.getText();
		getCellEditor().setValue(initialLabelText);
		IFigure figure = getEditPart().getFigure();
		scaledFont = figure.getFont();
		FontData data = scaledFont.getFontData()[0];
		Dimension fontSize = new Dimension(0, data.getHeight());
		activityLabel.translateToAbsolute(fontSize);
		data.setHeight(fontSize.height);
		scaledFont = new Font(null, data);
		
		text.setFont(scaledFont);
	}
	
	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#unhookListeners()
	 * @Override
	 */
	protected void unhookListeners() {
		super.unhookListeners();
		Text text = (Text)getCellEditor().getControl();
		text.removeVerifyListener(verifyListener);
		verifyListener = null;
	}
	
}
