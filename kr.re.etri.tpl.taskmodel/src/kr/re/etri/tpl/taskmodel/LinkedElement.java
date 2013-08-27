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
 * A representation of the model object '<em><b>Linked Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.LinkedElement#getSourceConnections <em>Source Connections</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.LinkedElement#getTargetConnections <em>Target Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLinkedElement()
 * @model
 * @generated
 */
public interface LinkedElement extends ShapeElement {
	/**
	 * Returns the value of the '<em><b>Source Connections</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ConnectionElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Connections</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLinkedElement_SourceConnections()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ConnectionElement> getSourceConnections();

	/**
	 * Returns the value of the '<em><b>Target Connections</b></em>' reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ConnectionElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Connections</em>' reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLinkedElement_TargetConnections()
	 * @model derived="true"
	 * @generated
	 */
	EList<ConnectionElement> getTargetConnections();

} // LinkedElement
