package kr.re.etri.tpl.script.editors.outline;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import kr.re.etri.tpl.grammar.RFSMLexer;
import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class OutlineContentProvider implements ITreeContentProvider {
	private static Logger logger = Logger.getLogger(OutlineContentProvider.class);
	private ITPLTree root = null;
//	private IEditorInput input;
//	private IDocument input;

	protected final static String TAG_POSITIONS = "__tag_positions";
	protected IPositionUpdater positionUpdater = new DefaultPositionUpdater(
			TAG_POSITIONS);

	public OutlineContentProvider() {
		super();
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement == root) {
			if (root == null)
				return new Object[0];
			List<ITPLTree> children = root.getChildren();
			if (children != null)
				return children.toArray();
		} else {
			ITPLTree parent = (ITPLTree) parentElement;
			List<ITPLTree> children = parent.getChildren();
			if (children != null)
				return children.toArray();
		}
		return new Object[0];
	}

	public Object getParent(Object element) {
		if (element instanceof ITPLTree)
			return ((ITPLTree) element).getParent();
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element == root)
			return true;
		else {
			return ((ITPLTree) element).getChildCount() > 0;
		}
	}

	public Object[] getElements(Object inputElement) {
		if (root == null)
			return new Object[0];
		List<ITPLTree> childrenDTDElements = root.getChildren();
		if (childrenDTDElements != null)
			return childrenDTDElements.toArray();
		return new Object[0];
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

//		if (oldInput != null) {
//			// KJH 20100507, TreeViewer의 input을 IEditorInput->IDocument로 변경
////			IDocument document = documentProvider.getDocument(oldInput);
//			IDocument document = (IDocument)oldInput;
//			if (document != null) {
//				try {
//					document.removePositionCategory(TAG_POSITIONS);
//				} catch (BadPositionCategoryException x) {
//				}
//				document.removePositionUpdater(positionUpdater);
//			}
//		}
//
//		input = (IDocument) newInput;
//
//		if (newInput != null) {
//			// KJH 20100507, TreeViewer의 input을 IEditorInput->IDocument로 변경
////			IDocument document = documentProvider.getDocument(newInput);
//			IDocument document = (IDocument)newInput;
//			if (document != null) {
//				document.addPositionCategory(TAG_POSITIONS);
//				document.addPositionUpdater(positionUpdater);
//
//				ITPLTree rootElement = parseRootElement(document);
//				if (rootElement != null) {
//					root = rootElement;
//				}
//			}
//		}
		root = (ITPLTree)newInput;
	}

	private ITPLTree parseRootElement(IDocument document) {
		String text = document.get();
		ITPLTree tagPositions = parseRootElements(text, document);
		return tagPositions;
	}

	private ITPLTree parseRootElements(String text, IDocument document) {
		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(new ByteArrayInputStream(text
					.getBytes()));
		} catch (IOException e1) {
			logger.warn("Can not create ANTLRInputStream", e1);
			return null;
		}
		RFSMLexer lexer = new RFSMLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RFSMParser parser = new RFSMParser(tokens);
		try {
			RFSMParser.rfsm_return ret = parser.rfsm();
	
			CommonTree tree = (CommonTree) ret.getTree();
			ITPLTree node =OutlineTreeParser.creatTree(tree);
			
			// KJH 20100507 s, Behavior 이외는 제외
//			for(int i=0; i<node.getChildCount(); i++){
//				ITPLTree child = node.getChildren().get(i);
//
//				if(!ITPLTree.BEHAVIOR.equals(child.getType())){
//					node.removeChild(child);
//					i--;
//				}
//			}
			// KJH 20100507 e, Behavior 이외는 제외
			
			return node;
		} catch (Exception e) {
			logger.warn("",e);
			return null;
		}
	}

}
