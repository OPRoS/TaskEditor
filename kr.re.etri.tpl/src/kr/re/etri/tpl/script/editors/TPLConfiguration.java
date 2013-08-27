package kr.re.etri.tpl.script.editors;

import kr.re.etri.tpl.script.editors.highlighting.TPLCodeScanner;
import kr.re.etri.tpl.script.editors.highlighting.TPLColorManager;
import kr.re.etri.tpl.script.editors.highlighting.TPLCommentScanner;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class TPLConfiguration extends SourceViewerConfiguration {
	private TPLDoubleClickStrategy doubleClickStrategy;
	private TPLCodeScanner keyworkdScanner;
	private TPLCommentScanner commentScanner;
	private TPLColorManager colorManager;

	public TPLConfiguration(TPLColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			TPLPartitionScanner.TASK_COMMENT,
		};
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new TPLDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected TPLCodeScanner getTaskCodeScanner() {
		if (keyworkdScanner == null) {
			keyworkdScanner = new TPLCodeScanner(colorManager);
		}
		return keyworkdScanner;
	}

	protected TPLCommentScanner getTaskCommentScanner() {
		if (commentScanner == null) {
			commentScanner = new TPLCommentScanner(colorManager);
		}
		return commentScanner;
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
	
		DefaultDamagerRepairer defaultDR =
			new DefaultDamagerRepairer(getTaskCodeScanner());
		reconciler.setDamager(defaultDR, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(defaultDR, IDocument.DEFAULT_CONTENT_TYPE);			
		
		DefaultDamagerRepairer commentDR =
			new DefaultDamagerRepairer(getTaskCommentScanner());
		reconciler.setDamager(commentDR, TPLPartitionScanner.TASK_COMMENT);
		reconciler.setRepairer(commentDR, TPLPartitionScanner.TASK_COMMENT);		
		
		return reconciler;
	}

}