/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
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
 * An implementation of the model object '<em><b>Refer Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ReferElementImpl#getRealModel <em>Real Model</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ReferElementImpl#getItems <em>Items</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ReferElementImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ReferElementImpl#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferElementImpl extends LinkedElementImpl implements ReferElement {
	/**
	 * The cached value of the '{@link #getRealModel() <em>Real Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealModel()
	 * @generated
	 * @ordered
	 */
	protected ItemElement realModel;

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
	 * The default value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final ReferAttribute ATTRIBUTE_EDEFAULT = ReferAttribute.NORMAL;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected ReferAttribute attribute = ATTRIBUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.REFER_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement getRealModel() {
		if (realModel != null && realModel.eIsProxy()) {
			InternalEObject oldRealModel = (InternalEObject)realModel;
			realModel = (ItemElement)eResolveProxy(oldRealModel);
			if (realModel != oldRealModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.REFER_ELEMENT__REAL_MODEL, oldRealModel, realModel));
			}
		}
		return realModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement basicGetRealModel() {
		return realModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRealModel(ItemElement newRealModel) {
		ItemElement oldRealModel = realModel;
		realModel = newRealModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.REFER_ELEMENT__REAL_MODEL, oldRealModel, realModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ItemElement> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<ItemElement>(ItemElement.class, this, TaskModelPackage.REFER_ELEMENT__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferAttribute getAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(ReferAttribute newAttribute) {
		ReferAttribute oldAttribute = attribute;
		attribute = newAttribute == null ? ATTRIBUTE_EDEFAULT : newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.REFER_ELEMENT__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.REFER_ELEMENT__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
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
			case TaskModelPackage.REFER_ELEMENT__REAL_MODEL:
				if (resolve) return getRealModel();
				return basicGetRealModel();
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				return getItems();
			case TaskModelPackage.REFER_ELEMENT__ATTRIBUTE:
				return getAttribute();
			case TaskModelPackage.REFER_ELEMENT__PATH:
				return getPath();
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
			case TaskModelPackage.REFER_ELEMENT__REAL_MODEL:
				setRealModel((ItemElement)newValue);
				return;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends ItemElement>)newValue);
				return;
			case TaskModelPackage.REFER_ELEMENT__ATTRIBUTE:
				setAttribute((ReferAttribute)newValue);
				return;
			case TaskModelPackage.REFER_ELEMENT__PATH:
				setPath((String)newValue);
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
			case TaskModelPackage.REFER_ELEMENT__REAL_MODEL:
				setRealModel((ItemElement)null);
				return;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				getItems().clear();
				return;
			case TaskModelPackage.REFER_ELEMENT__ATTRIBUTE:
				setAttribute(ATTRIBUTE_EDEFAULT);
				return;
			case TaskModelPackage.REFER_ELEMENT__PATH:
				setPath(PATH_EDEFAULT);
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
			case TaskModelPackage.REFER_ELEMENT__REAL_MODEL:
				return realModel != null;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				return items != null && !items.isEmpty();
			case TaskModelPackage.REFER_ELEMENT__ATTRIBUTE:
				return attribute != ATTRIBUTE_EDEFAULT;
			case TaskModelPackage.REFER_ELEMENT__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
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
		result.append(" (attribute: ");
		result.append(attribute);
		result.append(", path: ");
		result.append(path);
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
		return this == obj;
	}
	
	// KJH 20101130 s, override
	@Override
	public String getName() {
		ItemElement realItem = getRealModel();
		if (realItem != null) {
			return realItem.getName();
		}
		
		return super.getName();
	}

	@Override
	public void setName(String newName) {
		ItemElement realItem = getRealModel();
		if (realItem != null) {
			realItem.setName(newName);
			return;
		}
		super.setName(newName);
	}

	@Override
	public String getDescription() {
		ItemElement realItem = getRealModel();
		if(realItem == null) {
			return null;
		}
		
		return realItem.getDescription();
	}

	@Override
	public void setDescription(String newDescription) {
		ItemElement realItem = getRealModel();
		if(realItem != null) {
			realItem.setDescription(newDescription);
			return;
		}
		
		super.setDescription(newDescription);
	}
	// KJH 20101130 e, override
	
} //ReferElementImpl
