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
		// KJH 20100512 s, ITaskNavigatorViewPage 인터페이스로 변경
		Platform.getAdapterManager().getAdapter(part, ITaskNavigatorViewPage.class);
		Object obj = ViewsPlugin.getAdapter(part, ITaskNavigatorViewPage.class, false);
		if (obj instanceof ITaskNavigatorViewPage) {
			ITaskNavigatorViewPage page = (ITaskNavigatorViewPage) obj;
			
			if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
			/*
			 * KJH 20100709, treeViewer가 새로 생성되는것을 막기 위해
			 *               page.getControl() == null 조건을 추가함
			 *               treeViewer == null인 경우 getControl()은 null을 반환
			 */
			if(page.getControl() == null)
				page.createControl(getPageBook());
			return new PageRec(part, page);
		}
		// KJH 20100512 s, ITaskNavigatorViewPage 인터페이스로 변경

		// There is no content outline
		return null;
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		super.partBroughtToTop(part);
		// 현재 TPLScriptEditor를 활성화 시킴 (GLOBAL->INCLUDE 하여 view의 아이콘을 변경하는 기능)
		if(part instanceof TPLScriptEditor){
			((TPLScriptEditor)part).setActivated();
		}
	}
}
