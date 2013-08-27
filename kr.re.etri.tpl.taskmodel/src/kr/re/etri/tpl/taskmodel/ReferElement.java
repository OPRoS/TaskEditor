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
 * A representation of the model object '<em><b>Refer Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ReferElement#getRealModel <em>Real Model</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ReferElement#getItems <em>Items</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ReferElement#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ReferElement#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getReferElement()
 * @model
 * @generated
 */
public interface ReferElement extends LinkedElement {
	/**
	 * Returns the value of the '<em><b>Real Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Real Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Real Model</em>' reference.
	 * @see #setRealModel(ItemElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getReferElement_RealModel()
	 * @model derived="true"
	 * @generated
	 */
	ItemElement getRealModel();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ReferElement#getRealModel <em>Real Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Real Model</em>' reference.
	 * @see #getRealModel()
	 * @generated
	 */
	void setRealModel(ItemElement value);

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
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getReferElement_Items()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ItemElement> getItems();

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' attribute.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.ReferAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.ReferAttribute
	 * @see #setAttribute(ReferAttribute)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getReferElement_Attribute()
	 * @model
	 * @generated
	 */
	ReferAttribute getAttribute();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ReferElement#getAttribute <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.ReferAttribute
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(ReferAttribute value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getReferElement_Path()
	 * @model default=""
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ReferElement#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // ReferElement
