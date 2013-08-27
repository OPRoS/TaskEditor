/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateActionImpl#getStatements <em>Statements</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateActionImpl#getStateActionType <em>State Action Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateActionImpl extends ItemElementImpl implements StateAction {
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
	 * The default value of the '{@link #getStateActionType() <em>State Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateActionType()
	 * @generated
	 * @ordered
	 */
	protected static final StateActionType STATE_ACTION_TYPE_EDEFAULT = StateActionType.ENTRY;

	/**
	 * The cached value of the '{@link #getStateActionType() <em>State Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateActionType()
	 * @generated
	 * @ordered
	 */
	protected StateActionType stateActionType = STATE_ACTION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.STATE_ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStatements() {
		if (statements == null) {
			statements = new EDataTypeUniqueEList<String>(String.class, this, TaskModelPackage.STATE_ACTION__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateActionType getStateActionType() {
		return stateActionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateActionType(StateActionType newStateActionType) {
		StateActionType oldStateActionType = stateActionType;
		stateActionType = newStateActionType == null ? STATE_ACTION_TYPE_EDEFAULT : newStateActionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ACTION__STATE_ACTION_TYPE, oldStateActionType, stateActionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				return getStatements();
			case TaskModelPackage.STATE_ACTION__STATE_ACTION_TYPE:
				return getStateActionType();
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
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends String>)newValue);
				return;
			case TaskModelPackage.STATE_ACTION__STATE_ACTION_TYPE:
				setStateActionType((StateActionType)newValue);
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
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				getStatements().clear();
				return;
			case TaskModelPackage.STATE_ACTION__STATE_ACTION_TYPE:
				setStateActionType(STATE_ACTION_TYPE_EDEFAULT);
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
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case TaskModelPackage.STATE_ACTION__STATE_ACTION_TYPE:
				return stateActionType != STATE_ACTION_TYPE_EDEFAULT;
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
				case TaskModelPackage.STATE_ACTION__STATEMENTS: return TaskModelPackage.BLOCK_ELEMENT__STATEMENTS;
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
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS: return TaskModelPackage.STATE_ACTION__STATEMENTS;
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
		result.append(", stateActionType: ");
		result.append(stateActionType);
		result.append(')');
		return result.toString();
	}

} //StateActionImpl
