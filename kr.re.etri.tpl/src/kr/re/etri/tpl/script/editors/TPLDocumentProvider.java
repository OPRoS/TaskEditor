package kr.re.etri.tpl.script.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

public class TPLDocumentProvider extends TextFileDocumentProvider {
	
	public TPLDocumentProvider(
			TPLParentDocumentProvider parentProvider) {
		super(parentProvider);
	}

	@Override
	public IDocument getDocument(Object element) {
		IDocument document = super.getDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner = new FastPartitioner(	new TPLPartitionScanner(), TPLPartitionScanner.TASK_PARTITION_TYPES);
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}

	@Override
	protected FileInfo createFileInfo(Object element) throws CoreException {
		return super.createFileInfo(element);
	}

}
//public class TPLDocumentProvider extends FileDocumentProvider {
//
//	/** Element information of all connected elements */
//	private static Map fElementInfoMap= new HashMap();	// KJH 20110216, 
//	
//	@Override
//	protected IDocument createDocument(Object element) throws CoreException {
//		IDocument document = super.createDocument(element);
//		if (document != null) {
//			IDocumentPartitioner partitioner = new FastPartitioner(	new TPLPartitionScanner(), TPLPartitionScanner.TASK_PARTITION_TYPES);
//			partitioner.connect(document);
//			document.setDocumentPartitioner(partitioner);
//		}
//		return document;
//	}
//
//	// KJH 20110215 s,
//	@Override
//	protected IAnnotationModel createAnnotationModel(Object element)
//			throws CoreException {
////		if (element instanceof IFileEditorInput) {
////			IFileEditorInput input= (IFileEditorInput) element;
////			return new TPLResourceMarkerAnnotationModel(input.getFile());
////		}
//		return super.createAnnotationModel(element);
//	}
//	// KJH 20110215 e,
//
//	// KJH 20110216 s,
////	@Override
//	protected ElementInfo createElementInfo(Object element)
//			throws CoreException {
//		ElementInfo info = super.createElementInfo(element);
//		
//		if (info != null) {
//			fElementInfoMap.put(element, info);
//		}
//		
//		return info;
//	}
//
//	@Override
//	protected void disposeElementInfo(Object element, ElementInfo info) {
//		fElementInfoMap.remove(element);
//		super.disposeElementInfo(element, info);
//	}
//	// KJH 20110216 e,
//	
//}