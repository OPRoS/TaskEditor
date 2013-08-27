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
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#isVisible <em>Visible</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#getSubDiagram <em>Sub Diagram</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#getReferences <em>References</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ItemElementImpl#getItemState <em>Item State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemElementImpl extends EObjectImpl implements ItemElement {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected ItemElement parent;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubDiagram() <em>Sub Diagram</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubDiagram()
	 * @generated
	 * @ordered
	 */
	protected SubDiagram subDiagram;

	/**
	 * The cached value of the '{@link #getReferences() <em>References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<ReferElement> references;

	/**
	 * The default value of the '{@link #getItemState() <em>Item State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItemState()
	 * @generated
	 * @ordered
	 */
	protected static final int ITEM_STATE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getItemState() <em>Item State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItemState()
	 * @generated
	 * @ordered
	 */
	protected int itemState = ITEM_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ItemElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.ITEM_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (ItemElement)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.ITEM_ELEMENT__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(ItemElement newParent) {
		ItemElement oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisible(boolean newVisible) {
		boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__VISIBLE, oldVisible, visible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubDiagram getSubDiagram() {
		return subDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubDiagram(SubDiagram newSubDiagram, NotificationChain msgs) {
		SubDiagram oldSubDiagram = subDiagram;
		subDiagram = newSubDiagram;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM, oldSubDiagram, newSubDiagram);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubDiagram(SubDiagram newSubDiagram) {
		if (newSubDiagram != subDiagram) {
			NotificationChain msgs = null;
			if (subDiagram != null)
				msgs = ((InternalEObject)subDiagram).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM, null, msgs);
			if (newSubDiagram != null)
				msgs = ((InternalEObject)newSubDiagram).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM, null, msgs);
			msgs = basicSetSubDiagram(newSubDiagram, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM, newSubDiagram, newSubDiagram));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ReferElement> getReferences() {
		if (references == null) {
			references = new EObjectResolvingEList<ReferElement>(ReferElement.class, this, TaskModelPackage.ITEM_ELEMENT__REFERENCES);
		}
		return references;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getItemState() {
		return itemState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItemState(int newItemState) {
		int oldItemState = itemState;
		itemState = newItemState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.ITEM_ELEMENT__ITEM_STATE, oldItemState, itemState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIncluded() {
		// KJH 20101130 s, modify
		ItemElement parent = getParent();
		if(parent == null) {
			return false;
		}
		
		while(parent != null) {
			if(parent instanceof IncludedElement) {
				return true;
			}
			parent = parent.getParent();
		}
		
		return false;
		// KJH 20101130 e, modify
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM:
				return basicSetSubDiagram(null, msgs);
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
			case TaskModelPackage.ITEM_ELEMENT__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				return getName();
			case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
				return getDescription();
			case TaskModelPackage.ITEM_ELEMENT__VISIBLE:
				return isVisible();
			case TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM:
				return getSubDiagram();
			case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
				return getReferences();
			case TaskModelPackage.ITEM_ELEMENT__ITEM_STATE:
				return getItemState();
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
			case TaskModelPackage.ITEM_ELEMENT__PARENT:
				setParent((ItemElement)newValue);
				return;
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TaskModelPackage.ITEM_ELEMENT__VISIBLE:
				setVisible((Boolean)newValue);
				return;
			case TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM:
				setSubDiagram((SubDiagram)newValue);
				return;
			case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
				getReferences().clear();
				getReferences().addAll((Collection<? extends ReferElement>)newValue);
				return;
			case TaskModelPackage.ITEM_ELEMENT__ITEM_STATE:
				setItemState((Integer)newValue);
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
			case TaskModelPackage.ITEM_ELEMENT__PARENT:
				setParent((ItemElement)null);
				return;
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TaskModelPackage.ITEM_ELEMENT__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
				return;
			case TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM:
				setSubDiagram((SubDiagram)null);
				return;
			case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
				getReferences().clear();
				return;
			case TaskModelPackage.ITEM_ELEMENT__ITEM_STATE:
				setItemState(ITEM_STATE_EDEFAULT);
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
			case TaskModelPackage.ITEM_ELEMENT__PARENT:
				return parent != null;
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TaskModelPackage.ITEM_ELEMENT__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case TaskModelPackage.ITEM_ELEMENT__SUB_DIAGRAM:
				return subDiagram != null;
			case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
				return references != null && !references.isEmpty();
			case TaskModelPackage.ITEM_ELEMENT__ITEM_STATE:
				return itemState != ITEM_STATE_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", visible: ");
		result.append(visible);
		result.append(", itemState: ");
		result.append(itemState);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean equals(Object obj) {	// KJH 20110905
		if (super.equals(obj)) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		if (this.getClass().equals(obj.getClass()) == false) {
			return false;
		}
		
		ItemElement objItem = (ItemElement)obj;
		String s1 = this.getName();
		String s2 = objItem.getName();

		if (s1 == null || s1.equals(s2) == false) {
			return false;
		}

		ItemElement p1 = this.getParent();
		ItemElement p2 = objItem.getParent();
		if (p1 != null) {
			return p1.equals(p2);
		}

		return false;
	}

} //ItemElementImpl
