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
 * A representation of the model object '<em><b>Sub Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.SubDiagram#getItems <em>Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getSubDiagram()
 * @model
 * @generated
 */
public interface SubDiagram extends ItemElement {
	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ReferElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getSubDiagram_Items()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ReferElement> getItems();

} // SubDiagram
