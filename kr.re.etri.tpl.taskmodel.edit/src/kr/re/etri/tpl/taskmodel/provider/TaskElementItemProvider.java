/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.provider;


import java.util.Collection;
import java.util.List;

import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link kr.re.etri.tpl.taskmodel.BehaviorElement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TaskElementItemProvider
	extends ItemElementItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskElementItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addStatementsPropertyDescriptor(object);
			addInitialStatePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Statements feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStatementsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BlockElement_statements_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BlockElement_statements_feature", "_UI_BlockElement_type"),
				 TaskModelPackage.Literals.BLOCK_ELEMENT__STATEMENTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Initial State feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInitialStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TaskElement_initialState_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TaskElement_initialState_feature", "_UI_TaskElement_type"),
				 TaskModelPackage.Literals.TASK_ELEMENT__INITIAL_STATE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(TaskModelPackage.Literals.BLOCK_ELEMENT__STATEMENTS);
			childrenFeatures.add(TaskModelPackage.Literals.TASK_ELEMENT__PARAMS);
			childrenFeatures.add(TaskModelPackage.Literals.TASK_ELEMENT__STATES);
			childrenFeatures.add(TaskModelPackage.Literals.TASK_ELEMENT__CONSTRUCT);
			childrenFeatures.add(TaskModelPackage.Literals.TASK_ELEMENT__DESTRUCT);
			childrenFeatures.add(TaskModelPackage.Literals.TASK_ELEMENT__BIFURCATES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TaskElement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TaskElement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((BehaviorElement)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TaskElement_type") :
			getString("_UI_TaskElement_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(BehaviorElement.class)) {
			case TaskModelPackage.TASK_ELEMENT__STATEMENTS:
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
			case TaskModelPackage.TASK_ELEMENT__STATES:
			case TaskModelPackage.TASK_ELEMENT__CONSTRUCT:
			case TaskModelPackage.TASK_ELEMENT__DESTRUCT:
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.TASK_ELEMENT__PARAMS,
				 TaskModelFactory.eINSTANCE.createParameter()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.TASK_ELEMENT__STATES,
				 TaskModelFactory.eINSTANCE.createStateElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.TASK_ELEMENT__CONSTRUCT,
				 TaskModelFactory.eINSTANCE.createStructBlockElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.TASK_ELEMENT__DESTRUCT,
				 TaskModelFactory.eINSTANCE.createStructBlockElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.TASK_ELEMENT__BIFURCATES,
				 TaskModelFactory.eINSTANCE.createExpandTransElement()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == TaskModelPackage.Literals.TASK_ELEMENT__CONSTRUCT ||
			childFeature == TaskModelPackage.Literals.TASK_ELEMENT__DESTRUCT;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
