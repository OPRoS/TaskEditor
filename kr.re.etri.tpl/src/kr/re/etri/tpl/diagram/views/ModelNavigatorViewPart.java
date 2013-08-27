package kr.re.etri.tpl.diagram.views;

import kr.re.etri.tpl.script.editors.TPLScriptEditor;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class ModelNavigatorViewPart extends ContentOutline {

	public static final String viewId = "kr.re.etri.tpl.diagram.views.ModelNavigatorViewPart";

	public ModelNavigatorViewPart() {
		super();
	}

	/* (non-Javadoc)
	 * Method declared on PageBookView.
	 */
	protected PageRec doCreatePage(IWorkbenchPart part) {
		// Try to get an outline page.
		// KJH 20100512 s, IModelNavigatorViewPage 인터페이스로 변경
		Object obj = ViewsPlugin.getAdapter(part, IModelNavigatorViewPage.class, false);
		if (obj instanceof IModelNavigatorViewPage) {
			IModelNavigatorViewPage page = (IModelNavigatorViewPage) obj;

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
		// KJH 20100512 e, IModelNavigatorViewPage 인터페이스로 변경

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
