package kr.re.etri.tpl.diagram.views;

import kr.re.etri.tpl.script.editors.TPLScriptEditor;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class TaskNavigatorViewPart extends ContentOutline {

	public static final String viewId = "kr.re.etri.tpl.diagram.views.TaskNavigatorViewPart";

	public TaskNavigatorViewPart() {
		super();
	}

	/* (non-Javadoc)
	 * Method declared on PageBookView.
	 */
	
	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec rec) {
//		if(part instanceof TPLScriptEditor)
//			return;
		super.doDestroyPage(part, rec);
	}
	
	protected PageRec doCreatePage(IWorkbenchPart part) {
		// Try to get an outline page.
		// KJH 20100512 s, ITaskNavigatorViewPage �������̽��� ����
		Platform.getAdapterManager().getAdapter(part, ITaskNavigatorViewPage.class);
		Object obj = ViewsPlugin.getAdapter(part, ITaskNavigatorViewPage.class, false);
		if (obj instanceof ITaskNavigatorViewPage) {
			ITaskNavigatorViewPage page = (ITaskNavigatorViewPage) obj;
			
			if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
			/*
			 * KJH 20100709, treeViewer�� ���� �����Ǵ°��� ���� ����
			 *               page.getControl() == null ������ �߰���
			 *               treeViewer == null�� ��� getControl()�� null�� ��ȯ
			 */
			if(page.getControl() == null)
				page.createControl(getPageBook());
			return new PageRec(part, page);
		}
		// KJH 20100512 s, ITaskNavigatorViewPage �������̽��� ����

		// There is no content outline
		return null;
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		super.partBroughtToTop(part);
		// ���� TPLScriptEditor�� Ȱ��ȭ ��Ŵ (GLOBAL->INCLUDE �Ͽ� view�� �������� �����ϴ� ���)
		if(part instanceof TPLScriptEditor){
			((TPLScriptEditor)part).setActivated();
		}
	}
}
