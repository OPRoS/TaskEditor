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
 * Ȱ��ȭ�� ȭ�� ����� CellEditor ��ġ��Ű�� ����� �����ϴ� Ŭ�����̴�.
 * @author sfline
 */
public class LabelCellEditorLocator implements CellEditorLocator {
	/** CelEditor �� ǥ�õ� Text �� ������ �ִ� Label */
	private Label label;

	/**
	 * Label �� ���� Editor �� �����Ѵ�.
	 * @param label the Label
	 */	
	public LabelCellEditorLocator(Label label) {
		setLabel(label);
	}
	
	/**
	 * Lebel ũ���� Editor ��Ʈ���� ��ġ��Ų��.
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
	 * Label �� ��ȯ�Ѵ�.
	 * @return ���� Label
	 */
	protected Label getLabel() {
		return label;
	}
	
	/**
	 * CellEditor �� ǥ���� Label �� �����Ѵ�
	 * @param label ���� Label �� �����Ѵ�.
	 */
	protected void setLabel(Label label) {
		this.label = label;
	}

}
