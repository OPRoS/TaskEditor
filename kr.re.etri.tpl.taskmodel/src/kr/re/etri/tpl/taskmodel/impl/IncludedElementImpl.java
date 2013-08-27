/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Included Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.IncludedElementImpl#getIncludePath <em>Include Path</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.IncludedElementImpl#getItems <em>Items</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IncludedElementImpl extends ItemElementImpl implements IncludedElement {
	/**
	 * The default value of the '{@link #getIncludePath() <em>Include Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludePath()
	 * @generated
	 * @ordered
	 */
	protected static final String INCLUDE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncludePath() <em>Include Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludePath()
	 * @generated
	 * @ordered
	 */
	protected String includePath = INCLUDE_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<ItemElement> items;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncludedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.INCLUDED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIncludePath() {
		return includePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncludePath(String newIncludePath) {
		String oldIncludePath = includePath;
		includePath = newIncludePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH, oldIncludePath, includePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ItemElement> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<ItemElement>(ItemElement.class, this, TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.INCLUDED_ELEMENT__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
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
			case TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH:
				return getIncludePath();
			case TaskModelPackage.INCLUDED_ELEMENT__ITEMS:
				return getItems();
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
			case TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH:
				setIncludePath((String)newValue);
				return;
			case TaskModelPackage.INCLUDED_ELEMENT__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends ItemElement>)newValue);
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
			case TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH:
				setIncludePath(INCLUDE_PATH_EDEFAULT);
				return;
			case TaskModelPackage.INCLUDED_ELEMENT__ITEMS:
				getItems().clear();
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
			case TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH:
				return INCLUDE_PATH_EDEFAULT == null ? includePath != null : !INCLUDE_PATH_EDEFAULT.equals(includePath);
			case TaskModelPackage.INCLUDED_ELEMENT__ITEMS:
				return items != null && !items.isEmpty();
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
		result.append(" (includePath: ");
		result.append(includePath);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean equals(Object obj) {	// KJH 20110930
		if (super.equals(obj)) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		if (this.getClass().equals(obj.getClass()) == false) {
			return false;
		}
		
		IncludedElement objItem = (IncludedElement)obj;
		String s1 = this.getIncludePath();
		String s2 = objItem.getIncludePath();
		return (s1 != null && s1.equals(s2));
	}
	
} //IncludedElementImpl
