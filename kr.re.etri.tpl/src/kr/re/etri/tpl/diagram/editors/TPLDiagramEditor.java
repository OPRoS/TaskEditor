package kr.re.etri.tpl.diagram.editors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.views.ActionNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.BehaviorNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.IModelNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.ITaskNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.ItemPropertySheetPage;
import kr.re.etri.tpl.diagram.views.ModelNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.ProjectNavigatorView;
import kr.re.etri.tpl.diagram.views.TaskNavigatorViewPage;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.provider.TaskModelItemProviderAdapterFactory;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class TPLDiagramEditor extends MultiPageEditorPart implements IResourceChangeListener {
	private static Logger logger = Logger.getLogger(TPLDiagramEditor.class);
	/** Editor에서 최상위 model */
	private ModelDiagram rootModel;

	// KJH 20100831, TPLDiagramEditorResourceManager에서 관리하도록 수정
//	private final Resource resource = new XMLResourceImpl();
	private Resource resource;

	BehaviorDiagramEditor taskEditor;
	
	TaskDiagramEditor taskEditor2;
	
	DefaultEditDomain editDomain;
	
	private MarkerLogger problemLogger;
	
	public TPLDiagramEditor() {
	}

	@Override
	protected void createPages() {
		try {
			int idx;
			
			SubDiagram childDiagram = null;
			childDiagram = rootModel.getSubDiagram();
			if(childDiagram == null) {
				childDiagram = ModelManager.getFactory().createSubDiagram();
				childDiagram.setParent(rootModel);
				rootModel.setSubDiagram(childDiagram);
			}

			taskEditor = new BehaviorDiagramEditor(editDomain);
			DiagramEditorInput input = new DiagramEditorInput(childDiagram);
			idx = this.addPage(taskEditor, input);
			setPageText(idx, "Task Diagram");
			
//			taskEditor2 = new TaskDiagramEditor(editDomain);
//			idx = this.addPage(taskEditor2, input);
//			setPageText(idx, "Task Diagram2");
			
			
//			new TemplateTransferDropTargetListener(taskEditor) {
//				protected CreationFactory getFactory(Object template) {
//					return new SimpleFactory((Class) template);
//				}
//			}
			
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}
	public BehaviorDiagramEditor getBehaviorDiagramEditor(){
		return taskEditor;
	}

	@Override
	public void dispose()
	{
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		DiagramResourceManager.setResource(((IFileEditorInput)getEditorInput()).getFile(), null);
		
		super.dispose();
	}

	/**
	 * Worker에 대한 다이어그램을 오픈한다.
	 * 새로 생성하거나 기존 다이어그램을 오픈한다. 
	 * @param worker
	 * @param cmdStack
	 * @return
	 * @throws PartInitException
	 */
	public int addWorkerDiagramPage(TaskElement worker, CommandStack cmdStack) throws PartInitException {

		this.getSite().getPage().activate(this);

		SubDiagram workerDiagram = null;
		//이미 오픈되어 있는지 확인하여 오픈되어 있다면 활성화 한다.
		int pageCnt = this.getPageCount();
		for(int pageIndex = 0; pageIndex < pageCnt; pageIndex += 1) {
			IEditorPart editPart = this.getEditor(pageIndex);
			if(editPart instanceof TaskDiagramEditor) {
				if(worker == ((TaskDiagramEditor)editPart).getWorkerModel()) {
					setActiveEditor(editPart);
					
					return pageIndex;
				}
			}
		}

		// 이미 생성된 다이어그램이 있다면 오픈한다.
		workerDiagram = worker.getSubDiagram();
		if(workerDiagram == null) {
			workerDiagram = ModelManager.getFactory().createSubDiagram();
			workerDiagram.setParent(worker);
			worker.setSubDiagram(workerDiagram);
		}
		
		DiagramEditorInput input = new DiagramEditorInput(workerDiagram);
		TaskDiagramEditor workerEditor = new TaskDiagramEditor(worker, cmdStack);
		int index = super.addPage(workerEditor, input);
		setPageText(index, "Worker " + worker.getName());
		setActiveEditor(workerEditor);

		return index;
	}

	public void removePage(TaskElement worker) {
		//이미 오픈되어 있는지 확인하여 오픈되어 있다면 활성화 한다.
		int pageCnt = this.getPageCount();
		for(int pageIndex = 0; pageIndex < pageCnt; pageIndex += 1) {
			IEditorPart editPart = this.getEditor(pageIndex);
			if(editPart instanceof TaskDiagramEditor) {
				if(worker == ((TaskDiagramEditor)editPart).getWorkerModel()) {
					removePage(editPart);

					return;
				}
			}
		}
	}

	public void removePage(IEditorPart removePart) {
		int pageCnt = this.getPageCount();
		for(int pageIndex = 0; pageIndex < pageCnt; pageIndex += 1) {
			IEditorPart editPart = this.getEditor(pageIndex);
			if(removePart == editPart) {
				this.removePage(pageIndex);
				return;
			}
		}
	}

	protected void setInput(IEditorInput input) {
		super.setInput(input);
		IFile file = ((IFileEditorInput) input).getFile();
		String fullPath = file.getFullPath().toString();
		
		// KJH 20100831 s, DiagramResourceManager에서 관리하도록 수정
		resource = (XMLResource)DiagramResourceManager.getResource(fullPath);
		// KJH 20100831 e, DiagramResourceManager에서 관리하도록 수정

		// KJH 20101018 s, getResource()에서 처리
//		try {
//			resource.load(Collections.EMPTY_MAP);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// KJH 20101018 e, getResource()에서 처리
		
		EList<EObject> contents = resource.getContents();	// KJH 20101018
		if(contents != null && contents.size() > 0) {
			rootModel = (ModelDiagram)contents.get(0);

			String script = rootModel.getScript();
			if (script != null) {
				DiagramResourceManager.setResource(script, resource);
			}
		}
		
		setPartName(file.getName());
		
		editDomain = new DefaultEditDomain(this);

		problemLogger = new MarkerLogger(file, MarkerLogger.TYPE_DIAGRAM);
	}

	public Resource getResource(){
		return resource;
	}
	public Object getModel() {
		return rootModel;
	}
	
	public MarkerLogger getLogger() {
		return this.problemLogger;
	}
//
//	public IErrorReporter getErrorReporter(Map args) {
//		return errorRepoter;
//	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			resource.save(Collections.EMPTY_MAP);
			getCommandStack().markSaveLocation();

			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow ww = wb.getActiveWorkbenchWindow();
			IWorkbenchPage wp = ww.getActivePage();
			IViewPart vp = wp.findView(ProjectNavigatorView.VIEW_ID);
			if(vp != null) {
				CommonViewer cv = ((CommonNavigator)vp).getCommonViewer();
				cv.refresh();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSaveAs() {
		// Show a SaveAs dialog
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		
		IPath path = dialog.getResult();	
		if (path != null) {
			// try to save the editor's contents under a different file name
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			try {
				new ProgressMonitorDialog(shell).run(
						false, // don't fork
						false, // not cancelable
						new WorkspaceModifyOperation() { // run this operation
							public void execute(final IProgressMonitor monitor) {
								try {
									ByteArrayOutputStream out = new ByteArrayOutputStream();
									createOutputStream(out);
									file.create(
											new ByteArrayInputStream(out.toByteArray()), // contents
											true, // keep saving, even if IFile is out of sync with the Workspace
											monitor); // progress monitor
								}
								catch (CoreException ce) { ce.printStackTrace(); }
								catch (IOException ioe) { ioe.printStackTrace(); } 
							}
						});
				// set input to the new file
				setInput(new FileEditorInput(file));
				getCommandStack().markSaveLocation();
			}
			catch (InterruptedException ie) {
				// should not happen, since the monitor dialog is not cancelable
				ie.printStackTrace(); 
			}
			catch (InvocationTargetException ite) { 
				ite.printStackTrace(); 
			}
		} // if
	}

	public CommandStack getCommandStack() {
		return getEditDomain().getCommandStack();
	}

	public DefaultEditDomain getEditDomain() {
		return editDomain;
	}

	private void createOutputStream(OutputStream os) throws IOException {
		try {
			resource.save(os,Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	public Object getAdapter(Class type) {
		// KJH 20110530 s, BehaviorDiagramEditor.getAdapter()
//		if (type == IContentOutlinePage.class) {
//			return new ModelOutlinePage(new TreeViewer(),
//					getEditDomain(),
//					taskEditor.getActionRegistry(),
//					taskEditor.getSelectionSynchronizer(),
//					(ModelDiagram)getModel());
//		}	// KJH 20110530 s, BehaviorDiagramEditor.getAdapter()
		if(type == IPropertySheetPage.class) {
			ItemPropertySheetPage page;
			page = new ItemPropertySheetPage(getCommandStack());
			return page;
		}
		else if(type == IModelNavigatorViewPage.class) {
			ModelNavigatorViewPage page;
			page = new ModelNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					taskEditor.getSelectionSynchronizer(),
					(ModelDiagram)getModel());
			return page;
		}else if(type == ActionNavigatorViewPage.class){
			ActionNavigatorViewPage page;
			page = new ActionNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					taskEditor.getSelectionSynchronizer(),
					(ModelDiagram)getModel());
			return page;
		}else if(type == ITaskNavigatorViewPage.class){	// KJH 20100512, 인터페이스로 교체
			TaskNavigatorViewPage page;
			page = new TaskNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					taskEditor.getSelectionSynchronizer(),
					(ModelDiagram)getModel());
			return page;
		}else if(type == BehaviorNavigatorViewPage.class){
			BehaviorNavigatorViewPage page;
			page = new BehaviorNavigatorViewPage(new TreeViewer(),
					getEditDomain(),
					taskEditor.getActionRegistry(),
					taskEditor.getSelectionSynchronizer(),
					(ModelDiagram)getModel());
			return page;
		}
		else if(type == TaskModelItemProviderAdapterFactory.class) {
			return new TaskModelItemProviderAdapterFactory();
		}

		return super.getAdapter(type);
	}

	@Override
	protected void handlePropertyChange(int propertyId) {
		super.handlePropertyChange(propertyId);
		IStatusLineManager statusLineManager =
			getEditorSite().getActionBars().getStatusLineManager();
		if (statusLineManager != null) {
			statusLineManager.setErrorMessage("");
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
	}

	private KeyHandler m_sharedKeyHandler;
	protected KeyHandler getSharedKeyHandler()   {
		logger.debug("start");
        if (m_sharedKeyHandler == null)
        {
        	logger.debug("in");
            m_sharedKeyHandler = new KeyHandler();

            // configure common keys for all viewers
            logger.debug("taskEditor.getActionRegistry().getAction(ActionFactory.DELETE.getId())");
            m_sharedKeyHandler.put(
                KeyStroke.getPressed(SWT.DEL, 127, 0),
                taskEditor.getActionRegistry().getAction(ActionFactory.DELETE.getId()));
        }
        return m_sharedKeyHandler;
    }

}
