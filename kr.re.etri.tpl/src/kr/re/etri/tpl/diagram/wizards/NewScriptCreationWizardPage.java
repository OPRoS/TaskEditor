package kr.re.etri.tpl.diagram.wizards;

import kr.re.etri.tpl.IRTMDefines;

import org.eclipse.jface.viewers.ISelection;

public class NewScriptCreationWizardPage extends NewFileWizardPage {

	public NewScriptCreationWizardPage(ISelection selection) {
		super(selection);
		setTitle("New Task Script");
		setDescription("This wizard creates a Task Script with " +
				"a *."+IRTMDefines.TASK_SCRIPT_EXTENSION_NAME+". extension. Choose a container and file name for the new" +
				" resource.");

		setFileName("new_script."+IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
		setFileExtension(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
	}
}
