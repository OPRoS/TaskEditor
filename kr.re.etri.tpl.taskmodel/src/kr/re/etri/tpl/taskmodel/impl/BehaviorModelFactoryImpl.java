/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import kr.re.etri.tpl.taskmodel.*;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BehaviorModelFactoryImpl extends EFactoryImpl implements TaskModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TaskModelFactory init() {
		try {
			TaskModelFactory theTaskModelFactory = (TaskModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://kr.re.etri.tpl/taskmodel"); 
			if (theTaskModelFactory != null) {
				return theTaskModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BehaviorModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviorModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TaskModelPackage.ITEM_ELEMENT: return createItemElement();
			case TaskModelPackage.SHAPE_ELEMENT: return createShapeElement();
			case TaskModelPackage.LINKED_ELEMENT: return createLinkedElement();
			case TaskModelPackage.LINE_ELEMENT: return createLineElement();
			case TaskModelPackage.CONNECTION_ELEMENT: return createConnectionElement();
			case TaskModelPackage.WORKER_ELEMENT: return createWorkerElement();
			case TaskModelPackage.BLOCK_ELEMENT: return createBlockElement();
			case TaskModelPackage.TASK_ELEMENT: return createTaskElement();
			case TaskModelPackage.STATE_ELEMENT: return createStateElement();
			case TaskModelPackage.ACTION_ELEMENT: return createActionElement();
			case TaskModelPackage.STATE_ACTION: return createStateAction();
			case TaskModelPackage.SYMBOL: return createSymbol();
			case TaskModelPackage.CONSTANT: return createConstant();
			case TaskModelPackage.FUNCTION: return createFunction();
			case TaskModelPackage.MODEL_ELEMENT: return createModelElement();
			case TaskModelPackage.MODEL_DIAGRAM: return createModelDiagram();
			case TaskModelPackage.INCLUDED_ELEMENT: return createIncludedElement();
			case TaskModelPackage.PARAMETER: return createParameter();
			case TaskModelPackage.ENUM_ELEMENT: return createEnumElement();
			case TaskModelPackage.ENUM_ITEM_ELEMENT: return createEnumItemElement();
			case TaskModelPackage.REFER_ELEMENT: return createReferElement();
			case TaskModelPackage.SUB_DIAGRAM: return createSubDiagram();
			case TaskModelPackage.CONNECTOR_ELEMENT: return createConnectorElement();
			case TaskModelPackage.WITH_ELEMENT: return createWithElement();
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT: return createStructBlockElement();
			case TaskModelPackage.EXPAND_TRANS_ELEMENT: return createExpandTransElement();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TaskModelPackage.LINE_STYLE:
				return createLineStyleFromString(eDataType, initialValue);
			case TaskModelPackage.LINE_END_POINT:
				return createLineEndPointFromString(eDataType, initialValue);
			case TaskModelPackage.RELATION_SHIP:
				return createRelationShipFromString(eDataType, initialValue);
			case TaskModelPackage.STATE_ATTRIBUTE:
				return createStateAttributeFromString(eDataType, initialValue);
			case TaskModelPackage.STATE_ACTION_TYPE:
				return createStateActionTypeFromString(eDataType, initialValue);
			case TaskModelPackage.DIRECTION:
				return createDirectionFromString(eDataType, initialValue);
			case TaskModelPackage.FLOATING_LITERAL:
				return createFloatingLiteralFromString(eDataType, initialValue);
			case TaskModelPackage.BOOLEAN_LITERAL:
				return createBooleanLiteralFromString(eDataType, initialValue);
			case TaskModelPackage.REFER_ATTRIBUTE:
				return createReferAttributeFromString(eDataType, initialValue);
			case TaskModelPackage.STRUCT_TYPE:
				return createStructTypeFromString(eDataType, initialValue);
			case TaskModelPackage.JOIN_TYPE:
				return createJoinTypeFromString(eDataType, initialValue);
			case TaskModelPackage.CONNECTOR_TYPE:
				return createConnectorTypeFromString(eDataType, initialValue);
			case TaskModelPackage.POINT:
				return createPointFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TaskModelPackage.LINE_STYLE:
				return convertLineStyleToString(eDataType, instanceValue);
			case TaskModelPackage.LINE_END_POINT:
				return convertLineEndPointToString(eDataType, instanceValue);
			case TaskModelPackage.RELATION_SHIP:
				return convertRelationShipToString(eDataType, instanceValue);
			case TaskModelPackage.STATE_ATTRIBUTE:
				return convertStateAttributeToString(eDataType, instanceValue);
			case TaskModelPackage.STATE_ACTION_TYPE:
				return convertStateActionTypeToString(eDataType, instanceValue);
			case TaskModelPackage.DIRECTION:
				return convertDirectionToString(eDataType, instanceValue);
			case TaskModelPackage.FLOATING_LITERAL:
				return convertFloatingLiteralToString(eDataType, instanceValue);
			case TaskModelPackage.BOOLEAN_LITERAL:
				return convertBooleanLiteralToString(eDataType, instanceValue);
			case TaskModelPackage.REFER_ATTRIBUTE:
				return convertReferAttributeToString(eDataType, instanceValue);
			case TaskModelPackage.STRUCT_TYPE:
				return convertStructTypeToString(eDataType, instanceValue);
			case TaskModelPackage.JOIN_TYPE:
				return convertJoinTypeToString(eDataType, instanceValue);
			case TaskModelPackage.CONNECTOR_TYPE:
				return convertConnectorTypeToString(eDataType, instanceValue);
			case TaskModelPackage.POINT:
				return convertPointToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement createItemElement() {
		ItemElementImpl itemElement = new ItemElementImpl();
		return itemElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShapeElement createShapeElement() {
		ShapeElementImpl shapeElement = new ShapeElementImpl();
		return shapeElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement createLinkedElement() {
		LinkedElementImpl linkedElement = new LinkedElementImpl();
		return linkedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineElement createLineElement() {
		LineElementImpl lineElement = new LineElementImpl();
		return lineElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionElement createConnectionElement() {
		ConnectionElementImpl connectionElement = new ConnectionElementImpl();
		return connectionElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskElement createWorkerElement() {
		TaskElementImpl workerElement = new TaskElementImpl();
		return workerElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockElement createBlockElement() {
		BlockElementImpl blockElement = new BlockElementImpl();
		return blockElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviorElement createTaskElement() {
		BehaviorElementImpl taskElement = new BehaviorElementImpl();
		return taskElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateElement createStateElement() {
		StateElementImpl stateElement = new StateElementImpl();
		return stateElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionElement createActionElement() {
		ActionElementImpl actionElement = new ActionElementImpl();
		return actionElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAction createStateAction() {
		StateActionImpl stateAction = new StateActionImpl();
		return stateAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Symbol createSymbol() {
		SymbolImpl symbol = new SymbolImpl();
		return symbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constant createConstant() {
		ConstantImpl constant = new ConstantImpl();
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function createFunction() {
		FunctionImpl function = new FunctionImpl();
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElement createModelElement() {
		ModelElementImpl modelElement = new ModelElementImpl();
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelDiagram createModelDiagram() {
		ModelDiagramImpl modelDiagram = new ModelDiagramImpl();
		return modelDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncludedElement createIncludedElement() {
		IncludedElementImpl includedElement = new IncludedElementImpl();
		return includedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumElement createEnumElement() {
		EnumElementImpl enumElement = new EnumElementImpl();
		return enumElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumItemElement createEnumItemElement() {
		EnumItemElementImpl enumItemElement = new EnumItemElementImpl();
		return enumItemElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferElement createReferElement() {
		ReferElementImpl referElement = new ReferElementImpl();
		return referElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubDiagram createSubDiagram() {
		SubDiagramImpl subDiagram = new SubDiagramImpl();
		return subDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorElement createConnectorElement() {
		ConnectorElementImpl connectorElement = new ConnectorElementImpl();
		return connectorElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WithElement createWithElement() {
		WithElementImpl withElement = new WithElementImpl();
		return withElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement createStructBlockElement() {
		StructBlockElementImpl structBlockElement = new StructBlockElementImpl();
		return structBlockElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpandTransElement createExpandTransElement() {
		ExpandTransElementImpl expandTransElement = new ExpandTransElementImpl();
		return expandTransElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineStyle createLineStyleFromString(EDataType eDataType, String initialValue) {
		LineStyle result = LineStyle.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLineStyleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineEndPoint createLineEndPointFromString(EDataType eDataType, String initialValue) {
		LineEndPoint result = LineEndPoint.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLineEndPointToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationShip createRelationShipFromString(EDataType eDataType, String initialValue) {
		RelationShip result = RelationShip.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRelationShipToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAttribute createStateAttributeFromString(EDataType eDataType, String initialValue) {
		StateAttribute result = StateAttribute.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStateAttributeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateActionType createStateActionTypeFromString(EDataType eDataType, String initialValue) {
		StateActionType result = StateActionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStateActionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Direction createDirectionFromString(EDataType eDataType, String initialValue) {
		Direction result = Direction.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatingLiteral createFloatingLiteralFromString(EDataType eDataType, String initialValue) {
		FloatingLiteral result = FloatingLiteral.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFloatingLiteralToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanLiteral createBooleanLiteralFromString(EDataType eDataType, String initialValue) {
		BooleanLiteral result = BooleanLiteral.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBooleanLiteralToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferAttribute createReferAttributeFromString(EDataType eDataType, String initialValue) {
		ReferAttribute result = ReferAttribute.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertReferAttributeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructType createStructTypeFromString(EDataType eDataType, String initialValue) {
		StructType result = StructType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStructTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JoinType createJoinTypeFromString(EDataType eDataType, String initialValue) {
		JoinType result = JoinType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJoinTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorType createConnectorTypeFromString(EDataType eDataType, String initialValue) {
		ConnectorType result = ConnectorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConnectorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Point createPointFromString(EDataType eDataType, String initialValue) {
		return (Point)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPointToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskModelPackage getTaskModelPackage() {
		return (TaskModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TaskModelPackage getPackage() {
		return TaskModelPackage.eINSTANCE;
	}

} //TaskModelFactoryImpl
