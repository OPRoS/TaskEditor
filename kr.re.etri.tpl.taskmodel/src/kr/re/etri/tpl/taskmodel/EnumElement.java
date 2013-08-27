/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.EnumElement#getEnumItem <em>Enum Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getEnumElement()
 * @model
 * @generated
 */
public interface EnumElement extends ItemElement {
	/**
	 * Returns the value of the '<em><b>Enum Item</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.EnumItemElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Item</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum Item</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getEnumElement_EnumItem()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<EnumItemElement> getEnumItem();

} // EnumElement
