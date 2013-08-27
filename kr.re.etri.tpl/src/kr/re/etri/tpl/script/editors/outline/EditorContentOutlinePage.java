package kr.re.etri.tpl.script.editors.outline;

import kr.re.etri.tpl.script.grammar.tree2.ElementComparer;
import kr.re.etri.tpl.script.grammar.tree2.IBlockNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;

import org.apache.log4j.Logger;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class EditorContentOutlinePage extends ContentOutlinePage {
	private static Logger logger = Logger.getLogger(EditorContentOutlinePage.class);
	private ITextEditor editor;
	private IEditorInput input;
	private EditorOutlineContentProvider outlineContentProvider;
	private OutlineLabelProvider2 outlineLabelProvider;
	private IDocument doc;

	public EditorContentOutlinePage(ITextEditor editor, IDocument doc) {
		super();
		this.editor = editor;
		this.doc = doc;
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();

		outlineContentProvider = new EditorOutlineContentProvider(editor.getDocumentProvider());
		viewer.setContentProvider(outlineContentProvider);
		outlineLabelProvider = new OutlineLabelProvider2();
		viewer.setLabelProvider(outlineLabelProvider);
		viewer.addSelectionChangedListener(this);
		
		// KJH 20100608, element ºñ±³
		viewer.setComparer(new ElementComparer());

		if (input != null){
			viewer.setInput(input);
			update();
		}
	}

	public void setInput(Object input) {
		this.input = (IEditorInput)input;
		update();
	}
	
	public Object getInput(){
		return input;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		ISelection selection = event.getSelection();
		if (selection.isEmpty()){
			editor.resetHighlightRange();
			return;
		}

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

	public void update() {
		TreeViewer viewer = getTreeViewer();

		if (viewer != null) {
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				Object vi = viewer.getInput();
				Object[] expandedElements = viewer.getExpandedElements();
				if(vi != null && vi.equals(input)){
					viewer.refresh();
				} else{
					viewer.setInput(input);
				}
				for(Object element : expandedElements){
					viewer.setExpandedState(element, true);
				}
				control.setRedraw(true);
			}
//			Object vi = viewer.getInput();
//			if(vi != null && vi.equals(input)){
//				Object[] expandedElements = viewer.getExpandedElements();
//				viewer.refresh();
//				for(Object element : expandedElements){
//					viewer.setExpandedState(element, true);
//				}
//			}
//			else{
//				Control control = viewer.getControl();
//				if (control != null && !control.isDisposed()) {
//					control.setRedraw(false);
//					viewer.setInput(input);
//					control.setRedraw(true);
//				}
//			}
		}
	}
}