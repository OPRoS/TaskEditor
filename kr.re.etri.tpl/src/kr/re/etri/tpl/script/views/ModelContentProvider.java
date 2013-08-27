package kr.re.etri.tpl.script.views;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import kr.re.etri.tpl.grammar.RFSMLexer;
import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class ModelContentProvider implements ITreeContentProvider {
	private TPLTree root = null;
	private IEditorInput input;
	private IDocumentProvider documentProvider;	
	protected final static String TAG_POSITIONS = "_tag_positions";
	protected IPositionUpdater positionUpdater = new DefaultPositionUpdater(
			TAG_POSITIONS);
	
	public ModelContentProvider(IDocumentProvider docProvider){
		super();
		documentProvider = docProvider;
	}
	public void setDocumentProvider(IDocumentProvider docProvider){
		documentProvider = docProvider;
	}
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement == input) {
			if (root == null)
				return new Object[0];
			List<ITPLTree> children = root.getChildren();
			if (children != null)
				return children.toArray();
		} else {
			TPLTree parent = (TPLTree) parentElement;
			List<ITPLTree> children = parent.getChildren();
			if (children != null)
				return children.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof TPLTree)
			return ((TPLTree) element).getParent();
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element == input)
			return true;
		else {
			return ((TPLTree) element).getChildCount() > 0;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (root == null)
			return new Object[0];
		List<ITPLTree> children = root.getChildren();
		if (children != null)
			return children.toArray();
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

		if (newInput != null) {
			IDocument document = documentProvider.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(TAG_POSITIONS);
				document.addPositionUpdater(positionUpdater);

				TPLTree rootElement = parseRootElement(document);
				if (rootElement != null) {
					root = rootElement;
				}
			}
		}
	}
	
	private TPLTree parseRootElement(IDocument document) {
		String text = document.get();
		TPLTree tagPositions = parseRootElements(text, document);
		return tagPositions;
	}

	private TPLTree parseRootElements(String text, IDocument document) {
		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(new ByteArrayInputStream(text
					.getBytes()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		RFSMLexer lexer = new RFSMLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RFSMParser parser = new RFSMParser(tokens);
		try {
			RFSMParser.rfsm_return ret = parser.rfsm();
	
			CommonTree tree = (CommonTree) ret.getTree();
			ModelTreeParser mParser =ModelTreeParser.createParser();
			TPLTree root = new TPLTree();
			root = mParser.parseTplTree(tree, root,0);	
			for(ITPLTree next : root.getChildren()){
				if(next.getType() == ITPLTree.INCLUDE){
					includeParse(next.getName(),(TPLTree)next);
				}				
			}
			return root;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ITPLTree includeParse(String include, TPLTree parent){		
		if(include.startsWith("\"") &&include.endsWith("\"")){
			include = include.substring(1,include.length()-1);
			
		}
		IProject prj = getProject();
		IPath prjPath = prj.getLocation();
		IPath path = prjPath.append(include);
		File f = path.toFile();
		if(f.exists()){				
			try {
				ANTLRInputStream  input = new ANTLRInputStream(new FileInputStream(f));
				RFSMLexer lexer = new RFSMLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				RFSMParser parser = new RFSMParser(tokens);
				RFSMParser.rfsm_return ret = parser.rfsm();
				CommonTree tree = (CommonTree) ret.getTree();
				ModelTreeParser mParser =ModelTreeParser.createParser();
				mParser.parseTplTree(tree, parent,1);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RecognitionException e) {
				e.printStackTrace();
			}				
		}
		return parent;
	}
	
	private IProject getProject(){
		if(input != null){
			if(input instanceof IFileEditorInput){
				IFile f = ((IFileEditorInput)input).getFile();
				return f.getProject();				
			}
		}
		return null;
	}

}
