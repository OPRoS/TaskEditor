package kr.re.etri.tpl.script.editors;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.StorageDocumentProvider;

public class TPLParentDocumentProvider extends StorageDocumentProvider {

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		if (element instanceof TPLTextEditorInput) {
			TaskControlManager manager = TaskControlManager.getDefault();
			if (manager.isConnected() == false) {
				return;
			}

			TPLTextEditorInput editorInput = (TPLTextEditorInput)element;
			IPath path = editorInput.getPath();
			String content = document.get();
			
			manager.upload(content, path.toString());

		}
	}

}
