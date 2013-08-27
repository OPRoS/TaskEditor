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
 * A representation of the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelElement#getSymbols <em>Symbols</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelElement#getConstants <em>Constants</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelElement#getFunctions <em>Functions</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelElement#getModels <em>Models</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelElement()
 * @model
 * @generated
 */
public interface ModelElement extends ItemElement {
	/**
	 * Returns the value of the '<em><b>Symbols</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.Symbol}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbols</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbols</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelElement_Symbols()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<Symbol> getSymbols();

	/**
	 * Returns the value of the '<em><b>Constants</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.Constant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constants</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constants</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelElement_Constants()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<Constant> getConstants();

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelElement_Functions()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<Function> getFunctions();

	/**
	 * Returns the value of the '<em><b>Models</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ModelElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Models</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelElement_Models()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ModelElement> getModels();

} // ModelElement
