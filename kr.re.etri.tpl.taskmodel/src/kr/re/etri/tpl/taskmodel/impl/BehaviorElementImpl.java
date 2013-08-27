/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
 * An implementation of the model object '<em><b>Task Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getStatements <em>Statements</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getParams <em>Params</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getStates <em>States</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getConstruct <em>Construct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getDestruct <em>Destruct</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.BehaviorElementImpl#getBifurcates <em>Bifurcates</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BehaviorElementImpl extends ItemElementImpl implements BehaviorElement {
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
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<StateElement> states;

	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected StateElement initialState;

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
	 * The cached value of the '{@link #getBifurcates() <em>Bifurcates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBifurcates()
	 * @generated
	 * @ordered
	 */
	protected EList<ExpandTransElement> bifurcates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BehaviorElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.TASK_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStatements() {
		if (statements == null) {
			statements = new EDataTypeUniqueEList<String>(String.class, this, TaskModelPackage.TASK_ELEMENT__STATEMENTS);
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
			params = new EObjectContainmentEList<Parameter>(Parameter.class, this, TaskModelPackage.TASK_ELEMENT__PARAMS);
		}
		return params;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateElement> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<StateElement>(StateElement.class, this, TaskModelPackage.TASK_ELEMENT__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateElement getInitialState() {
		if (initialState != null && initialState.eIsProxy()) {
			InternalEObject oldInitialState = (InternalEObject)initialState;
			initialState = (StateElement)eResolveProxy(oldInitialState);
			if (initialState != oldInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.TASK_ELEMENT__INITIAL_STATE, oldInitialState, initialState));
			}
		}
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateElement basicGetInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialState(StateElement newInitialState) {
		StateElement oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.TASK_ELEMENT__INITIAL_STATE, oldInitialState, initialState));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.TASK_ELEMENT__CONSTRUCT, oldConstruct, newConstruct);
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
				msgs = ((InternalEObject)construct).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.TASK_ELEMENT__CONSTRUCT, null, msgs);
			if (newConstruct != null)
				msgs = ((InternalEObject)newConstruct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.TASK_ELEMENT__CONSTRUCT, null, msgs);
			msgs = basicSetConstruct(newConstruct, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.TASK_ELEMENT__CONSTRUCT, newConstruct, newConstruct));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.TASK_ELEMENT__DESTRUCT, oldDestruct, newDestruct);
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
				msgs = ((InternalEObject)destruct).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.TASK_ELEMENT__DESTRUCT, null, msgs);
			if (newDestruct != null)
				msgs = ((InternalEObject)newDestruct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.TASK_ELEMENT__DESTRUCT, null, msgs);
			msgs = basicSetDestruct(newDestruct, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.TASK_ELEMENT__DESTRUCT, newDestruct, newDestruct));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExpandTransElement> getBifurcates() {
		if (bifurcates == null) {
			bifurcates = new EObjectContainmentEList<ExpandTransElement>(ExpandTransElement.class, this, TaskModelPackage.TASK_ELEMENT__BIFURCATES);
		}
		return bifurcates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
				return ((InternalEList<?>)getParams()).basicRemove(otherEnd, msgs);
			case TaskModelPackage.TASK_ELEMENT__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case TaskModelPackage.TASK_ELEMENT__CONSTRUCT:
				return basicSetConstruct(null, msgs);
			case TaskModelPackage.TASK_ELEMENT__DESTRUCT:
				return basicSetDestruct(null, msgs);
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
				return ((InternalEList<?>)getBifurcates()).basicRemove(otherEnd, msgs);
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
			case TaskModelPackage.TASK_ELEMENT__STATEMENTS:
				return getStatements();
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
				return getParams();
			case TaskModelPackage.TASK_ELEMENT__STATES:
				return getStates();
			case TaskModelPackage.TASK_ELEMENT__INITIAL_STATE:
				if (resolve) return getInitialState();
				return basicGetInitialState();
			case TaskModelPackage.TASK_ELEMENT__CONSTRUCT:
				return getConstruct();
			case TaskModelPackage.TASK_ELEMENT__DESTRUCT:
				return getDestruct();
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
				return getBifurcates();
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
			case TaskModelPackage.TASK_ELEMENT__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends String>)newValue);
				return;
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
				getParams().clear();
				getParams().addAll((Collection<? extends Parameter>)newValue);
				return;
			case TaskModelPackage.TASK_ELEMENT__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends StateElement>)newValue);
				return;
			case TaskModelPackage.TASK_ELEMENT__INITIAL_STATE:
				setInitialState((StateElement)newValue);
				return;
			case TaskModelPackage.TASK_ELEMENT__CONSTRUCT:
				setConstruct((StructBlockElement)newValue);
				return;
			case TaskModelPackage.TASK_ELEMENT__DESTRUCT:
				setDestruct((StructBlockElement)newValue);
				return;
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
				getBifurcates().clear();
				getBifurcates().addAll((Collection<? extends ExpandTransElement>)newValue);
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
			case TaskModelPackage.TASK_ELEMENT__STATEMENTS:
				getStatements().clear();
				return;
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
				getParams().clear();
				return;
			case TaskModelPackage.TASK_ELEMENT__STATES:
				getStates().clear();
				return;
			case TaskModelPackage.TASK_ELEMENT__INITIAL_STATE:
				setInitialState((StateElement)null);
				return;
			case TaskModelPackage.TASK_ELEMENT__CONSTRUCT:
				setConstruct((StructBlockElement)null);
				return;
			case TaskModelPackage.TASK_ELEMENT__DESTRUCT:
				setDestruct((StructBlockElement)null);
				return;
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
				getBifurcates().clear();
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
			case TaskModelPackage.TASK_ELEMENT__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
				return params != null && !params.isEmpty();
			case TaskModelPackage.TASK_ELEMENT__STATES:
				return states != null && !states.isEmpty();
			case TaskModelPackage.TASK_ELEMENT__INITIAL_STATE:
				return initialState != null;
			case TaskModelPackage.TASK_ELEMENT__CONSTRUCT:
				return construct != null;
			case TaskModelPackage.TASK_ELEMENT__DESTRUCT:
				return destruct != null;
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
				return bifurcates != null && !bifurcates.isEmpty();
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
				case TaskModelPackage.TASK_ELEMENT__STATEMENTS: return TaskModelPackage.BLOCK_ELEMENT__STATEMENTS;
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
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS: return TaskModelPackage.TASK_ELEMENT__STATEMENTS;
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
		result.append(')');
		return result.toString();
	}

} //TaskElementImpl
