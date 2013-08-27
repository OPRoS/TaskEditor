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
 * A representation of the model object '<em><b>State Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StateElement#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StateElement#getEntry <em>Entry</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StateElement#getStay <em>Stay</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StateElement#getExit <em>Exit</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StateElement#getBifurcates <em>Bifurcates</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateElement()
 * @model
 * @generated
 */
public interface StateElement extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.StateAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.StateAttribute
	 * @see #setAttribute(StateAttribute)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateElement_Attribute()
	 * @model default=""
	 * @generated
	 */
	StateAttribute getAttribute();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.StateElement#getAttribute <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.StateAttribute
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(StateAttribute value);

	/**
	 * Returns the value of the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry</em>' containment reference.
	 * @see #setEntry(StateAction)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateElement_Entry()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StateAction getEntry();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.StateElement#getEntry <em>Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' containment reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(StateAction value);

	/**
	 * Returns the value of the '<em><b>Stay</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stay</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stay</em>' containment reference.
	 * @see #setStay(StateAction)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateElement_Stay()
	 * @model containment="true" required="true" derived="true"
	 * @generated
	 */
	StateAction getStay();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.StateElement#getStay <em>Stay</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stay</em>' containment reference.
	 * @see #getStay()
	 * @generated
	 */
	void setStay(StateAction value);

	/**
	 * Returns the value of the '<em><b>Exit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exit</em>' containment reference.
	 * @see #setExit(StateAction)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateElement_Exit()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StateAction getExit();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.StateElement#getExit <em>Exit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit</em>' containment reference.
	 * @see #getExit()
	 * @generated
	 */
	void setExit(StateAction value);

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
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateElement_Bifurcates()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ExpandTransElement> getBifurcates();

} // StateElement
