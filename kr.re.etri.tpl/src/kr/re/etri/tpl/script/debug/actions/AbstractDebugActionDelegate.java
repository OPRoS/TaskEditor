package kr.re.etri.tpl.script.debug.actions;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.IPostStateChangedListender;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public abstract class AbstractDebugActionDelegate implements IWorkbenchWindowActionDelegate,
IActionDelegate2, IPostStateChangedListender {

	protected IAction fAction;
	protected IWorkbenchWindow fWindow;

	@Override
	public void dispose() {
		DebugManager.getDefault().removePostStateChangedListener(this);	// KJH 20111125
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.fWindow = window;
	}

	@Override
	public void init(IAction action) {
		DebugManager.getDefault().addPostStateChangedListener(this);
		fAction = action;
		update();
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		update();
	}
	
	@Override
	public void stateChanged() {
		update();
	}

	protected void update() {
		
	}
}
