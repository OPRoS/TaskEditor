package kr.re.etri.tpl.script.editors.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class EditorOutlineContentProvider implements ITreeContentProvider {

//	private static Logger logger = Logger.getLogger(EditorOutlineContentProvider.class);
	private IScriptNode root = null;
	private IEditorInput input;
	private IDocumentProvider documentProvider;
	
	protected final static String TAG_POSITIONS = "__tag_positions";
	protected IPositionUpdater positionUpdater = new DefaultPositionUpdater(TAG_POSITIONS);

	public EditorOutlineContentProvider(IDocumentProvider provider) {
		super();
		this.documentProvider = provider;
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement == input){
			parentElement = root;
		}
		
		if(parentElement instanceof IScriptNode){
			IScriptNode parent = (IScriptNode)parentElement;
			List<IScriptNode> children = parent.getChildren();
			List<IScriptNode> elements = new ArrayList<IScriptNode>();

			if(children != null){
				for(IScriptNode element : children){	// KJH 20110901
					if(RFSMParser.WORKER2 == element.getType())
						elements.add(element);
				}
				for(IScriptNode element : children){
					if(RFSMParser.BEHA == element.getType())
						elements.add(element);
				}
				for (IScriptNode element : children) {	// KJH 20110208 s, connector
					if (RFSMParser.CNT == element.getType())
						elements.add(element);
				}	// KJH 20110208 e, connector
				for (IScriptNode element : children) {	// KJH 20110210 s, construct/destruct
					if (RFSMParser.CONSTR == element.getType() ||
							RFSMParser.DEST == element.getType())
						elements.add(element);
				}	// KJH 20110210 e, construct/destruct
				for(IScriptNode element : children){
					if(RFSMParser.STATE == element.getType())
						elements.add(element);
				}
				for(IScriptNode element : children){
					if(RFSMParser.ACTION == element.getType())
						elements.add(element);
				}
				for(IScriptNode element : children){
					if(RFSMParser.GOTO == element.getType() ||
							RFSMParser.IVK == element.getType())
						elements.add(element);
				}
				for (IScriptNode element : children) {	// KJH 20110210 s, conexer
					if (RFSMParser.RUN == element.getType()) {
						elements.add(element);
					}
				}	// KJH 20110210 e, conexer

				return elements.toArray();
			}
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof IScriptNode)
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
//		if (inputElement instanceof IFileEditorInput) {
//			IFile file = ((IFileEditorInput)inputElement).getFile();
//			root = ScriptManager.getInstance().getTree(file);
//		}
		
		if (root != null){
			return getChildren(root);
		}
		return new Object[0];
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null) {
			IDocument document = documentProvider.getDocument(oldInput);
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

				IScriptNode rootElement = ScriptManager.getInstance().getTree((IEditorInput)newInput, document);
				if (rootElement != null) {
					root = rootElement;
				}
			}
		}
	}

}
