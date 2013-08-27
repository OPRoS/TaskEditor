package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import kr.re.etri.tpl.diagram.views.taskcontrol.DownloadMessageReader;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TreeItem2;
import kr.re.etri.tpl.script.editors.TPLTextEditorInput;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.ide.IDE;

public class OpenLocalFileAction extends SelectionProviderAction {

	public final static String actionId = "kr.re.etri.tpl.diagram.views.taskcontrol.actions.OpenLocalFileAction";
	
	public OpenLocalFileAction(ISelectionProvider provider, String text) {
		super(provider, text);
		setId(actionId);
	}

	@Override
	public void run() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		final IWorkbenchPage page = window.getActivePage();
		Shell shell = window.getShell();
		Display display = shell.getDisplay();
		
		Object element = getStructuredSelection().getFirstElement();
		if (element instanceof TreeItem2) {
			TreeItem2 item = (TreeItem2)element;
			String name = item.getText();
			String path = item.getPath();
			final TPLTextEditorInput editorInput = new TPLTextEditorInput(name, new Path(path));

			IEditorDescriptor desc = null;
			try {
				desc = IDE.getEditorDescriptor(".tpl");
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			
			if (desc != null) {
				final String editorId = desc.getId();
				DownloadRunnable runnable = new DownloadRunnable() {
					@Override
					public void run() {
						try {
							editorInput.setContent(getContent());
							page.openEditor(editorInput, editorId);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				};
				
				DownloadMessageReader.getDefault().setDisplay(display);
				DownloadMessageReader.getDefault().addRunnable(runnable);
				TaskControlManager.getDefault().download(path);
			}
		}
		
		
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof TreeItem2) {
				setEnabled(!((TreeItem2)element).isContainer());
				return;
			}
		}
		setEnabled(false);
	}

}
