/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import kr.re.etri.tpl.taskmodel.util.TaskModelAdapterFactory;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TaskModelItemProviderAdapterFactory extends TaskModelAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This helps manage the child creation extenders.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(TaskModelEditPlugin.INSTANCE, TaskModelPackage.eNS_URI);

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskModelItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ItemElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ItemElementItemProvider itemElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ItemElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createItemElementAdapter() {
		if (itemElementItemProvider == null) {
			itemElementItemProvider = new ItemElementItemProvider(this);
		}

		return itemElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ShapeElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ShapeElementItemProvider shapeElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ShapeElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createShapeElementAdapter() {
		if (shapeElementItemProvider == null) {
			shapeElementItemProvider = new ShapeElementItemProvider(this);
		}

		return shapeElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.LinkedElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkedElementItemProvider linkedElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.LinkedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLinkedElementAdapter() {
		if (linkedElementItemProvider == null) {
			linkedElementItemProvider = new LinkedElementItemProvider(this);
		}

		return linkedElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.LineElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineElementItemProvider lineElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.LineElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLineElementAdapter() {
		if (lineElementItemProvider == null) {
			lineElementItemProvider = new LineElementItemProvider(this);
		}

		return lineElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ConnectionElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionElementItemProvider connectionElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ConnectionElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createConnectionElementAdapter() {
		if (connectionElementItemProvider == null) {
			connectionElementItemProvider = new ConnectionElementItemProvider(this);
		}

		return connectionElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.TaskElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkerElementItemProvider workerElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.TaskElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createWorkerElementAdapter() {
		if (workerElementItemProvider == null) {
			workerElementItemProvider = new WorkerElementItemProvider(this);
		}

		return workerElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.BlockElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockElementItemProvider blockElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.BlockElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBlockElementAdapter() {
		if (blockElementItemProvider == null) {
			blockElementItemProvider = new BlockElementItemProvider(this);
		}

		return blockElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.BehaviorElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskElementItemProvider taskElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.BehaviorElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTaskElementAdapter() {
		if (taskElementItemProvider == null) {
			taskElementItemProvider = new TaskElementItemProvider(this);
		}

		return taskElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.StateElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateElementItemProvider stateElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.StateElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStateElementAdapter() {
		if (stateElementItemProvider == null) {
			stateElementItemProvider = new StateElementItemProvider(this);
		}

		return stateElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ActionElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionElementItemProvider actionElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ActionElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createActionElementAdapter() {
		if (actionElementItemProvider == null) {
			actionElementItemProvider = new ActionElementItemProvider(this);
		}

		return actionElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.StateAction} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateActionItemProvider stateActionItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.StateAction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStateActionAdapter() {
		if (stateActionItemProvider == null) {
			stateActionItemProvider = new StateActionItemProvider(this);
		}

		return stateActionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.Symbol} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolItemProvider symbolItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.Symbol}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSymbolAdapter() {
		if (symbolItemProvider == null) {
			symbolItemProvider = new SymbolItemProvider(this);
		}

		return symbolItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.Constant} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstantItemProvider constantItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.Constant}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createConstantAdapter() {
		if (constantItemProvider == null) {
			constantItemProvider = new ConstantItemProvider(this);
		}

		return constantItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.Function} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionItemProvider functionItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.Function}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createFunctionAdapter() {
		if (functionItemProvider == null) {
			functionItemProvider = new FunctionItemProvider(this);
		}

		return functionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ModelElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelElementItemProvider modelElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ModelElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createModelElementAdapter() {
		if (modelElementItemProvider == null) {
			modelElementItemProvider = new ModelElementItemProvider(this);
		}

		return modelElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ModelDiagram} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelDiagramItemProvider modelDiagramItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ModelDiagram}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createModelDiagramAdapter() {
		if (modelDiagramItemProvider == null) {
			modelDiagramItemProvider = new ModelDiagramItemProvider(this);
		}

		return modelDiagramItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.IncludedElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncludedElementItemProvider includedElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.IncludedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createIncludedElementAdapter() {
		if (includedElementItemProvider == null) {
			includedElementItemProvider = new IncludedElementItemProvider(this);
		}

		return includedElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.Parameter} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterItemProvider parameterItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createParameterAdapter() {
		if (parameterItemProvider == null) {
			parameterItemProvider = new ParameterItemProvider(this);
		}

		return parameterItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.EnumElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumElementItemProvider enumElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.EnumElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEnumElementAdapter() {
		if (enumElementItemProvider == null) {
			enumElementItemProvider = new EnumElementItemProvider(this);
		}

		return enumElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.EnumItemElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumItemElementItemProvider enumItemElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.EnumItemElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEnumItemElementAdapter() {
		if (enumItemElementItemProvider == null) {
			enumItemElementItemProvider = new EnumItemElementItemProvider(this);
		}

		return enumItemElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ReferElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferElementItemProvider referElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ReferElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createReferElementAdapter() {
		if (referElementItemProvider == null) {
			referElementItemProvider = new ReferElementItemProvider(this);
		}

		return referElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.SubDiagram} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubDiagramItemProvider subDiagramItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.SubDiagram}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSubDiagramAdapter() {
		if (subDiagramItemProvider == null) {
			subDiagramItemProvider = new SubDiagramItemProvider(this);
		}

		return subDiagramItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ConnectorElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectorElementItemProvider connectorElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ConnectorElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createConnectorElementAdapter() {
		if (connectorElementItemProvider == null) {
			connectorElementItemProvider = new ConnectorElementItemProvider(this);
		}

		return connectorElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.WithElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WithElementItemProvider withElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.WithElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createWithElementAdapter() {
		if (withElementItemProvider == null) {
			withElementItemProvider = new WithElementItemProvider(this);
		}

		return withElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.StructBlockElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StructBlockElementItemProvider structBlockElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.StructBlockElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStructBlockElementAdapter() {
		if (structBlockElementItemProvider == null) {
			structBlockElementItemProvider = new StructBlockElementItemProvider(this);
		}

		return structBlockElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link kr.re.etri.tpl.taskmodel.ExpandTransElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpandTransElementItemProvider expandTransElementItemProvider;

	/**
	 * This creates an adapter for a {@link kr.re.etri.tpl.taskmodel.ExpandTransElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createExpandTransElementAdapter() {
		if (expandTransElementItemProvider == null) {
			expandTransElementItemProvider = new ExpandTransElementItemProvider(this);
		}

		return expandTransElementItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<IChildCreationExtender> getChildCreationExtenders() {
		return childCreationExtenderManager.getChildCreationExtenders();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
		return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return childCreationExtenderManager;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (itemElementItemProvider != null) itemElementItemProvider.dispose();
		if (shapeElementItemProvider != null) shapeElementItemProvider.dispose();
		if (linkedElementItemProvider != null) linkedElementItemProvider.dispose();
		if (lineElementItemProvider != null) lineElementItemProvider.dispose();
		if (connectionElementItemProvider != null) connectionElementItemProvider.dispose();
		if (workerElementItemProvider != null) workerElementItemProvider.dispose();
		if (blockElementItemProvider != null) blockElementItemProvider.dispose();
		if (taskElementItemProvider != null) taskElementItemProvider.dispose();
		if (stateElementItemProvider != null) stateElementItemProvider.dispose();
		if (actionElementItemProvider != null) actionElementItemProvider.dispose();
		if (stateActionItemProvider != null) stateActionItemProvider.dispose();
		if (symbolItemProvider != null) symbolItemProvider.dispose();
		if (constantItemProvider != null) constantItemProvider.dispose();
		if (functionItemProvider != null) functionItemProvider.dispose();
		if (modelElementItemProvider != null) modelElementItemProvider.dispose();
		if (modelDiagramItemProvider != null) modelDiagramItemProvider.dispose();
		if (includedElementItemProvider != null) includedElementItemProvider.dispose();
		if (parameterItemProvider != null) parameterItemProvider.dispose();
		if (enumElementItemProvider != null) enumElementItemProvider.dispose();
		if (enumItemElementItemProvider != null) enumItemElementItemProvider.dispose();
		if (referElementItemProvider != null) referElementItemProvider.dispose();
		if (subDiagramItemProvider != null) subDiagramItemProvider.dispose();
		if (connectorElementItemProvider != null) connectorElementItemProvider.dispose();
		if (withElementItemProvider != null) withElementItemProvider.dispose();
		if (structBlockElementItemProvider != null) structBlockElementItemProvider.dispose();
		if (expandTransElementItemProvider != null) expandTransElementItemProvider.dispose();
	}

}
