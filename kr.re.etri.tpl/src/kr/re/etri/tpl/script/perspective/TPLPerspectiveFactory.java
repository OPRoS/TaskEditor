package kr.re.etri.tpl.script.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class TPLPerspectiveFactory implements IPerspectiveFactory{
	private static final String TPL_ACTION_ID ="kr.re.etri.tpl.workbenchActionSet";
	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
	
		layout.addView(IPageLayout.ID_RES_NAV, IPageLayout.LEFT, 0.25f, editorArea);
	
		
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM,0.66f, editorArea);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);	
		
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.75f, editorArea);
		
		layout.addView("kr.re.etri.tpl.ModelView", IPageLayout.BOTTOM, 0.5f, IPageLayout.ID_RES_NAV);		
		
		layout.addActionSet(TPL_ACTION_ID);
	}
}
