package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TreeItem2;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionProviderAction;

public class MkdirProviderAction extends SelectionProviderAction {

	public final static String actionId = "kr.re.etri.tpl.diagram.views.taskcontrol.actions.MkdirAction";
	private StructuredViewer viewer;
	
	class MkdirInputValidator implements IInputValidator {
		private TreeItem2 parent;
		
		public MkdirInputValidator(TreeItem2 item) {
			this.parent = item;
		}
		
		@Override
		public String isValid(String newText) {
			if (newText == null || newText.length() == 0)
				return "Directory name must be specified";
			for (Object child : parent.getChildren()) {
				if (child.toString().equals(newText)) {
					return "Directory name already exists";
				}
			}
			return null;
		}
	}
	
	public MkdirProviderAction(StructuredViewer viewer, String text) {
		super(viewer, text);
		setId(actionId);
		this.viewer = viewer;
	}
	
	@Override
	public void run() {
		Shell shell = viewer.getControl().getShell();
		IStructuredSelection structuredSelection = getStructuredSelection();
		Object element = structuredSelection.getFirstElement();

		if (!(element instanceof TreeItem2))
			return;

		TreeItem2 parent = (TreeItem2)element;
		if (!parent.isContainer())
			return;

		IInputValidator inputValidator = new MkdirInputValidator(parent);
		InputDialog dlg = new InputDialog(shell, "Mkdir",
				"Input Directory Name :", "new", inputValidator);
		if (IDialogConstants.OK_ID == dlg.open()) {
			String value = dlg.getValue();

			TaskControlManager manager = TaskControlManager.getDefault();
			manager.mkdir(parent, value);
			manager.dirall();
			viewer.setInput(manager.getRoot());
		}

	}
	
	// KJH 20110215 s, enable을 action에서 처리
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof TreeItem2) {
				setEnabled(((TreeItem2)element).isContainer());
				return;
			}
		}
		setEnabled(false);
	}
	// KJH 20110215 e, enable을 action에서 처리
}
