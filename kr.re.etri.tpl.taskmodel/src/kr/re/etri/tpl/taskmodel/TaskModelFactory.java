/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage
 * @generated
 */
public interface TaskModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TaskModelFactory eINSTANCE = kr.re.etri.tpl.taskmodel.impl.BehaviorModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Item Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item Element</em>'.
	 * @generated
	 */
	ItemElement createItemElement();

	/**
	 * Returns a new object of class '<em>Shape Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shape Element</em>'.
	 * @generated
	 */
	ShapeElement createShapeElement();

	/**
	 * Returns a new object of class '<em>Linked Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Linked Element</em>'.
	 * @generated
	 */
	LinkedElement createLinkedElement();

	/**
	 * Returns a new object of class '<em>Line Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Line Element</em>'.
	 * @generated
	 */
	LineElement createLineElement();

	/**
	 * Returns a new object of class '<em>Connection Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection Element</em>'.
	 * @generated
	 */
	ConnectionElement createConnectionElement();

	/**
	 * Returns a new object of class '<em>Worker Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Worker Element</em>'.
	 * @generated
	 */
	TaskElement createWorkerElement();

	/**
	 * Returns a new object of class '<em>Block Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block Element</em>'.
	 * @generated
	 */
	BlockElement createBlockElement();

	/**
	 * Returns a new object of class '<em>Task Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Task Element</em>'.
	 * @generated
	 */
	BehaviorElement createTaskElement();

	/**
	 * Returns a new object of class '<em>State Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Element</em>'.
	 * @generated
	 */
	StateElement createStateElement();

	/**
	 * Returns a new object of class '<em>Action Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Element</em>'.
	 * @generated
	 */
	ActionElement createActionElement();

	/**
	 * Returns a new object of class '<em>State Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Action</em>'.
	 * @generated
	 */
	StateAction createStateAction();

	/**
	 * Returns a new object of class '<em>Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbol</em>'.
	 * @generated
	 */
	Symbol createSymbol();

	/**
	 * Returns a new object of class '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constant</em>'.
	 * @generated
	 */
	Constant createConstant();

	/**
	 * Returns a new object of class '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function</em>'.
	 * @generated
	 */
	Function createFunction();

	/**
	 * Returns a new object of class '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Element</em>'.
	 * @generated
	 */
	ModelElement createModelElement();

	/**
	 * Returns a new object of class '<em>Model Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Diagram</em>'.
	 * @generated
	 */
	ModelDiagram createModelDiagram();

	/**
	 * Returns a new object of class '<em>Included Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Included Element</em>'.
	 * @generated
	 */
	IncludedElement createIncludedElement();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Enum Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Element</em>'.
	 * @generated
	 */
	EnumElement createEnumElement();

	/**
	 * Returns a new object of class '<em>Enum Item Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Item Element</em>'.
	 * @generated
	 */
	EnumItemElement createEnumItemElement();

	/**
	 * Returns a new object of class '<em>Refer Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Refer Element</em>'.
	 * @generated
	 */
	ReferElement createReferElement();

	/**
	 * Returns a new object of class '<em>Sub Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sub Diagram</em>'.
	 * @generated
	 */
	SubDiagram createSubDiagram();

	/**
	 * Returns a new object of class '<em>Connector Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connector Element</em>'.
	 * @generated
	 */
	ConnectorElement createConnectorElement();

	/**
	 * Returns a new object of class '<em>With Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>With Element</em>'.
	 * @generated
	 */
	WithElement createWithElement();

	/**
	 * Returns a new object of class '<em>Struct Block Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Struct Block Element</em>'.
	 * @generated
	 */
	StructBlockElement createStructBlockElement();

	/**
	 * Returns a new object of class '<em>Expand Trans Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expand Trans Element</em>'.
	 * @generated
	 */
	ExpandTransElement createExpandTransElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TaskModelPackage getTaskModelPackage();

} //TaskModelFactory
