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
 * A representation of the model object '<em><b>Worker Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.TaskElement#getInitialTask <em>Initial Task</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.TaskElement#getItems <em>Items</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.TaskElement#getInitialize <em>Initialize</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.TaskElement#getFinalize <em>Finalize</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.TaskElement#getRun <em>Run</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWorkerElement()
 * @model
 * @generated
 */
public interface TaskElement extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>Initial Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Task</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Task</em>' reference.
	 * @see #setInitialTask(ItemElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWorkerElement_InitialTask()
	 * @model
	 * @generated
	 */
	ItemElement getInitialTask();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.TaskElement#getInitialTask <em>Initial Task</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Task</em>' reference.
	 * @see #getInitialTask()
	 * @generated
	 */
	void setInitialTask(ItemElement value);

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
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWorkerElement_Items()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<ItemElement> getItems();

	/**
	 * Returns the value of the '<em><b>Initialize</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialize</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialize</em>' containment reference.
	 * @see #setInitialize(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWorkerElement_Initialize()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getInitialize();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.TaskElement#getInitialize <em>Initialize</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialize</em>' containment reference.
	 * @see #getInitialize()
	 * @generated
	 */
	void setInitialize(StructBlockElement value);

	/**
	 * Returns the value of the '<em><b>Finalize</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Finalize</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Finalize</em>' containment reference.
	 * @see #setFinalize(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWorkerElement_Finalize()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getFinalize();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.TaskElement#getFinalize <em>Finalize</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Finalize</em>' containment reference.
	 * @see #getFinalize()
	 * @generated
	 */
	void setFinalize(StructBlockElement value);

	/**
	 * Returns the value of the '<em><b>Run</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Run</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Run</em>' containment reference.
	 * @see #setRun(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getWorkerElement_Run()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getRun();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.TaskElement#getRun <em>Run</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Run</em>' containment reference.
	 * @see #getRun()
	 * @generated
	 */
	void setRun(StructBlockElement value);

} // WorkerElement
