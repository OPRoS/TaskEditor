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
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
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
 * An implementation of the model object '<em><b>State Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl#getStatements <em>Statements</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl#getStay <em>Stay</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl#getExit <em>Exit</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.StateElementImpl#getBifurcates <em>Bifurcates</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateElementImpl extends ItemElementImpl implements StateElement {
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
	 * The default value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final StateAttribute ATTRIBUTE_EDEFAULT = StateAttribute.NORMAL;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected StateAttribute attribute = ATTRIBUTE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected StateAction entry;

	/**
	 * The cached value of the '{@link #getStay() <em>Stay</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStay()
	 * @generated
	 * @ordered
	 */
	protected StateAction stay;

	/**
	 * The cached value of the '{@link #getExit() <em>Exit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExit()
	 * @generated
	 * @ordered
	 */
	protected StateAction exit;

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
	protected StateElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.STATE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStatements() {
		if (statements == null) {
			statements = new EDataTypeUniqueEList<String>(String.class, this, TaskModelPackage.STATE_ELEMENT__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAttribute getAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(StateAttribute newAttribute) {
		StateAttribute oldAttribute = attribute;
		attribute = newAttribute == null ? ATTRIBUTE_EDEFAULT : newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAction getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntry(StateAction newEntry, NotificationChain msgs) {
		StateAction oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__ENTRY, oldEntry, newEntry);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(StateAction newEntry) {
		if (newEntry != entry) {
			NotificationChain msgs = null;
			if (entry != null)
				msgs = ((InternalEObject)entry).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.STATE_ELEMENT__ENTRY, null, msgs);
			if (newEntry != null)
				msgs = ((InternalEObject)newEntry).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.STATE_ELEMENT__ENTRY, null, msgs);
			msgs = basicSetEntry(newEntry, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__ENTRY, newEntry, newEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAction getStay() {
		return stay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStay(StateAction newStay, NotificationChain msgs) {
		StateAction oldStay = stay;
		stay = newStay;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__STAY, oldStay, newStay);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStay(StateAction newStay) {
		if (newStay != stay) {
			NotificationChain msgs = null;
			if (stay != null)
				msgs = ((InternalEObject)stay).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.STATE_ELEMENT__STAY, null, msgs);
			if (newStay != null)
				msgs = ((InternalEObject)newStay).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.STATE_ELEMENT__STAY, null, msgs);
			msgs = basicSetStay(newStay, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__STAY, newStay, newStay));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAction getExit() {
		return exit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExit(StateAction newExit, NotificationChain msgs) {
		StateAction oldExit = exit;
		exit = newExit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__EXIT, oldExit, newExit);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExit(StateAction newExit) {
		if (newExit != exit) {
			NotificationChain msgs = null;
			if (exit != null)
				msgs = ((InternalEObject)exit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.STATE_ELEMENT__EXIT, null, msgs);
			if (newExit != null)
				msgs = ((InternalEObject)newExit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.STATE_ELEMENT__EXIT, null, msgs);
			msgs = basicSetExit(newExit, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.STATE_ELEMENT__EXIT, newExit, newExit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExpandTransElement> getBifurcates() {
		if (bifurcates == null) {
			bifurcates = new EObjectContainmentEList<ExpandTransElement>(ExpandTransElement.class, this, TaskModelPackage.STATE_ELEMENT__BIFURCATES);
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
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				return basicSetEntry(null, msgs);
			case TaskModelPackage.STATE_ELEMENT__STAY:
				return basicSetStay(null, msgs);
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				return basicSetExit(null, msgs);
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
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
			case TaskModelPackage.STATE_ELEMENT__STATEMENTS:
				return getStatements();
			case TaskModelPackage.STATE_ELEMENT__ATTRIBUTE:
				return getAttribute();
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				return getEntry();
			case TaskModelPackage.STATE_ELEMENT__STAY:
				return getStay();
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				return getExit();
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
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
			case TaskModelPackage.STATE_ELEMENT__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends String>)newValue);
				return;
			case TaskModelPackage.STATE_ELEMENT__ATTRIBUTE:
				setAttribute((StateAttribute)newValue);
				return;
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				setEntry((StateAction)newValue);
				return;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				setStay((StateAction)newValue);
				return;
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				setExit((StateAction)newValue);
				return;
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
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
			case TaskModelPackage.STATE_ELEMENT__STATEMENTS:
				getStatements().clear();
				return;
			case TaskModelPackage.STATE_ELEMENT__ATTRIBUTE:
				setAttribute(ATTRIBUTE_EDEFAULT);
				return;
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				setEntry((StateAction)null);
				return;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				setStay((StateAction)null);
				return;
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				setExit((StateAction)null);
				return;
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
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
			case TaskModelPackage.STATE_ELEMENT__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case TaskModelPackage.STATE_ELEMENT__ATTRIBUTE:
				return attribute != ATTRIBUTE_EDEFAULT;
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				return entry != null;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				return stay != null;
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				return exit != null;
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
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
				case TaskModelPackage.STATE_ELEMENT__STATEMENTS: return TaskModelPackage.BLOCK_ELEMENT__STATEMENTS;
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
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS: return TaskModelPackage.STATE_ELEMENT__STATEMENTS;
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
		result.append(", attribute: ");
		result.append(attribute);
		result.append(')');
		return result.toString();
	}

} //StateElementImpl
