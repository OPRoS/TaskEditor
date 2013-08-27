/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StateAction#getStateActionType <em>State Action Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateAction()
 * @model
 * @generated
 */
public interface StateAction extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>State Action Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.StateActionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Action Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Action Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.StateActionType
	 * @see #setStateActionType(StateActionType)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateAction_StateActionType()
	 * @model default=""
	 * @generated
	 */
	StateActionType getStateActionType();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.StateAction#getStateActionType <em>State Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Action Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.StateActionType
	 * @see #getStateActionType()
	 * @generated
	 */
	void setStateActionType(StateActionType value);

} // StateAction
