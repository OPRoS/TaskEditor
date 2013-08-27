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

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

/**
 * 활성화된 화면 요소의 CellEditor 위치시키는 기능을 수행하는 클래스이다.
 * @author sfline
 */
public class LabelCellEditorLocator implements CellEditorLocator {
	/** CelEditor 에 표시될 Text 를 가지고 있는 Label */
	private Label label;

	/**
	 * Label 에 대한 Editor 를 생성한다.
	 * @param label the Label
	 */	
	public LabelCellEditorLocator(Label label) {
		setLabel(label);
	}
	
	/**
	 * Lebel 크기의 Editor 컨트롤을 위치시킨다.
	 * @see CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
	 */
	public void relocate(CellEditor celleditor) {
		Text text = (Text)celleditor.getControl();
//		System.out.println(text.getText());
		Dimension size = TextUtilities.INSTANCE.getTextExtents(text.getText(), text.getFont());
		Rectangle rect = label.getTextBounds().getCopy();
		label.translateToAbsolute(rect);
		text.setBounds(rect.x - 1, rect.y - 1, size.width + 15, size.height + 1);
	}
	
	/**
	 * Label 을 반환한다.
	 * @return 원본 Label
	 */
	protected Label getLabel() {
		return label;
	}
	
	/**
	 * CellEditor 를 표시할 Label 을 설정한다
	 * @param label 원본 Label 을 설정한다.
	 */
	protected void setLabel(Label label) {
		this.label = label;
	}

}
