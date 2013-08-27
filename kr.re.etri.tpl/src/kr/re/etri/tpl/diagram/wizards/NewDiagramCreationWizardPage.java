package kr.re.etri.tpl.diagram.wizards;

import kr.re.etri.tpl.IRTMDefines;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

class NewDiagramCreationWizardPage extends NewFileWizardPage {

	private List fileList;
	
	private String[] includeFiles;
	
	public NewDiagramCreationWizardPage(ISelection selection) {
		super(selection);
		setTitle("New Robot Task Model Diagram");
		setDescription("This wizard creates a Robot Task Model Diagram with " +
				"a *."+IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME+". extension. Choose a container and file name for the new" +
				" resource.");

		setFileName("new_diagram."+IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME);
		setFileExtension(IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME);
	}
	
	@Override
	protected Composite createClientArea(Composite parent) {
		Composite basePanel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		basePanel.setLayout(layout);
		
		Composite container = super.createClientArea(basePanel);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayoutData(gridData);
		
		Composite includePanel = new Composite(basePanel, SWT.NONE);
		gridData = new GridData(GridData.FILL_BOTH);
		includePanel.setLayoutData(gridData);

		layout = new GridLayout();
		includePanel.setLayout(layout);

		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		Label label = new Label(includePanel, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 60;
		gridData.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(gridData);
		label.setText("Include:");

		fileList = new List(includePanel, SWT.BORDER|SWT.MULTI);
		gridData = new GridData(GridData.FILL_BOTH);
		fileList.setLayoutData(gridData);

		Composite buttonPanel = new Composite(includePanel, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 68;
		gridData.verticalAlignment = GridData.BEGINNING;
		buttonPanel.setLayoutData(gridData);
		
		FormLayout formLayout = new FormLayout();
//		layout.verticalSpacing = 9;
//		layout.marginLeft = 0;
		buttonPanel.setLayout(formLayout);
		
		FormData formData;
		Button otherBtn, button;
		button = new Button(buttonPanel, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(0, 0);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("B&rowse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleResourceBrowse();
			}
		});
		otherBtn = button;

		button = new Button(buttonPanel, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(otherBtn, 20);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("Re&move...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selIdx[] = fileList.getSelectionIndices();
				fileList.remove(selIdx);
			}
		});
		otherBtn = button;

		button = new Button(buttonPanel, SWT.PUSH|SWT.CENTER);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(otherBtn, 5);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("&Up");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selIdxes[] = fileList.getSelectionIndices();
				fileList.deselectAll();
				int selIdx = selIdxes[0];
				if(0 < selIdx) {
					String selItem = fileList.getItem(selIdx);
					fileList.remove(selIdx);
					selIdx -= 1;
					fileList.add(selItem, selIdx);
					fileList.select(selIdx);
				}
			}
		});
		otherBtn = button;

		button = new Button(buttonPanel, SWT.PUSH|SWT.CENTER);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(otherBtn, 5);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("&Down");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selIdxes[] = fileList.getSelectionIndices();
				fileList.deselectAll();
				int selIdx = selIdxes[0];
				if(selIdx < (fileList.getItemCount() - 1)) {
					String selItem = fileList.getItem(selIdx);
					fileList.remove(selIdx);
					selIdx += 1;
					fileList.add(selItem, selIdx);
					fileList.select(selIdx);
				}
			}
		});
		
		return basePanel;
	}
	
	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleResourceBrowse() {
		IProject project = getProject();
		if(project == null) {
			return;
		}

		ResourceSelectionDialog dialog;
		dialog = new ResourceSelectionDialog(
				getShell(), project,
				"");

		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length > 0) {
				for(int i = 0; i < result.length; i += 1) {
					if((result[i] instanceof IFile) == false) {
						continue;
					}

					if(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(((IFile)result[i]).getFileExtension())) {
						fileList.add(((IFile)result[i]).getFullPath().toString());
					}
					else if(IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equalsIgnoreCase(((IFile)result[i]).getFileExtension())) {
						fileList.add(((IFile)result[i]).getFullPath().toString());
					}
				}
				
				includeFiles = fileList.getItems();
			}
		}
	}
	
	public String[] getIncludeFiles() {
		return includeFiles;
	}
}
