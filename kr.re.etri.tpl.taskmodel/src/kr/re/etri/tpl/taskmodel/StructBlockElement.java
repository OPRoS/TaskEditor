/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Struct Block Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.StructBlockElement#getStructType <em>Struct Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStructBlockElement()
 * @model
 * @generated
 */
public interface StructBlockElement extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>Struct Type</b></em>' attribute.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.StructType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.StructType
	 * @see #setStructType(StructType)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStructBlockElement_StructType()
	 * @model
	 * @generated
	 */
	StructType getStructType();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.StructBlockElement#getStructType <em>Struct Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.StructType
	 * @see #getStructType()
	 * @generated
	 */
	void setStructType(StructType value);

} // StructBlockElement
