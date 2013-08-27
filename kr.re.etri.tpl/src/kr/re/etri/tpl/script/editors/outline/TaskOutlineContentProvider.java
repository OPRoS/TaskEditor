package kr.re.etri.tpl.script.editors.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.editors.TPLTextEditorInput;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class TaskOutlineContentProvider implements ITreeContentProvider {
//	private static Logger logger = Logger.getLogger(TaskOutlineContentProvider.class);
	private IScriptNode root = null;
	private IEditorInput input;
	private IDocumentProvider documentProvider;
	private IDocumentProvider oldDocumentProvider;	// KJH 20110916

	protected final static String TAG_POSITIONS = "__tag_positions";
	protected IPositionUpdater positionUpdater = new DefaultPositionUpdater(TAG_POSITIONS);
	
	public TaskOutlineContentProvider(IDocumentProvider provider) {
		super();
		this.documentProvider = provider;
	}
	
	// KJH 20100720, Editor 변경에 따른 documentProvider변경
	public void setDocumentProvider(IDocumentProvider provider) {
		this.oldDocumentProvider = this.documentProvider;	// KJH 20110916
		this.documentProvider = provider;
	}


	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement == input){
			parentElement = root;
		}
		
		if(parentElement instanceof IScriptNode){
			IScriptNode parent = (IScriptNode)parentElement;
			List<IScriptNode> children = parent.getChildren();
			List<IScriptNode> elements = new ArrayList<IScriptNode>();

			// KJH 20110907 s, modify
			for (IScriptNode element : children) {
				if (RFSMParser.RTDL == element.getType()) {
					for (IScriptNode child : element.getChildren()) {
						if (RFSMParser.WORKER2 == child.getType()) {
							elements.add(element);
							break;
						}
					}
				}
			}
			for(IScriptNode element : children){
				if (RFSMParser.INCL == element.getType()) {
//					elements.add(element);
				}
			}	// KJH 20110907 e, modify
			for(IScriptNode element : children){
				if(RFSMParser.WORKER2 == element.getType())
					elements.add(element);
			}
			for(IScriptNode element : children){
				if(RFSMParser.GOTO == element.getType() ||
						RFSMParser.IVK == element.getType())
					elements.add(element);
			}

			return elements.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IScriptNode)
			return ((IScriptNode)element).getParent();
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element == input)
			return true;
		if(element instanceof IScriptNode)
			return getChildren(element).length > 0;
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null) {
			IDocument document = oldDocumentProvider == null ? null :
				oldDocumentProvider.getDocument(oldInput);
			if (document != null) {
				try {
					document.removePositionCategory(TAG_POSITIONS);
				} catch (BadPositionCategoryException x) {
				}
				document.removePositionUpdater(positionUpdater);
			}
		}

		input = (IEditorInput) newInput;

		if (newInput != null /*&& newInput instanceof IFileEditorInput*/) {
			IDocument document = documentProvider.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(TAG_POSITIONS);
				document.addPositionUpdater(positionUpdater);
				
				if (newInput instanceof IFileEditorInput) {
					IFile file = ((IFileEditorInput)newInput).getFile();
					IScriptNode rootElement = ScriptManager.getInstance().getTPLList(file);
					if (rootElement != null) {
						root = rootElement;
					}
				}
				else if (newInput instanceof FileStoreEditorInput) {
					IScriptNode rootElement = ScriptManager.getInstance().getTPLList((IEditorInput)newInput, document);
					if (rootElement != null) {
						root = rootElement;
					}
				}
				else if (newInput instanceof TPLTextEditorInput) {
					IScriptNode rootElement = ScriptManager.getInstance().getTPLList((IEditorInput)newInput, document);
					if (rootElement != null) {
						root = rootElement;
					}
				}
			}
		}
	}

}
