package kr.re.etri.tpl.script.views;

import kr.re.etri.tpl.diagram.views.IModelNavigatorViewPage;
import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.script.utils.TPLResourceUtil;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class ModelView extends ViewPart {
	private static Logger logger = Logger.getLogger(ModelView.class);
	public static String ID = "kr.re.etri.tpl.script.views.ModelView";
	private TreeViewer treeViewer;
	private ModelContentProvider cProvider;
	private ModelLabelProvider lProvider;
	private IEditorInput input;
	private TPLScriptEditor editor;
	private IDocumentProvider docProvider;
	private ISelectionListener selectionListener;

	public ModelView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent);
		cProvider = new ModelContentProvider(docProvider);
		treeViewer.setContentProvider(cProvider);
		lProvider = new ModelLabelProvider();
		treeViewer.setLabelProvider(lProvider);
		if (input != null)
			treeViewer.setInput(input);
	}

	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);

//		editor = TPLResourceUtil.getTPLEditor();
//		if(editor == null){
//			logger.warn("TPLEditor is null.");
//			return;
//		}
//		
//		docProvider = editor.getDocumentProvider();
//		IEditorInput inp = editor.getEditorInput();
//		input = inp;
		
		this.getSite().getPage().addPostSelectionListener(
			selectionListener = new ISelectionListener() {
				@Override
				public void selectionChanged(IWorkbenchPart part,
						ISelection selection) {
					if (!(part instanceof TPLScriptEditor)) {
						return;
					}
					editor = (TPLScriptEditor) part;
					docProvider = editor.getDocumentProvider();
					if (cProvider == null) {
						cProvider = new ModelContentProvider(docProvider);
					} else {
						cProvider.setDocumentProvider(docProvider);
					}
					setInput(editor.getEditorInput());
				}
			});

	}

	public void setInput(Object input) {
		this.input = (IEditorInput) input;
		this.update();
	}

	@Override
	public void setFocus() {
	}

	public void dispose() {
		getSite().getPage().removePostSelectionListener(selectionListener);
		super.dispose();
	}

	public void update() {
		if (treeViewer != null) {
			Control control = treeViewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				treeViewer.setInput(input);
				treeViewer.expandAll();
				control.setRedraw(true);
			}
		}
	}
}
