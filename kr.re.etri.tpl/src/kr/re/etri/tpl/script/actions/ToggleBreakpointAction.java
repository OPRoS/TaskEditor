package kr.re.etri.tpl.script.actions;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.diagram.util.MarkerLogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class ToggleBreakpointAction extends Action implements IUpdate {

	private IVerticalRulerInfo fRuler;
	private ITextEditor fTextEditor;
	
	public ToggleBreakpointAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		super("Toggle Brea&kpoint");
		
		fTextEditor = editor;
		fRuler = rulerInfo;
	}
	
	private IDocument getDocument() {
		IDocumentProvider provider = fTextEditor.getDocumentProvider();
		if (provider != null)
			return provider.getDocument(fTextEditor.getEditorInput());
		
		return (IDocument) fTextEditor.getAdapter(IDocument.class);
	}
	
	private IResource getResource() {
		IEditorInput editorInput = fTextEditor.getEditorInput();
		IResource resource = (IResource)editorInput.getAdapter(IFile.class);
		return resource;
	}
	
	private ITextSelection getTextSelection(IDocument document, int line) throws BadLocationException {
		IRegion region = document.getLineInformation(line);
		ITextSelection textSelection = new TextSelection(document, region.getOffset(), 0);
		ISelectionProvider provider = fTextEditor.getSelectionProvider();
		if (provider != null){
			ISelection selection = provider.getSelection();
			if (selection instanceof ITextSelection
					&& ((ITextSelection) selection).getStartLine() <= line
					&& ((ITextSelection) selection).getEndLine() >= line) {
				textSelection = (ITextSelection) selection;
			} 
		}
		return textSelection;
	}
	
	@Override
	public void run() {
		IDocument document = getDocument();
		IResource resource = getResource();
		if (document == null || resource == null) {
			return;
		}

		int line = fRuler.getLineOfLastMouseButtonActivity();
		if (line == -1) {
			return;
		}
		
		try {
			IMarker[] markers = resource.findMarkers(MarkerLogger.TYPE_SCRIPT, true, IResource.DEPTH_ZERO);
			boolean isExist = false;
			if (markers != null) {
				for (int i=0; i<markers.length; i++) {
					Object obj = markers[i].getAttribute(IMarker.LINE_NUMBER);
					if (Integer.valueOf(line+1).equals(obj)) {
						isExist = true;
						markers[i].delete();
						break;
					}
						
				}
			}
			
			if (!isExist) {
				ITextSelection selection = getTextSelection(document, line);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(IMarker.LOCATION, resource.getFullPath().toString());
				map.put(IMarker.LINE_NUMBER, selection.getEndLine() + 1);
				map.put(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);

				MarkerUtilities.createMarker(resource, map, MarkerLogger.TYPE_SCRIPT);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update() {
		
	}

	public void dispose() {
		
	}
	
}
