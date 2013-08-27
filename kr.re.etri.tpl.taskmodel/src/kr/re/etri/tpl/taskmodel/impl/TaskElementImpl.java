/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.TaskElement;

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
 * An implementation of the model object '<em><b>Worker Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl#getStatements <em>Statements</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl#getInitialTask <em>Initial Task</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl#getItems <em>Items</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl#getInitialize <em>Initialize</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl#getFinalize <em>Finalize</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.TaskElementImpl#getRun <em>Run</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskElementImpl extends ItemElementImpl implements TaskElement {
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
	 * The cached value of the '{@link #getInitialTask() <em>Initial Task</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialTask()
	 * @generated
	 * @ordered
	 */
	protected ItemElement initialTask;

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
	 * The cached value of the '{@link #getInitialize() <em>Initialize</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialize()
	 * @generated
	 * @ordered
	 */
	protected StructBlockElement initialize;

	/**
	 * The cached value of the '{@link #getFinalize() <em>Finalize</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFinalize()
	 * @generated
	 * @ordered
	 */
	protected StructBlockElement finalize;

	/**
	 * The cached value of the '{@link #getRun() <em>Run</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRun()
	 * @generated
	 * @ordered
	 */
	protected StructBlockElement run;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.WORKER_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStatements() {
		if (statements == null) {
			statements = new EDataTypeUniqueEList<String>(String.class, this, TaskModelPackage.WORKER_ELEMENT__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement getInitialTask() {
		if (initialTask != null && initialTask.eIsProxy()) {
			InternalEObject oldInitialTask = (InternalEObject)initialTask;
			initialTask = (ItemElement)eResolveProxy(oldInitialTask);
			if (initialTask != oldInitialTask) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK, oldInitialTask, initialTask));
			}
		}
		return initialTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemElement basicGetInitialTask() {
		return initialTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialTask(ItemElement newInitialTask) {
		ItemElement oldInitialTask = initialTask;
		initialTask = newInitialTask;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK, oldInitialTask, initialTask));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ItemElement> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<ItemElement>(ItemElement.class, this, TaskModelPackage.WORKER_ELEMENT__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement getInitialize() {
		return initialize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialize(StructBlockElement newInitialize, NotificationChain msgs) {
		StructBlockElement oldInitialize = initialize;
		initialize = newInitialize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__INITIALIZE, oldInitialize, newInitialize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialize(StructBlockElement newInitialize) {
		if (newInitialize != initialize) {
			NotificationChain msgs = null;
			if (initialize != null)
				msgs = ((InternalEObject)initialize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.WORKER_ELEMENT__INITIALIZE, null, msgs);
			if (newInitialize != null)
				msgs = ((InternalEObject)newInitialize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.WORKER_ELEMENT__INITIALIZE, null, msgs);
			msgs = basicSetInitialize(newInitialize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__INITIALIZE, newInitialize, newInitialize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement getFinalize() {
		return finalize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFinalize(StructBlockElement newFinalize, NotificationChain msgs) {
		StructBlockElement oldFinalize = finalize;
		finalize = newFinalize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__FINALIZE, oldFinalize, newFinalize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFinalize(StructBlockElement newFinalize) {
		if (newFinalize != finalize) {
			NotificationChain msgs = null;
			if (finalize != null)
				msgs = ((InternalEObject)finalize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.WORKER_ELEMENT__FINALIZE, null, msgs);
			if (newFinalize != null)
				msgs = ((InternalEObject)newFinalize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.WORKER_ELEMENT__FINALIZE, null, msgs);
			msgs = basicSetFinalize(newFinalize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__FINALIZE, newFinalize, newFinalize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructBlockElement getRun() {
		return run;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRun(StructBlockElement newRun, NotificationChain msgs) {
		StructBlockElement oldRun = run;
		run = newRun;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__RUN, oldRun, newRun);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRun(StructBlockElement newRun) {
		if (newRun != run) {
			NotificationChain msgs = null;
			if (run != null)
				msgs = ((InternalEObject)run).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.WORKER_ELEMENT__RUN, null, msgs);
			if (newRun != null)
				msgs = ((InternalEObject)newRun).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TaskModelPackage.WORKER_ELEMENT__RUN, null, msgs);
			msgs = basicSetRun(newRun, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.WORKER_ELEMENT__RUN, newRun, newRun));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
			case TaskModelPackage.WORKER_ELEMENT__INITIALIZE:
				return basicSetInitialize(null, msgs);
			case TaskModelPackage.WORKER_ELEMENT__FINALIZE:
				return basicSetFinalize(null, msgs);
			case TaskModelPackage.WORKER_ELEMENT__RUN:
				return basicSetRun(null, msgs);
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
			case TaskModelPackage.WORKER_ELEMENT__STATEMENTS:
				return getStatements();
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				if (resolve) return getInitialTask();
				return basicGetInitialTask();
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
				return getItems();
			case TaskModelPackage.WORKER_ELEMENT__INITIALIZE:
				return getInitialize();
			case TaskModelPackage.WORKER_ELEMENT__FINALIZE:
				return getFinalize();
			case TaskModelPackage.WORKER_ELEMENT__RUN:
				return getRun();
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
			case TaskModelPackage.WORKER_ELEMENT__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends String>)newValue);
				return;
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				setInitialTask((ItemElement)newValue);
				return;
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends ItemElement>)newValue);
				return;
			case TaskModelPackage.WORKER_ELEMENT__INITIALIZE:
				setInitialize((StructBlockElement)newValue);
				return;
			case TaskModelPackage.WORKER_ELEMENT__FINALIZE:
				setFinalize((StructBlockElement)newValue);
				return;
			case TaskModelPackage.WORKER_ELEMENT__RUN:
				setRun((StructBlockElement)newValue);
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
			case TaskModelPackage.WORKER_ELEMENT__STATEMENTS:
				getStatements().clear();
				return;
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				setInitialTask((ItemElement)null);
				return;
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
				getItems().clear();
				return;
			case TaskModelPackage.WORKER_ELEMENT__INITIALIZE:
				setInitialize((StructBlockElement)null);
				return;
			case TaskModelPackage.WORKER_ELEMENT__FINALIZE:
				setFinalize((StructBlockElement)null);
				return;
			case TaskModelPackage.WORKER_ELEMENT__RUN:
				setRun((StructBlockElement)null);
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
			case TaskModelPackage.WORKER_ELEMENT__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				return initialTask != null;
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
				return items != null && !items.isEmpty();
			case TaskModelPackage.WORKER_ELEMENT__INITIALIZE:
				return initialize != null;
			case TaskModelPackage.WORKER_ELEMENT__FINALIZE:
				return finalize != null;
			case TaskModelPackage.WORKER_ELEMENT__RUN:
				return run != null;
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
				case TaskModelPackage.WORKER_ELEMENT__STATEMENTS: return TaskModelPackage.BLOCK_ELEMENT__STATEMENTS;
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
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS: return TaskModelPackage.WORKER_ELEMENT__STATEMENTS;
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

} //WorkerElementImpl
