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
package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.gef.commands.Command;

/**
 * ���� name�� �����ϴ� command Ŭ�����̴�.
 * @author sfline
 */
public class ItemElementRenameCommand extends Command {
	/** name�� ������ �� ��� */
	private ItemElement source;
	/** �� ����� ���ο� �̸� */
	private String name;
	/** �� ����� ���� �� �̸� */
	private String oldName;
	
	/**
	 * �� command �� �����Ѵ�.
	 * �� �޼ҵ�� ���� Ŭ������ redo �޼ҵ� ȣ�� �� ����ȴ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		source.setName(name);
	}
	
	/**
	 * �� ����� ���ο� �̸��� �����Ѵ�.
	 * @param string ���ο� �̸�
	 */
	public void setName(String string) {
		name = string;
	}
	
	/**
	 * �� ����� ���� �̸��� �����Ѵ�.
	 * @param string ���� �̸�
	 */
	public void setOldName(String string) {
		oldName = string;
	}
	
	/**
	 * �̸��� ������ �� ��Ҹ� �����Ѵ�.
	 * @param activity �̸��� ������ �� ���
	 */
	public void setSource(ItemElement item) {
		source = item;
	}
	
	/**
	 * �� command �� Undo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		source.setName(oldName);
	}

}
