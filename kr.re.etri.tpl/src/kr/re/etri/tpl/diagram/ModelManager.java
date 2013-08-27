/*******************************************************************************
 * Copyright (c) 2005 Chris Aniszczyk.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Aniszczyk - updated to work with EMF
 *    IBM Corporation
 ******************************************************************************/

package kr.re.etri.tpl.diagram;

import java.util.Map;

import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * 모델 생성할 수 있는 Factory 를 제공하거나 모델을 생성하여 제공하는 클래스이다.
 * @author sfline
 *
 */
public class ModelManager {

	/** RTM 모델 생성 Factory */
	private static TaskModelFactory factory = getFactory();

	/**
	 * 모델을 생성할 수 있는 Factory 를 제공한다.
	 * @return RTM 모델 생성 Factory
	 */
	public static TaskModelFactory getFactory() {
		if (factory == null) {
			BehaviorModelPackageImpl.init();
			// Access the factory (needed to create instances)
			Map registry = EPackage.Registry.INSTANCE;
			String URI = TaskModelPackage.eNS_URI;
			TaskModelPackage modelPackage = (TaskModelPackage) registry.get(URI);
			factory = modelPackage.getTaskModelFactory();
		}
		return factory;
	}

	/**
	 * 주어진 cls 클래스의 instance 를 생성하여 제공한다.
	 * @param cls 생성될 모델의 타입
	 * @param parent 모델의 Parent 모델
	 * @return 생성된 모델
	 */
	public static EObject createElement(EClass cls, ItemElement parent) {
		TaskModelFactory factory = getFactory();
		  
		EObject obj = factory.create(cls);
		if(obj instanceof ItemElement) {
			((ItemElement)obj).setParent(parent);
		}
		  
		return obj;
	}
}
