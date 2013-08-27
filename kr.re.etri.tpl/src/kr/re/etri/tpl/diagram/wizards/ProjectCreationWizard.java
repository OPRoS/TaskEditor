package kr.re.etri.tpl.diagram.wizards;

import kr.re.etri.tpl.diagram.builder.ScriptParsingBuilder;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.ide.undo.ResourceDescription;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class ProjectCreationWizard extends BasicNewProjectResourceWizard implements INewWizard {

	private IWorkbench m_workbench;
	private IStructuredSelection m_selection;
	private WizardNewProjectCreationPage m_page;
	
	public ProjectCreationWizard() {
		super();
	}
	
	public void addPages() {
		super.addPages();

		// 추가 Page가 있다면 여기서
	}

	@Override
	public boolean performFinish() {
		if(super.performFinish() == false) {
			return false;
		}

		// New Project Wizard에서 Finish 버튼 추가 처리는 여기서

		ResourceDescription model;
		IResource resource;
		IProject newProject;
		newProject = this.getNewProject();
		resource = (IResource)newProject;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		// KJH 20100712, model, script 폴더 추가되지 않도록 수정
//		try {
//			IFolder modelFolder, scriptFolder;
//
//			modelFolder = newProject.getFolder("models");
//			createFolder(modelFolder);

//			scriptFolder = newProject.getFolder("scripts");
//			createFolder(scriptFolder);
			
			ScriptParsingBuilder.addBuilderToProject(newProject);
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}

		return true;
	}
	
	private void createFolder(IFolder folderHandle)  throws CoreException {		
		try {
			// Create the folder resource in the workspace
			folderHandle.create(false, true, null); //new SubProgressMonitor(monitor, 500));
		}
		catch (CoreException e) {
			// If the folder already existed locally, just refresh to get contents
			if (e.getStatus().getCode() == IResourceStatus.PATH_OCCUPIED)
				folderHandle.refreshLocal(IResource.DEPTH_INFINITE, null); //new SubProgressMonitor(monitor, 500));
			else
				throw e;
		}
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);

		// 초기화시 추가할 내용이 있다면 여기서
	}
}
