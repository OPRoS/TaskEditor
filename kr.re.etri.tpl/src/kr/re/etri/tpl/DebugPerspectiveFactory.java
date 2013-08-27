package kr.re.etri.tpl;

import kr.re.etri.tpl.diagram.views.ModelNavigatorViewPart;
import kr.re.etri.tpl.diagram.views.ProjectNavigatorView;
import kr.re.etri.tpl.diagram.views.TaskNavigatorViewPart;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControllerView;
import kr.re.etri.tpl.script.debug.thread.DebugThreadView;
import kr.re.etri.tpl.script.debug.variables.DebugVariableView;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DebugPerspectiveFactory implements IPerspectiveFactory {

	public static String persp_id = "kr.re.etri.tpl.DebugPerspectiveFactory";
	
	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea;
		editorArea = layout.getEditorArea();

		IFolderLayout left = layout.createFolder( "left",
													IPageLayout.LEFT,
													0.20f,
													editorArea);
		left.addView(ProjectNavigatorView.VIEW_ID);
		
		layout.addView( TaskControllerView.viewId,
						IPageLayout.BOTTOM,
						0.40f,
						ProjectNavigatorView.VIEW_ID);

		layout.addView( TaskNavigatorViewPart.viewId,
						IPageLayout.BOTTOM,
						0.40f,
						TaskControllerView.viewId);


		layout.addView( ModelNavigatorViewPart.viewId,
						IPageLayout.BOTTOM,
						0.50f,
						TaskNavigatorViewPart.viewId);

		IFolderLayout bottom = layout.createFolder( "bottom",
													IPageLayout.BOTTOM,
													0.70f,
													editorArea);
		bottom.addView(IDebugUIConstants.ID_BREAKPOINT_VIEW);
		
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);

		IFolderLayout bottomLeft = layout.createFolder( "bottomLeft",
				IPageLayout.LEFT,
				0.65f,
				"bottom");

		bottomLeft.addView(DebugThreadView.VIEW_ID);
		
		bottomLeft.addView(IPageLayout.ID_PROP_SHEET);
		
		IFolderLayout bottomCenter = layout.createFolder("bottomCenter",
				IPageLayout.RIGHT,
				0.50f,
				"bottomLeft");
		
		bottomCenter.addView(DebugVariableView.VIEW_ID);
		
		// Right : editorArea의  75%를 제외한 나머지 = 25%
		// editorArea : 80(100%) - 20(25%) = 60(75%);
		// right : 80(100%) - 64(80%) = 16(20%)
		IFolderLayout right = layout.createFolder( "right",
													IPageLayout.RIGHT,
													0.80f,
													editorArea);
		right.addView(IPageLayout.ID_OUTLINE);
		
	}

}
