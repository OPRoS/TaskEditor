/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.JoinType;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getStatements <em>Statements</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getParams <em>Params</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getWiths <em>Withs</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getJoinType <em>Join Type</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getConType <em>Con Type</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getConstruct <em>Construct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getDestruct <em>Destruct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectorElementImpl#getExercise <em>Exercise</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectorElementImpl extends ItemElementImpl implements ConnectorElement {
	/**
	 * The cached value of the '{@link #getStatements() <em>Statements</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<String> statements;

	/**
	 * The cached value of the '{@link #getParams() <em>Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParams()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> params;

	/**
	 * The cached value of the '{@link #getWiths() <em>Withs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWiths()
	 * @generated
	 * @ordered
	 */
	protected EList<WithElement> withs;

	/**
	 * The default value of the '{@link #getJoinType() <em>Join Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinType()
	 * @generated
	 * @ordered
	 */
	protected static final JoinType JOIN_TYPE_EDEFAULT = JoinType.ANDJOIN;

	/**
	 * The cached value of the '{@link #getJoinType() <em>Join Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinType()
	 * @generated
	 * @ordered
	 */
	protected JoinType joinType = JOIN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getConType() <em>Con Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConType()
	 * @generated
	 * @ordered
	 */
	protected static final ConnectorType CON_TYPE_EDEFAULT = ConnectorType.CONEXER;

	/**
	 * The cached value of the '{@link #getConType() <em>Con Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConType()
	 * @generated
	 * @ordered
	 */
	protected ConnectorType conType = CON_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConstruct() <em>Construct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstruct()
	 * @generated
	 * @ordered
	 */
	protected StructBlockElement construct;

	/**
	 * The cached value of the '{@link #getDestruct() <em>Destruct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestruct()
	 * @generated
	 * @ordered
	 */
	protected StructBlockElement destruct;

	/**
	 * The cached value of the '{@link #getExercise() <em>Exercise</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExercise()
	 * @generated
	 * @ordered
	 */
	protected StructBlockElement exercise;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectorElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.CONNECTOR_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStatements() {
		if (statements == null) {
			statements = new EDataTypeUniqueEList<String>(String.class, this, TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParams() {
		if (params == null) {
			params = new EObjectContainmentEList<Parameter>(Parameter.class, this, TaskModelPackage.CONNECTOR_ELEMENT__PARAMS);
		}
		return params;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WithElement> getWiths() {
		if (withs == null) {
			withs = new EObjectContainmentEList<WithElement>(WithElement.class, this, TaskModelPackage.CONNECTOR_ELEMENT__WITHS);
		}
		return withs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JoinType getJoinType() {
		return joinType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinType(JoinType newJoinType) {
		JoinType oldJoinType = joinType;
		joinType = newJoinType == null ? JOIN_TYPE_EDEFAULT : newJoinType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE, oldJoinType, joinType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorType getConType() {
		return conType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConType(ConnectorType newConType) {
		ConnectorType oldConType = conType;
		conType = newConType == null ? CON_TYPE_EDEFAULT : newConType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__CON_TYPE, oldConType, conType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement getConstruct() {
		return construct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstruct(StructBlockElement newConstruct, NotificationChain msgs) {
		StructBlockElement oldConstruct = construct;
		construct = newConstruct;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT, oldConstruct, newConstruct);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstruct(StructBlockElement newConstruct) {
		if (newConstruct != construct) {
			NotificationChain msgs = null;
			if (construct != null)
				msgs = ((InternalEObject)construct).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT, null, msgs);
			if (newConstruct != null)
				msgs = ((InternalEObject)newConstruct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT, null, msgs);
			msgs = basicSetConstruct(newConstruct, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT, newConstruct, newConstruct));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement getDestruct() {
		return destruct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestruct(StructBlockElement newDestruct, NotificationChain msgs) {
		StructBlockElement oldDestruct = destruct;
		destruct = newDestruct;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT, oldDestruct, newDestruct);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestruct(StructBlockElement newDestruct) {
		if (newDestruct != destruct) {
			NotificationChain msgs = null;
			if (destruct != null)
				msgs = ((InternalEObject)destruct).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT, null, msgs);
			if (newDestruct != null)
				msgs = ((InternalEObject)newDestruct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT, null, msgs);
			msgs = basicSetDestruct(newDestruct, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT, newDestruct, newDestruct));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement getExercise() {
		if (exercise != null && exercise.eIsProxy()) {
			InternalEObject oldExercise = (InternalEObject)exercise;
			exercise = (StructBlockElement)eResolveProxy(oldExercise);
			if (exercise != oldExercise) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.CONNECTOR_ELEMENT__EXERCISE, oldExercise, exercise));
			}
		}
		return exercise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement basicGetExercise() {
		return exercise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExercise(StructBlockElement newExercise) {
		StructBlockElement oldExercise = exercise;
		exercise = newExercise;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTOR_ELEMENT__EXERCISE, oldExercise, exercise));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
				return ((InternalEList<?>)getParams()).basicRemove(otherEnd, msgs);
			case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
				return ((InternalEList<?>)getWiths()).basicRemove(otherEnd, msgs);
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
				return basicSetConstruct(null, msgs);
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				return basicSetDestruct(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS:
				return getStatements();
			case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
				return getParams();
			case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
				return getWiths();
			case TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE:
				return getJoinType();
			case TaskModelPackage.CONNECTOR_ELEMENT__CON_TYPE:
				return getConType();
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
				return getConstruct();
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				return getDestruct();
			case TaskModelPackage.CONNECTOR_ELEMENT__EXERCISE:
				if (resolve) return getExercise();
				return basicGetExercise();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends String>)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
				getParams().clear();
				getParams().addAll((Collection<? extends Parameter>)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
				getWiths().clear();
				getWiths().addAll((Collection<? extends WithElement>)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE:
				setJoinType((JoinType)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__CON_TYPE:
				setConType((ConnectorType)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
				setConstruct((StructBlockElement)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				setDestruct((StructBlockElement)newValue);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__EXERCISE:
				setExercise((StructBlockElement)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS:
				getStatements().clear();
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
				getParams().clear();
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
				getWiths().clear();
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE:
				setJoinType(JOIN_TYPE_EDEFAULT);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__CON_TYPE:
				setConType(CON_TYPE_EDEFAULT);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
				setConstruct((StructBlockElement)null);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				setDestruct((StructBlockElement)null);
				return;
			case TaskModelPackage.CONNECTOR_ELEMENT__EXERCISE:
				setExercise((StructBlockElement)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
				return params != null && !params.isEmpty();
			case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
				return withs != null && !withs.isEmpty();
			case TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE:
				return joinType != JOIN_TYPE_EDEFAULT;
			case TaskModelPackage.CONNECTOR_ELEMENT__CON_TYPE:
				return conType != CON_TYPE_EDEFAULT;
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
				return construct != null;
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				return destruct != null;
			case TaskModelPackage.CONNECTOR_ELEMENT__EXERCISE:
				return exercise != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == BlockElement.class) {
			switch (derivedFeatureID) {
				case TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS: return TaskModelPackage.BLOCK_ELEMENT__STATEMENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == BlockElement.class) {
			switch (baseFeatureID) {
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS: return TaskModelPackage.CONNECTOR_ELEMENT__STATEMENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (statements: ");
		result.append(statements);
		result.append(", joinType: ");
		result.append(joinType);
		result.append(", conType: ");
		result.append(conType);
		result.append(')');
		return result.toString();
	}

} //ConnectorElementImpl
