/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>With Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.WithElement#getCycle <em>Cycle</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWithElement()
 * @model
 * @generated
 */
public interface WithElement extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>Cycle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cycle</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cycle</em>' attribute.
	 * @see #setCycle(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWithElement_Cycle()
	 * @model required="true" derived="true"
	 * @generated
	 */
	int getCycle();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.WithElement#getCycle <em>Cycle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cycle</em>' attribute.
	 * @see #getCycle()
	 * @generated
	 */
	void setCycle(int value);

} // WithElement
