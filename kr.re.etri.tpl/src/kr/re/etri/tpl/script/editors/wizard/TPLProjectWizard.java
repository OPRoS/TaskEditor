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
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class TPLProjectWizard implements INewWizard{
	BasicNewProjectResourceWizard prjWizard;
	@Override
	public boolean performFinish() {
		return prjWizard.performFinish();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {		
		prjWizard = new BasicNewProjectResourceWizard();
		prjWizard.init(workbench, selection);
	}
	public void addPages(){
		prjWizard.setWindowTitle("TPL Project");
		prjWizard.addPages();
		
		IWizardPage[] pages = prjWizard.getPages();
		ImageDescriptor ides = TaskModelPlugin.getImageDescriptor("/icons/robot_prj.jpg");
		
		for(int i = 0 ; i < pages.length;i++){
			pages[i].setTitle("TPL Project");
			
			pages[i].setImageDescriptor(ides);
		}		
	}

	@Override
	public boolean canFinish() {
		return prjWizard.canFinish();
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		prjWizard.createPageControls(pageContainer);
		
	}

	@Override
	public void dispose() {
		prjWizard.dispose();
		
	}

	@Override
	public IWizardContainer getContainer() {
		return prjWizard.getContainer();
	}

	@Override
	public Image getDefaultPageImage() {
		return prjWizard.getDefaultPageImage();
	}

	@Override
	public IDialogSettings getDialogSettings() {
		return prjWizard.getDialogSettings();
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return prjWizard.getNextPage(page);
	}

	@Override
	public IWizardPage getPage(String pageName) {
		return prjWizard.getPage(pageName);
	}

	@Override
	public int getPageCount() {
		return prjWizard.getPageCount();
	}

	@Override
	public IWizardPage[] getPages() {
		return prjWizard.getPages();
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		return prjWizard.getPreviousPage(page);
	}

	@Override
	public IWizardPage getStartingPage() {
		return prjWizard.getStartingPage();
	}

	@Override
	public RGB getTitleBarColor() {
		return prjWizard.getTitleBarColor();
	}

	@Override
	public String getWindowTitle() {
		return prjWizard.getWindowTitle();
	}

	@Override
	public boolean isHelpAvailable() {
		return prjWizard.isHelpAvailable();
	}

	@Override
	public boolean needsPreviousAndNextButtons() {
		return prjWizard.needsPreviousAndNextButtons();
	}

	@Override
	public boolean needsProgressMonitor() {
		return prjWizard.needsProgressMonitor();
	}

	@Override
	public boolean performCancel() {
		return prjWizard.performCancel();
	}

	@Override
	public void setContainer(IWizardContainer wizardContainer) {
		prjWizard.setContainer(wizardContainer);		
	}

}
