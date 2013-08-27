package kr.re.etri.tpl.diagram.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.diagram.util.TPLUtil;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "text". If a sample editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */
public class BehaviorModelDiagramCreationWizard extends Wizard implements INewWizard {
	private NewDiagramCreationWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for TextEditorWizard.
	 */
	public BehaviorModelDiagramCreationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		page = new NewDiagramCreationWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
		final String []includeFiles = page.getIncludeFiles();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			return false;
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		if (file.exists()) {
			// ������ �����ϸ� ������ ���� �������� ���ϵ��� �Ѵ�.
			return false;
		}

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, includeFiles, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */
	private void doFinish(String containerName, String fileName, String []includeFiles, IProgressMonitor monitor)
			throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
//			InputStream stream = openContentStream();
			String modelName;
			int nameLen = fileName.length();
			if(file.getFileExtension() != null && file.getFileExtension().length() > 0) {
				modelName = fileName.substring(0, nameLen - (file.getFileExtension().length() + 1));
			}
			else {
				modelName = fileName;
			}
			InputStream stream = getInitialContents(modelName, container, includeFiles);
			if (file.exists()) {
				// ������ �̹� �����Ѵٸ� ������ �ʱ�ȭ ��Ų��?
				file.setContents(stream, true, true, monitor);
			} else {
				// ������ �����Ѵ�.
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e) {
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}
	
	private InputStream openContentStream() {
		return new ByteArrayInputStream(new byte[0]);
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "kr.re.etri.tpl", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * This method seems a bit crazy but I couldn't think of another way of doing it
	 * without overriding doFinish() and adding extra work :)
	 * @throws CoreException 
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
/*
	protected InputStream getInitialContents() {
		ByteArrayInputStream bais = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(createDefaultContent()); // argument must be Serializable
			oos.flush();
			oos.close();
			bais = new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bais;
	}
*/
	protected InputStream getInitialContents(String name, IContainer container, String []includeFiles) throws CoreException {
		ModelDiagram diagram = (ModelDiagram) createDefaultContent();
		diagram.setName(name);

		if(includeFiles != null) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = container.getProject();
			
			MarkerLogger logger = new MarkerLogger(project, MarkerLogger.TYPE_DIAGRAM);
			for(int idx = 0; idx < includeFiles.length; idx += 1) {
				IResource resource = root.findMember(new Path(includeFiles[idx]));
				if (resource.exists() == false) {
					logger.warning(String.format("%s ������ �������� �ʽ��ϴ�.", includeFiles[idx]), -1, -1, -1);
					continue;
				}
				else if((resource instanceof IFile) == false) {
					logger.warning(String.format("���õ� Resource�� ������ �ƴմϴ�."), -1, -1, -1);
					continue;
				}

				IResource res = root.findMember(includeFiles[idx]);

				if(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(res.getFileExtension())) {
					IncludedElement incElmt;
					try {
						ModelDiagram modelDiagram;
						MarkerLogger markerLogger = new MarkerLogger(res, MarkerLogger.TYPE_SCRIPT);
						modelDiagram = ParserUtil.parseModel((IFile)res, markerLogger);
						if(modelDiagram == null || logger.hasError()) {
							continue;
						}

						incElmt = TPLUtil.convertTo(modelDiagram);
						incElmt.setIncludePath(res.getFullPath().toString());
						diagram.getIncludeItems().add(incElmt);

//						RTMUtil.mergeModel(modelDiagram, diagram);
					} catch (Exception e) {
						TPLErrorLogger.error(e.getMessage(), e);
						e.printStackTrace();
					}
				}
				// RTM ������ Include�ϴ� ��� RTM ���ϳ� ������ �ߺ��Ǵ� ���
				// ��� �����Ͽ��� �ϹǷ� �Ұ�
				// �ߺ��� �����Ѵٸ� �Ʒ� �ּ��� �����Ͽ��� ��.
/*				
				else if(IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equalsIgnoreCase(res.getFileExtension())) {
					XMLResource incRes = new XMLResourceImpl();
					URI uri = URI.createPlatformResourceURI(includeFiles[idx]);
					incRes.setURI(uri);
					try {
						incRes.load(Collections.EMPTY_MAP);
					} catch (IOException e) {
						RTMErrorLogger.error(e);
						e.printStackTrace();
					}
					if(incRes.getContents().size() > 0) {
						ModelDiagram incModel = (ModelDiagram)incRes.getContents().get(0);

						// Merge�� �ƴ� Include��� IncludedElement RTMUtil.converTo(ModelDiagram)��
						// �̿��Ͽ� ������ �Ŀ� ModelDiagram.getIncluedItems.add();�� �̿��Ѵ�.
						RTMUtil.mergeModel(incModel, diagram);
					}
				}

*/			}
		}
		
		XMLResource resource = new XMLResourceImpl();
		resource.getContents().add(diagram);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			resource.save(out,Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return in;
	}

	/** Return a new ShapesDiagram instance. */
	private Object createDefaultContent() {
		return ModelManager.getFactory().createModelDiagram();
	}
}