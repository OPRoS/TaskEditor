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
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Linked Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.LinkedElementImpl#getSourceConnections <em>Source Connections</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.LinkedElementImpl#getTargetConnections <em>Target Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkedElementImpl extends ShapeElementImpl implements LinkedElement {
	/**
	 * The cached value of the '{@link #getSourceConnections() <em>Source Connections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<ConnectionElement> sourceConnections;

	/**
	 * The cached value of the '{@link #getTargetConnections() <em>Target Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<ConnectionElement> targetConnections;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.LINKED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConnectionElement> getSourceConnections() {
		if (sourceConnections == null) {
			sourceConnections = new EObjectContainmentEList<ConnectionElement>(ConnectionElement.class, this, TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS);
		}
		return sourceConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConnectionElement> getTargetConnections() {
		if (targetConnections == null) {
			targetConnections = new EObjectResolvingEList<ConnectionElement>(ConnectionElement.class, this, TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS);
		}
		return targetConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				return ((InternalEList<?>)getSourceConnections()).basicRemove(otherEnd, msgs);
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
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				return getSourceConnections();
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				return getTargetConnections();
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
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				getSourceConnections().clear();
				getSourceConnections().addAll((Collection<? extends ConnectionElement>)newValue);
				return;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				getTargetConnections().clear();
				getTargetConnections().addAll((Collection<? extends ConnectionElement>)newValue);
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
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				getSourceConnections().clear();
				return;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				getTargetConnections().clear();
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
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				return sourceConnections != null && !sourceConnections.isEmpty();
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				return targetConnections != null && !targetConnections.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LinkedElementImpl
