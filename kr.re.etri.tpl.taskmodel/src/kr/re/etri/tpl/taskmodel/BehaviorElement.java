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
 * A representation of the model object '<em><b>Task Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getParams <em>Params</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getStates <em>States</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getConstruct <em>Construct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getDestruct <em>Destruct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getBifurcates <em>Bifurcates</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement()
 * @model
 * @generated
 */
public interface BehaviorElement extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>Params</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Params</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement_Params()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<Parameter> getParams();

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.StateElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement_States()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<StateElement> getStates();

	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' reference.
	 * @see #setInitialState(StateElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement_InitialState()
	 * @model
	 * @generated
	 */
	StateElement getInitialState();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getInitialState <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(StateElement value);

	/**
	 * Returns the value of the '<em><b>Construct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Construct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Construct</em>' containment reference.
	 * @see #setConstruct(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement_Construct()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getConstruct();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getConstruct <em>Construct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Construct</em>' containment reference.
	 * @see #getConstruct()
	 * @generated
	 */
	void setConstruct(StructBlockElement value);

	/**
	 * Returns the value of the '<em><b>Destruct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destruct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destruct</em>' containment reference.
	 * @see #setDestruct(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement_Destruct()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getDestruct();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getDestruct <em>Destruct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destruct</em>' containment reference.
	 * @see #getDestruct()
	 * @generated
	 */
	void setDestruct(StructBlockElement value);

	/**
	 * Returns the value of the '<em><b>Bifurcates</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ExpandTransElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bifurcates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bifurcates</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getTaskElement_Bifurcates()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ExpandTransElement> getBifurcates();

} // TaskElement
