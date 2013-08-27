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
 * A representation of the model object '<em><b>Model Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getItems <em>Items</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getIncludeItems <em>Include Items</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getScript <em>Script</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelDiagram()
 * @model
 * @generated
 */
public interface ModelDiagram extends ItemElement {
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
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelDiagram_Items()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ItemElement> getItems();

	/**
	 * Returns the value of the '<em><b>Include Items</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.IncludedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Items</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelDiagram_IncludeItems()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<IncludedElement> getIncludeItems();

	/**
	 * Returns the value of the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Script</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Script</em>' attribute.
	 * @see #setScript(String)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getModelDiagram_Script()
	 * @model
	 * @generated
	 */
	String getScript();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getScript <em>Script</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Script</em>' attribute.
	 * @see #getScript()
	 * @generated
	 */
	void setScript(String value);

} // ModelDiagram
