package kr.re.etri.tpl.diagram.views;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class ActionNavigatorViewPart extends ContentOutline {

	public static final String viewId = "kr.re.etri.tpl.diagram.views.ActionNavigatorViewPart";

	public ActionNavigatorViewPart() {
		super();
	}
	
    /* (non-Javadoc)
     * Method declared on PageBookView.
     */
    protected PageRec doCreatePage(IWorkbenchPart part) {
        // Try to get an outline page.
        Object obj = ViewsPlugin.getAdapter(part, ActionNavigatorViewPage.class, false);
        if (obj instanceof ActionNavigatorViewPage) {
        	ActionNavigatorViewPage page = (ActionNavigatorViewPage) obj;
            if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
            page.createControl(getPageBook());
            return new PageRec(part, page);
        }
        // There is no content outline
        return null;
    }
}
