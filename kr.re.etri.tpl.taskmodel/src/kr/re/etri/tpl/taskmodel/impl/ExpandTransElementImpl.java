/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expand Trans Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl#getSource <em>Source</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl#getExpand <em>Expand</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ExpandTransElementImpl#getTrans <em>Trans</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpandTransElementImpl extends ItemElementImpl implements ExpandTransElement {
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
	 * The cached value of the '{@link #getExpand() <em>Expand</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpand()
	 * @generated
	 * @ordered
	 */
	protected LinkedElement expand;

	/**
	 * The cached value of the '{@link #getTrans() <em>Trans</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrans()
	 * @generated
	 * @ordered
	 */
	protected LinkedElement trans;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpandTransElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.EXPAND_TRANS_ELEMENT;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.EXPAND_TRANS_ELEMENT__SOURCE, oldSource, source));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.EXPAND_TRANS_ELEMENT__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement getExpand() {
		if (expand != null && expand.eIsProxy()) {
			InternalEObject oldExpand = (InternalEObject)expand;
			expand = (LinkedElement)eResolveProxy(oldExpand);
			if (expand != oldExpand) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.EXPAND_TRANS_ELEMENT__EXPAND, oldExpand, expand));
			}
		}
		return expand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement basicGetExpand() {
		return expand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpand(LinkedElement newExpand) {
		LinkedElement oldExpand = expand;
		expand = newExpand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.EXPAND_TRANS_ELEMENT__EXPAND, oldExpand, expand));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement getTrans() {
		if (trans != null && trans.eIsProxy()) {
			InternalEObject oldTrans = (InternalEObject)trans;
			trans = (LinkedElement)eResolveProxy(oldTrans);
			if (trans != oldTrans) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.EXPAND_TRANS_ELEMENT__TRANS, oldTrans, trans));
			}
		}
		return trans;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkedElement basicGetTrans() {
		return trans;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrans(LinkedElement newTrans) {
		LinkedElement oldTrans = trans;
		trans = newTrans;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.EXPAND_TRANS_ELEMENT__TRANS, oldTrans, trans));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__EXPAND:
				if (resolve) return getExpand();
				return basicGetExpand();
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__TRANS:
				if (resolve) return getTrans();
				return basicGetTrans();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__SOURCE:
				setSource((LinkedElement)newValue);
				return;
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__EXPAND:
				setExpand((LinkedElement)newValue);
				return;
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__TRANS:
				setTrans((LinkedElement)newValue);
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
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__SOURCE:
				setSource((LinkedElement)null);
				return;
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__EXPAND:
				setExpand((LinkedElement)null);
				return;
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__TRANS:
				setTrans((LinkedElement)null);
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
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__SOURCE:
				return source != null;
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__EXPAND:
				return expand != null;
			case TaskModelPackage.EXPAND_TRANS_ELEMENT__TRANS:
				return trans != null;
		}
		return super.eIsSet(featureID);
	}

} //ExpandTransElementImpl
