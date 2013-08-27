/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.provider;


import java.util.Collection;
import java.util.List;

import kr.re.etri.tpl.taskmodel.IncludedElement;
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
 * This is the item provider adapter for a {@link kr.re.etri.tpl.taskmodel.IncludedElement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class IncludedElementItemProvider
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
	public IncludedElementItemProvider(AdapterFactory adapterFactory) {
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

			addIncludePathPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Include Path feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIncludePathPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_IncludedElement_includePath_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_IncludedElement_includePath_feature", "_UI_IncludedElement_type"),
				 TaskModelPackage.Literals.INCLUDED_ELEMENT__INCLUDE_PATH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
			childrenFeatures.add(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS);
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
	 * This returns IncludedElement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/IncludedElement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((IncludedElement)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_IncludedElement_type") :
			getString("_UI_IncludedElement_type") + " " + label;
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

		switch (notification.getFeatureID(IncludedElement.class)) {
			case TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TaskModelPackage.INCLUDED_ELEMENT__ITEMS:
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
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createItemElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createShapeElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createLinkedElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createLineElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createConnectionElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createWorkerElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createTaskElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createStateElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createActionElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createStateAction()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createSymbol()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createConstant()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createFunction()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createModelElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createModelDiagram()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createIncludedElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createParameter()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createEnumElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createEnumItemElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createReferElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createSubDiagram()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createConnectorElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createWithElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
				 TaskModelFactory.eINSTANCE.createStructBlockElement()));

		newChildDescriptors.add
			(createChildParameter
				(TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS,
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
			childFeature == TaskModelPackage.Literals.ITEM_ELEMENT__SUB_DIAGRAM ||
			childFeature == TaskModelPackage.Literals.INCLUDED_ELEMENT__ITEMS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
