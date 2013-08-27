package kr.re.etri.tpl.script.editors.outline;

import kr.re.etri.tpl.diagram.views.IModelNavigatorViewPage;
import kr.re.etri.tpl.script.grammar.tree2.ElementComparer;
import kr.re.etri.tpl.script.grammar.tree2.IBlockNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class ScriptModelNavigatorViewPage extends ContentOutlinePage implements
		IModelNavigatorViewPage, IDoubleClickListener {
	private static Logger logger = Logger.getLogger(ScriptModelNavigatorViewPage.class);
	private ITextEditor editor;
	private IEditorInput input;
	private ModelOutlineContentProvider outlineContentProvider;
	private OutlineLabelProvider2 outlineLabelProvider;
	
	public ScriptModelNavigatorViewPage(ITextEditor editor, IDocument doc) {
		super();
		this.editor = editor;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();

		outlineContentProvider = new ModelOutlineContentProvider(editor.getDocumentProvider());
		viewer.setContentProvider(outlineContentProvider);
		outlineLabelProvider = new OutlineLabelProvider2();
		viewer.setLabelProvider(outlineLabelProvider);
		viewer.addSelectionChangedListener(this);
		viewer.addDoubleClickListener(this);

		viewer.setComparer(new ElementComparer());
		
		if(input != null){
			viewer.setInput(input);
			update();
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		
		ISelection selection = event.getSelection();
		if (selection.isEmpty()){
			editor.resetHighlightRange();
		}
		else {
			IScriptNode element = (IScriptNode)((IStructuredSelection) selection).getFirstElement();

			if(element.isIncluded())
				return;

			int start = 0;
			int length = 0;
			try {
				IScriptNode parent = element;
				while(parent != null && !(parent instanceof IBlockNode)){
					parent = parent.getParent();
				}
				
				if(parent != null){
					start = parent.getStart();
					length = parent.getLength();
					if(start >= 0 && length >= 0)
						editor.setHighlightRange(start, length, false);
				}

				if(element != null){
					logger.debug("start = " + element.getStart() + ", end = " + element.getEnd()
							+ ", nstart = " + element.getNameStart() + ", nend = " + element.getNameEnd());

					start = element.getNameStart();
					length = element.getNameLength();
					if(start >= 0 && length >= 0)
						editor.selectAndReveal(start, length);
				}
			} catch (IllegalArgumentException x) {
				logger.warn("",x);
				editor.resetHighlightRange();
			}
		}
	}
	
	@Override
	public void doubleClick(DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		IScriptNode element = (IScriptNode)((IStructuredSelection)selection).getFirstElement();
		
		if(element instanceof IScriptRootNode){
			IFile file = ((IScriptRootNode)element).getFile();
			if(file != null && file.exists()){
				try {
					IDE.openEditor(getSite().getPage(), file, true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setInput(Object input) {
		this.input = (IEditorInput)input;
		update();
	}
	
	public Object getInput(){
		return input;
	}
	
	public void update() {
		TreeViewer viewer = getTreeViewer();

		if (viewer != null) {
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				Object vi = viewer.getInput();
				Object[] expandedElements = viewer.getExpandedElements();
				if(vi != null && vi.equals(input)){
//					viewer.refresh();
					viewer.setInput(input);
				} else{
					viewer.setInput(input);
				}
				for(Object element : expandedElements){
					viewer.setExpandedState(element, true);
				}
				control.setRedraw(true);
			}
		}
	}
	
	// KJH 20100720, 현재 활성화된 Editor 변경
	public void setEditor(ITextEditor editor){
		this.editor = editor;
		outlineContentProvider.setDocumentProvider(editor.getDocumentProvider());
	}

}
