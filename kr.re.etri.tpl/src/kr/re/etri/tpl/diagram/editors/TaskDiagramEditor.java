package kr.re.etri.tpl.diagram.editors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.EventObject;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.ShapeModelTreeEditPartFactory;
import kr.re.etri.tpl.diagram.ShortestConnectionRouter;
import kr.re.etri.tpl.diagram.TaskDiagramEditPartFactory;
import kr.re.etri.tpl.diagram.editors.actions.WorkerDiagramCloseAction;
import kr.re.etri.tpl.diagram.views.ItemPropertySheetPage;
import kr.re.etri.tpl.diagram.views.TaskNavigatorViewPage;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class TaskDiagramEditor extends GraphicalEditorWithFlyoutPalette {

	public static String editorId = "kr.re.etri.tpl.diagram.editors.WorkerDiagramEditor";
	
	/** 모델의 최상위 model */
	private SubDiagram rootModel;
	/** Editor에서 최상위 model */
	private TaskElement parentModel;
	/** Palette component, holding the tools and shapes. */
	private static PaletteRoot PALETTE_MODEL;
	
	private ShortestConnectionRouter connectionRouter = new ShortestConnectionRouter();
	
	private final Resource resource = new XMLResourceImpl();

	public TaskDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	// MultipageEditPart용
	public TaskDiagramEditor(TaskElement worker, CommandStack cmdStack) {
		DefaultEditDomain editDomain = new DefaultEditDomain(this);
		editDomain.setCommandStack(cmdStack);
		setEditDomain(editDomain);

		parentModel = worker;
	}

	// MultipageEditPart용
	public TaskDiagramEditor(DefaultEditDomain editDomain) {
		setEditDomain(editDomain);
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new TaskDiagramEditPartFactory(connectionRouter));
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		// configure the context menu provider
		ContextMenuProvider cmProvider =
			new TaskDiagramContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(TaskDiagramContextMenuProvider.MENU_ID, cmProvider, viewer);

	}
	
	@Override
	protected void createActions() {
		super.createActions();

		ActionRegistry registry = getActionRegistry();
		IAction action;
		
		action = new WorkerDiagramCloseAction(getEditDomain());
		registry.registerAction(action);
	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	private void createOutputStream(OutputStream os) throws IOException {
		try {
			resource.save(os,Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				// create a drag source listener for this palette viewer
				// together with an appropriate transfer drop target listener, this will enable
				// model element creation by dragging a CombinatedTemplateCreationEntries 
				// from the palette into the editor
				// @see ShapesEditor#createTransferDropTargetListener()
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}

	/**
	 * Create a transfer drop target listener. When using a CombinedTemplateCreationEntry
	 * tool in the palette, this will enable model element creation by dragging from the palette.
	 * @see #createPaletteViewerProvider()
	 */
	private TransferDropTargetListener createTransferDropTargetListener() {
		return new TemplateTransferDropTargetListener(getGraphicalViewer()) {
			protected CreationFactory getFactory(final Object template) {
				if(template instanceof ItemElement) {
					return new CreationFactory() {
						public Object getNewObject() {
							return template;
						}
						public Object getObjectType() { return null; }
					};
				}
/*				
				else if(template == TaskElement.class) {
					return new CreationFactory() {
						public Object getNewObject() {
							return ModelManager.getFactory().createTaskElement();
						}
						public Object getObjectType() { return null; }
					};
				}
				else if(template == StateElement.class) {
					return new CreationFactory() {
						public Object getNewObject() {
							return ModelManager.getFactory().createStateElement();
						}
						public Object getObjectType() { return null; }
					};
				}
				else if(template == ActionElement.class) {
					return new CreationFactory() {
						public Object getNewObject() {
							return ModelManager.getFactory().createActionElement();
						}
						public Object getObjectType() { return null; }
					};
				}
*/

//				return new SimpleFactory((Class) template);
				return new CreationFactory() {
					public Object getNewObject() { return null; }
					public Object getObjectType() { return null; }
				};
			}
			public void dragEnter(DropTargetEvent event) {
				super.dragEnter(event);
			}
			public void dragLeave(DropTargetEvent event) {
				super.dragLeave(event);
			}
			public void dragOperationChanged(DropTargetEvent event) {
				super.dragOperationChanged(event);
			}
			public void dragOver(DropTargetEvent event) {
				super.dragOver(event);
			}
			public void drop(DropTargetEvent event) {
				super.drop(event);
			}
			public void dropAccept(DropTargetEvent event) {
				super.dropAccept(event);
			}
			public Transfer getTransfer() {
				return super.getTransfer();
//				return TemplateTransfer.getInstance();
			}
			public boolean isEnabled(DropTargetEvent event) {
				return super.isEnabled(event);
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
//		try {
//			resource.save(Collections.EMPTY_MAP);
//			getCommandStack().markSaveLocation();
//			
//			IWorkbench wb = PlatformUI.getWorkbench();
//			IWorkbenchWindow ww = wb.getActiveWorkbenchWindow();
//			IWorkbenchPage wp = ww.getActivePage();
//			IViewPart vp = wp.findView(ProjectNavigatorView.viewId);
//			if(vp != null) {
//				CommonViewer cv = ((CommonNavigator)vp).getCommonViewer();
//				cv.refresh();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		IEditorPart editPart = ((DefaultEditDomain)this.getEditDomain()).getEditorPart();
//		IEditorPart editPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editPart instanceof TPLDiagramEditor) {
			TPLDiagramEditor rtmEditor = (TPLDiagramEditor)editPart;
			rtmEditor.doSave(monitor);
		}
	}

	@Override
	public void doSaveAs() {
//		// Show a SaveAs dialog
//		Shell shell = getSite().getWorkbenchWindow().getShell();
//		SaveAsDialog dialog = new SaveAsDialog(shell);
//		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
//		dialog.open();
//		
//		IPath path = dialog.getResult();	
//		if (path != null) {
//			// try to save the editor's contents under a different file name
//			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
//			try {
//				new ProgressMonitorDialog(shell).run(
//						false, // don't fork
//						false, // not cancelable
//						new WorkspaceModifyOperation() { // run this operation
//							public void execute(final IProgressMonitor monitor) {
//								try {
//									ByteArrayOutputStream out = new ByteArrayOutputStream();
//									createOutputStream(out);
//									file.create(
//											new ByteArrayInputStream(out.toByteArray()), // contents
//											true, // keep saving, even if IFile is out of sync with the Workspace
//											monitor); // progress monitor
//								}
//								catch (CoreException ce) { ce.printStackTrace(); }
//								catch (IOException ioe) { ioe.printStackTrace(); } 
//							}
//						});
//				// set input to the new file
//				setInput(new FileEditorInput(file));
//				getCommandStack().markSaveLocation();
//			}
//			catch (InterruptedException ie) {
//				// should not happen, since the monitor dialog is not cancelable
//				ie.printStackTrace(); 
//			}
//			catch (InvocationTargetException ite) { 
//				ite.printStackTrace(); 
//			}
//		} // if

		IEditorPart editPart = ((DefaultEditDomain)this.getEditDomain()).getEditorPart();
//		IEditorPart editPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editPart instanceof TPLDiagramEditor) {
			TPLDiagramEditor rtmEditor = (TPLDiagramEditor)editPart;
			rtmEditor.doSaveAs();
		}

	}
	
	public Object getAdapter(Class type) {
		if (type == IContentOutlinePage.class) {
			return new DiagramOutlinePage(new TreeViewer());
		}
		else if(type == IPropertySheetPage.class) {
			ItemPropertySheetPage page;
			page = new ItemPropertySheetPage(getCommandStack());
			return page;
		}else if(type == TaskNavigatorViewPage.class){
			TaskNavigatorViewPage page;
			page = new TaskNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					getSelectionSynchronizer(),
					(ModelDiagram)getRootModel());
			return page;
		}

		return super.getAdapter(type);
	}
	
	public ModelDiagram getRootModel() {
		ItemElement item = getModel();
		while(item != null) {
			if(item.getParent() == null) {
				if(item instanceof ModelDiagram) {
					return (ModelDiagram)item;
				}
				return null;
			}
			
			item = item.getParent();
		}
		return null;
	}
	
	public SubDiagram getModel() {
		return rootModel;
	}

	public TaskElement getWorkerModel() {
		return parentModel;
	}

	protected FlyoutPreferences getPalettePreferences() {
		return TaskDiagramEditorPaletteFactory.createPalettePreferences();
	}

	protected PaletteRoot getPaletteRoot() {
		if (PALETTE_MODEL == null)
			PALETTE_MODEL = TaskDiagramEditorPaletteFactory.createPalette();
		return PALETTE_MODEL;
	}
	
	private void handleLoadException(Exception e) {
		System.err.println("** Load failed. Using default model. **");
		e.printStackTrace();
		rootModel = ModelManager.getFactory().createSubDiagram();
	}

	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(getModel()); // set the contents of this editor
		
		// add the ShortestPathConnectionRouter
		ScalableFreeformRootEditPart root = 
			(ScalableFreeformRootEditPart)viewer.getRootEditPart();
		ConnectionLayer connLayer =
			(ConnectionLayer)root.getLayer(LayerConstants.CONNECTION_LAYER);
		GraphicalEditPart contentEditPart = (GraphicalEditPart)root.getContents();

//		BendpointConnectionRouter bendpointRouter = 
//			new BendpointConnectionRouter();
//		connLayer.setConnectionRouter(bendpointRouter);

//		ShortestRoundConnectionRouter shortestRoundRouter = 
//			new ShortestRoundConnectionRouter(contentEditPart.getFigure());
//		connLayer.setConnectionRouter(shortestRoundRouter);

		connectionRouter.setSpacing(8);
		
		connectionRouter.addContainer(contentEditPart.getFigure());
		connLayer.setConnectionRouter(connectionRouter);
	
		//contentEditPart.getContentPane().addLayoutListener(router.getLayoutListener());

		// listen for dropped parts
		viewer.addDropTargetListener(createTransferDropTargetListener());
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void setInput(IEditorInput input) {
		super.setInput(input);
//		IFile file = ((IFileEditorInput) input).getFile();
//		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString());
//		resource.setURI(uri);
//		try {
//			resource.load(Collections.EMPTY_MAP);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if(resource.getContents().size() > 0) {
//			rootModel = (SubDiagram)resource.getContents().get(0);
//		}
//		setPartName(file.getName());

		rootModel = (SubDiagram)((DiagramEditorInput)input).getModel();
		setPartName((rootModel.getName() != null) ? rootModel.getName() : "");
	}

	/**
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// If not the active editor, ignore selection changed.
//		if (this.equals(getSite().getPage().getActiveEditor()))
		if (this.equals(getSite().getPart()))
			updateActions(getSelectionActions());
	}

	public class DiagramOutlinePage extends ContentOutlinePage {	
		/**
		 * Create a new outline page for the shapes editor.
		 * @param viewer a viewer (TreeViewer instance) used for this outline page
		 * @throws IllegalArgumentException if editor is null
		 */
		public DiagramOutlinePage(EditPartViewer viewer) {
			super(viewer);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			// create outline viewer page
			getViewer().createControl(parent);
			// configure outline viewer
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new ShapeModelTreeEditPartFactory());
			// configure & add context menu to viewer
			ContextMenuProvider cmProvider = new TaskDiagramContextMenuProvider(
					getViewer(), getActionRegistry()); 
			getViewer().setContextMenu(cmProvider);
			getSite().registerContextMenu(
					TaskDiagramContextMenuProvider.MENU_ID,
					cmProvider, getSite().getSelectionProvider());		
			// hook outline viewer
			getSelectionSynchronizer().addViewer(getViewer());
			// initialize outline viewer with model
			getViewer().setContents(getModel());
			// show outline viewer
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#dispose()
		 */
		public void dispose() {
			// unhook outline viewer
			getSelectionSynchronizer().removeViewer(getViewer());
			// dispose
			super.dispose();
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#getControl()
		 */
		public Control getControl() {
			return getViewer().getControl();
		}
		
		/**
		 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
		 */
		public void init(IPageSite pageSite) {
			super.init(pageSite);

			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
			String id = ActionFactory.UNDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.REDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
//			id = RTMActionFactory.PROPERTIES.getId();
//			bars.setGlobalActionHandler(id, registry.getAction(id));
		}
	}

}
