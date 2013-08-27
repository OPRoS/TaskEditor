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
 * A representation of the model object '<em><b>Connection Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getSource <em>Source</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget <em>Target</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getSource2 <em>Source2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget2 <em>Target2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getRelationship <em>Relationship</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement()
 * @model
 * @generated
 */
public interface ConnectionElement extends LineElement {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(LinkedElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement_Source()
	 * @model derived="true"
	 * @generated
	 */
	LinkedElement getSource();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(LinkedElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(LinkedElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement_Target()
	 * @model derived="true"
	 * @generated
	 */
	LinkedElement getTarget();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(LinkedElement value);

	/**
	 * Returns the value of the '<em><b>Source2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source2</em>' reference.
	 * @see #setSource2(LinkedElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement_Source2()
	 * @model derived="true"
	 * @generated
	 */
	LinkedElement getSource2();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getSource2 <em>Source2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source2</em>' reference.
	 * @see #getSource2()
	 * @generated
	 */
	void setSource2(LinkedElement value);

	/**
	 * Returns the value of the '<em><b>Target2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target2</em>' reference.
	 * @see #setTarget2(LinkedElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement_Target2()
	 * @model derived="true"
	 * @generated
	 */
	LinkedElement getTarget2();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget2 <em>Target2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target2</em>' reference.
	 * @see #getTarget2()
	 * @generated
	 */
	void setTarget2(LinkedElement value);

	/**
	 * Returns the value of the '<em><b>Relationship</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.RelationShip}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relationship</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relationship</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.RelationShip
	 * @see #setRelationship(RelationShip)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement_Relationship()
	 * @model default="0" required="true" derived="true"
	 * @generated
	 */
	RelationShip getRelationship();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getRelationship <em>Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relationship</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.RelationShip
	 * @see #getRelationship()
	 * @generated
	 */
	void setRelationship(RelationShip value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' attribute list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectionElement_Condition()
	 * @model derived="true"
	 * @generated
	 */
	EList<String> getCondition();

} // ConnectionElement
