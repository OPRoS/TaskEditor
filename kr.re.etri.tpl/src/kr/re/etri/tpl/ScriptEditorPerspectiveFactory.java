package kr.re.etri.tpl;

import kr.re.etri.tpl.diagram.views.ProjectNavigatorView;
import kr.re.etri.tpl.script.views.ModelView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ScriptEditorPerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea;
		editorArea = layout.getEditorArea();

		IFolderLayout left = layout.createFolder( "left",
													IPageLayout.LEFT,
													0.20f,
													editorArea);
		left.addView(ProjectNavigatorView.VIEW_ID);

		layout.addView( ModelView.ID,
						IPageLayout.BOTTOM,
						0.50f,
						ProjectNavigatorView.VIEW_ID);

		IFolderLayout bottom = layout.createFolder( "bottom",
													IPageLayout.BOTTOM,
													0.70f,
													editorArea);
		bottom.addView(IPageLayout.ID_PROP_SHEET);
		
		IFolderLayout bottomRight = layout.createFolder( "bottomRight",
				IPageLayout.RIGHT,
				0.50f,
				"bottom");

		bottomRight.addView(IPageLayout.ID_PROBLEM_VIEW);

		IFolderLayout right = layout.createFolder( "right",
													IPageLayout.RIGHT,
													0.80f,
													editorArea);
		right.addView(IPageLayout.ID_OUTLINE);
	}

}
