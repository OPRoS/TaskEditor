/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.util;

import java.util.List;

import kr.re.etri.tpl.taskmodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage
 * @generated
 */
public class TaskModelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TaskModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskModelSwitch() {
		if (modelPackage == null) {
			modelPackage = TaskModelPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TaskModelPackage.ITEM_ELEMENT: {
				ItemElement itemElement = (ItemElement)theEObject;
				T result = caseItemElement(itemElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.SHAPE_ELEMENT: {
				ShapeElement shapeElement = (ShapeElement)theEObject;
				T result = caseShapeElement(shapeElement);
				if (result == null) result = caseItemElement(shapeElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.LINKED_ELEMENT: {
				LinkedElement linkedElement = (LinkedElement)theEObject;
				T result = caseLinkedElement(linkedElement);
				if (result == null) result = caseShapeElement(linkedElement);
				if (result == null) result = caseItemElement(linkedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.LINE_ELEMENT: {
				LineElement lineElement = (LineElement)theEObject;
				T result = caseLineElement(lineElement);
				if (result == null) result = caseItemElement(lineElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.CONNECTION_ELEMENT: {
				ConnectionElement connectionElement = (ConnectionElement)theEObject;
				T result = caseConnectionElement(connectionElement);
				if (result == null) result = caseLineElement(connectionElement);
				if (result == null) result = caseItemElement(connectionElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.WORKER_ELEMENT: {
				TaskElement workerElement = (TaskElement)theEObject;
				T result = caseWorkerElement(workerElement);
				if (result == null) result = caseItemElement(workerElement);
				if (result == null) result = caseBlockElement(workerElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.BLOCK_ELEMENT: {
				BlockElement blockElement = (BlockElement)theEObject;
				T result = caseBlockElement(blockElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.TASK_ELEMENT: {
				BehaviorElement taskElement = (BehaviorElement)theEObject;
				T result = caseTaskElement(taskElement);
				if (result == null) result = caseItemElement(taskElement);
				if (result == null) result = caseBlockElement(taskElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.STATE_ELEMENT: {
				StateElement stateElement = (StateElement)theEObject;
				T result = caseStateElement(stateElement);
				if (result == null) result = caseItemElement(stateElement);
				if (result == null) result = caseBlockElement(stateElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.ACTION_ELEMENT: {
				ActionElement actionElement = (ActionElement)theEObject;
				T result = caseActionElement(actionElement);
				if (result == null) result = caseItemElement(actionElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.STATE_ACTION: {
				StateAction stateAction = (StateAction)theEObject;
				T result = caseStateAction(stateAction);
				if (result == null) result = caseItemElement(stateAction);
				if (result == null) result = caseBlockElement(stateAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.SYMBOL: {
				Symbol symbol = (Symbol)theEObject;
				T result = caseSymbol(symbol);
				if (result == null) result = caseItemElement(symbol);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.CONSTANT: {
				Constant constant = (Constant)theEObject;
				T result = caseConstant(constant);
				if (result == null) result = caseItemElement(constant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = caseItemElement(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.MODEL_ELEMENT: {
				ModelElement modelElement = (ModelElement)theEObject;
				T result = caseModelElement(modelElement);
				if (result == null) result = caseItemElement(modelElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.MODEL_DIAGRAM: {
				ModelDiagram modelDiagram = (ModelDiagram)theEObject;
				T result = caseModelDiagram(modelDiagram);
				if (result == null) result = caseItemElement(modelDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.INCLUDED_ELEMENT: {
				IncludedElement includedElement = (IncludedElement)theEObject;
				T result = caseIncludedElement(includedElement);
				if (result == null) result = caseItemElement(includedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.PARAMETER: {
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = caseItemElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.ENUM_ELEMENT: {
				EnumElement enumElement = (EnumElement)theEObject;
				T result = caseEnumElement(enumElement);
				if (result == null) result = caseItemElement(enumElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.ENUM_ITEM_ELEMENT: {
				EnumItemElement enumItemElement = (EnumItemElement)theEObject;
				T result = caseEnumItemElement(enumItemElement);
				if (result == null) result = caseItemElement(enumItemElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.REFER_ELEMENT: {
				ReferElement referElement = (ReferElement)theEObject;
				T result = caseReferElement(referElement);
				if (result == null) result = caseLinkedElement(referElement);
				if (result == null) result = caseShapeElement(referElement);
				if (result == null) result = caseItemElement(referElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.SUB_DIAGRAM: {
				SubDiagram subDiagram = (SubDiagram)theEObject;
				T result = caseSubDiagram(subDiagram);
				if (result == null) result = caseItemElement(subDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.CONNECTOR_ELEMENT: {
				ConnectorElement connectorElement = (ConnectorElement)theEObject;
				T result = caseConnectorElement(connectorElement);
				if (result == null) result = caseItemElement(connectorElement);
				if (result == null) result = caseBlockElement(connectorElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.WITH_ELEMENT: {
				WithElement withElement = (WithElement)theEObject;
				T result = caseWithElement(withElement);
				if (result == null) result = caseItemElement(withElement);
				if (result == null) result = caseBlockElement(withElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT: {
				StructBlockElement structBlockElement = (StructBlockElement)theEObject;
				T result = caseStructBlockElement(structBlockElement);
				if (result == null) result = caseItemElement(structBlockElement);
				if (result == null) result = caseBlockElement(structBlockElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TaskModelPackage.EXPAND_TRANS_ELEMENT: {
				ExpandTransElement expandTransElement = (ExpandTransElement)theEObject;
				T result = caseExpandTransElement(expandTransElement);
				if (result == null) result = caseItemElement(expandTransElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Item Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Item Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseItemElement(ItemElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shape Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shape Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShapeElement(ShapeElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Linked Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Linked Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkedElement(LinkedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Line Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Line Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLineElement(LineElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionElement(ConnectionElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Worker Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Worker Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWorkerElement(TaskElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockElement(BlockElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskElement(BehaviorElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateElement(StateElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionElement(ActionElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateAction(StateAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSymbol(Symbol object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstant(Constant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelElement(ModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelDiagram(ModelDiagram object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Included Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Included Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncludedElement(IncludedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameter(Parameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumElement(EnumElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Item Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Item Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumItemElement(EnumItemElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Refer Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Refer Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferElement(ReferElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubDiagram(SubDiagram object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connector Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connector Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectorElement(ConnectorElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>With Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>With Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWithElement(WithElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Struct Block Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Struct Block Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructBlockElement(StructBlockElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expand Trans Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expand Trans Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpandTransElement(ExpandTransElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //TaskModelSwitch
