/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.BooleanLiteral;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.Direction;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.FloatingLiteral;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.JoinType;
import kr.re.etri.tpl.taskmodel.LineElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BehaviorModelPackageImpl extends EPackageImpl implements TaskModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass itemElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass shapeElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lineElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass workerElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taskElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass includedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumItemElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass withElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structBlockElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expandTransElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum lineStyleEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum lineEndPointEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum relationShipEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stateAttributeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stateActionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum directionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum floatingLiteralEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum booleanLiteralEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum referAttributeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum structTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum joinTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum connectorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pointEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BehaviorModelPackageImpl() {
		super(eNS_URI, TaskModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TaskModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TaskModelPackage init() {
		if (isInited) return (TaskModelPackage)EPackage.Registry.INSTANCE.getEPackage(TaskModelPackage.eNS_URI);

		// Obtain or create and register package
		BehaviorModelPackageImpl theTaskModelPackage = (BehaviorModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BehaviorModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BehaviorModelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTaskModelPackage.createPackageContents();

		// Initialize created meta-data
		theTaskModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTaskModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TaskModelPackage.eNS_URI, theTaskModelPackage);
		return theTaskModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getItemElement() {
		return itemElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getItemElement_Parent() {
		return (EReference)itemElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItemElement_Name() {
		return (EAttribute)itemElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItemElement_Description() {
		return (EAttribute)itemElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItemElement_Visible() {
		return (EAttribute)itemElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getItemElement_SubDiagram() {
		return (EReference)itemElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getItemElement_References() {
		return (EReference)itemElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItemElement_ItemState() {
		return (EAttribute)itemElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShapeElement() {
		return shapeElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_X() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Y() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Width() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Height() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Collapsed() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_X2() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Y2() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Width2() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShapeElement_Height2() {
		return (EAttribute)shapeElementEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLinkedElement() {
		return linkedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkedElement_SourceConnections() {
		return (EReference)linkedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkedElement_TargetConnections() {
		return (EReference)linkedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLineElement() {
		return lineElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineElement_LineStyle() {
		return (EAttribute)lineElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineElement_SourceEndPoint() {
		return (EAttribute)lineElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineElement_TargetEndPoint() {
		return (EAttribute)lineElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineElement_BendPoints() {
		return (EAttribute)lineElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectionElement() {
		return connectionElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionElement_Source() {
		return (EReference)connectionElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionElement_Target() {
		return (EReference)connectionElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionElement_Source2() {
		return (EReference)connectionElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionElement_Target2() {
		return (EReference)connectionElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionElement_Relationship() {
		return (EAttribute)connectionElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionElement_Condition() {
		return (EAttribute)connectionElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWorkerElement() {
		return workerElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWorkerElement_InitialTask() {
		return (EReference)workerElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWorkerElement_Items() {
		return (EReference)workerElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWorkerElement_Initialize() {
		return (EReference)workerElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWorkerElement_Finalize() {
		return (EReference)workerElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWorkerElement_Run() {
		return (EReference)workerElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBlockElement() {
		return blockElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBlockElement_Statements() {
		return (EAttribute)blockElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTaskElement() {
		return taskElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskElement_Params() {
		return (EReference)taskElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskElement_States() {
		return (EReference)taskElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskElement_InitialState() {
		return (EReference)taskElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskElement_Construct() {
		return (EReference)taskElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskElement_Destruct() {
		return (EReference)taskElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskElement_Bifurcates() {
		return (EReference)taskElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateElement() {
		return stateElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateElement_Attribute() {
		return (EAttribute)stateElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateElement_Entry() {
		return (EReference)stateElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateElement_Stay() {
		return (EReference)stateElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateElement_Exit() {
		return (EReference)stateElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateElement_Bifurcates() {
		return (EReference)stateElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionElement() {
		return actionElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionElement_Params() {
		return (EReference)actionElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateAction() {
		return stateActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateAction_StateActionType() {
		return (EAttribute)stateActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbol() {
		return symbolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_Direction() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_Type() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbol_Value() {
		return (EAttribute)symbolEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstant() {
		return constantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstant_Type() {
		return (EAttribute)constantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstant_InitValue() {
		return (EAttribute)constantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunction_Type() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunction_Params() {
		return (EReference)functionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelElement() {
		return modelElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_Symbols() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_Constants() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_Functions() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_Models() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelDiagram() {
		return modelDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelDiagram_Items() {
		return (EReference)modelDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelDiagram_IncludeItems() {
		return (EReference)modelDiagramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelDiagram_Script() {
		return (EAttribute)modelDiagramEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIncludedElement() {
		return includedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIncludedElement_IncludePath() {
		return (EAttribute)includedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIncludedElement_Items() {
		return (EReference)includedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Type() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Value() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumElement() {
		return enumElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumElement_EnumItem() {
		return (EReference)enumElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumItemElement() {
		return enumItemElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumItemElement_Value() {
		return (EAttribute)enumItemElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferElement() {
		return referElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReferElement_RealModel() {
		return (EReference)referElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReferElement_Items() {
		return (EReference)referElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReferElement_Attribute() {
		return (EAttribute)referElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReferElement_Path() {
		return (EAttribute)referElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubDiagram() {
		return subDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubDiagram_Items() {
		return (EReference)subDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectorElement() {
		return connectorElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorElement_Params() {
		return (EReference)connectorElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorElement_Withs() {
		return (EReference)connectorElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorElement_JoinType() {
		return (EAttribute)connectorElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorElement_ConType() {
		return (EAttribute)connectorElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorElement_Construct() {
		return (EReference)connectorElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorElement_Destruct() {
		return (EReference)connectorElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorElement_Exercise() {
		return (EReference)connectorElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWithElement() {
		return withElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWithElement_Cycle() {
		return (EAttribute)withElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructBlockElement() {
		return structBlockElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStructBlockElement_StructType() {
		return (EAttribute)structBlockElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpandTransElement() {
		return expandTransElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpandTransElement_Source() {
		return (EReference)expandTransElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpandTransElement_Expand() {
		return (EReference)expandTransElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpandTransElement_Trans() {
		return (EReference)expandTransElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLineStyle() {
		return lineStyleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLineEndPoint() {
		return lineEndPointEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRelationShip() {
		return relationShipEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStateAttribute() {
		return stateAttributeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStateActionType() {
		return stateActionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDirection() {
		return directionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFloatingLiteral() {
		return floatingLiteralEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBooleanLiteral() {
		return booleanLiteralEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getReferAttribute() {
		return referAttributeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStructType() {
		return structTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getJoinType() {
		return joinTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getConnectorType() {
		return connectorTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPoint() {
		return pointEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskModelFactory getTaskModelFactory() {
		return (TaskModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		itemElementEClass = createEClass(ITEM_ELEMENT);
		createEReference(itemElementEClass, ITEM_ELEMENT__PARENT);
		createEAttribute(itemElementEClass, ITEM_ELEMENT__NAME);
		createEAttribute(itemElementEClass, ITEM_ELEMENT__DESCRIPTION);
		createEAttribute(itemElementEClass, ITEM_ELEMENT__VISIBLE);
		createEReference(itemElementEClass, ITEM_ELEMENT__SUB_DIAGRAM);
		createEReference(itemElementEClass, ITEM_ELEMENT__REFERENCES);
		createEAttribute(itemElementEClass, ITEM_ELEMENT__ITEM_STATE);

		shapeElementEClass = createEClass(SHAPE_ELEMENT);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__X);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__Y);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__WIDTH);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__HEIGHT);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__COLLAPSED);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__X2);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__Y2);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__WIDTH2);
		createEAttribute(shapeElementEClass, SHAPE_ELEMENT__HEIGHT2);

		linkedElementEClass = createEClass(LINKED_ELEMENT);
		createEReference(linkedElementEClass, LINKED_ELEMENT__SOURCE_CONNECTIONS);
		createEReference(linkedElementEClass, LINKED_ELEMENT__TARGET_CONNECTIONS);

		lineElementEClass = createEClass(LINE_ELEMENT);
		createEAttribute(lineElementEClass, LINE_ELEMENT__LINE_STYLE);
		createEAttribute(lineElementEClass, LINE_ELEMENT__SOURCE_END_POINT);
		createEAttribute(lineElementEClass, LINE_ELEMENT__TARGET_END_POINT);
		createEAttribute(lineElementEClass, LINE_ELEMENT__BEND_POINTS);

		connectionElementEClass = createEClass(CONNECTION_ELEMENT);
		createEReference(connectionElementEClass, CONNECTION_ELEMENT__SOURCE);
		createEReference(connectionElementEClass, CONNECTION_ELEMENT__TARGET);
		createEReference(connectionElementEClass, CONNECTION_ELEMENT__SOURCE2);
		createEReference(connectionElementEClass, CONNECTION_ELEMENT__TARGET2);
		createEAttribute(connectionElementEClass, CONNECTION_ELEMENT__RELATIONSHIP);
		createEAttribute(connectionElementEClass, CONNECTION_ELEMENT__CONDITION);

		workerElementEClass = createEClass(WORKER_ELEMENT);
		createEReference(workerElementEClass, WORKER_ELEMENT__INITIAL_TASK);
		createEReference(workerElementEClass, WORKER_ELEMENT__ITEMS);
		createEReference(workerElementEClass, WORKER_ELEMENT__INITIALIZE);
		createEReference(workerElementEClass, WORKER_ELEMENT__FINALIZE);
		createEReference(workerElementEClass, WORKER_ELEMENT__RUN);

		blockElementEClass = createEClass(BLOCK_ELEMENT);
		createEAttribute(blockElementEClass, BLOCK_ELEMENT__STATEMENTS);

		taskElementEClass = createEClass(TASK_ELEMENT);
		createEReference(taskElementEClass, TASK_ELEMENT__PARAMS);
		createEReference(taskElementEClass, TASK_ELEMENT__STATES);
		createEReference(taskElementEClass, TASK_ELEMENT__INITIAL_STATE);
		createEReference(taskElementEClass, TASK_ELEMENT__CONSTRUCT);
		createEReference(taskElementEClass, TASK_ELEMENT__DESTRUCT);
		createEReference(taskElementEClass, TASK_ELEMENT__BIFURCATES);

		stateElementEClass = createEClass(STATE_ELEMENT);
		createEAttribute(stateElementEClass, STATE_ELEMENT__ATTRIBUTE);
		createEReference(stateElementEClass, STATE_ELEMENT__ENTRY);
		createEReference(stateElementEClass, STATE_ELEMENT__STAY);
		createEReference(stateElementEClass, STATE_ELEMENT__EXIT);
		createEReference(stateElementEClass, STATE_ELEMENT__BIFURCATES);

		actionElementEClass = createEClass(ACTION_ELEMENT);
		createEReference(actionElementEClass, ACTION_ELEMENT__PARAMS);

		stateActionEClass = createEClass(STATE_ACTION);
		createEAttribute(stateActionEClass, STATE_ACTION__STATE_ACTION_TYPE);

		symbolEClass = createEClass(SYMBOL);
		createEAttribute(symbolEClass, SYMBOL__DIRECTION);
		createEAttribute(symbolEClass, SYMBOL__TYPE);
		createEAttribute(symbolEClass, SYMBOL__VALUE);

		constantEClass = createEClass(CONSTANT);
		createEAttribute(constantEClass, CONSTANT__TYPE);
		createEAttribute(constantEClass, CONSTANT__INIT_VALUE);

		functionEClass = createEClass(FUNCTION);
		createEAttribute(functionEClass, FUNCTION__TYPE);
		createEReference(functionEClass, FUNCTION__PARAMS);

		modelElementEClass = createEClass(MODEL_ELEMENT);
		createEReference(modelElementEClass, MODEL_ELEMENT__SYMBOLS);
		createEReference(modelElementEClass, MODEL_ELEMENT__CONSTANTS);
		createEReference(modelElementEClass, MODEL_ELEMENT__FUNCTIONS);
		createEReference(modelElementEClass, MODEL_ELEMENT__MODELS);

		modelDiagramEClass = createEClass(MODEL_DIAGRAM);
		createEReference(modelDiagramEClass, MODEL_DIAGRAM__ITEMS);
		createEReference(modelDiagramEClass, MODEL_DIAGRAM__INCLUDE_ITEMS);
		createEAttribute(modelDiagramEClass, MODEL_DIAGRAM__SCRIPT);

		includedElementEClass = createEClass(INCLUDED_ELEMENT);
		createEAttribute(includedElementEClass, INCLUDED_ELEMENT__INCLUDE_PATH);
		createEReference(includedElementEClass, INCLUDED_ELEMENT__ITEMS);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__TYPE);
		createEAttribute(parameterEClass, PARAMETER__VALUE);

		enumElementEClass = createEClass(ENUM_ELEMENT);
		createEReference(enumElementEClass, ENUM_ELEMENT__ENUM_ITEM);

		enumItemElementEClass = createEClass(ENUM_ITEM_ELEMENT);
		createEAttribute(enumItemElementEClass, ENUM_ITEM_ELEMENT__VALUE);

		referElementEClass = createEClass(REFER_ELEMENT);
		createEReference(referElementEClass, REFER_ELEMENT__REAL_MODEL);
		createEReference(referElementEClass, REFER_ELEMENT__ITEMS);
		createEAttribute(referElementEClass, REFER_ELEMENT__ATTRIBUTE);
		createEAttribute(referElementEClass, REFER_ELEMENT__PATH);

		subDiagramEClass = createEClass(SUB_DIAGRAM);
		createEReference(subDiagramEClass, SUB_DIAGRAM__ITEMS);

		connectorElementEClass = createEClass(CONNECTOR_ELEMENT);
		createEReference(connectorElementEClass, CONNECTOR_ELEMENT__PARAMS);
		createEReference(connectorElementEClass, CONNECTOR_ELEMENT__WITHS);
		createEAttribute(connectorElementEClass, CONNECTOR_ELEMENT__JOIN_TYPE);
		createEAttribute(connectorElementEClass, CONNECTOR_ELEMENT__CON_TYPE);
		createEReference(connectorElementEClass, CONNECTOR_ELEMENT__CONSTRUCT);
		createEReference(connectorElementEClass, CONNECTOR_ELEMENT__DESTRUCT);
		createEReference(connectorElementEClass, CONNECTOR_ELEMENT__EXERCISE);

		withElementEClass = createEClass(WITH_ELEMENT);
		createEAttribute(withElementEClass, WITH_ELEMENT__CYCLE);

		structBlockElementEClass = createEClass(STRUCT_BLOCK_ELEMENT);
		createEAttribute(structBlockElementEClass, STRUCT_BLOCK_ELEMENT__STRUCT_TYPE);

		expandTransElementEClass = createEClass(EXPAND_TRANS_ELEMENT);
		createEReference(expandTransElementEClass, EXPAND_TRANS_ELEMENT__SOURCE);
		createEReference(expandTransElementEClass, EXPAND_TRANS_ELEMENT__EXPAND);
		createEReference(expandTransElementEClass, EXPAND_TRANS_ELEMENT__TRANS);

		// Create enums
		lineStyleEEnum = createEEnum(LINE_STYLE);
		lineEndPointEEnum = createEEnum(LINE_END_POINT);
		relationShipEEnum = createEEnum(RELATION_SHIP);
		stateAttributeEEnum = createEEnum(STATE_ATTRIBUTE);
		stateActionTypeEEnum = createEEnum(STATE_ACTION_TYPE);
		directionEEnum = createEEnum(DIRECTION);
		floatingLiteralEEnum = createEEnum(FLOATING_LITERAL);
		booleanLiteralEEnum = createEEnum(BOOLEAN_LITERAL);
		referAttributeEEnum = createEEnum(REFER_ATTRIBUTE);
		structTypeEEnum = createEEnum(STRUCT_TYPE);
		joinTypeEEnum = createEEnum(JOIN_TYPE);
		connectorTypeEEnum = createEEnum(CONNECTOR_TYPE);

		// Create data types
		pointEDataType = createEDataType(POINT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		shapeElementEClass.getESuperTypes().add(this.getItemElement());
		linkedElementEClass.getESuperTypes().add(this.getShapeElement());
		lineElementEClass.getESuperTypes().add(this.getItemElement());
		connectionElementEClass.getESuperTypes().add(this.getLineElement());
		workerElementEClass.getESuperTypes().add(this.getItemElement());
		workerElementEClass.getESuperTypes().add(this.getBlockElement());
		taskElementEClass.getESuperTypes().add(this.getItemElement());
		taskElementEClass.getESuperTypes().add(this.getBlockElement());
		stateElementEClass.getESuperTypes().add(this.getItemElement());
		stateElementEClass.getESuperTypes().add(this.getBlockElement());
		actionElementEClass.getESuperTypes().add(this.getItemElement());
		stateActionEClass.getESuperTypes().add(this.getItemElement());
		stateActionEClass.getESuperTypes().add(this.getBlockElement());
		symbolEClass.getESuperTypes().add(this.getItemElement());
		constantEClass.getESuperTypes().add(this.getItemElement());
		functionEClass.getESuperTypes().add(this.getItemElement());
		modelElementEClass.getESuperTypes().add(this.getItemElement());
		modelDiagramEClass.getESuperTypes().add(this.getItemElement());
		includedElementEClass.getESuperTypes().add(this.getItemElement());
		parameterEClass.getESuperTypes().add(this.getItemElement());
		enumElementEClass.getESuperTypes().add(this.getItemElement());
		enumItemElementEClass.getESuperTypes().add(this.getItemElement());
		referElementEClass.getESuperTypes().add(this.getLinkedElement());
		subDiagramEClass.getESuperTypes().add(this.getItemElement());
		connectorElementEClass.getESuperTypes().add(this.getItemElement());
		connectorElementEClass.getESuperTypes().add(this.getBlockElement());
		withElementEClass.getESuperTypes().add(this.getItemElement());
		withElementEClass.getESuperTypes().add(this.getBlockElement());
		structBlockElementEClass.getESuperTypes().add(this.getItemElement());
		structBlockElementEClass.getESuperTypes().add(this.getBlockElement());
		expandTransElementEClass.getESuperTypes().add(this.getItemElement());

		// Initialize classes and features; add operations and parameters
		initEClass(itemElementEClass, ItemElement.class, "ItemElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getItemElement_Parent(), this.getItemElement(), null, "parent", null, 0, 1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItemElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getItemElement_Description(), ecorePackage.getEString(), "description", null, 0, 1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getItemElement_Visible(), ecorePackage.getEBoolean(), "visible", "true", 1, 1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getItemElement_SubDiagram(), this.getSubDiagram(), null, "subDiagram", null, 0, 1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getItemElement_References(), this.getReferElement(), null, "references", null, 0, -1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getItemElement_ItemState(), ecorePackage.getEInt(), "itemState", null, 1, 1, ItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(itemElementEClass, ecorePackage.getEBoolean(), "isIncluded", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(shapeElementEClass, ShapeElement.class, "ShapeElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getShapeElement_X(), ecorePackage.getEInt(), "x", "5", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Y(), ecorePackage.getEInt(), "y", "5", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Width(), ecorePackage.getEInt(), "width", "100", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Height(), ecorePackage.getEInt(), "height", "70", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Collapsed(), ecorePackage.getEBoolean(), "collapsed", null, 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_X2(), ecorePackage.getEInt(), "x2", "0", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Y2(), ecorePackage.getEInt(), "y2", "5", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Width2(), ecorePackage.getEInt(), "width2", "100", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getShapeElement_Height2(), ecorePackage.getEInt(), "height2", "70", 1, 1, ShapeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(linkedElementEClass, LinkedElement.class, "LinkedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLinkedElement_SourceConnections(), this.getConnectionElement(), null, "sourceConnections", null, 0, -1, LinkedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLinkedElement_TargetConnections(), this.getConnectionElement(), null, "targetConnections", null, 0, -1, LinkedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(lineElementEClass, LineElement.class, "LineElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLineElement_LineStyle(), this.getLineStyle(), "lineStyle", "1", 1, 1, LineElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getLineElement_SourceEndPoint(), this.getLineEndPoint(), "sourceEndPoint", "", 1, 1, LineElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getLineElement_TargetEndPoint(), this.getLineEndPoint(), "targetEndPoint", null, 1, 1, LineElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getLineElement_BendPoints(), this.getPoint(), "bendPoints", "", 0, -1, LineElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(connectionElementEClass, ConnectionElement.class, "ConnectionElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectionElement_Source(), this.getLinkedElement(), null, "source", null, 0, 1, ConnectionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConnectionElement_Target(), this.getLinkedElement(), null, "target", null, 0, 1, ConnectionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConnectionElement_Source2(), this.getLinkedElement(), null, "source2", null, 0, 1, ConnectionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConnectionElement_Target2(), this.getLinkedElement(), null, "target2", null, 0, 1, ConnectionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionElement_Relationship(), this.getRelationShip(), "relationship", "0", 1, 1, ConnectionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionElement_Condition(), ecorePackage.getEString(), "condition", null, 0, -1, ConnectionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(workerElementEClass, TaskElement.class, "WorkerElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWorkerElement_InitialTask(), this.getItemElement(), null, "initialTask", null, 0, 1, TaskElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWorkerElement_Items(), this.getItemElement(), null, "items", null, 0, -1, TaskElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getWorkerElement_Initialize(), this.getStructBlockElement(), null, "initialize", null, 0, 1, TaskElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getWorkerElement_Finalize(), this.getStructBlockElement(), null, "finalize", null, 0, 1, TaskElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getWorkerElement_Run(), this.getStructBlockElement(), null, "run", null, 0, 1, TaskElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(blockElementEClass, BlockElement.class, "BlockElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBlockElement_Statements(), ecorePackage.getEString(), "statements", null, 0, -1, BlockElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(taskElementEClass, BehaviorElement.class, "TaskElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTaskElement_Params(), this.getParameter(), null, "params", null, 0, -1, BehaviorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTaskElement_States(), this.getStateElement(), null, "states", null, 0, -1, BehaviorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTaskElement_InitialState(), this.getStateElement(), null, "initialState", null, 0, 1, BehaviorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTaskElement_Construct(), this.getStructBlockElement(), null, "construct", null, 0, 1, BehaviorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTaskElement_Destruct(), this.getStructBlockElement(), null, "destruct", null, 0, 1, BehaviorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTaskElement_Bifurcates(), this.getExpandTransElement(), null, "bifurcates", null, 0, -1, BehaviorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(stateElementEClass, StateElement.class, "StateElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStateElement_Attribute(), this.getStateAttribute(), "attribute", "", 0, 1, StateElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateElement_Entry(), this.getStateAction(), null, "entry", null, 0, 1, StateElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getStateElement_Stay(), this.getStateAction(), null, "stay", null, 1, 1, StateElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getStateElement_Exit(), this.getStateAction(), null, "exit", null, 0, 1, StateElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getStateElement_Bifurcates(), this.getExpandTransElement(), null, "bifurcates", null, 0, -1, StateElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(actionElementEClass, ActionElement.class, "ActionElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActionElement_Params(), this.getParameter(), null, "params", null, 0, -1, ActionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(stateActionEClass, StateAction.class, "StateAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStateAction_StateActionType(), this.getStateActionType(), "stateActionType", "", 0, 1, StateAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbolEClass, Symbol.class, "Symbol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbol_Direction(), this.getDirection(), "direction", null, 0, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbol_Type(), ecorePackage.getEString(), "type", null, 1, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbol_Value(), ecorePackage.getEString(), "value", null, 0, 1, Symbol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(constantEClass, Constant.class, "Constant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstant_Type(), ecorePackage.getEString(), "type", null, 1, 1, Constant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConstant_InitValue(), ecorePackage.getEString(), "initValue", null, 0, 1, Constant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(functionEClass, Function.class, "Function", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunction_Type(), ecorePackage.getEString(), "type", null, 1, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunction_Params(), this.getParameter(), null, "params", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(modelElementEClass, ModelElement.class, "ModelElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelElement_Symbols(), this.getSymbol(), null, "symbols", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getModelElement_Constants(), this.getConstant(), null, "constants", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getModelElement_Functions(), this.getFunction(), null, "functions", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getModelElement_Models(), this.getModelElement(), null, "models", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(modelDiagramEClass, ModelDiagram.class, "ModelDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelDiagram_Items(), this.getItemElement(), null, "items", null, 0, -1, ModelDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getModelDiagram_IncludeItems(), this.getIncludedElement(), null, "includeItems", null, 0, -1, ModelDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelDiagram_Script(), ecorePackage.getEString(), "script", null, 0, 1, ModelDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(includedElementEClass, IncludedElement.class, "IncludedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIncludedElement_IncludePath(), ecorePackage.getEString(), "includePath", null, 0, 1, IncludedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIncludedElement_Items(), this.getItemElement(), null, "items", null, 0, -1, IncludedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Type(), ecorePackage.getEString(), "type", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Value(), ecorePackage.getEString(), "value", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(enumElementEClass, EnumElement.class, "EnumElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumElement_EnumItem(), this.getEnumItemElement(), null, "enumItem", null, 0, -1, EnumElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(enumItemElementEClass, EnumItemElement.class, "EnumItemElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumItemElement_Value(), ecorePackage.getEInt(), "value", null, 1, 1, EnumItemElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referElementEClass, ReferElement.class, "ReferElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferElement_RealModel(), this.getItemElement(), null, "realModel", null, 0, 1, ReferElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getReferElement_Items(), this.getItemElement(), null, "items", null, 0, -1, ReferElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferElement_Attribute(), this.getReferAttribute(), "attribute", null, 0, 1, ReferElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferElement_Path(), ecorePackage.getEString(), "path", "", 0, 1, ReferElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subDiagramEClass, SubDiagram.class, "SubDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubDiagram_Items(), this.getReferElement(), null, "items", null, 0, -1, SubDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(connectorElementEClass, ConnectorElement.class, "ConnectorElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectorElement_Params(), this.getParameter(), null, "params", null, 0, -1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConnectorElement_Withs(), this.getWithElement(), null, "withs", null, 0, -1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorElement_JoinType(), this.getJoinType(), "joinType", null, 0, 1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorElement_ConType(), this.getConnectorType(), "conType", null, 0, 1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnectorElement_Construct(), this.getStructBlockElement(), null, "construct", null, 0, 1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConnectorElement_Destruct(), this.getStructBlockElement(), null, "destruct", null, 0, 1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConnectorElement_Exercise(), this.getStructBlockElement(), null, "exercise", null, 0, 1, ConnectorElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(withElementEClass, WithElement.class, "WithElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWithElement_Cycle(), ecorePackage.getEInt(), "cycle", null, 1, 1, WithElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(structBlockElementEClass, StructBlockElement.class, "StructBlockElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStructBlockElement_StructType(), this.getStructType(), "structType", null, 0, 1, StructBlockElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expandTransElementEClass, ExpandTransElement.class, "ExpandTransElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpandTransElement_Source(), this.getLinkedElement(), null, "source", null, 0, 1, ExpandTransElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExpandTransElement_Expand(), this.getLinkedElement(), null, "expand", null, 0, 1, ExpandTransElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExpandTransElement_Trans(), this.getLinkedElement(), null, "trans", null, 0, 1, ExpandTransElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(lineStyleEEnum, LineStyle.class, "LineStyle");
		addEEnumLiteral(lineStyleEEnum, LineStyle.LINE_SOLID);
		addEEnumLiteral(lineStyleEEnum, LineStyle.LINE_DASH);
		addEEnumLiteral(lineStyleEEnum, LineStyle.LINE_DOT);
		addEEnumLiteral(lineStyleEEnum, LineStyle.LINE_DASHDOT);
		addEEnumLiteral(lineStyleEEnum, LineStyle.LINE_DASHDOTDOT);
		addEEnumLiteral(lineStyleEEnum, LineStyle.LINE_CUSTOM);

		initEEnum(lineEndPointEEnum, LineEndPoint.class, "LineEndPoint");
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.NONE);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.OPENED_ARROW);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.OPENED_TRIANGLE);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.OPENED_SQUARE);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.OPENED_CIRCLE);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.CLOSED_TRIANGLE);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.CLOSED_SQUARE);
		addEEnumLiteral(lineEndPointEEnum, LineEndPoint.CLOSED_CIRCLE);

		initEEnum(relationShipEEnum, RelationShip.class, "RelationShip");
		addEEnumLiteral(relationShipEEnum, RelationShip.TRANSITION);
		addEEnumLiteral(relationShipEEnum, RelationShip.TASK_CALL);
		addEEnumLiteral(relationShipEEnum, RelationShip.ACTION_CALL);
		addEEnumLiteral(relationShipEEnum, RelationShip.INCLUDE);

		initEEnum(stateAttributeEEnum, StateAttribute.class, "StateAttribute");
		addEEnumLiteral(stateAttributeEEnum, StateAttribute.NORMAL);
		addEEnumLiteral(stateAttributeEEnum, StateAttribute.INITIAL);
		addEEnumLiteral(stateAttributeEEnum, StateAttribute.TARGET);

		initEEnum(stateActionTypeEEnum, StateActionType.class, "StateActionType");
		addEEnumLiteral(stateActionTypeEEnum, StateActionType.ENTRY);
		addEEnumLiteral(stateActionTypeEEnum, StateActionType.STAY);
		addEEnumLiteral(stateActionTypeEEnum, StateActionType.EXIT);

		initEEnum(directionEEnum, Direction.class, "Direction");
		addEEnumLiteral(directionEEnum, Direction.IN);
		addEEnumLiteral(directionEEnum, Direction.OUT);

		initEEnum(floatingLiteralEEnum, FloatingLiteral.class, "FloatingLiteral");
		addEEnumLiteral(floatingLiteralEEnum, FloatingLiteral.STATE_TIME);
		addEEnumLiteral(floatingLiteralEEnum, FloatingLiteral.TASK_TIME);
		addEEnumLiteral(floatingLiteralEEnum, FloatingLiteral.REAL_FLOAT_LITERAL);

		initEEnum(booleanLiteralEEnum, BooleanLiteral.class, "BooleanLiteral");
		addEEnumLiteral(booleanLiteralEEnum, BooleanLiteral.FALSE);
		addEEnumLiteral(booleanLiteralEEnum, BooleanLiteral.TRUE);
		addEEnumLiteral(booleanLiteralEEnum, BooleanLiteral.IS_SUB_TASK_FINAL);

		initEEnum(referAttributeEEnum, ReferAttribute.class, "ReferAttribute");
		addEEnumLiteral(referAttributeEEnum, ReferAttribute.NORMAL);
		addEEnumLiteral(referAttributeEEnum, ReferAttribute.EXTERNAL);

		initEEnum(structTypeEEnum, StructType.class, "StructType");
		addEEnumLiteral(structTypeEEnum, StructType.CONSTRUCT);
		addEEnumLiteral(structTypeEEnum, StructType.RUN);
		addEEnumLiteral(structTypeEEnum, StructType.DESTRUCT);

		initEEnum(joinTypeEEnum, JoinType.class, "JoinType");
		addEEnumLiteral(joinTypeEEnum, JoinType.ANDJOIN);
		addEEnumLiteral(joinTypeEEnum, JoinType.ORJOIN);

		initEEnum(connectorTypeEEnum, ConnectorType.class, "ConnectorType");
		addEEnumLiteral(connectorTypeEEnum, ConnectorType.CONEXER);
		addEEnumLiteral(connectorTypeEEnum, ConnectorType.SEQEXER);

		// Initialize data types
		initEDataType(pointEDataType, Point.class, "Point", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //TaskModelPackageImpl
