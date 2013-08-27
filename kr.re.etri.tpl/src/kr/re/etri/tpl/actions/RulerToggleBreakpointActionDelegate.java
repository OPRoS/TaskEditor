package kr.re.etri.tpl.actions;

import kr.re.etri.tpl.script.actions.ToggleBreakpointAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

public class RulerToggleBreakpointActionDelegate extends AbstractRulerActionDelegate implements IActionDelegate2  {

	private IEditorPart fEditor = null;
	private ToggleBreakpointAction fDelegate = null;
	
	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (fEditor != null) {
			if (fDelegate != null) {
				fDelegate.dispose();
				fDelegate = null;
			}
		}
		fEditor = targetEditor;
		super.setActiveEditor(action, targetEditor);
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}

	@Override
	protected IAction createAction(ITextEditor editor,
			IVerticalRulerInfo rulerInfo) {
		fDelegate = new ToggleBreakpointAction(editor, rulerInfo);
		return fDelegate;
	}

	@Override
	public void dispose() {
		if (fDelegate != null)
			fDelegate.dispose();
		fDelegate = null;
		fEditor = null;
	}

}
