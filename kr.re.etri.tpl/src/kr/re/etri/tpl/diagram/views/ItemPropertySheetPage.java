package kr.re.etri.tpl.diagram.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IContextComputer;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.CellEditorActionHandler;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class ItemPropertySheetPage extends Page implements IPropertySheetPage, IAdaptable {

	private ItemPropertyViewer viewer;

	private IPropertySourceProvider provider;

	private CellEditorActionHandler cellEditorActionHandler;

	private Clipboard clipboard;

	private IWorkbenchPart sourcePart;
	
	private CommandStack commandStack;

	private class PartListener implements IPartListener {
		public void partActivated(IWorkbenchPart part) {
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
			if (sourcePart == part) {
				sourcePart = null;
				if (viewer != null && !viewer.getControl().isDisposed()) {
					viewer.setInput(new Object[0]);
				}
			}
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
		}
	}
	
	private PartListener partListener = new PartListener();

	private class PropertyChangeListener implements IPropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private PropertyChangeListener propertyChangeListener = new PropertyChangeListener();
	
	public ItemPropertySheetPage(CommandStack commandStack) {
		super();

		this.commandStack = commandStack;
	}

	@Override
	public void createControl(Composite parent) {
		viewer = new ItemPropertyViewer(parent, commandStack);

//		viewer.setRootEntry(rootEntry);
//		viewer.addActivationListener(...);
		// add a listener to track when the entry selection changes
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleEntrySelection(event.getSelection());
			}
		});

		initDragAndDrop();
		makeActions();

		// Create the popup menu for the page.
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		// Set help on the viewer 
		viewer.getControl().addHelpListener(new HelpListener() {
			/*
			 * @see HelpListener#helpRequested(HelpEvent)
			 */
			public void helpRequested(HelpEvent e) {
				// Get the context for the selected item
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				if (!selection.isEmpty()) {
					IPropertySheetEntry entry = (IPropertySheetEntry) selection
							.getFirstElement();
					Object helpContextId = entry.getHelpContextIds();
					if (helpContextId != null) {
						if (helpContextId instanceof String) {
							PlatformUI.getWorkbench()
									.getHelpSystem().displayHelp(
											(String) helpContextId);
							return;
						}

						// Since 2.0 the only valid type for helpContextIds
						// is a String (a single id).
						// However for backward compatibility we have to handle
						// and array of contexts (Strings and/or IContexts) 
						// or a context computer.
						Object[] contexts = null;
						if (helpContextId instanceof IContextComputer) {
							// get local contexts
							contexts = ((IContextComputer) helpContextId)
									.getLocalContexts(e);
						} else {
							contexts = (Object[]) helpContextId;
						}
//						IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
//						// Ignore all but the first element in the array
//						if (contexts[0] instanceof IContext) {
//							help.displayHelp((IContext) contexts[0]);
//						} else {
//							help.displayHelp((String) contexts[0]);
//						}
						return;
					}
				}

//				// No help for the selection so show page help
//				PlatformUI.getWorkbench().getHelpSystem().displayHelp(HELP_CONTEXT_PROPERTY_SHEET_PAGE);
			}
		});
	}

	@Override
	public void dispose() {
		super.dispose();
	
		if (sourcePart != null) {
			IWorkbenchPartSite partSite;
			IWorkbenchPage page;
			partSite = sourcePart.getSite();
			page = partSite.getPage();
			page.removePartListener(partListener);
			page.removePropertyChangeListener(propertyChangeListener);
		}		

		if (clipboard != null) {
			clipboard.dispose();
			clipboard = null;
		}
	}

	@Override
	public Control getControl() {
		if (viewer == null) {
			return null;
		}
		return viewer.getControl();
	}

	@Override
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);
	}

	@Override
	public void setFocus() {
		Control ctrl = viewer.getControl();
		if(ctrl != null) {
			ctrl.setFocus();
		}
	}

	/**
	 * Updates the model for the viewer.
	 * <p>
	 * Note that this means ensuring that the model reflects the state
	 * of the current viewer input. 
	 * </p>
	 */
	public void refresh() {
		if (viewer == null) {
			return;
		}
		// calling setInput on the viewer will cause the model to refresh
		viewer.setInput(viewer.getInput());
	}

    /* (non-Javadoc)
     * Method declared on ISelectionListener.
     */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (viewer == null) {
			return;
		}

		if (sourcePart != null) {
			IWorkbenchPartSite partSite;
			IWorkbenchPage page;
			partSite = sourcePart.getSite();
			page = partSite.getPage();
			page.removePartListener(partListener);
			page.removePropertyChangeListener(propertyChangeListener);
			sourcePart = null;
		}
		
		// change the viewer input since the workbench selection has changed.
		if (selection instanceof IStructuredSelection) {
			sourcePart = part;
			viewer.setInput(((IStructuredSelection) selection).toArray());
		}

		if (sourcePart != null) {
			IWorkbenchPartSite partSite;
			IWorkbenchPage page;
			partSite = sourcePart.getSite();
			page = partSite.getPage();
			page.addPartListener(partListener);
			page.addPropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public Object getAdapter(Class key) {
		if (ISaveablePart.class.equals(key)) {
			return getSaveablePart();
		}
		
		return null;
	}
	
	/**
	 * Returns an <code>ISaveablePart</code> that delegates to the source part
	 * for the current page if it implements <code>ISaveablePart</code>, or
	 * <code>null</code> otherwise.
	 * 
	 * @return an <code>ISaveablePart</code> or <code>null</code>
	 * @since 3.2
	 */
	protected ISaveablePart getSaveablePart() {
		if (sourcePart instanceof ISaveablePart) {
			return (ISaveablePart) sourcePart;
		}
		return null;
	}


	/**
	 * Handles a selection change in the entry table.
	 *
	 * @param selection the new selection
	 */
	public void handleEntrySelection(ISelection selection) {

	}


	/**
	 * Adds drag and drop support.
	 */
	protected void initDragAndDrop() {
		int operations = DND.DROP_COPY;
		Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance() };
		DragSourceListener listener = new DragSourceAdapter() {
			public void dragSetData(DragSourceEvent event) {
				performDragSetData(event);
			}

			public void dragFinished(DragSourceEvent event) {
				//Nothing to do here
			}
		};
		DragSource dragSource = new DragSource(
				viewer.getControl(), operations);
		dragSource.setTransfer(transferTypes);
		dragSource.addDragListener(listener);
	}


	/**
	 * The user is attempting to drag.  Add the appropriate
	 * data to the event.
	 * @param event The event sent from the drag and drop support.
	 */
	void performDragSetData(DragSourceEvent event) {
		// Get the selected property
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		// Assume single selection
		IPropertySheetEntry entry = (IPropertySheetEntry) selection
				.getFirstElement();

		// Place text as the data
		StringBuffer buffer = new StringBuffer();
		buffer.append(entry.getDisplayName());
		buffer.append("\t"); //$NON-NLS-1$
		buffer.append(entry.getValueAsString());

		event.data = buffer.toString();
	}

	/**
	 * Make action objects.
	 */
	private void makeActions() {
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();

	}

	/* (non-Javadoc)
	 * Method declared on IPage (and Page).
	 */
	public void makeContributions(IMenuManager menuManager,
			IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {

		// add actions to the tool bar
//		toolBarManager.add(categoriesAction);
//		toolBarManager.add(filterAction);
//		toolBarManager.add(defaultsAction);

		// add actions to the menu
//		menuManager.add(categoriesAction);
//		menuManager.add(filterAction);
//		menuManager.add(columnsAction);

		// set status line manager into the viewer
		viewer.setStatusLineManager(statusLineManager);
	}
}
