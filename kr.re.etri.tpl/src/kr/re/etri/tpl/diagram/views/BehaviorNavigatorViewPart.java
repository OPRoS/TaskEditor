package kr.re.etri.tpl.diagram.views;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class BehaviorNavigatorViewPart extends ContentOutline {

	public static final String viewId = "kr.re.etri.tpl.diagram.views.BehaviorNavigatorViewPart";

	public BehaviorNavigatorViewPart() {
		super();
	}

    /* (non-Javadoc)
     * Method declared on PageBookView.
     */
    protected PageRec doCreatePage(IWorkbenchPart part) {
        // Try to get an outline page.
        Object obj = ViewsPlugin.getAdapter(part, BehaviorNavigatorViewPage.class, false);
        if (obj instanceof BehaviorNavigatorViewPage) {
        	BehaviorNavigatorViewPage page = (BehaviorNavigatorViewPage) obj;
            if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
            page.createControl(getPageBook());
            return new PageRec(part, page);
        }
        // There is no content outline
        return null;
    }

    /* (non-Javadoc)
     * Method declared on IAdaptable.
     */
    public Object getAdapter(Class key) {
        if (key == ActionNavigatorViewPage.class) {
        	return null;
		}
        return super.getAdapter(key);
    }
}
