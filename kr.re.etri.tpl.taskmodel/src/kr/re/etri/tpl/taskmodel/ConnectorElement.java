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
 * A representation of the model object '<em><b>Connector Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getParams <em>Params</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getWiths <em>Withs</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getJoinType <em>Join Type</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getConType <em>Con Type</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getConstruct <em>Construct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getDestruct <em>Destruct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getExercise <em>Exercise</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement()
 * @model
 * @generated
 */
public interface ConnectorElement extends ItemElement, BlockElement {
	/**
	 * Returns the value of the '<em><b>Params</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Params</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_Params()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<Parameter> getParams();

	/**
	 * Returns the value of the '<em><b>Withs</b></em>' containment reference list.
	 * The list contents are of type {@link kr.re.etri.tpl.taskmodel.WithElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Withs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Withs</em>' containment reference list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_Withs()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	EList<WithElement> getWiths();

	/**
	 * Returns the value of the '<em><b>Join Type</b></em>' attribute.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.JoinType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.JoinType
	 * @see #setJoinType(JoinType)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_JoinType()
	 * @model
	 * @generated
	 */
	JoinType getJoinType();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getJoinType <em>Join Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.JoinType
	 * @see #getJoinType()
	 * @generated
	 */
	void setJoinType(JoinType value);

	/**
	 * Returns the value of the '<em><b>Con Type</b></em>' attribute.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.ConnectorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Con Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Con Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorType
	 * @see #setConType(ConnectorType)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_ConType()
	 * @model
	 * @generated
	 */
	ConnectorType getConType();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getConType <em>Con Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Con Type</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorType
	 * @see #getConType()
	 * @generated
	 */
	void setConType(ConnectorType value);

	/**
	 * Returns the value of the '<em><b>Construct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Construct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Construct</em>' containment reference.
	 * @see #setConstruct(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_Construct()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getConstruct();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getConstruct <em>Construct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Construct</em>' containment reference.
	 * @see #getConstruct()
	 * @generated
	 */
	void setConstruct(StructBlockElement value);

	/**
	 * Returns the value of the '<em><b>Destruct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destruct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destruct</em>' containment reference.
	 * @see #setDestruct(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_Destruct()
	 * @model containment="true" derived="true"
	 * @generated
	 */
	StructBlockElement getDestruct();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getDestruct <em>Destruct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destruct</em>' containment reference.
	 * @see #getDestruct()
	 * @generated
	 */
	void setDestruct(StructBlockElement value);

	/**
	 * Returns the value of the '<em><b>Exercise</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exercise</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exercise</em>' reference.
	 * @see #setExercise(StructBlockElement)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getConnectorElement_Exercise()
	 * @model
	 * @generated
	 */
	StructBlockElement getExercise();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getExercise <em>Exercise</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exercise</em>' reference.
	 * @see #getExercise()
	 * @generated
	 */
	void setExercise(StructBlockElement value);

} // ConnectorElement
