/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelFactory
 * @model kind="package"
 * @generated
 */
public interface TaskModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "taskmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://kr.re.etri.tpl/taskmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tpl";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TaskModelPackage eINSTANCE = kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl <em>Item Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ItemElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getItemElement()
	 * @generated
	 */
	int ITEM_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__PARENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__VISIBLE = 3;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__SUB_DIAGRAM = 4;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__REFERENCES = 5;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT__ITEM_STATE = 6;

	/**
	 * The number of structural features of the '<em>Item Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_ELEMENT_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl <em>Shape Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getShapeElement()
	 * @generated
	 */
	int SHAPE_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__X = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__Y = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__WIDTH = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__HEIGHT = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__COLLAPSED = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>X2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__X2 = ITEM_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Y2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__Y2 = ITEM_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Width2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__WIDTH2 = ITEM_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Height2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT__HEIGHT2 = ITEM_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Shape Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.LinkedElementImpl <em>Linked Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.LinkedElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLinkedElement()
	 * @generated
	 */
	int LINKED_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__PARENT = SHAPE_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__NAME = SHAPE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__DESCRIPTION = SHAPE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__VISIBLE = SHAPE_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__SUB_DIAGRAM = SHAPE_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__REFERENCES = SHAPE_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__ITEM_STATE = SHAPE_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__X = SHAPE_ELEMENT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__Y = SHAPE_ELEMENT__Y;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__WIDTH = SHAPE_ELEMENT__WIDTH;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__HEIGHT = SHAPE_ELEMENT__HEIGHT;

	/**
	 * The feature id for the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__COLLAPSED = SHAPE_ELEMENT__COLLAPSED;

	/**
	 * The feature id for the '<em><b>X2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__X2 = SHAPE_ELEMENT__X2;

	/**
	 * The feature id for the '<em><b>Y2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__Y2 = SHAPE_ELEMENT__Y2;

	/**
	 * The feature id for the '<em><b>Width2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__WIDTH2 = SHAPE_ELEMENT__WIDTH2;

	/**
	 * The feature id for the '<em><b>Height2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__HEIGHT2 = SHAPE_ELEMENT__HEIGHT2;

	/**
	 * The feature id for the '<em><b>Source Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__SOURCE_CONNECTIONS = SHAPE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT__TARGET_CONNECTIONS = SHAPE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Linked Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_ELEMENT_FEATURE_COUNT = SHAPE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.LineElementImpl <em>Line Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.LineElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLineElement()
	 * @generated
	 */
	int LINE_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Line Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__LINE_STYLE = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source End Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__SOURCE_END_POINT = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target End Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__TARGET_END_POINT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Bend Points</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT__BEND_POINTS = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Line Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl <em>Connection Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConnectionElement()
	 * @generated
	 */
	int CONNECTION_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__PARENT = LINE_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__NAME = LINE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__DESCRIPTION = LINE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__VISIBLE = LINE_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__SUB_DIAGRAM = LINE_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__REFERENCES = LINE_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__ITEM_STATE = LINE_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Line Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__LINE_STYLE = LINE_ELEMENT__LINE_STYLE;

	/**
	 * The feature id for the '<em><b>Source End Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__SOURCE_END_POINT = LINE_ELEMENT__SOURCE_END_POINT;

	/**
	 * The feature id for the '<em><b>Target End Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__TARGET_END_POINT = LINE_ELEMENT__TARGET_END_POINT;

	/**
	 * The feature id for the '<em><b>Bend Points</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__BEND_POINTS = LINE_ELEMENT__BEND_POINTS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__SOURCE = LINE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__TARGET = LINE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__SOURCE2 = LINE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Target2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__TARGET2 = LINE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Relationship</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__RELATIONSHIP = LINE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT__CONDITION = LINE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Connection Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ELEMENT_FEATURE_COUNT = LINE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl <em>Worker Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.TaskElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getWorkerElement()
	 * @generated
	 */
	int WORKER_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__INITIAL_TASK = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__ITEMS = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Initialize</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__INITIALIZE = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Finalize</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__FINALIZE = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Run</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT__RUN = ITEM_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Worker Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKER_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.BlockElementImpl <em>Block Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.BlockElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getBlockElement()
	 * @generated
	 */
	int BLOCK_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_ELEMENT__STATEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Block Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl <em>Task Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getTaskElement()
	 * @generated
	 */
	int TASK_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__PARAMS = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__STATES = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__INITIAL_STATE = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Construct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__CONSTRUCT = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Destruct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__DESTRUCT = ITEM_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Bifurcates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT__BIFURCATES = ITEM_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Task Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl <em>State Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.StateElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateElement()
	 * @generated
	 */
	int STATE_ELEMENT = 8;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__ATTRIBUTE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__ENTRY = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Stay</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__STAY = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__EXIT = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Bifurcates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT__BIFURCATES = ITEM_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>State Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ActionElementImpl <em>Action Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ActionElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getActionElement()
	 * @generated
	 */
	int ACTION_ELEMENT = 9;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT__PARAMS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Action Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.StateActionImpl <em>State Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.StateActionImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateAction()
	 * @generated
	 */
	int STATE_ACTION = 10;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State Action Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION__STATE_ACTION_TYPE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>State Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ACTION_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.SymbolImpl <em>Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.SymbolImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getSymbol()
	 * @generated
	 */
	int SYMBOL = 11;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__DIRECTION = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__TYPE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL__VALUE = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ConstantImpl <em>Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ConstantImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConstant()
	 * @generated
	 */
	int CONSTANT = 12;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__TYPE = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__INIT_VALUE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.FunctionImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 13;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__TYPE = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PARAMS = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ModelElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getModelElement()
	 * @generated
	 */
	int MODEL_ELEMENT = 14;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Symbols</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__SYMBOLS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__CONSTANTS = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__FUNCTIONS = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__MODELS = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ModelDiagramImpl <em>Model Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ModelDiagramImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getModelDiagram()
	 * @generated
	 */
	int MODEL_DIAGRAM = 15;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__ITEMS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Include Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__INCLUDE_ITEMS = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM__SCRIPT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Model Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_DIAGRAM_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.IncludedElementImpl <em>Included Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.IncludedElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getIncludedElement()
	 * @generated
	 */
	int INCLUDED_ELEMENT = 16;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Include Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__INCLUDE_PATH = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT__ITEMS = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Included Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ParameterImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 17;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.EnumElementImpl <em>Enum Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.EnumElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getEnumElement()
	 * @generated
	 */
	int ENUM_ELEMENT = 18;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Enum Item</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__ENUM_ITEM = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enum Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.EnumItemElementImpl <em>Enum Item Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.EnumItemElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getEnumItemElement()
	 * @generated
	 */
	int ENUM_ITEM_ELEMENT = 19;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT__VALUE = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enum Item Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ITEM_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ReferElementImpl <em>Refer Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ReferElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getReferElement()
	 * @generated
	 */
	int REFER_ELEMENT = 20;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__PARENT = LINKED_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__NAME = LINKED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__DESCRIPTION = LINKED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__VISIBLE = LINKED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__SUB_DIAGRAM = LINKED_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__REFERENCES = LINKED_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__ITEM_STATE = LINKED_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__X = LINKED_ELEMENT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__Y = LINKED_ELEMENT__Y;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__WIDTH = LINKED_ELEMENT__WIDTH;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__HEIGHT = LINKED_ELEMENT__HEIGHT;

	/**
	 * The feature id for the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__COLLAPSED = LINKED_ELEMENT__COLLAPSED;

	/**
	 * The feature id for the '<em><b>X2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__X2 = LINKED_ELEMENT__X2;

	/**
	 * The feature id for the '<em><b>Y2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__Y2 = LINKED_ELEMENT__Y2;

	/**
	 * The feature id for the '<em><b>Width2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__WIDTH2 = LINKED_ELEMENT__WIDTH2;

	/**
	 * The feature id for the '<em><b>Height2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__HEIGHT2 = LINKED_ELEMENT__HEIGHT2;

	/**
	 * The feature id for the '<em><b>Source Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__SOURCE_CONNECTIONS = LINKED_ELEMENT__SOURCE_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Target Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__TARGET_CONNECTIONS = LINKED_ELEMENT__TARGET_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Real Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__REAL_MODEL = LINKED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__ITEMS = LINKED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__ATTRIBUTE = LINKED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT__PATH = LINKED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Refer Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFER_ELEMENT_FEATURE_COUNT = LINKED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.SubDiagramImpl <em>Sub Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.SubDiagramImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getSubDiagram()
	 * @generated
	 */
	int SUB_DIAGRAM = 21;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM__ITEMS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sub Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_DIAGRAM_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl <em>Connector Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConnectorElement()
	 * @generated
	 */
	int CONNECTOR_ELEMENT = 22;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__PARAMS = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__WITHS = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Join Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__JOIN_TYPE = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Con Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__CON_TYPE = ITEM_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Construct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__CONSTRUCT = ITEM_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Destruct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__DESTRUCT = ITEM_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Exercise</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT__EXERCISE = ITEM_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Connector Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.WithElementImpl <em>With Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.WithElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getWithElement()
	 * @generated
	 */
	int WITH_ELEMENT = 23;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cycle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT__CYCLE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>With Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.StructBlockElementImpl <em>Struct Block Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.StructBlockElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStructBlockElement()
	 * @generated
	 */
	int STRUCT_BLOCK_ELEMENT = 24;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__STATEMENTS = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Struct Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT__STRUCT_TYPE = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Struct Block Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_BLOCK_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl <em>Expand Trans Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getExpandTransElement()
	 * @generated
	 */
	int EXPAND_TRANS_ELEMENT = 25;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__PARENT = ITEM_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__NAME = ITEM_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__DESCRIPTION = ITEM_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__VISIBLE = ITEM_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Sub Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__SUB_DIAGRAM = ITEM_ELEMENT__SUB_DIAGRAM;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__REFERENCES = ITEM_ELEMENT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Item State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__ITEM_STATE = ITEM_ELEMENT__ITEM_STATE;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__SOURCE = ITEM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expand</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__EXPAND = ITEM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Trans</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT__TRANS = ITEM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Expand Trans Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_TRANS_ELEMENT_FEATURE_COUNT = ITEM_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.LineStyle <em>Line Style</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.LineStyle
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLineStyle()
	 * @generated
	 */
	int LINE_STYLE = 26;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.LineEndPoint <em>Line End Point</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLineEndPoint()
	 * @generated
	 */
	int LINE_END_POINT = 27;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.RelationShip <em>Relation Ship</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.RelationShip
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getRelationShip()
	 * @generated
	 */
	int RELATION_SHIP = 28;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.StateAttribute <em>State Attribute</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.StateAttribute
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateAttribute()
	 * @generated
	 */
	int STATE_ATTRIBUTE = 29;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.StateActionType <em>State Action Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.StateActionType
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateActionType()
	 * @generated
	 */
	int STATE_ACTION_TYPE = 30;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.Direction <em>Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.Direction
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getDirection()
	 * @generated
	 */
	int DIRECTION = 31;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.FloatingLiteral <em>Floating Literal</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.FloatingLiteral
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getFloatingLiteral()
	 * @generated
	 */
	int FLOATING_LITERAL = 32;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.BooleanLiteral <em>Boolean Literal</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.BooleanLiteral
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getBooleanLiteral()
	 * @generated
	 */
	int BOOLEAN_LITERAL = 33;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.ReferAttribute <em>Refer Attribute</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.ReferAttribute
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getReferAttribute()
	 * @generated
	 */
	int REFER_ATTRIBUTE = 34;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.StructType <em>Struct Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.StructType
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStructType()
	 * @generated
	 */
	int STRUCT_TYPE = 35;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.JoinType <em>Join Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.JoinType
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getJoinType()
	 * @generated
	 */
	int JOIN_TYPE = 36;

	/**
	 * The meta object id for the '{@link kr.re.etri.tpl.taskmodel.ConnectorType <em>Connector Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see kr.re.etri.tpl.taskmodel.ConnectorType
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConnectorType()
	 * @generated
	 */
	int CONNECTOR_TYPE = 37;

	/**
	 * The meta object id for the '<em>Point</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.draw2d.geometry.Point
	 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 38;


	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ItemElement <em>Item Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement
	 * @generated
	 */
	EClass getItemElement();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ItemElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#getParent()
	 * @see #getItemElement()
	 * @generated
	 */
	EReference getItemElement_Parent();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ItemElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#getName()
	 * @see #getItemElement()
	 * @generated
	 */
	EAttribute getItemElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ItemElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#getDescription()
	 * @see #getItemElement()
	 * @generated
	 */
	EAttribute getItemElement_Description();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ItemElement#isVisible <em>Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#isVisible()
	 * @see #getItemElement()
	 * @generated
	 */
	EAttribute getItemElement_Visible();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.ItemElement#getSubDiagram <em>Sub Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sub Diagram</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#getSubDiagram()
	 * @see #getItemElement()
	 * @generated
	 */
	EReference getItemElement_SubDiagram();

	/**
	 * Returns the meta object for the reference list '{@link kr.re.etri.tpl.taskmodel.ItemElement#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>References</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#getReferences()
	 * @see #getItemElement()
	 * @generated
	 */
	EReference getItemElement_References();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ItemElement#getItemState <em>Item State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Item State</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ItemElement#getItemState()
	 * @see #getItemElement()
	 * @generated
	 */
	EAttribute getItemElement_ItemState();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ShapeElement <em>Shape Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shape Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement
	 * @generated
	 */
	EClass getShapeElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getX()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_X();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getY()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Y();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getWidth()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Width();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getHeight()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Height();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#isCollapsed <em>Collapsed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Collapsed</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#isCollapsed()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Collapsed();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getX2 <em>X2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X2</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getX2()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_X2();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getY2 <em>Y2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y2</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getY2()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Y2();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getWidth2 <em>Width2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width2</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getWidth2()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Width2();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getHeight2 <em>Height2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height2</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ShapeElement#getHeight2()
	 * @see #getShapeElement()
	 * @generated
	 */
	EAttribute getShapeElement_Height2();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.LinkedElement <em>Linked Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Linked Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LinkedElement
	 * @generated
	 */
	EClass getLinkedElement();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.LinkedElement#getSourceConnections <em>Source Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Connections</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LinkedElement#getSourceConnections()
	 * @see #getLinkedElement()
	 * @generated
	 */
	EReference getLinkedElement_SourceConnections();

	/**
	 * Returns the meta object for the reference list '{@link kr.re.etri.tpl.taskmodel.LinkedElement#getTargetConnections <em>Target Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Connections</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LinkedElement#getTargetConnections()
	 * @see #getLinkedElement()
	 * @generated
	 */
	EReference getLinkedElement_TargetConnections();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.LineElement <em>Line Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineElement
	 * @generated
	 */
	EClass getLineElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.LineElement#getLineStyle <em>Line Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Style</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineElement#getLineStyle()
	 * @see #getLineElement()
	 * @generated
	 */
	EAttribute getLineElement_LineStyle();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.LineElement#getSourceEndPoint <em>Source End Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source End Point</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineElement#getSourceEndPoint()
	 * @see #getLineElement()
	 * @generated
	 */
	EAttribute getLineElement_SourceEndPoint();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.LineElement#getTargetEndPoint <em>Target End Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target End Point</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineElement#getTargetEndPoint()
	 * @see #getLineElement()
	 * @generated
	 */
	EAttribute getLineElement_TargetEndPoint();

	/**
	 * Returns the meta object for the attribute list '{@link kr.re.etri.tpl.taskmodel.LineElement#getBendPoints <em>Bend Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Bend Points</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineElement#getBendPoints()
	 * @see #getLineElement()
	 * @generated
	 */
	EAttribute getLineElement_BendPoints();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ConnectionElement <em>Connection Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement
	 * @generated
	 */
	EClass getConnectionElement();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement#getSource()
	 * @see #getConnectionElement()
	 * @generated
	 */
	EReference getConnectionElement_Source();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget()
	 * @see #getConnectionElement()
	 * @generated
	 */
	EReference getConnectionElement_Target();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getSource2 <em>Source2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source2</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement#getSource2()
	 * @see #getConnectionElement()
	 * @generated
	 */
	EReference getConnectionElement_Source2();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget2 <em>Target2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target2</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement#getTarget2()
	 * @see #getConnectionElement()
	 * @generated
	 */
	EReference getConnectionElement_Target2();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getRelationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relationship</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement#getRelationship()
	 * @see #getConnectionElement()
	 * @generated
	 */
	EAttribute getConnectionElement_Relationship();

	/**
	 * Returns the meta object for the attribute list '{@link kr.re.etri.tpl.taskmodel.ConnectionElement#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Condition</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectionElement#getCondition()
	 * @see #getConnectionElement()
	 * @generated
	 */
	EAttribute getConnectionElement_Condition();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.TaskElement <em>Worker Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Worker Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement
	 * @generated
	 */
	EClass getWorkerElement();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.TaskElement#getInitialTask <em>Initial Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initial Task</em>'.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement#getInitialTask()
	 * @see #getWorkerElement()
	 * @generated
	 */
	EReference getWorkerElement_InitialTask();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.TaskElement#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement#getItems()
	 * @see #getWorkerElement()
	 * @generated
	 */
	EReference getWorkerElement_Items();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.TaskElement#getInitialize <em>Initialize</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initialize</em>'.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement#getInitialize()
	 * @see #getWorkerElement()
	 * @generated
	 */
	EReference getWorkerElement_Initialize();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.TaskElement#getFinalize <em>Finalize</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Finalize</em>'.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement#getFinalize()
	 * @see #getWorkerElement()
	 * @generated
	 */
	EReference getWorkerElement_Finalize();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.TaskElement#getRun <em>Run</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Run</em>'.
	 * @see kr.re.etri.tpl.taskmodel.TaskElement#getRun()
	 * @see #getWorkerElement()
	 * @generated
	 */
	EReference getWorkerElement_Run();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.BlockElement <em>Block Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BlockElement
	 * @generated
	 */
	EClass getBlockElement();

	/**
	 * Returns the meta object for the attribute list '{@link kr.re.etri.tpl.taskmodel.BlockElement#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Statements</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BlockElement#getStatements()
	 * @see #getBlockElement()
	 * @generated
	 */
	EAttribute getBlockElement_Statements();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.BehaviorElement <em>Task Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement
	 * @generated
	 */
	EClass getTaskElement();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement#getParams()
	 * @see #getTaskElement()
	 * @generated
	 */
	EReference getTaskElement_Params();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement#getStates()
	 * @see #getTaskElement()
	 * @generated
	 */
	EReference getTaskElement_States();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initial State</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement#getInitialState()
	 * @see #getTaskElement()
	 * @generated
	 */
	EReference getTaskElement_InitialState();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getConstruct <em>Construct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Construct</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement#getConstruct()
	 * @see #getTaskElement()
	 * @generated
	 */
	EReference getTaskElement_Construct();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getDestruct <em>Destruct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destruct</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement#getDestruct()
	 * @see #getTaskElement()
	 * @generated
	 */
	EReference getTaskElement_Destruct();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.BehaviorElement#getBifurcates <em>Bifurcates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bifurcates</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BehaviorElement#getBifurcates()
	 * @see #getTaskElement()
	 * @generated
	 */
	EReference getTaskElement_Bifurcates();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.StateElement <em>State Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateElement
	 * @generated
	 */
	EClass getStateElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.StateElement#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateElement#getAttribute()
	 * @see #getStateElement()
	 * @generated
	 */
	EAttribute getStateElement_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.StateElement#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entry</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateElement#getEntry()
	 * @see #getStateElement()
	 * @generated
	 */
	EReference getStateElement_Entry();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.StateElement#getStay <em>Stay</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stay</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateElement#getStay()
	 * @see #getStateElement()
	 * @generated
	 */
	EReference getStateElement_Stay();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.StateElement#getExit <em>Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exit</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateElement#getExit()
	 * @see #getStateElement()
	 * @generated
	 */
	EReference getStateElement_Exit();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.StateElement#getBifurcates <em>Bifurcates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bifurcates</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateElement#getBifurcates()
	 * @see #getStateElement()
	 * @generated
	 */
	EReference getStateElement_Bifurcates();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ActionElement <em>Action Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ActionElement
	 * @generated
	 */
	EClass getActionElement();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ActionElement#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ActionElement#getParams()
	 * @see #getActionElement()
	 * @generated
	 */
	EReference getActionElement_Params();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.StateAction <em>State Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Action</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateAction
	 * @generated
	 */
	EClass getStateAction();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.StateAction#getStateActionType <em>State Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Action Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateAction#getStateActionType()
	 * @see #getStateAction()
	 * @generated
	 */
	EAttribute getStateAction_StateActionType();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.Symbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Symbol
	 * @generated
	 */
	EClass getSymbol();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Symbol#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Symbol#getDirection()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_Direction();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Symbol#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Symbol#getType()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_Type();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Symbol#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Symbol#getValue()
	 * @see #getSymbol()
	 * @generated
	 */
	EAttribute getSymbol_Value();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Constant
	 * @generated
	 */
	EClass getConstant();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Constant#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Constant#getType()
	 * @see #getConstant()
	 * @generated
	 */
	EAttribute getConstant_Type();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Constant#getInitValue <em>Init Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Init Value</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Constant#getInitValue()
	 * @see #getConstant()
	 * @generated
	 */
	EAttribute getConstant_InitValue();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Function#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Function#getType()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.Function#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Function#getParams()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Params();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelElement
	 * @generated
	 */
	EClass getModelElement();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ModelElement#getSymbols <em>Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Symbols</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelElement#getSymbols()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_Symbols();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ModelElement#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelElement#getConstants()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_Constants();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ModelElement#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelElement#getFunctions()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_Functions();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ModelElement#getModels <em>Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Models</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelElement#getModels()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_Models();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ModelDiagram <em>Model Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Diagram</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelDiagram
	 * @generated
	 */
	EClass getModelDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelDiagram#getItems()
	 * @see #getModelDiagram()
	 * @generated
	 */
	EReference getModelDiagram_Items();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getIncludeItems <em>Include Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Include Items</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelDiagram#getIncludeItems()
	 * @see #getModelDiagram()
	 * @generated
	 */
	EReference getModelDiagram_IncludeItems();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ModelDiagram#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ModelDiagram#getScript()
	 * @see #getModelDiagram()
	 * @generated
	 */
	EAttribute getModelDiagram_Script();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.IncludedElement <em>Included Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Included Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.IncludedElement
	 * @generated
	 */
	EClass getIncludedElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.IncludedElement#getIncludePath <em>Include Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include Path</em>'.
	 * @see kr.re.etri.tpl.taskmodel.IncludedElement#getIncludePath()
	 * @see #getIncludedElement()
	 * @generated
	 */
	EAttribute getIncludedElement_IncludePath();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.IncludedElement#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see kr.re.etri.tpl.taskmodel.IncludedElement#getItems()
	 * @see #getIncludedElement()
	 * @generated
	 */
	EReference getIncludedElement_Items();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Type();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.EnumElement <em>Enum Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.EnumElement
	 * @generated
	 */
	EClass getEnumElement();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.EnumElement#getEnumItem <em>Enum Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enum Item</em>'.
	 * @see kr.re.etri.tpl.taskmodel.EnumElement#getEnumItem()
	 * @see #getEnumElement()
	 * @generated
	 */
	EReference getEnumElement_EnumItem();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.EnumItemElement <em>Enum Item Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Item Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.EnumItemElement
	 * @generated
	 */
	EClass getEnumItemElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.EnumItemElement#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see kr.re.etri.tpl.taskmodel.EnumItemElement#getValue()
	 * @see #getEnumItemElement()
	 * @generated
	 */
	EAttribute getEnumItemElement_Value();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ReferElement <em>Refer Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Refer Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ReferElement
	 * @generated
	 */
	EClass getReferElement();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ReferElement#getRealModel <em>Real Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Real Model</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ReferElement#getRealModel()
	 * @see #getReferElement()
	 * @generated
	 */
	EReference getReferElement_RealModel();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ReferElement#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ReferElement#getItems()
	 * @see #getReferElement()
	 * @generated
	 */
	EReference getReferElement_Items();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ReferElement#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ReferElement#getAttribute()
	 * @see #getReferElement()
	 * @generated
	 */
	EAttribute getReferElement_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ReferElement#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ReferElement#getPath()
	 * @see #getReferElement()
	 * @generated
	 */
	EAttribute getReferElement_Path();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.SubDiagram <em>Sub Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub Diagram</em>'.
	 * @see kr.re.etri.tpl.taskmodel.SubDiagram
	 * @generated
	 */
	EClass getSubDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.SubDiagram#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see kr.re.etri.tpl.taskmodel.SubDiagram#getItems()
	 * @see #getSubDiagram()
	 * @generated
	 */
	EReference getSubDiagram_Items();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ConnectorElement <em>Connector Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement
	 * @generated
	 */
	EClass getConnectorElement();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getParams()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EReference getConnectorElement_Params();

	/**
	 * Returns the meta object for the containment reference list '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getWiths <em>Withs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Withs</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getWiths()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EReference getConnectorElement_Withs();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getJoinType <em>Join Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Join Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getJoinType()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EAttribute getConnectorElement_JoinType();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getConType <em>Con Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Con Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getConType()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EAttribute getConnectorElement_ConType();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getConstruct <em>Construct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Construct</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getConstruct()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EReference getConnectorElement_Construct();

	/**
	 * Returns the meta object for the containment reference '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getDestruct <em>Destruct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destruct</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getDestruct()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EReference getConnectorElement_Destruct();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ConnectorElement#getExercise <em>Exercise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exercise</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorElement#getExercise()
	 * @see #getConnectorElement()
	 * @generated
	 */
	EReference getConnectorElement_Exercise();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.WithElement <em>With Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>With Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.WithElement
	 * @generated
	 */
	EClass getWithElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.WithElement#getCycle <em>Cycle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cycle</em>'.
	 * @see kr.re.etri.tpl.taskmodel.WithElement#getCycle()
	 * @see #getWithElement()
	 * @generated
	 */
	EAttribute getWithElement_Cycle();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.StructBlockElement <em>Struct Block Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Struct Block Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StructBlockElement
	 * @generated
	 */
	EClass getStructBlockElement();

	/**
	 * Returns the meta object for the attribute '{@link kr.re.etri.tpl.taskmodel.StructBlockElement#getStructType <em>Struct Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Struct Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StructBlockElement#getStructType()
	 * @see #getStructBlockElement()
	 * @generated
	 */
	EAttribute getStructBlockElement_StructType();

	/**
	 * Returns the meta object for class '{@link kr.re.etri.tpl.taskmodel.ExpandTransElement <em>Expand Trans Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expand Trans Element</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ExpandTransElement
	 * @generated
	 */
	EClass getExpandTransElement();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ExpandTransElement#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ExpandTransElement#getSource()
	 * @see #getExpandTransElement()
	 * @generated
	 */
	EReference getExpandTransElement_Source();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ExpandTransElement#getExpand <em>Expand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Expand</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ExpandTransElement#getExpand()
	 * @see #getExpandTransElement()
	 * @generated
	 */
	EReference getExpandTransElement_Expand();

	/**
	 * Returns the meta object for the reference '{@link kr.re.etri.tpl.taskmodel.ExpandTransElement#getTrans <em>Trans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Trans</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ExpandTransElement#getTrans()
	 * @see #getExpandTransElement()
	 * @generated
	 */
	EReference getExpandTransElement_Trans();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.LineStyle <em>Line Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Line Style</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineStyle
	 * @generated
	 */
	EEnum getLineStyle();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.LineEndPoint <em>Line End Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Line End Point</em>'.
	 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
	 * @generated
	 */
	EEnum getLineEndPoint();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.RelationShip <em>Relation Ship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relation Ship</em>'.
	 * @see kr.re.etri.tpl.taskmodel.RelationShip
	 * @generated
	 */
	EEnum getRelationShip();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.StateAttribute <em>State Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>State Attribute</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateAttribute
	 * @generated
	 */
	EEnum getStateAttribute();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.StateActionType <em>State Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>State Action Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StateActionType
	 * @generated
	 */
	EEnum getStateActionType();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.Direction <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Direction</em>'.
	 * @see kr.re.etri.tpl.taskmodel.Direction
	 * @generated
	 */
	EEnum getDirection();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.FloatingLiteral <em>Floating Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Floating Literal</em>'.
	 * @see kr.re.etri.tpl.taskmodel.FloatingLiteral
	 * @generated
	 */
	EEnum getFloatingLiteral();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.BooleanLiteral <em>Boolean Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Boolean Literal</em>'.
	 * @see kr.re.etri.tpl.taskmodel.BooleanLiteral
	 * @generated
	 */
	EEnum getBooleanLiteral();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.ReferAttribute <em>Refer Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Refer Attribute</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ReferAttribute
	 * @generated
	 */
	EEnum getReferAttribute();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.StructType <em>Struct Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Struct Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.StructType
	 * @generated
	 */
	EEnum getStructType();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.JoinType <em>Join Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Join Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.JoinType
	 * @generated
	 */
	EEnum getJoinType();

	/**
	 * Returns the meta object for enum '{@link kr.re.etri.tpl.taskmodel.ConnectorType <em>Connector Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Connector Type</em>'.
	 * @see kr.re.etri.tpl.taskmodel.ConnectorType
	 * @generated
	 */
	EEnum getConnectorType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.draw2d.geometry.Point <em>Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Point</em>'.
	 * @see org.eclipse.draw2d.geometry.Point
	 * @model instanceClass="org.eclipse.draw2d.geometry.Point"
	 * @generated
	 */
	EDataType getPoint();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TaskModelFactory getTaskModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl <em>Item Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ItemElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getItemElement()
		 * @generated
		 */
		EClass ITEM_ELEMENT = eINSTANCE.getItemElement();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM_ELEMENT__PARENT = eINSTANCE.getItemElement_Parent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM_ELEMENT__NAME = eINSTANCE.getItemElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM_ELEMENT__DESCRIPTION = eINSTANCE.getItemElement_Description();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM_ELEMENT__VISIBLE = eINSTANCE.getItemElement_Visible();

		/**
		 * The meta object literal for the '<em><b>Sub Diagram</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM_ELEMENT__SUB_DIAGRAM = eINSTANCE.getItemElement_SubDiagram();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM_ELEMENT__REFERENCES = eINSTANCE.getItemElement_References();

		/**
		 * The meta object literal for the '<em><b>Item State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM_ELEMENT__ITEM_STATE = eINSTANCE.getItemElement_ItemState();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl <em>Shape Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getShapeElement()
		 * @generated
		 */
		EClass SHAPE_ELEMENT = eINSTANCE.getShapeElement();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__X = eINSTANCE.getShapeElement_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__Y = eINSTANCE.getShapeElement_Y();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__WIDTH = eINSTANCE.getShapeElement_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__HEIGHT = eINSTANCE.getShapeElement_Height();

		/**
		 * The meta object literal for the '<em><b>Collapsed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__COLLAPSED = eINSTANCE.getShapeElement_Collapsed();

		/**
		 * The meta object literal for the '<em><b>X2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__X2 = eINSTANCE.getShapeElement_X2();

		/**
		 * The meta object literal for the '<em><b>Y2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__Y2 = eINSTANCE.getShapeElement_Y2();

		/**
		 * The meta object literal for the '<em><b>Width2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__WIDTH2 = eINSTANCE.getShapeElement_Width2();

		/**
		 * The meta object literal for the '<em><b>Height2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_ELEMENT__HEIGHT2 = eINSTANCE.getShapeElement_Height2();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.LinkedElementImpl <em>Linked Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.LinkedElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLinkedElement()
		 * @generated
		 */
		EClass LINKED_ELEMENT = eINSTANCE.getLinkedElement();

		/**
		 * The meta object literal for the '<em><b>Source Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINKED_ELEMENT__SOURCE_CONNECTIONS = eINSTANCE.getLinkedElement_SourceConnections();

		/**
		 * The meta object literal for the '<em><b>Target Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINKED_ELEMENT__TARGET_CONNECTIONS = eINSTANCE.getLinkedElement_TargetConnections();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.LineElementImpl <em>Line Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.LineElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLineElement()
		 * @generated
		 */
		EClass LINE_ELEMENT = eINSTANCE.getLineElement();

		/**
		 * The meta object literal for the '<em><b>Line Style</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_ELEMENT__LINE_STYLE = eINSTANCE.getLineElement_LineStyle();

		/**
		 * The meta object literal for the '<em><b>Source End Point</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_ELEMENT__SOURCE_END_POINT = eINSTANCE.getLineElement_SourceEndPoint();

		/**
		 * The meta object literal for the '<em><b>Target End Point</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_ELEMENT__TARGET_END_POINT = eINSTANCE.getLineElement_TargetEndPoint();

		/**
		 * The meta object literal for the '<em><b>Bend Points</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_ELEMENT__BEND_POINTS = eINSTANCE.getLineElement_BendPoints();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl <em>Connection Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConnectionElement()
		 * @generated
		 */
		EClass CONNECTION_ELEMENT = eINSTANCE.getConnectionElement();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_ELEMENT__SOURCE = eINSTANCE.getConnectionElement_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_ELEMENT__TARGET = eINSTANCE.getConnectionElement_Target();

		/**
		 * The meta object literal for the '<em><b>Source2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_ELEMENT__SOURCE2 = eINSTANCE.getConnectionElement_Source2();

		/**
		 * The meta object literal for the '<em><b>Target2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_ELEMENT__TARGET2 = eINSTANCE.getConnectionElement_Target2();

		/**
		 * The meta object literal for the '<em><b>Relationship</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ELEMENT__RELATIONSHIP = eINSTANCE.getConnectionElement_Relationship();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ELEMENT__CONDITION = eINSTANCE.getConnectionElement_Condition();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl <em>Worker Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.TaskElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getWorkerElement()
		 * @generated
		 */
		EClass WORKER_ELEMENT = eINSTANCE.getWorkerElement();

		/**
		 * The meta object literal for the '<em><b>Initial Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKER_ELEMENT__INITIAL_TASK = eINSTANCE.getWorkerElement_InitialTask();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKER_ELEMENT__ITEMS = eINSTANCE.getWorkerElement_Items();

		/**
		 * The meta object literal for the '<em><b>Initialize</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKER_ELEMENT__INITIALIZE = eINSTANCE.getWorkerElement_Initialize();

		/**
		 * The meta object literal for the '<em><b>Finalize</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKER_ELEMENT__FINALIZE = eINSTANCE.getWorkerElement_Finalize();

		/**
		 * The meta object literal for the '<em><b>Run</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKER_ELEMENT__RUN = eINSTANCE.getWorkerElement_Run();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.BlockElementImpl <em>Block Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.BlockElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getBlockElement()
		 * @generated
		 */
		EClass BLOCK_ELEMENT = eINSTANCE.getBlockElement();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_ELEMENT__STATEMENTS = eINSTANCE.getBlockElement_Statements();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl <em>Task Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getTaskElement()
		 * @generated
		 */
		EClass TASK_ELEMENT = eINSTANCE.getTaskElement();

		/**
		 * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_ELEMENT__PARAMS = eINSTANCE.getTaskElement_Params();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_ELEMENT__STATES = eINSTANCE.getTaskElement_States();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_ELEMENT__INITIAL_STATE = eINSTANCE.getTaskElement_InitialState();

		/**
		 * The meta object literal for the '<em><b>Construct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_ELEMENT__CONSTRUCT = eINSTANCE.getTaskElement_Construct();

		/**
		 * The meta object literal for the '<em><b>Destruct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_ELEMENT__DESTRUCT = eINSTANCE.getTaskElement_Destruct();

		/**
		 * The meta object literal for the '<em><b>Bifurcates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_ELEMENT__BIFURCATES = eINSTANCE.getTaskElement_Bifurcates();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl <em>State Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.StateElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateElement()
		 * @generated
		 */
		EClass STATE_ELEMENT = eINSTANCE.getStateElement();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_ELEMENT__ATTRIBUTE = eINSTANCE.getStateElement_Attribute();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ELEMENT__ENTRY = eINSTANCE.getStateElement_Entry();

		/**
		 * The meta object literal for the '<em><b>Stay</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ELEMENT__STAY = eINSTANCE.getStateElement_Stay();

		/**
		 * The meta object literal for the '<em><b>Exit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ELEMENT__EXIT = eINSTANCE.getStateElement_Exit();

		/**
		 * The meta object literal for the '<em><b>Bifurcates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ELEMENT__BIFURCATES = eINSTANCE.getStateElement_Bifurcates();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ActionElementImpl <em>Action Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ActionElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getActionElement()
		 * @generated
		 */
		EClass ACTION_ELEMENT = eINSTANCE.getActionElement();

		/**
		 * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_ELEMENT__PARAMS = eINSTANCE.getActionElement_Params();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.StateActionImpl <em>State Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.StateActionImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateAction()
		 * @generated
		 */
		EClass STATE_ACTION = eINSTANCE.getStateAction();

		/**
		 * The meta object literal for the '<em><b>State Action Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_ACTION__STATE_ACTION_TYPE = eINSTANCE.getStateAction_StateActionType();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.SymbolImpl <em>Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.SymbolImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getSymbol()
		 * @generated
		 */
		EClass SYMBOL = eINSTANCE.getSymbol();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__DIRECTION = eINSTANCE.getSymbol_Direction();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__TYPE = eINSTANCE.getSymbol_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL__VALUE = eINSTANCE.getSymbol_Value();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ConstantImpl <em>Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ConstantImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConstant()
		 * @generated
		 */
		EClass CONSTANT = eINSTANCE.getConstant();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTANT__TYPE = eINSTANCE.getConstant_Type();

		/**
		 * The meta object literal for the '<em><b>Init Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTANT__INIT_VALUE = eINSTANCE.getConstant_InitValue();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.FunctionImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__TYPE = eINSTANCE.getFunction_Type();

		/**
		 * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__PARAMS = eINSTANCE.getFunction_Params();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ModelElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getModelElement()
		 * @generated
		 */
		EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

		/**
		 * The meta object literal for the '<em><b>Symbols</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_ELEMENT__SYMBOLS = eINSTANCE.getModelElement_Symbols();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_ELEMENT__CONSTANTS = eINSTANCE.getModelElement_Constants();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_ELEMENT__FUNCTIONS = eINSTANCE.getModelElement_Functions();

		/**
		 * The meta object literal for the '<em><b>Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_ELEMENT__MODELS = eINSTANCE.getModelElement_Models();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ModelDiagramImpl <em>Model Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ModelDiagramImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getModelDiagram()
		 * @generated
		 */
		EClass MODEL_DIAGRAM = eINSTANCE.getModelDiagram();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_DIAGRAM__ITEMS = eINSTANCE.getModelDiagram_Items();

		/**
		 * The meta object literal for the '<em><b>Include Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_DIAGRAM__INCLUDE_ITEMS = eINSTANCE.getModelDiagram_IncludeItems();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_DIAGRAM__SCRIPT = eINSTANCE.getModelDiagram_Script();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.IncludedElementImpl <em>Included Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.IncludedElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getIncludedElement()
		 * @generated
		 */
		EClass INCLUDED_ELEMENT = eINSTANCE.getIncludedElement();

		/**
		 * The meta object literal for the '<em><b>Include Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDED_ELEMENT__INCLUDE_PATH = eINSTANCE.getIncludedElement_IncludePath();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCLUDED_ELEMENT__ITEMS = eINSTANCE.getIncludedElement_Items();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ParameterImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__TYPE = eINSTANCE.getParameter_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.EnumElementImpl <em>Enum Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.EnumElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getEnumElement()
		 * @generated
		 */
		EClass ENUM_ELEMENT = eINSTANCE.getEnumElement();

		/**
		 * The meta object literal for the '<em><b>Enum Item</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_ELEMENT__ENUM_ITEM = eINSTANCE.getEnumElement_EnumItem();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.EnumItemElementImpl <em>Enum Item Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.EnumItemElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getEnumItemElement()
		 * @generated
		 */
		EClass ENUM_ITEM_ELEMENT = eINSTANCE.getEnumItemElement();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_ITEM_ELEMENT__VALUE = eINSTANCE.getEnumItemElement_Value();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ReferElementImpl <em>Refer Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ReferElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getReferElement()
		 * @generated
		 */
		EClass REFER_ELEMENT = eINSTANCE.getReferElement();

		/**
		 * The meta object literal for the '<em><b>Real Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFER_ELEMENT__REAL_MODEL = eINSTANCE.getReferElement_RealModel();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFER_ELEMENT__ITEMS = eINSTANCE.getReferElement_Items();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFER_ELEMENT__ATTRIBUTE = eINSTANCE.getReferElement_Attribute();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFER_ELEMENT__PATH = eINSTANCE.getReferElement_Path();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.SubDiagramImpl <em>Sub Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.SubDiagramImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getSubDiagram()
		 * @generated
		 */
		EClass SUB_DIAGRAM = eINSTANCE.getSubDiagram();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_DIAGRAM__ITEMS = eINSTANCE.getSubDiagram_Items();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl <em>Connector Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConnectorElement()
		 * @generated
		 */
		EClass CONNECTOR_ELEMENT = eINSTANCE.getConnectorElement();

		/**
		 * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_ELEMENT__PARAMS = eINSTANCE.getConnectorElement_Params();

		/**
		 * The meta object literal for the '<em><b>Withs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_ELEMENT__WITHS = eINSTANCE.getConnectorElement_Withs();

		/**
		 * The meta object literal for the '<em><b>Join Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_ELEMENT__JOIN_TYPE = eINSTANCE.getConnectorElement_JoinType();

		/**
		 * The meta object literal for the '<em><b>Con Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_ELEMENT__CON_TYPE = eINSTANCE.getConnectorElement_ConType();

		/**
		 * The meta object literal for the '<em><b>Construct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_ELEMENT__CONSTRUCT = eINSTANCE.getConnectorElement_Construct();

		/**
		 * The meta object literal for the '<em><b>Destruct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_ELEMENT__DESTRUCT = eINSTANCE.getConnectorElement_Destruct();

		/**
		 * The meta object literal for the '<em><b>Exercise</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_ELEMENT__EXERCISE = eINSTANCE.getConnectorElement_Exercise();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.WithElementImpl <em>With Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.WithElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getWithElement()
		 * @generated
		 */
		EClass WITH_ELEMENT = eINSTANCE.getWithElement();

		/**
		 * The meta object literal for the '<em><b>Cycle</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WITH_ELEMENT__CYCLE = eINSTANCE.getWithElement_Cycle();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.StructBlockElementImpl <em>Struct Block Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.StructBlockElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStructBlockElement()
		 * @generated
		 */
		EClass STRUCT_BLOCK_ELEMENT = eINSTANCE.getStructBlockElement();

		/**
		 * The meta object literal for the '<em><b>Struct Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRUCT_BLOCK_ELEMENT__STRUCT_TYPE = eINSTANCE.getStructBlockElement_StructType();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl <em>Expand Trans Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getExpandTransElement()
		 * @generated
		 */
		EClass EXPAND_TRANS_ELEMENT = eINSTANCE.getExpandTransElement();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPAND_TRANS_ELEMENT__SOURCE = eINSTANCE.getExpandTransElement_Source();

		/**
		 * The meta object literal for the '<em><b>Expand</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPAND_TRANS_ELEMENT__EXPAND = eINSTANCE.getExpandTransElement_Expand();

		/**
		 * The meta object literal for the '<em><b>Trans</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPAND_TRANS_ELEMENT__TRANS = eINSTANCE.getExpandTransElement_Trans();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.LineStyle <em>Line Style</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.LineStyle
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLineStyle()
		 * @generated
		 */
		EEnum LINE_STYLE = eINSTANCE.getLineStyle();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.LineEndPoint <em>Line End Point</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getLineEndPoint()
		 * @generated
		 */
		EEnum LINE_END_POINT = eINSTANCE.getLineEndPoint();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.RelationShip <em>Relation Ship</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.RelationShip
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getRelationShip()
		 * @generated
		 */
		EEnum RELATION_SHIP = eINSTANCE.getRelationShip();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.StateAttribute <em>State Attribute</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.StateAttribute
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateAttribute()
		 * @generated
		 */
		EEnum STATE_ATTRIBUTE = eINSTANCE.getStateAttribute();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.StateActionType <em>State Action Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.StateActionType
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStateActionType()
		 * @generated
		 */
		EEnum STATE_ACTION_TYPE = eINSTANCE.getStateActionType();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.Direction <em>Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.Direction
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getDirection()
		 * @generated
		 */
		EEnum DIRECTION = eINSTANCE.getDirection();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.FloatingLiteral <em>Floating Literal</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.FloatingLiteral
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getFloatingLiteral()
		 * @generated
		 */
		EEnum FLOATING_LITERAL = eINSTANCE.getFloatingLiteral();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.BooleanLiteral <em>Boolean Literal</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.BooleanLiteral
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getBooleanLiteral()
		 * @generated
		 */
		EEnum BOOLEAN_LITERAL = eINSTANCE.getBooleanLiteral();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.ReferAttribute <em>Refer Attribute</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.ReferAttribute
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getReferAttribute()
		 * @generated
		 */
		EEnum REFER_ATTRIBUTE = eINSTANCE.getReferAttribute();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.StructType <em>Struct Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.StructType
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getStructType()
		 * @generated
		 */
		EEnum STRUCT_TYPE = eINSTANCE.getStructType();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.JoinType <em>Join Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.JoinType
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getJoinType()
		 * @generated
		 */
		EEnum JOIN_TYPE = eINSTANCE.getJoinType();

		/**
		 * The meta object literal for the '{@link kr.re.etri.tpl.taskmodel.ConnectorType <em>Connector Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see kr.re.etri.tpl.taskmodel.ConnectorType
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getConnectorType()
		 * @generated
		 */
		EEnum CONNECTOR_TYPE = eINSTANCE.getConnectorType();

		/**
		 * The meta object literal for the '<em>Point</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.draw2d.geometry.Point
		 * @see kr.re.etri.tpl.taskmodel.impl.BehaviorModelPackageImpl#getPoint()
		 * @generated
		 */
		EDataType POINT = eINSTANCE.getPoint();

	}

} //TaskModelPackage
