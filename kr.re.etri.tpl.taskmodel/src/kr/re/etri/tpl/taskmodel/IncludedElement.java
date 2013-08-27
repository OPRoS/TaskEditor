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
 * A representation of the model object '<em><b>Included Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.IncludedElement#getIncludePath <em>Include Path</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.IncludedElement#getItems <em>Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getIncludedElement()
 * @model
 * @generated
 */
public interface IncludedElement extends ItemElement {
	/**
	 * Returns the value of the '<em><b>Include Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Path</em>' attribute.
	 * @see #setIncludePath(String)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getIncludedElement_IncludePath()
	 * @model
	 * @generated
	 */
	String getIncludePath();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.IncludedElement#getIncludePath <em>Include Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Path</em>' attribute.
	 * @see #getIncludePath()
	 * @generated
	 */
	void setIncludePath(String value);

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ItemElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getIncludedElement_Items()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ItemElement> getItems();

} // IncludedElement
