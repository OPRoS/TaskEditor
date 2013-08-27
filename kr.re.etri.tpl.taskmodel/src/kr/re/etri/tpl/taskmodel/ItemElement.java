/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#getParent <em>Parent</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#getName <em>Name</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#getDescription <em>Description</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#isVisible <em>Visible</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#getSubDiagram <em>Sub Diagram</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#getReferences <em>References</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ItemElement#getItemState <em>Item State</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement()
 * @model
 * @generated
 */
public interface ItemElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(ItemElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_Parent()
	 * @model
	 * @generated
	 */
	ItemElement getParent();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ItemElement#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(ItemElement value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_Name()
	 * @model derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ItemElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_Description()
	 * @model derived="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ItemElement#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Visible</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visible</em>' attribute.
	 * @see #setVisible(boolean)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_Visible()
	 * @model default="true" required="true" derived="true"
	 * @generated
	 */
	boolean isVisible();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ItemElement#isVisible <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visible</em>' attribute.
	 * @see #isVisible()
	 * @generated
	 */
	void setVisible(boolean value);

	/**
	 * Returns the value of the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Diagram</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Diagram</em>' containment reference.
	 * @see #setSubDiagram(SubDiagram)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_SubDiagram()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	SubDiagram getSubDiagram();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ItemElement#getSubDiagram <em>Sub Diagram</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Diagram</em>' containment reference.
	 * @see #getSubDiagram()
	 * @generated
	 */
	void setSubDiagram(SubDiagram value);

	/**
	 * Returns the value of the '<em><b>References</b></em>' reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.ReferElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_References()
	 * @model derived="true"
	 * @generated
	 */
	EList<ReferElement> getReferences();

	/**
	 * Returns the value of the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Item State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Item State</em>' attribute.
	 * @see #setItemState(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getItemElement_ItemState()
	 * @model required="true"
	 * @generated
	 */
	int getItemState();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ItemElement#getItemState <em>Item State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Item State</em>' attribute.
	 * @see #getItemState()
	 * @generated
	 */
	void setItemState(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isIncluded();

} // ItemElement
