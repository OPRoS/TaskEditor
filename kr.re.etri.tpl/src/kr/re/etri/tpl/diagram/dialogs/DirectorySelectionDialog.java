package kr.re.etri.tpl.diagram.dialogs;

import org.eclipse.core.resources.IContainer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class DirectorySelectionDialog extends ContainerSelectionDialog {

	public DirectorySelectionDialog(Shell parentShell, IContainer initialRoot,
			boolean allowNewContainerName, String message) {
		super(parentShell, initialRoot, allowNewContainerName, message);
		// TODO Auto-generated constructor stub
	}

}
