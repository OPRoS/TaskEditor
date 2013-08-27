/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.util;

import kr.re.etri.tpl.taskmodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage
 * @generated
 */
public class TaskModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TaskModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TaskModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskModelSwitch<Adapter> modelSwitch =
		new TaskModelSwitch<Adapter>() {
			@Override
			public Adapter caseItemElement(ItemElement object) {
				return createItemElementAdapter();
			}
			@Override
			public Adapter caseShapeElement(ShapeElement object) {
				return createShapeElementAdapter();
			}
			@Override
			public Adapter caseLinkedElement(LinkedElement object) {
				return createLinkedElementAdapter();
			}
			@Override
			public Adapter caseLineElement(LineElement object) {
				return createLineElementAdapter();
			}
			@Override
			public Adapter caseConnectionElement(ConnectionElement object) {
				return createConnectionElementAdapter();
			}
			@Override
			public Adapter caseWorkerElement(TaskElement object) {
				return createWorkerElementAdapter();
			}
			@Override
			public Adapter caseBlockElement(BlockElement object) {
				return createBlockElementAdapter();
			}
			@Override
			public Adapter caseTaskElement(BehaviorElement object) {
				return createTaskElementAdapter();
			}
			@Override
			public Adapter caseStateElement(StateElement object) {
				return createStateElementAdapter();
			}
			@Override
			public Adapter caseActionElement(ActionElement object) {
				return createActionElementAdapter();
			}
			@Override
			public Adapter caseStateAction(StateAction object) {
				return createStateActionAdapter();
			}
			@Override
			public Adapter caseSymbol(Symbol object) {
				return createSymbolAdapter();
			}
			@Override
			public Adapter caseConstant(Constant object) {
				return createConstantAdapter();
			}
			@Override
			public Adapter caseFunction(Function object) {
				return createFunctionAdapter();
			}
			@Override
			public Adapter caseModelElement(ModelElement object) {
				return createModelElementAdapter();
			}
			@Override
			public Adapter caseModelDiagram(ModelDiagram object) {
				return createModelDiagramAdapter();
			}
			@Override
			public Adapter caseIncludedElement(IncludedElement object) {
				return createIncludedElementAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object) {
				return createParameterAdapter();
			}
			@Override
			public Adapter caseEnumElement(EnumElement object) {
				return createEnumElementAdapter();
			}
			@Override
			public Adapter caseEnumItemElement(EnumItemElement object) {
				return createEnumItemElementAdapter();
			}
			@Override
			public Adapter caseReferElement(ReferElement object) {
				return createReferElementAdapter();
			}
			@Override
			public Adapter caseSubDiagram(SubDiagram object) {
				return createSubDiagramAdapter();
			}
			@Override
			public Adapter caseConnectorElement(ConnectorElement object) {
				return createConnectorElementAdapter();
			}
			@Override
			public Adapter caseWithElement(WithElement object) {
				return createWithElementAdapter();
			}
			@Override
			public Adapter caseStructBlockElement(StructBlockElement object) {
				return createStructBlockElementAdapter();
			}
			@Override
			public Adapter caseExpandTransElement(ExpandTransElement object) {
				return createExpandTransElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ItemElement <em>Item Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement
	 * @generated
	 */
	public Adapter createItemElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ShapeElement <em>Shape Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement
	 * @generated
	 */
	public Adapter createShapeElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.LinkedElement <em>Linked Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.LinkedElement
	 * @generated
	 */
	public Adapter createLinkedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.LineElement <em>Line Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.LineElement
	 * @generated
	 */
	public Adapter createLineElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ConnectionElement <em>Connection Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement
	 * @generated
	 */
	public Adapter createConnectionElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.TaskElement <em>Worker Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement
	 * @generated
	 */
	public Adapter createWorkerElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.BlockElement <em>Block Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.BlockElement
	 * @generated
	 */
	public Adapter createBlockElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.BehaviorElement <em>Task Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement
	 * @generated
	 */
	public Adapter createTaskElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.StateElement <em>State Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.StateElement
	 * @generated
	 */
	public Adapter createStateElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ActionElement <em>Action Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ActionElement
	 * @generated
	 */
	public Adapter createActionElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.StateAction <em>State Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.StateAction
	 * @generated
	 */
	public Adapter createStateActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.Symbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.Symbol
	 * @generated
	 */
	public Adapter createSymbolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.Constant
	 * @generated
	 */
	public Adapter createConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.Function
	 * @generated
	 */
	public Adapter createFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ModelElement
	 * @generated
	 */
	public Adapter createModelElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ModelDiagram <em>Model Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ModelDiagram
	 * @generated
	 */
	public Adapter createModelDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.IncludedElement <em>Included Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.IncludedElement
	 * @generated
	 */
	public Adapter createIncludedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.EnumElement <em>Enum Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.EnumElement
	 * @generated
	 */
	public Adapter createEnumElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.EnumItemElement <em>Enum Item Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.EnumItemElement
	 * @generated
	 */
	public Adapter createEnumItemElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ReferElement <em>Refer Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ReferElement
	 * @generated
	 */
	public Adapter createReferElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.SubDiagram <em>Sub Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.SubDiagram
	 * @generated
	 */
	public Adapter createSubDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ConnectorElement <em>Connector Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement
	 * @generated
	 */
	public Adapter createConnectorElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.WithElement <em>With Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.WithElement
	 * @generated
	 */
	public Adapter createWithElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.StructBlockElement <em>Struct Block Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.StructBlockElement
	 * @generated
	 */
	public Adapter createStructBlockElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link kr.re.etri.tpl.taskmodel.ExpandTransElement <em>Expand Trans Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see kr.re.etri.tpl.taskmodel.ExpandTransElement
	 * @generated
	 */
	public Adapter createExpandTransElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TaskModelAdapterFactory
