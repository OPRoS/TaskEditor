package kr.re.etri.tpl.diagram.views;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.diagram.views.controls.BehaviorPropertyForm;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class ItemPropertyViewer extends Viewer {
	Composite parent;
	
	/**
	 * The property sources for the values we are displaying/editing.
	 */
	private Map sources = new HashMap(0);

	// The input objects for the viewer
	private Object[] input;
	
	Composite control;
	BehaviorPropertyForm propertyForm;
	
//	ScrollingGraphicalViewer viewer;

	// The root entry of the viewer
//	private IPropertySheetEntry rootEntry;

//	private IPropertySheetEntryListener entryListener;

	// The status line manager for showing messages
	private IStatusLineManager statusLineManager;
	
	private CommandStack commandStack;

	int[] events = new int[] {
			SWT.Dispose,
			SWT.Resize,  
			SWT.FocusIn,
			SWT.FocusOut,
			SWT.Activate,
			SWT.Deactivate};

	Listener parentListener;
	Listener controlListener;
	
	public ItemPropertyViewer(Composite parent, CommandStack commandStack) {
		this.parent = parent;
		this.commandStack = commandStack;

		control = new  Composite(parent, SWT.NONE);
		
		Rectangle rect = new Rectangle(0, 0, 200, 118);
		propertyForm = new BehaviorPropertyForm(control, SWT.NONE);
		propertyForm.createControl();
		propertyForm.setCommandStack(commandStack);
		propertyForm.setBounds(rect);
		
		hookControl();
	}

	@Override
	public Control getControl() {
		return control;
	}

	@Override
	public Object getInput() {
		return input;
	}

	@Override
	public ISelection getSelection() {
		return StructuredSelection.EMPTY;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns an property source for the given object.
	 * 
	 * @param object
	 *			an object for which to obtain a property source or
	 *			<code>null</code> if a property source is not available
	 * @return an property source for the given object
	 * @since 3.1 (was previously private)
	 */
	protected IPropertySource getPropertySource(Object object) {
		if (sources.containsKey(object))
			return (IPropertySource) sources.get(object);

		IPropertySource result = null;
		IPropertySourceProvider provider = null;

		if (object != null) {
			provider = (IPropertySourceProvider) ViewsPlugin.getAdapter(object, 
					IPropertySourceProvider.class, false);
		}

		if (provider != null) {
			result = provider.getPropertySource(object);
		} else {
			result = (IPropertySource)ViewsPlugin.getAdapter(object, IPropertySource.class, false);
		}

		sources.put(object, result);
		return result;
	}

	@Override
	public void setInput(Object newInput) {
		// set the new input to the root entry
		Object oldInput = input;
		input = (Object[]) newInput;
		if (input == null) {
			input = new Object[0];
		}

		IPropertySource propertySource = null;
		if(input.length > 0) {
			propertySource = (IPropertySource)getPropertySource(input[0]);
			propertyForm.setPropertySource(propertySource);
		}
		else {
			propertyForm.setPropertySource(null);
		}

		propertyForm.setInput(input);
		
		inputChanged(input, oldInput);
	}

    protected void inputChanged(Object input, Object oldInput) {
    }

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		return;
	}

	/**
	 * Sends out a selection changed event for the entry tree to all registered
	 * listeners.
	 */
	private void entrySelectionChanged() {
		SelectionChangedEvent changeEvent = new SelectionChangedEvent(this,
				getSelection());
		fireSelectionChanged(changeEvent);
	}

	/**
	 * Sets the error message to be displayed in the status line.
	 * 
	 * @param errorMessage
	 *			the message to be displayed, or <code>null</code>
	 */
	private void setErrorMessage(String errorMessage) {
		// show the error message
		if (statusLineManager != null) {
			statusLineManager.setErrorMessage(errorMessage);
		}
	}

	/**
	 * Sets the message to be displayed in the status line. This message is
	 * displayed when there is no error message.
	 * 
	 * @param message
	 *			the message to be displayed, or <code>null</code>
	 */
	private void setMessage(String message) {
		// show the message
		if (statusLineManager != null) {
			statusLineManager.setMessage(message);
		}
	}

	/**
	 * Sets the status line manager this view will use to show messages.
	 * 
	 * @param manager
	 *			the status line manager
	 */
	public void setStatusLineManager(IStatusLineManager manager) {
		statusLineManager = manager;
	}

	/**
	 * Establish this viewer as a listener on the control
	 */
	private void hookControl() {
		parentListener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Resize: onResize(); break;
				}
			}
		};

		for (int i = 0; i < events.length; i++) {
			parent.addListener(events[i], parentListener);
		}
		
		controlListener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Resize: onResize(); break;
					case SWT.Dispose: onDispose(); break;
				}
			}
		};

		for (int i = 0; i < events.length; i++) {
			control.addListener(events[i], controlListener);
		}

/*		// Handle selections in the Tree
		// Part1: Double click only (allow traversal via keyboard without
		// activation
		tree.addSelectionListener(new SelectionAdapter() {
			 (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 
			public void widgetSelected(SelectionEvent e) {
				// The viewer only owns the status line when there is
				// no 'active' cell editor
				if (cellEditor == null || !cellEditor.isActivated()) {
					updateStatusLine(e.item);
				}
			}

			 (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 
			public void widgetDefaultSelected(SelectionEvent e) {
				handleSelect((TreeItem) e.item);
			}
		});
		// Part2: handle single click activation of cell editor
		tree.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				// only activate if there is a cell editor
				Point pt = new Point(event.x, event.y);
				TreeItem item = tree.getItem(pt);
				if (item != null) {
					handleSelect(item);
				}
			}
		});

		// Add a tree listener to expand and collapse which
		// allows for lazy creation of children
		tree.addTreeListener(new TreeListener() {
			public void treeExpanded(final TreeEvent event) {
				handleTreeExpand(event);
			}

			public void treeCollapsed(final TreeEvent event) {
				handleTreeCollapse(event);
			}
		});

		// Refresh the tree when F5 pressed
		tree.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.ESC) {
					deactivateCellEditor();
				} else if (e.keyCode == SWT.F5) {
					// The following will simulate a reselect
					setInput(getInput());
				}
			}
		});
*/	}

	private void onDispose() {
		for (int i = 0; i < events.length; i++) {
			parent.removeListener(events[i], parentListener);
		}
		for (int i = 0; i < events.length; i++) {
			control.removeListener(events[i], controlListener);
		}
		propertyForm.dispose();
	}

	private void onResize() {
		Point size = parent.getSize();

		propertyForm.setSize(size.x, size.y);
	}
}
