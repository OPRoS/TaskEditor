package kr.re.etri.tpl.script.editors;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.editors.BehaviorDiagramEditor;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.editparts.behavior.BLinkedElementEditPart;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.views.IModelNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.ITaskNavigatorViewPage;
import kr.re.etri.tpl.grammar.ScriptParser;
import kr.re.etri.tpl.script.editors.highlighting.TPLColorManager;
import kr.re.etri.tpl.script.editors.outline.EditorContentOutlinePage;
import kr.re.etri.tpl.script.editors.outline.ScriptModelNavigatorViewPage;
import kr.re.etri.tpl.script.editors.outline.ScriptTaskNavigatorViewPage;
import kr.re.etri.tpl.script.grammar.tree2.IBlockNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.ITranNode;
import kr.re.etri.tpl.script.grammar.tree2.RunNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;
import kr.re.etri.tpl.script.graph.DotGenerator;
import kr.re.etri.tpl.script.views.ModelView;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.EditorPane;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class TPLScriptEditor extends TextEditor {

	protected abstract class AbstractSelectionChangedListener implements ISelectionChangedListener  {
		public void install(ISelectionProvider selectionProvider) {
			if (selectionProvider == null)
				return;

			if (selectionProvider instanceof IPostSelectionProvider)  {
				IPostSelectionProvider provider= (IPostSelectionProvider) selectionProvider;
				provider.addPostSelectionChangedListener(this);
			} else  {
				selectionProvider.addSelectionChangedListener(this);
			}
		}

		public void uninstall(ISelectionProvider selectionProvider) {
			if (selectionProvider == null)
				return;

			if (selectionProvider instanceof IPostSelectionProvider)  {
				IPostSelectionProvider provider= (IPostSelectionProvider) selectionProvider;
				provider.removePostSelectionChangedListener(this);
			} else  {
				selectionProvider.removeSelectionChangedListener(this);
			}
		}
	}

	private class EditorSelectionChangedListener extends AbstractSelectionChangedListener implements IDocumentListener{

		private IDocument fDocument;
		
		public void selectionChanged(SelectionChangedEvent event) {
			TPLScriptEditor.this.selectionChanged(event);
		}

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
		}

		@Override
		public void documentChanged(DocumentEvent event) {
			TPLScriptEditor.this.documentChanged(event);
		}
		
		public void setDocument(IDocument document) {
			if (fDocument != null)
				fDocument.removeDocumentListener(this);

			fDocument= document;
			if (fDocument != null)
				fDocument.addDocumentListener(this);
		}

	}
	
	/*
	 * KJH 20100708, IFile comparator
	 */
	// KJH 20101015, delete
//	private class ComparatorImpl implements Comparator<IFile> {
//		public int compare(IFile f1, IFile f2){
//			String n1 = f1.getFullPath().toString();
//			String n2 = f2.getFullPath().toString();
//			
//			return n1.compareTo(n2);
//		}
//	}
	
	// KJH 20100520
	private static Logger logger = Logger.getLogger(TPLScriptEditor.class);
//	private IScriptElement root;
	
	private TPLColorManager colorManager;
	private EditorContentOutlinePage fOutlinePage;
	private ModelView modelView;
	private DotGenerator dotGenerator;
	
	/*
	 * KJH 20100709, ��� ��ũ��Ʈ�������� tasks/models view�� �������� �ϱ����� static���� ����
	 *               fTaskViewPage, fModelViewPage
	 */
	// KJH 20100513 s, �߰�
	private static ScriptTaskNavigatorViewPage fTaskViewPage;
	private static ScriptModelNavigatorViewPage fModelViewPage;
	// KJH 20100513 e,
	
	// KJH 20100531
	private EditorSelectionChangedListener fEditorSelectionChangedListener;
	
	// KJH 20110128, current->currentScriptNode
	private IScriptNode currentScriptNode;
	
	public TPLScriptEditor() {
		super();
		colorManager = new TPLColorManager();
		setSourceViewerConfiguration(new TPLConfiguration(colorManager));
		setDocumentProvider(new TPLDocumentProvider(new TPLParentDocumentProvider()));
		dotGenerator = DotGenerator.getInstance();
	}

	// KJH 20100421 s, ��ũ��Ʈ ��ġ ���� ���ŵǵ��� ��
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		

		// KJH 20100531
		addSelectionChangedListener();
//		fEditorSelectionChangedListener = new EditorSelectionChangedListener();
//		fEditorSelectionChangedListener.install(getSelectionProvider());
//		fEditorSelectionChangedListener.setDocument(getInputDocument());
		
	}
	// KJH 20100421 e, ��ũ��Ʈ ��ġ ���� ���ŵǵ��� ��

	public void dispose() {
		if(colorManager != null){
			colorManager.dispose();
			colorManager = null;
		}
		
		removeSelectionChangedListener();
		
		if(fTaskViewPage != null && fTaskViewPage.getControl().isDisposed()){
			fTaskViewPage = null;
		}
		
		if(fModelViewPage != null && fModelViewPage.getControl().isDisposed()){
			fModelViewPage = null;
		}
		
		super.dispose();
	}
	
	// KJH 20101021 s, add
	@Override
	protected void createActions() {
		super.createActions();
	}
	// KJH 20101021 e, add

	public void addSelectionChangedListener(){
		if(fEditorSelectionChangedListener == null)
			fEditorSelectionChangedListener = new EditorSelectionChangedListener();
		fEditorSelectionChangedListener.install(getSelectionProvider());
		fEditorSelectionChangedListener.setDocument(getInputDocument());
	}
	
	public void removeSelectionChangedListener(){
		if(fEditorSelectionChangedListener != null){
			fEditorSelectionChangedListener.uninstall(getSelectionProvider());
			fEditorSelectionChangedListener.setDocument(null);
			fEditorSelectionChangedListener = null;
		}
	}

	public void setModelView(ModelView mv){
		modelView = mv;
	}

	public IFile getInputFile() {	// WJH 100902 protected -> public�� ����
		if (getEditorInput() instanceof IFileEditorInput) {
			IFileEditorInput ife = (IFileEditorInput) getEditorInput();		
			IFile file = ife.getFile();
			return file;
		}
		Object adapter = getEditorInput().getAdapter(IFile.class);
		if (adapter instanceof IFile) {
			return (IFile)adapter;
		}
		return null;
	}

	@Override
	protected void setTitleImage(Image titleImage) {
		if (getEditorInput() instanceof TPLTextEditorInput) {
			titleImage = TaskModelPlugin.getImageDescriptor(IconStrings.RTS2_16).createImage();
		}
		
		super.setTitleImage(titleImage);
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
	}

	@Override
	public void doSaveAs() {
		super.doSaveAs();
	}

	protected void editorSaved() {
		super.editorSaved();
		validateAndMark();
//		dotGenerator.generate(getInputFile().getProject().getLocation().toFile());
	}

	public static final String ERROR_MARKER_ID = "kr.re.etri.tpl.syntaxerrormarker";

	protected void validateAndMark() {
		IDocument doc = getDocumentProvider().getDocument(getEditorInput());
		if (doc == null) {	// KJH 20110809
			return;
		}
		String text = doc.get();
		if (text == null || text.length() == 0) {
			return;
		}
		
		MarkerLogger markerLogger = new MarkerLogger(getInputFile(), MarkerLogger.TYPE_SCRIPT);
		markerLogger.clearProblem();
		InputStream stream = new ByteArrayInputStream(text.getBytes());
		ScriptParser parser = new ScriptParser(getInputFile());
		parser.addErrorListener(markerLogger);
		parser.parse(stream);
		parser.removeErrorListener(markerLogger);
		
//		ANTLRInputStream input = null;
//		try {
//			input = new ANTLRInputStream(new ByteArrayInputStream(text.getBytes()));
//		} catch (IOException e1) {
//			logger.warn("", e1);
//		}
//		
//		MarkerProvider listener = new MarkerProvider();
//		RFSMLexer lexer = new RFSMLexer(input);
//		lexer.setErrorListener(listener);
//		CommonTokenStream tokens = new CommonTokenStream(lexer);
//		// KJH 20110614 s, errorMessage �ٲ�� ����. TPLParserForErrorMarking->RTMParser
//		RTMParser parser = new RTMParser(tokens);
//		parser.setErrorListener(listener);
//		// KJH 20110614 e, errorMessage �ٲ�� ����. TPLParserForErrorMarking->RTMParser
//		try {
//			parser.rfsm();
//		} catch (Exception e) {
//			logger.warn("", e);
//		}

	}

	public IDocument getInputDocument() {		// WJH 101220 protected->public ����
		IDocument document = getDocumentProvider().getDocument(getEditorInput());
		return document;
	}

	protected void doSetInput(IEditorInput newInput) throws CoreException {
		super.doSetInput(newInput);
		
//		if(!(newInput instanceof IFileEditorInput))	// KJH 20110809
//			return;
		
		// KJH 20100514 s, taskPage, modelPage �߰�
		if (fOutlinePage != null){
			fOutlinePage.setInput(newInput);
//			setOutlinePageInput(root);
		}
		if(fTaskViewPage != null){
			fTaskViewPage.setEditor(this);
			fTaskViewPage.setInput(newInput);
		}
		if(fModelViewPage != null){
			fModelViewPage.setEditor(this);
			fModelViewPage.setInput(newInput);
		}
		// KJH 20100513 e, 
		if(modelView != null){
			modelView.setInput(newInput);
		}
		validateAndMark();
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (fOutlinePage == null) {
				fOutlinePage = new EditorContentOutlinePage(this,getInputDocument());
				if(getEditorInput() != null)
					fOutlinePage.setInput(getEditorInput());
//					setOutlinePageInput(root);
			}
			return fOutlinePage;
		}else if(ITaskNavigatorViewPage.class.equals(required)){
			if(fTaskViewPage == null){
				fTaskViewPage = new ScriptTaskNavigatorViewPage(this, getInputDocument());
				if(getEditorInput() != null)
					fTaskViewPage.setInput(getEditorInput());
			}
			return fTaskViewPage;
		}else if(IModelNavigatorViewPage.class.equals(required)){
			if(fModelViewPage == null){
				fModelViewPage = new ScriptModelNavigatorViewPage(this, getInputDocument());
				if(getEditorInput() != null)
					fModelViewPage.setInput(getEditorInput());
			}
			return fModelViewPage;
		}
		return super.getAdapter(required);
	}

	/*
	 * KJH 20100618
	 */
	public IScriptNode getRoot(){
		IScriptNode root = null; 
		IEditorInput editorInput = getEditorInput();
		
		root = ScriptManager.getInstance().getTree(editorInput, getInputDocument());
		return root;
	}
	
	public void selectScript(Object model){
		IScriptNode root = getRoot();
		if(root == null)
			return;
		
		ArrayList<String> elementList = new ArrayList<String>();
		
		if(model instanceof ItemElement){
			ItemElement element = (ItemElement)model;

			while(element != null && !(element instanceof ModelDiagram || element instanceof SubDiagram)){
				String name = element.getName();
				if(element instanceof IncludedElement){
					name = ((IncludedElement)element).getIncludePath();
				}
				else if(element instanceof ConnectionElement){
					name = ((ConnectionElement)element).getTarget().getName();
				}

				elementList.add(name);

				if(element instanceof ConnectionElement){
					// Connection�� ��� souce ������Ʈ�� �θ��尡 ��
					element = ((ConnectionElement)element).getSource();
				}
				else{
					element = element.getParent();
				}

				if(element instanceof ReferElement){
					element = ((ReferElement)element).getRealModel();
				}
			}
		}
		
		if(elementList.size() <= 0)
			return;

		IScriptNode selectedScript = null;

		// �ƿ����� �������� Ʈ��
		List<IScriptNode> children = new ArrayList<IScriptNode>();
		children.addAll(root.getChildren());
		// �ֻ������(Behavior)���� �̸��� ���Ͽ� �ش��ϴ� ���� ��带 ã�Ƴ�
		for(int i=elementList.size()-1; i>=0; i--){
			String elementName = elementList.get(i);
			
			for(IScriptNode child : children){
				String childName = child.getName();
				if(child instanceof ITranNode)
					childName = ((ITranNode)child).getTarget();

				// KJH 20110214 s, fix
				if(elementName.equals(childName)){
					children.clear();
					for (IScriptNode c : child.getChildren()) {
						if (c instanceof RunNode) {
							children.addAll(c.getChildren());
						} else {
							children.add(c);
						}
					}

					selectedScript = child;
					break;
				}	// KJH 20110214 e, fix
				
				selectedScript = null;
			}
		}
		
		if(selectedScript == null)
			return;
		
		logger.debug("start = " + selectedScript.getStart() + ", end = " + selectedScript.getEnd()
				+ ", nstart = " + selectedScript.getNameStart() + ", nend = " + selectedScript.getNameEnd());
		
		int start = 0;
		int length = 0;
		try {
			IScriptNode parent = selectedScript;
			while(parent != null && !(parent instanceof IBlockNode)){
				parent = parent.getParent();
			}
			
			if(parent != null){
				start = parent.getStart();
				length = parent.getLength();
				setHighlightRange(start, length, false);
			}

			start = selectedScript.getNameStart();
			length = selectedScript.getNameLength();
			selectAndReveal(start, length);
			
			// KJH 20100713
			currentScriptNode = selectedScript;
		} catch (IllegalArgumentException x) {
			logger.warn("", x);
			resetHighlightRange();
		}
	}
	
	public void selectLine(int line) {
		IDocument document = getInputDocument();
		line--;
		try {
			int start = document.getLineOffset(line);
			int end = start + document.getLineLength(line) - 1;
			int length = document.getLength();
			if (start < length && end - 1 < length) {
				setHighlightRange(start, end - start, false);
				selectAndReveal(start, end - start);
			}
		} catch (BadLocationException e) {
			logger.warn("", e);
		}
	}
	
	protected void selectionChanged(SelectionChangedEvent event){
		ISelection selection = event.getSelection();
		int offset = ((TextSelection)selection).getOffset();
		
		// KJH 20110128, root->rootScriptNode
		IScriptNode rootScriptNode = getRoot();
		// KJH 20110128, selected->selectedScriptNode
		IScriptNode selectedScriptNode = rootScriptNode != null ? rootScriptNode.getChildByOffset(offset) : null;

		// KJH 20100713
		if (selectedScriptNode == null || selectedScriptNode == currentScriptNode) {
			resetHighlightRange();
			return;
		}
		currentScriptNode = selectedScriptNode;

		String thisPath = getInputFile().getFullPath().toString();
		if(!thisPath.startsWith("/"))
			thisPath = "/" + thisPath;

		// KJH 20110128 s, modify: ���� EditorStack�� �� ��ũ��Ʈ ���ý� ���̾�׷����� �Ѿ�� �ʵ��� ����
		// KJH 20100713, TPLDiagramEditor���� ����
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		PartPane currentPane = ((PartSite)this.getSite()).getPane();
		
		IEditorReference[] editorReferences = workbenchPage.getEditorReferences();
		BehaviorDiagramEditor diagramEditor = null;	// KJH 20110128, editor->diagramEditor

		for(IEditorReference ref : editorReferences){
			IEditorPart editorPart = ref.getEditor(true);
			if(!(editorPart instanceof TPLDiagramEditor)) {
				continue;
			}
			
			EditorPane pane = (EditorPane)((PartSite)editorPart.getEditorSite()).getPane();
			if(pane.getContainer() == currentPane.getContainer()) {
				continue;
			}

			Object model = ((TPLDiagramEditor)editorPart).getModel();
			if (model instanceof ModelDiagram) {
				ModelDiagram rootModel = (ModelDiagram)model;
				if (thisPath.equals(rootModel.getScript())){
					pane.getWorkbook().setSelection(pane);
					diagramEditor = ((TPLDiagramEditor)editorPart).getBehaviorDiagramEditor();
					break;
				}
			}
		}
		// KJH 20110128 e, modify: ���� EditorStack�� �� ��ũ��Ʈ ���ý� ���̾�׷����� �Ѿ�� �ʵ��� ����

		GraphicalViewer viewer = null;
		
		if(diagramEditor != null)
			viewer = diagramEditor.getGraphicalViewer();

		try {
			List<IScriptNode> group = selectedScriptNode.getParents();
			IScriptNode parent = (IScriptNode)selectedScriptNode.getBlockNode();

			if(parent != null){
				int start = parent.getStart();
				int length = parent.getLength();
				setHighlightRange(start, length, false);
			}

			// �ش� EditPart �˻�
			if(viewer != null){
				EditPart editPart = null;
				List children = new ArrayList();
				children.addAll(viewer.getContents().getChildren());

				for(int i = 0; i < group.size(); i++){
					IScriptNode node = group.get(i);
					if (node == null)	continue;	// KJH 20110208, fix

					Iterator it = children.iterator();
					while(it.hasNext()) {
						EditPart tEditPart = (EditPart)it.next();
						if(!(tEditPart.getModel() instanceof ItemElement)) {
							continue;
						}

						ItemElement model = (ItemElement)tEditPart.getModel();

						// ReferElement�� ��� RealModel�� ��ü
						if(model instanceof ReferElement)
							model = ((ReferElement)model).getRealModel();

						String nodeName = node.getFullName();
						String modelName = model.getName();

						// ConnectionElement�� ��� target�𵨷� ��ü
						if(model instanceof ConnectionElement && node instanceof ITranNode){
							nodeName = ((ITranNode)node).getTarget();
							modelName = ((ConnectionElement)model).getTarget().getName();
						}

						if(nodeName != null && nodeName.equals(modelName)){
							children.clear();
							children.addAll(tEditPart.getChildren());
							// KJH 20100907, children�� editpart�� children�� ���� ������ ����,
							//               �Ʒ��� ���ǿ��� Ŀ�ؼ��� children�� �߰��Ǿ� ������ ����
							//               children�� ���ο� ArrayList�� �����ϵ��� ����
							if(tEditPart instanceof BLinkedElementEditPart)
								children.addAll(((BLinkedElementEditPart)tEditPart).getSourceConnections());

							editPart = tEditPart;
							break;
						}
					}
				}

				IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
				if(editPart != null && !editPart.equals(sel.getFirstElement())){
					SelectionSynchronizer listener = diagramEditor.getSelectionSynchronizer();
					viewer.removeSelectionChangedListener(listener);
					if (editPart.isSelectable()) {
						viewer.select(editPart);
					}
					viewer.addSelectionChangedListener(listener);
				}
				else{
					viewer.deselectAll();
				}
			}
		} catch (IllegalArgumentException x) {
			logger.warn("", x);
			resetHighlightRange();
		}
	}
	
	// KJH 20101115 s,
	public void updateTaskView() {
		if (fTaskViewPage != null) {
			fTaskViewPage.update();
		}
	}
	
	public void updateModelView() {
		if (fModelViewPage != null) {
			fModelViewPage.update();
		}
	}
	// KJH 20101115 e,
	
	protected void documentChanged(DocumentEvent event){
		int offset = event.getOffset();
		int length = event.getLength();
		int changed = event.getText().length() - length;
		if(length == 1 && changed == -1){
			length = 0;
		}
		
		IDocument doc = getInputDocument();
		ScriptManager.getInstance().getTree(getEditorInput(), doc, offset, length, changed);
		ScriptManager.getInstance().getTPLList(getInputFile());
		
		if(fOutlinePage != null)
			fOutlinePage.update();
		
		updateTaskView();
		updateModelView();
		
		// KJH 20101021, �߰�
//		validateAndMark();
	}
	
	// KJH 20100712, ��ũ��Ʈ ������ �̵��� ������ tree ����
	public void setActivated(){
		IScriptNode root = getRoot();
		IScriptNode active = ScriptManager.getInstance().getActiveNode();
		if(active == null || !active.equals(root)){
			if(root != null){
				ScriptManager.getInstance().setActiveNode(root);

			}
//			IScriptNode root2 = ScriptManager.getManager().getTPLList(getInputFile());
//			setTaskViewPageInput(root2);
//			setModelViewPageInput(root2);
			
			// KJH 20100727 s, ��ũ��Ʈâ ��� �ݰ� �ٸ�������Ʈ�� ��ũ��Ʈ �� ��� �䰡 null�̾ �߻��ϴ� ���� ����
			if(fTaskViewPage != null){
				fTaskViewPage.setEditor(this);
				fTaskViewPage.setInput(getEditorInput());
			}
			if(fModelViewPage != null){
				fModelViewPage.setEditor(this);
				fModelViewPage.setInput(getEditorInput());
			}
			// KJH 20100727 e, ��ũ��Ʈâ ��� �ݰ� �ٸ�������Ʈ�� ��ũ��Ʈ �� ��� �䰡 null�̾ �߻��ϴ� ���� ����
			
//			fTaskViewPage.update();
//			fModelViewPage.update();
//			System.out.println("set activate :" + getInputFile().toString());
		}
	}

}