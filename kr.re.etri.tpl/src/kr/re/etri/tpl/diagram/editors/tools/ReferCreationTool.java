package kr.re.etri.tpl.diagram.editors.tools;

import kr.re.etri.tpl.diagram.commands.ReferElementCreateCommand;
import kr.re.etri.tpl.diagram.dialogs.BehaviorSelectionDialog;
import kr.re.etri.tpl.grammar.ItemElementVerify;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

public class ReferCreationTool extends CreationTool {

	@Override
	protected void executeCurrentCommand() {
		DefaultEditDomain domain = (DefaultEditDomain)this.getDomain();
		IEditorPart editor = domain.getEditorPart();
		Shell shell = editor.getSite().getShell();

		if (shell != null) {
			Dimension size = getCreateRequest().getSize();

			BehaviorSelectionDialog dialog = new BehaviorSelectionDialog(shell, editor);
			if (IDialogConstants.OK_ID != dialog.open()) {
				return;
			}

			getCreateRequest().setSize(size);

			Command curCommand = getCurrentCommand();
			if (curCommand instanceof ReferElementCreateCommand && curCommand.canExecute()) {
				ReferElementCreateCommand command = (ReferElementCreateCommand)curCommand;
				ItemElement newShape = dialog.getValue();
				ItemElementVerify.verify(newShape);
				command.setNewShape(newShape);
				command.setIncludedElement(dialog.getIncludedElement());
				
				executeCommand(curCommand);
			}

			setCurrentCommand(null);
		}
	}

}
