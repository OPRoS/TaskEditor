package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.editors.DiagramResourceManager;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.text.IDocument;

public class FileManager {
	private static Map<Object, IDocument> map = new HashMap<Object, IDocument>();
	
	public FileManager(){
		
	}
	
	public static IDocument getDocument(Object object) {
		if(map.containsKey(object))
			return map.get(object);

		IDocument document = null;
		if(object instanceof IFile){
			IFile file = (IFile)object;
			IPath location = file.getFullPath();
			LocationKind locationKind = LocationKind.IFILE;
//			IEditorInput editorInput = new FileEditorInput((IFile)object);
//			IDocumentProvider provider = new TPLDocumentProvider();
//			try {
//				provider.connect(editorInput);
//			} catch (CoreException e) {
//				return null;
//			}	// KJH 20110216,
//			document = provider.getDocument(editorInput);
//			document = new Document();
			ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
			try {
				IFileBuffer fileBuffer = manager.getFileBuffer(location, locationKind);
				if (fileBuffer == null) {
					manager.connect(location, locationKind, null);
					fileBuffer = manager.getFileBuffer(location, locationKind);
				}
				if (fileBuffer instanceof ITextFileBuffer) {
					document = ((ITextFileBuffer)fileBuffer).getDocument();
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		if(document != null)
			map.put(object, document);
		
		return document;
	}
	
	public static List<IFile> getFiles(IContainer parent, String ext){
		List<IFile> list = new ArrayList<IFile>();
		
		try {
			if(parent == null || !parent.exists() || !parent.isAccessible())
				return list;
			
			for(IResource resource : parent.members()){
				if(resource instanceof IContainer){
					list.addAll(getFiles((IContainer)resource, ext));
				}
				else if(resource instanceof IFile){
					IFile file = (IFile)resource;
					if(ext == null || ext.equals(""))
						list.add((IFile)resource);
					else if(file.getFileExtension().equals(ext))
						list.add((IFile)resource);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public static IFile convertTPLFile(IFile file) {
		IProject project = file.getProject();
		String ext = file.getFileExtension();
		if (IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equalsIgnoreCase(ext)) {
			Resource resource = DiagramResourceManager.getResource(file);
			
			EList<EObject> contents = resource.getContents();	// KJH 20101018
			if (contents != null && contents.size() > 0) {
				ModelDiagram modelDiagram = (ModelDiagram)contents.get(0);
				String scriptPath = modelDiagram.getScript();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource result = root.findMember(new Path(scriptPath));
				if (result instanceof IFile)
					return (IFile)result;
			}
		}
		else if (IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(ext)) {
			List<IFile> files = getFiles(project, IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME);
			for (IFile f : files) {
				if (file.equals(convertTPLFile(f)))
					return f;
			}
		}
		
		return null;
	}
}
