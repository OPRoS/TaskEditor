/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl#getSource <em>Source</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl#getSource2 <em>Source2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl#getTarget2 <em>Target2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl#getRelationship <em>Relationship</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ConnectionElementImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectionElementImpl extends LineElementImpl implements ConnectionElement {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected LinkedElement source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected LinkedElement target;

	/**
	 * The cached value of the '{@link #getSource2() <em>Source2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource2()
	 * @generated
	 * @ordered
	 */
	protected LinkedElement source2;

	/**
	 * The cached value of the '{@link #getTarget2() <em>Target2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget2()
	 * @generated
	 * @ordered
	 */
	protected LinkedElement target2;

	/**
	 * The default value of the '{@link #getRelationship() <em>Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationship()
	 * @generated
	 * @ordered
	 */
	protected static final RelationShip RELATIONSHIP_EDEFAULT = RelationShip.TRANSITION;

	/**
	 * The cached value of the '{@link #getRelationship() <em>Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationship()
	 * @generated
	 * @ordered
	 */
	protected RelationShip relationship = RELATIONSHIP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected EList<String> condition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.CONNECTION_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (LinkedElement)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.CONNECTION_ELEMENT__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(LinkedElement newSource) {
		LinkedElement oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTION_ELEMENT__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (LinkedElement)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.CONNECTION_ELEMENT__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(LinkedElement newTarget) {
		LinkedElement oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTION_ELEMENT__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement getSource2() {
		if (source2 != null && source2.eIsProxy()) {
			InternalEObject oldSource2 = (InternalEObject)source2;
			source2 = (LinkedElement)eResolveProxy(oldSource2);
			if (source2 != oldSource2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.CONNECTION_ELEMENT__SOURCE2, oldSource2, source2));
			}
		}
		return source2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement basicGetSource2() {
		return source2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource2(LinkedElement newSource2) {
		LinkedElement oldSource2 = source2;
		source2 = newSource2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTION_ELEMENT__SOURCE2, oldSource2, source2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement getTarget2() {
		if (target2 != null && target2.eIsProxy()) {
			InternalEObject oldTarget2 = (InternalEObject)target2;
			target2 = (LinkedElement)eResolveProxy(oldTarget2);
			if (target2 != oldTarget2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.CONNECTION_ELEMENT__TARGET2, oldTarget2, target2));
			}
		}
		return target2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement basicGetTarget2() {
		return target2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget2(LinkedElement newTarget2) {
		LinkedElement oldTarget2 = target2;
		target2 = newTarget2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTION_ELEMENT__TARGET2, oldTarget2, target2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationShip getRelationship() {
		return relationship;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelationship(RelationShip newRelationship) {
		RelationShip oldRelationship = relationship;
		relationship = newRelationship == null ? RELATIONSHIP_EDEFAULT : newRelationship;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP, oldRelationship, relationship));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getCondition() {
		if (condition == null) {
			condition = new EDataTypeUniqueEList<String>(String.class, this, TaskModelPackage.CONNECTION_ELEMENT__CONDITION);
		}
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
				if (resolve) return getSource2();
				return basicGetSource2();
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET2:
				if (resolve) return getTarget2();
				return basicGetTarget2();
			case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
				return getRelationship();
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				return getCondition();
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
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
				setSource((LinkedElement)newValue);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
				setTarget((LinkedElement)newValue);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
				setSource2((LinkedElement)newValue);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET2:
				setTarget2((LinkedElement)newValue);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
				setRelationship((RelationShip)newValue);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				getCondition().clear();
				getCondition().addAll((Collection<? extends String>)newValue);
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
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
				setSource((LinkedElement)null);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
				setTarget((LinkedElement)null);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
				setSource2((LinkedElement)null);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET2:
				setTarget2((LinkedElement)null);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
				setRelationship(RELATIONSHIP_EDEFAULT);
				return;
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				getCondition().clear();
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
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
				return source != null;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
				return target != null;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
				return source2 != null;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET2:
				return target2 != null;
			case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
				return relationship != RELATIONSHIP_EDEFAULT;
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				return condition != null && !condition.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (relationship: ");
		result.append(relationship);
		result.append(", condition: ");
		result.append(condition);
		result.append(')');
		return result.toString();
	}

} //ConnectionElementImpl
