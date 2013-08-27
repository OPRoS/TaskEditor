package kr.re.etri.tpl.script.editors.wizard;

import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;

public class TPLFileWizard implements INewWizard{
	BasicNewFileResourceWizard fileWarzard ;
	
	public TPLFileWizard(){
		fileWarzard = new BasicNewFileResourceWizard();
	}
	

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		fileWarzard.init(workbench, selection);		
	}

	@Override
	public void addPages() {
		fileWarzard.setWindowTitle("TPL File");
		fileWarzard.addPages();
		IWizardPage[] pages = fileWarzard.getPages();
		ImageDescriptor ides = TaskModelPlugin.getImageDescriptor("/icons/robot_prj.jpg");
		
		for(int i = 0 ; i < pages.length;i++){
			pages[i].setTitle("TPL File");			
			pages[i].setImageDescriptor(ides);
		}
	}

	@Override
	public boolean canFinish() {
		return fileWarzard.canFinish();
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		fileWarzard.createPageControls(pageContainer);		
	}

	@Override
	public void dispose() {
		fileWarzard.dispose();
	}

	@Override
	public IWizardContainer getContainer() {
		return fileWarzard.getContainer();
	}

	@Override
	public Image getDefaultPageImage() {
		return fileWarzard.getDefaultPageImage();
	}

	@Override
	public IDialogSettings getDialogSettings() {
		return fileWarzard.getDialogSettings();
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return fileWarzard.getNextPage(page);
	}

	@Override
	public IWizardPage getPage(String pageName) {
		return fileWarzard.getPage(pageName);
	}

	@Override
	public int getPageCount() {
		return fileWarzard.getPageCount();
	}

	@Override
	public IWizardPage[] getPages() {
		return fileWarzard.getPages();
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		return fileWarzard.getPreviousPage(page);
	}

	@Override
	public IWizardPage getStartingPage() {
		return fileWarzard.getStartingPage();
	}

	@Override
	public RGB getTitleBarColor() {
		return fileWarzard.getTitleBarColor();
	}

	@Override
	public String getWindowTitle() {
		return fileWarzard.getWindowTitle();
	}

	@Override
	public boolean isHelpAvailable() {
		return fileWarzard.isHelpAvailable();
	}

	@Override
	public boolean needsPreviousAndNextButtons() {
		return fileWarzard.needsPreviousAndNextButtons();
	}

	@Override
	public boolean needsProgressMonitor() {
		return fileWarzard.needsProgressMonitor();
	}

	@Override
	public boolean performCancel() {
		return fileWarzard.performCancel();
	}

	@Override
	public boolean performFinish() {
		return fileWarzard.performFinish();
	}

	@Override
	public void setContainer(IWizardContainer wizardContainer) {
		fileWarzard.setContainer(wizardContainer);
		
	}	
}
