package kr.re.etri.tpl.diagram.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * 다이어그래멩서 선택된 Graphical Node에 대한 Properties 뷰 화면을
 * 오픈하는 Action 클래스이다.
 * @author sfline
 *
 */
public class OpenPropertiesAction extends Action implements IHandler {

	/**
	 * A collection of objects listening to changes to this manager. This
	 * collection is <code>null</code> if there are no listeners.
	 */
	private transient ListenerList listenerList = null;

	/**
	 * Creates a new instance of the class.
	 */
	public OpenPropertiesAction() {
		super();
	}

	public final void addHandlerListener(final IHandlerListener listener) {
		if (listenerList == null) {
			listenerList = new ListenerList(ListenerList.IDENTITY);
		}

		listenerList.add(listener);
	}

	public final void dispose() {
		listenerList = null;
	}

	public final Object execute(final ExecutionEvent event)
			throws ExecutionException {

		return null;
	}

	public final void init(final IWorkbenchWindow window) {
		// Do nothing.
	}


	public final void removeHandlerListener(final IHandlerListener listener) {
		if (listenerList != null) {
			listenerList.remove(listener);

			if (listenerList.isEmpty()) {
				listenerList = null;
			}
		}
	}

	public final void run(final IAction action) {
		try {
			execute(new ExecutionEvent());
		} catch (final ExecutionException e) {
			// TODO Do something meaningful and poignant.
		}
	}

	public final void selectionChanged(final IAction action,
			final ISelection selection) {
		// Do nothing.
	}
}
