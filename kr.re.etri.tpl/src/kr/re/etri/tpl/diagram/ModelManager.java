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
 * �� ������ �� �ִ� Factory �� �����ϰų� ���� �����Ͽ� �����ϴ� Ŭ�����̴�.
 * @author sfline
 *
 */
public class ModelManager {

	/** RTM �� ���� Factory */
	private static TaskModelFactory factory = getFactory();

	/**
	 * ���� ������ �� �ִ� Factory �� �����Ѵ�.
	 * @return RTM �� ���� Factory
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
	 * �־��� cls Ŭ������ instance �� �����Ͽ� �����Ѵ�.
	 * @param cls ������ ���� Ÿ��
	 * @param parent ���� Parent ��
	 * @return ������ ��
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
