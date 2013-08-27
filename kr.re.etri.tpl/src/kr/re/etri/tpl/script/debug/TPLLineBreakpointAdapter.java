package kr.re.etri.tpl.script.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension4;
import org.eclipse.ui.texteditor.IEditorStatusLine;
import org.eclipse.ui.texteditor.ITextEditor;

public class TPLLineBreakpointAdapter implements IToggleBreakpointsTargetExtension {

	protected IVerticalRulerInfo fRulerInfo = null;

	@Override
	public boolean canToggleBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return canToggleLineBreakpoints(part, selection);
	}

	@Override
	public boolean canToggleLineBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		TPLScriptEditor textEditor = getEditor(part);

		if (textEditor != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public void toggleBreakpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		toggleLineBreakpoints(part, selection);
	}

	@Override
	public void toggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection)
	throws CoreException {
		final ITextEditor editor = getTextEditor(part);
		final IDocument document = getDocument(editor);
		
		Job job = new Job("Toggle Line Breakpoint") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (editor != null && selection instanceof ITextSelection) {
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					ITextSelection textSelection = (ITextSelection)selection;

					int lineNumber = -1;
					try {
						try {
							lineNumber = document.getLineOfOffset(textSelection.getOffset());
						} catch (BadLocationException e) {}

						if (lineNumber >= 0) {
							IResource resource = getResource(editor);
							AbstractMarkerAnnotationModel model = getAnnotationModel(editor);
							IBreakpoint[] breakpoints = getBreakpoints(resource, document, model, lineNumber);
							IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();

							IScriptNode root = getScriptRoot(getFile(editor), document);
							IScriptNode child = root.getNextChildByOffset(textSelection.getOffset());

							if (breakpoints.length > 0) {
								for (int i=0; i<breakpoints.length; i++) {
									breakpoints[i].getMarker().delete();
									breakpointManager.removeBreakpoint(breakpoints[i], true);
									
									DebugManager dManager = DebugManager.getDefault();
									if (dManager.isRunning()) {
										// KJH 20110315 s, 현재 디버깅중인 프로젝트와 다를 경우 breakpoint를 전송하지 않음
										IProject project = resource.getProject();
										if (project != null && project.equals(dManager.getCurProject())) {
											String file = resource.getFullPath().removeFirstSegments(1).toString();
											dManager.remove(null, file, child.getFullPath(), lineNumber + 1);
										}
										// KJH 20110315 e, 현재 디버깅중인 프로젝트와 다를 경우 breakpoint를 전송하지 않음
									}
								}
							}
							else {
								createBreakpoints(editor, lineNumber + 1, child);
							}
						}
					} catch (CoreException ce) {
						return ce.getStatus();
					}

				}
				report("", part);
				return Status.OK_STATUS;
			}
		};
        job.setPriority(Job.INTERACTIVE);
        job.setSystem(true);
        job.schedule();
	}

	@Override
	public void toggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection)
	throws CoreException {

	}

	@Override
	public void toggleWatchpoints(final IWorkbenchPart part, final ISelection selection)
	throws CoreException {

	}

	protected ITextEditor getTextEditor(IWorkbenchPart part) {
		if (part instanceof ITextEditor) {
			return (ITextEditor)part;
		}
		return (ITextEditor)part.getAdapter(ITextEditor.class);
	}

	protected ISelection translateToMember(IWorkbenchPart part, ISelection selection) throws CoreException {
		ITextEditor textEditor = getTextEditor(part);
		if (textEditor != null && selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection)selection;
			IDocument document = getDocument(textEditor);
			int offset = textSelection.getOffset();
			if (document != null) {
				try {
					IRegion region = document.getLineInformationOfOffset(offset);
					int end = region.getOffset() + region.getLength();
					while (Character.isWhitespace(document.getChar(offset)) && offset < end) {
						offset++;
					}
				} catch (BadLocationException e) {}
			}

			IScriptNode rootNode = getScriptRoot(getFile(textEditor), document);
			if (rootNode instanceof IScriptRootNode) {
				IScriptNode childNode = rootNode.getChildByOffset(offset);
				if (childNode != null) {
					return new StructuredSelection(childNode);
				}
			}

		}
		return selection;
	}

	protected IScriptNode getScriptRoot(IFile file, IDocument document) {
		IScriptNode root = ScriptManager.getInstance().getTree(file, document);
		if (root instanceof IScriptRootNode) {
			return (IScriptRootNode)root;
		}
		return null;
	}

	protected String getContentType(ITextEditor editor, IDocument document) {
		String contentType = null;

		IDocumentProvider provider = editor.getDocumentProvider();
		if (provider instanceof IDocumentProviderExtension4) {
			try {
				IContentType type = ((IDocumentProviderExtension4)provider).getContentType(editor.getEditorInput());
				if (type != null)
					contentType = type.getId();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		if (contentType == null) {

		}

		return contentType;
	}

	public static final String getFileExtension(IEditorInput input) {
		IPath path = null;
		if (input instanceof IStorageEditorInput) {
			try {
				path = ((IStorageEditorInput)input).getStorage().getFullPath();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		if (path != null) {
			return path.getFileExtension();
		}

		String name = input.getName();
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return null;
		}
		if (index == (name.length() - 1)) {
			return "";
		}
		return name.substring(index + 1);
	}

	protected boolean createBreakpoints(ITextEditor editor, int lineNumber, IScriptNode node) {
//		IEditorInput input = editor.getEditorInput();
//		IDocument document = editor.getDocumentProvider().getDocument(input);
//		if (document == null)
//			return false;

		try {
			Map attributes = new HashMap(10);
			IResource resource = getResource(editor);
			TPLBreakpoint breakpoint = new TPLBreakpoint(resource, lineNumber, node, attributes);
			IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
			breakpointManager.addBreakpoint(breakpoint);
			
			// KJH 20110225 s, registry
			DebugManager dManager = DebugManager.getDefault();
			if (dManager.isRunning()) {
				// KJH 20110315 s, 현재 디버깅중인 프로젝트와 다를 경우 breakpoint를 전송하지 않음
				IProject project = resource.getProject();
				if (project != null && project.equals(dManager.getCurProject())) {
					String file = resource.getFullPath().removeFirstSegments(1).toString();
					dManager.registry(null, file, node.getFullPath(), lineNumber);
				}
				// KJH 20110315 e, 현재 디버깅중인 프로젝트와 다를 경우 breakpoint를 전송하지 않음
			}
			// KJH 20110225 e, registry
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	protected IBreakpoint[] getBreakpoints(IMarker[] markers) {
		IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		List<IBreakpoint> breakpoints = new ArrayList<IBreakpoint>(markers.length);
		for (int i=0; i<markers.length; i++) {
			IBreakpoint breakpoint = manager.getBreakpoint(markers[i]);
			if (breakpoint != null) {
				breakpoints.add(breakpoint);
			}
		}
		return (IBreakpoint[])breakpoints.toArray(new IBreakpoint[0]);
	}

	protected IDocument getDocument(ITextEditor editor) {
		IDocumentProvider provider = editor.getDocumentProvider();
		return provider.getDocument(editor.getEditorInput());
	}

	protected IMarker[] getMarkers(ITextEditor editor) {
		List<IMarker> markers = new ArrayList<IMarker>();
		IResource resource = getResource(editor);
		IDocument document = getDocument(editor);
		AbstractMarkerAnnotationModel annotationModel = getAnnotationModel(editor);

		if (resource != null && annotationModel != null && resource.exists()) {
			try {
				IMarker[] allMarkers = resource.findMarkers(TPLBreakpoint.MARKER_TYPE, true, IResource.DEPTH_ZERO);
				if (allMarkers != null) {
					for (int i=0; i<allMarkers.length; i++) {
						if (includesRulerLine(editor, annotationModel.getMarkerPosition(allMarkers[i]), document))
							markers.add(allMarkers[i]);
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return (IMarker[])markers.toArray(new IMarker[0]);
	}

	protected boolean includesRulerLine(ITextEditor editor, Position position, IDocument document) {
		return false;
	}

	private IBreakpoint[] getBreakpoints(IResource resource, IDocument document,
			AbstractMarkerAnnotationModel model, int lineNumber) {
		List<IMarker> markers = new ArrayList<IMarker>();
		if (document != null && resource != null && model != null && resource.exists()) {
			try {
				IMarker[] allMarkers = resource.findMarkers(/*IBreakpoint.BREAKPOINT_MARKER*/
						TPLBreakpoint.MARKER_TYPE, true, IResource.DEPTH_ZERO);
				if (allMarkers != null) {
					for (int i=0; i<allMarkers.length; i++) {
						Position p = model.getMarkerPosition(allMarkers[i]);
						if (p == null)	// KJH 20110214,
							continue;
						int markerLine = -1;
						markerLine = document.getLineOfOffset(p.getOffset());
						if (markerLine == lineNumber) {
							markers.add(allMarkers[i]);
						}
					}
				}
			} catch (BadLocationException e) {
			} catch (CoreException e) {
			}

			return getBreakpoints((IMarker[])markers.toArray(new IMarker[0]));
		}
		return null;
	}

	protected AbstractMarkerAnnotationModel getAnnotationModel(ITextEditor editor) {
		IDocumentProvider provider = editor.getDocumentProvider();
		IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());

		if (model instanceof AbstractMarkerAnnotationModel) {
			return (AbstractMarkerAnnotationModel)model;
		}
		return null;
	}

	private TPLScriptEditor getEditor(IWorkbenchPart part) {
		if (part instanceof TPLScriptEditor) {
			return (TPLScriptEditor)part;
		}
		return null;
	}

	public final IResource getResource(IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();
		IResource resource = (IResource)editorInput.getAdapter(IFile.class);
		return resource;
	}
	
	public final IFile getFile(IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();
		IFile resource = null;
		if (editorInput instanceof IFileEditorInput) {
			resource = ((IFileEditorInput)editorInput).getFile();
		}
		if (resource == null) {
			resource = (IFile)editorInput.getAdapter(IFile.class);
		}

		return resource;
	}

    /**
     * Convenience method for printing messages to the status line
     * @param message the message to be displayed
     * @param part the currently active workbench part
     */
    protected void report(final String message, final IWorkbenchPart part) {
        Runnable results = new Runnable() {
            public void run() {
                IEditorStatusLine statusLine = (IEditorStatusLine) part.getAdapter(IEditorStatusLine.class);
                if (statusLine != null) {
                    if (message != null) {
                        statusLine.setMessage(true, message, null);
                    } else {
                        statusLine.setMessage(true, null, null);
                    }
                }
            }
        };
        PlatformUI.getWorkbench().getDisplay().asyncExec(results);
    }
}
