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
 * 모델의 name을 변경하는 command 클래스이다.
 * @author sfline
 */
public class ItemElementRenameCommand extends Command {
	/** name을 변경할 모델 요소 */
	private ItemElement source;
	/** 모델 요소의 새로운 이름 */
	private String name;
	/** 모델 요소의 변경 전 이름 */
	private String oldName;
	
	/**
	 * 이 command 를 수행한다.
	 * 이 메소드는 상위 클래스의 redo 메소드 호출 시 실행된다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		source.setName(name);
	}
	
	/**
	 * 모델 요소의 새로운 이름을 설정한다.
	 * @param string 새로운 이름
	 */
	public void setName(String string) {
		name = string;
	}
	
	/**
	 * 모델 요소의 원래 이름을 설정한다.
	 * @param string 원래 이름
	 */
	public void setOldName(String string) {
		oldName = string;
	}
	
	/**
	 * 이름을 변경할 모델 요소를 설정한다.
	 * @param activity 이름을 변경할 모델 요소
	 */
	public void setSource(ItemElement item) {
		source = item;
	}
	
	/**
	 * 이 command 를 Undo 한다.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		source.setName(oldName);
	}

}
