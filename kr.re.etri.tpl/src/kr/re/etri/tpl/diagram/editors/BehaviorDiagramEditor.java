package kr.re.etri.tpl.diagram.editors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.Iterator;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.BehaviorDiagramEditPartFactory;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.PropertyEditPartFactory;
import kr.re.etri.tpl.diagram.ShapeModelTreeEditPartFactory;
import kr.re.etri.tpl.diagram.editors.actions.ExternalBehaviorSetAction;
import kr.re.etri.tpl.diagram.editparts.TPLScalableFreeformRootEditPart;
import kr.re.etri.tpl.diagram.monitoring.controller.MonitoringAction;
import kr.re.etri.tpl.diagram.util.FormWizardArrowKeyHandler;
import kr.re.etri.tpl.diagram.util.TPLSelectionSynchronizer;
import kr.re.etri.tpl.diagram.views.ActionNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.IModelNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.ItemPropertySheetPage;
import kr.re.etri.tpl.diagram.views.ModelNavigatorViewPage;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 * A graphical editor with flyout palette that can edit .shapes files.
 * The binding between the .shapesemf file extension and this editor is done in plugin.xml
 * @author Elias Volanakis
 * @author Chris Aniszczyk - updated to work with EMF
 */
public class BehaviorDiagramEditor extends GraphicalEditorWithFlyoutPalette {
	private static Logger logger = Logger.getLogger(BehaviorDiagramEditor.class);
	public static String editorId = "kr.re.etri.tpl.diagram.editors.TaskDiagramEditor";
	
	/** This is the root of the editor's model. */
	private SubDiagram rootModel;
	/** Editor에서 최상위 model */
	private ModelDiagram parentModel;
	/** Palette component, holding the tools and shapes. */
	private static PaletteRoot PALETTE_MODEL;

//	private ShortestConnectionRouter connectionRouter = new ShortestConnectionRouter();
	
	private kr.re.etri.tpl.diagram.UniShortestConnectionRouter connectionRouter = new kr.re.etri.tpl.diagram.UniShortestConnectionRouter();
	
	private final Resource resource = new XMLResourceImpl();

	private KeyHandler keyHandler;	// KJH 20110323
	
	/** Create a new ShapesEditor instance. This is called by the Workspace. */
	public BehaviorDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	// MultipageEditPart용
	public BehaviorDiagramEditor(DefaultEditDomain editDomain) {
		setEditDomain(editDomain);
	}

	SelectionSynchronizer synchronizer;
	@Override
	public SelectionSynchronizer getSelectionSynchronizer() {
//		return super.getSelectionSynchronizer();
		if(synchronizer == null) {
			synchronizer = new TPLSelectionSynchronizer(getRootModel());	// KJH 20110623, param parentModel
		}
		
		return synchronizer;
	}
	
	public void setSelectionSynchronizer(SelectionSynchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	@Override
	protected ActionRegistry getActionRegistry() {
		return super.getActionRegistry();
	}

	/**
	 * Configure the graphical viewer before it receives contents.
	 * <p>This is the place to choose an appropriate RootEditPart and EditPartFactory
	 * for your editor. The RootEditPart determines the behavior of the editor's "work-area".
	 * For example, GEF includes zoomable and scrollable root edit parts. The EditPartFactory
	 * maps model elements to edit parts (controllers).</p>
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new BehaviorDiagramEditPartFactory(connectionRouter));
	//	viewer.setEditPartFactory(new TaskDiagramEditPartFactory());
		viewer.setRootEditPart(new TPLScalableFreeformRootEditPart());
		
		keyHandler = new FormWizardArrowKeyHandler(viewer, getGraphicalViewer(), getCommandStack());
		keyHandler.put(  KeyStroke.getPressed(SWT.DEL, 127, 0),
                getActionRegistry().getAction(ActionFactory.DELETE.getId()));
		
		// KJH 20110323 s,
		configureZoom();
		configureGeometry();
		configureGrid();
		// KJH 20110323 e,
		
		// 마우스 휠로 줌 조절
//		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.NONE), MouseWheelZoomHandler.SINGLETON);
		
		viewer.setKeyHandler(keyHandler);

		// configure the context menu provider
		ContextMenuProvider cmProvider =
			new BehaviorDiagramContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(BehaviorDiagramContextMenuProvider.MENU_ID, cmProvider, viewer);
	}
	// KJH 20110323 s, configure zoom
	private void configureZoom() {
		RootEditPart rootEditPart = getGraphicalViewer().getRootEditPart();
		if (!(rootEditPart instanceof ScalableFreeformRootEditPart))
			return;
		ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart)rootEditPart;
		
		ZoomManager manager = root.getZoomManager();
		
		// 줌인/줌아웃 액션의 등록
		IAction zoomIn = new ZoomInAction(manager);
		IAction zoomOut = new ZoomOutAction(manager);
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);

		// 줌 레벨의 설정
		double[] zoomLevels = new double[] {0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0, 20.0};
		manager.setZoomLevels(zoomLevels);
		ArrayList<String> zoomContributions = new ArrayList<String>();
		zoomContributions.add(ZoomManager.FIT_ALL);
		zoomContributions.add(ZoomManager.FIT_HEIGHT);
		zoomContributions.add(ZoomManager.FIT_WIDTH);
		manager.setZoomLevelContributions(zoomContributions);

		// 디폴트 줌 배율의 설정, 기본값으로 100%로 설정한다.
		root.getZoomManager().setZoom(1.0);

		// 줌인/줌아웃 액션이 키보드에서 할 수 있도록 등록
		keyHandler.put(KeyStroke.getPressed('+', SWT.KEYPAD_ADD), zoomIn);
		keyHandler.put(KeyStroke.getPressed('-', SWT.KEYPAD_SUBTRACT), zoomOut);
//		IWorkbenchWindow window = getSite().getWorkbenchWindow();
//		IHandlerService handlerService = (IHandlerService)window.getService(IHandlerService.class);
//		handlerService.activateHandler(zoomIn.getActionDefinitionId(), new ActionHandler(zoomIn));
//		handlerService.activateHandler(zoomOut.getActionDefinitionId(), new ActionHandler(zoomOut));
	}// KJH 20110323 e, configure zoom
	
	// KJH 20110323 s, configure geometry
	private void configureGeometry() {
		GraphicalViewer viewer = getGraphicalViewer();
		
		viewer.setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED, true);
		
		IAction action = new ToggleSnapToGeometryAction(viewer);
		getActionRegistry().registerAction(action);
	}// KJH 20110323 e, configure geometry
	
	// KJH 20110323 s, configure grid
	private void configureGrid() {
		GraphicalViewer viewer = getGraphicalViewer();
		
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, true);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, true);
		
		IAction action = new ToggleGridAction(viewer);
		getActionRegistry().registerAction(action);
	}// KJH 20110323 e, configure grid
	
	public GraphicalViewer getGraphicalViewer(){
		return super.getGraphicalViewer();
	}
	@Override
	protected void createActions() {
		super.createActions();

		ActionRegistry registry = getActionRegistry();
		
		Iterator iter = registry.getActions();
		while(iter.hasNext()){
			logger.debug(iter.next());
		}
		IAction action;
		
		action = new MonitoringAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
//		action = new StateDiagramOpenAction("&Open Task", getEditDomain(), getCommandStack());
//		registry.registerAction(action);
		
		// KJH 20110905, delete
//		action = new WorkerInitTaskOpenAction("&set init task", getEditDomain());
//		registry.registerAction(action);
		
		// KJH 20100825, external behavior selection
		action = new ExternalBehaviorSetAction(getEditDomain());
		registry.registerAction(action);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#commandStackChanged(java.util.EventObject)
	 */
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}
	
	/**
	 * Strange... I really should just modify doSave/As but I'm using the least
	 * path of resistance :)
	 * 
	 * @param os
	 * @throws IOException
	 */
	private void createOutputStream(OutputStream os) throws IOException {
		try {
			resource.save(os,Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#createPaletteViewerProvider()
	 */
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
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#doSaveAs()
	 */
	public void doSaveAs() {
		// Show a SaveAs dialog
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
			return new ShapesOutlinePage(new TreeViewer());
		}
		else if(type == IPropertySheetPage.class) {
			ItemPropertySheetPage page;
			page = new ItemPropertySheetPage(getCommandStack());
			return page;
		}
		else if(type == IModelNavigatorViewPage.class) {
			ModelNavigatorViewPage page;
			page = new ModelNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					getSelectionSynchronizer(),
					(ModelDiagram)getRootModel());
			return page;
		}else if(type == ActionNavigatorViewPage.class){
			ActionNavigatorViewPage page;
			page = new ActionNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					getSelectionSynchronizer(),
					(ModelDiagram)getRootModel());
			return page;
		}
		// KJH 20110322 s, zoom
		else if (type == ZoomManager.class) {
			return ((ScalableFreeformRootEditPart)getGraphicalViewer()
					.getRootEditPart())
					.getZoomManager();
		}// KJH 20110322 e, zoom

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

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPalettePreferences()
	 */
	protected FlyoutPreferences getPalettePreferences() {
		return BehaviorDiagramEditorPaletteFactory.createPalettePreferences();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPaletteRoot()
	 */
	protected PaletteRoot getPaletteRoot() {
		if (PALETTE_MODEL == null)
			PALETTE_MODEL = BehaviorDiagramEditorPaletteFactory.createPalette();
		return PALETTE_MODEL;
	}
	
	private void handleLoadException(Exception e) {
		System.err.println("** Load failed. Using default model. **");
		e.printStackTrace();
		rootModel = ModelManager.getFactory().createSubDiagram();
	}

	/**
	 * Set up the editor's inital content (after creation).
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(getModel()); // set the contents of this editor
		
		// add the ShortestPathConnectionRouter
		ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart)viewer.getRootEditPart();
		ConnectionLayer connLayer = (ConnectionLayer)root.getLayer(LayerConstants.CONNECTION_LAYER);
		GraphicalEditPart contentEditPart = (GraphicalEditPart)root.getContents();

		connectionRouter.setSpacing(10);		
		connLayer.setConnectionRouter(connectionRouter);
		connectionRouter.addContainer(contentEditPart.getFigure());		
		
		
//		connectionRouter = new UniShortestConnectionRouter(contentEditPart.getFigure());
//		connectionRouter.setSpacing(8);
//		connLayer.setConnectionRouter(connectionRouter);
		
		
		viewer.addDropTargetListener(createTransferDropTargetListener());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
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
//			ModelDiagram diagram = (ModelDiagram)resource.getContents().get(0);
//			rootModel = diagram.getSubDiagram();
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

	/**
	 * Creates an outline pagebook for this editor.
	 */
	public class ShapesOutlinePage extends ContentOutlinePage implements IAdaptable {	
		private SashForm sash;
		private Control outline;
		private Canvas overview;
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;
		
		private int[] weights;
		
		private boolean isOverviewVisible = false;
		
		private static final int OUTLINE = 0;
		private static final int OVERVIEW = 1;
		private static final String ID_OUTLINE = "outline";
		private static final String ID_OVERVIEW = "overview";
		
		/**
		 * Create a new outline page for the shapes editor.
		 * @param viewer a viewer (TreeViewer instance) used for this outline page
		 * @throws IllegalArgumentException if editor is null
		 */
		public ShapesOutlinePage(EditPartViewer viewer) {
			super(viewer);
		}
		
		protected void configureOutlineViewer() {
			// configure outline viewer
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new ShapeModelTreeEditPartFactory());
			
			// configure & add context menu to viewer
			ContextMenuProvider cmProvider = new BehaviorDiagramContextMenuProvider(
					getViewer(), getActionRegistry()); 
			getViewer().setContextMenu(cmProvider);
			getSite().registerContextMenu(
					BehaviorDiagramContextMenuProvider.MENU_ID,
					cmProvider, getSite().getSelectionProvider());
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);
			outline = getViewer().createControl(sash);
			overview = new Canvas(sash, SWT.NONE);

			weights = sash.getWeights();
			
			configureOutlineViewer();
			hookOutlineViewer();
			initializeOutlineViewer();
			
			getActionRegistry().getAction(ID_OUTLINE).setChecked(true);
			sash.setMaximizedControl(outline);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#dispose()
		 */
		public void dispose() {
			// unhook outline viewer
			unhookOutlineViewer();
			// dispose
			super.dispose();
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#getControl()
		 */
		public Control getControl() {
			return sash;
		}
		
		public void setControl(Object object) {
			// initialize outline viewer with model
			getViewer().setContents(object);	// KJH 20110323
		}
		
		protected void initializeOutlineViewer() {
			setControl(getModel().getParent());
		}
		
		protected void initializeOverview() {
			// KJH 20110323 s,
			weights = sash.getWeights();
			LightweightSystem lws = new LightweightSystem(overview);
			ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart)getGraphicalViewer().getRootEditPart();
			thumbnail = new ScrollableThumbnail((Viewport)root.getFigure());
			thumbnail.setSource(root.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			
			disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			getGraphicalControl().addDisposeListener(disposeListener);
			// KJH 20110323 e,
		}
		
		protected void hookOutlineViewer() {
			getSelectionSynchronizer().addViewer(getViewer());
		}
		
		protected void unhookOutlineViewer() {
			// unhook outline viewer
			getSelectionSynchronizer().removeViewer(getViewer());
			
			// KJH 20110323 s,
			if (getGraphicalControl() != null &&
					!getGraphicalControl().isDisposed() &&
					disposeListener != null) {
				getGraphicalControl().removeDisposeListener(disposeListener);
			}	// KJH 20110323 e,
		}
		
		protected void showPage(int type, boolean b) {
			if (type == OUTLINE) {
				if (!b) {
					if (!isOverviewVisible) {
						getActionRegistry().getAction(ID_OUTLINE).setChecked(true);
						return;
					}
					sash.setMaximizedControl(overview);
				} else {
					sash.setMaximizedControl(null);
				}
			} else if (type == OVERVIEW) {
				if (thumbnail == null) {
					initializeOverview();
				}
				if (!b) {
					sash.setMaximizedControl(outline);
					getActionRegistry().getAction(ID_OUTLINE).setChecked(true);
				} else {
					sash.setMaximizedControl(null);
				}
				isOverviewVisible = b;
			}
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
			
			getViewer().setKeyHandler(keyHandler);

			IToolBarManager tbars = bars.getToolBarManager();
			IAction action = new Action("Outline", IAction.AS_CHECK_BOX) {
				@Override
				public void run() {showPage(OUTLINE, isChecked());}
			};
			action.setId(ID_OUTLINE);
			action.setImageDescriptor(TaskModelPlugin.getImageDescriptor("/icons/outline.gif"));
			tbars.add(action);
			registry.registerAction(action);
			
			action = new Action("Overview", IAction.AS_CHECK_BOX) {
				@Override
				public void run() {showPage(OVERVIEW, isChecked());}
			};
			action.setId(ID_OVERVIEW);
			action.setImageDescriptor(TaskModelPlugin.getImageDescriptor("/icons/overview.gif"));
			tbars.add(action);
			registry.registerAction(action);
		}

		@Override
		public Object getAdapter(Class adapter) {
			if (adapter == ZoomManager.class) {
				return getGraphicalViewer().getProperty(ZoomManager.class.toString());
			}
			return null;
		}
	}

	/**
	 * Creates an outline pagebook for this editor.
	 */
	public class ShapesPropertyPage extends Page implements IPropertySheetPage {	
		private EditPartViewer viewer;
		private Composite control;
		private IWorkbenchPart sourcePart;
		private class PartListener implements IPartListener {
			public void partActivated(IWorkbenchPart part) {
			}

			public void partBroughtToTop(IWorkbenchPart part) {
			}

			public void partClosed(IWorkbenchPart part) {
				if (sourcePart == part) {
					sourcePart = null;
					if (viewer != null && !viewer.getControl().isDisposed()) {
//						viewer.setInput(new Object[0]);
						int a = 0;
					}
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partOpened(IWorkbenchPart part) {
			}
		}
		private PartListener partListener = new PartListener();

		/**
		 * Create a new outline page for the shapes editor.
		 * @param viewer a viewer (TreeViewer instance) used for this outline page
		 * @throws IllegalArgumentException if editor is null
		 */
		public ShapesPropertyPage(EditPartViewer viewer) {
			this.viewer = viewer;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			// create outline viewer page
			control = (Composite)getViewer().createControl(parent);
			// configure outline viewer
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new PropertyEditPartFactory(control));
			// configure & add context menu to viewer
			ContextMenuProvider cmProvider = new BehaviorDiagramContextMenuProvider(
					getViewer(), getActionRegistry()); 
			getViewer().setContextMenu(cmProvider);
			getSite().registerContextMenu(
					BehaviorDiagramContextMenuProvider.MENU_ID,
					cmProvider, getSite().getSelectionProvider());		
			// hook outline viewer
			getSelectionSynchronizer().addViewer(getViewer());
			// initialize outline viewer with model
			getViewer().setContents(getModel());
			// show outline viewer
		}

		/**
		 * @see ISelectionProvider#addSelectionChangedListener(ISelectionChangedListener)
		 */
		public void addSelectionChangedListener(ISelectionChangedListener listener) {
			getViewer().addSelectionChangedListener(listener);
		}
		/**
		 * Convenience method that returns the EditPart corresponding to a given child.
		 * @param child a model element instance
		 * @return the corresponding EditPart or null
		 */
		private EditPart getEditPartForChild(Object child) {
			return (EditPart) getViewer().getEditPartRegistry().get(child);
		}

		/**
		 * Forwards selection request to the viewer.
		 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
		 */
		public ISelection getSelection() {
			//$TODO when could this even happen?
			if (getViewer() == null)
				return StructuredSelection.EMPTY;
			return getViewer().getSelection();
		}

		/**
		 * Returns the EditPartViewer
		 * @return the viewer
		 */
		protected EditPartViewer getViewer() {
			return viewer;
		}

		/**
		 * @see ISelectionProvider#removeSelectionChangedListener(ISelectionChangedListener)
		 */
		public void removeSelectionChangedListener(ISelectionChangedListener listener) {
			getViewer().removeSelectionChangedListener(listener);
		}

		/**
		 * Sets focus to a part in the page.
		 */
		public void setFocus() {
			if (getControl() != null)
				getControl().setFocus();
		}

		/**
		 * @see ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
		 */
		public void setSelection(ISelection selection) {
			if (getViewer() != null)
				getViewer().setSelection(selection);
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
		}

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
				sourcePart = null;
			}
			
			// change the viewer input since the workbench selection has changed.
			if (selection instanceof IStructuredSelection) {
				sourcePart = part;
//				viewer.getControl().setInput(((IStructuredSelection) selection).toArray());
				int a = 0;
			}

			if (sourcePart != null) {
				IWorkbenchPartSite partSite;
				IWorkbenchPage page;
				partSite = sourcePart.getSite();
				page = partSite.getPage();
				page.addPartListener(partListener);
			}
		}
	}
	
	
}