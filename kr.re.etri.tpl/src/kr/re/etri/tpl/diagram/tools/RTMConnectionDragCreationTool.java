package kr.re.etri.tpl.diagram.tools;

import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand2;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.ConnectionDragCreationTool;

/**
 * Mouse 을 Drag 하여 Connection 을 생성하는 Tool 클래스이다.
 * 
 * @author sfline
 *
 */
public class RTMConnectionDragCreationTool extends ConnectionDragCreationTool {
	/**
	 * Connection 생성 Tool 클래스를 생성한다.
	 */
	public RTMConnectionDragCreationTool() {
		super();
	}

	/**
	 * Connection 생성 Tool 클래스를 생성한다.
	 * @param factory Connection 모델을 생성할 Factory
	 */
	public RTMConnectionDragCreationTool(CreationFactory factory) {
		super(factory);
	}

	/**
	 * mouse 버튼을 놓을 때 호출된다. 
	 * @Override
	 */
	protected boolean handleButtonUp(int button) {
		Request request = this.getTargetRequest();
		EditPartViewer viewer = getCurrentViewer();
		if((request instanceof CreateConnectionRequest) == false) {
			request = null;
		}
		boolean result = super.handleButtonUp(button);
		
		selectAddedObject(viewer, request);

		return result;
	}

	/*
	 * 새로 추가된 모델을 Viewer 에서 선택한다.
	 */
	private void selectAddedObject(EditPartViewer viewer, Request request) {
		if((request instanceof CreateConnectionRequest) == false) {
			return;
		}
		
		Command cmd = ((CreateConnectionRequest)request).getStartCommand();
		if((cmd instanceof ConnectionCreateCommand2) == false) {
			return;
		}
		
		final Object model = ((ConnectionCreateCommand2)cmd).getConnection();
		if (model == null || viewer == null)
			return;
		Object editpart = viewer.getEditPartRegistry().get(model);
		if (editpart instanceof EditPart) {
			//Force the new object to get positioned in the viewer. 
			viewer.flush();			
			viewer.select((EditPart)editpart);
		}
	}

	@Override
	protected boolean updateTargetUnderMouse() {
		if (!isTargetLocked()) {
			EditPart editPart = getCurrentViewer().findObjectAtExcluding(
				getLocation(),
				getExclusionSet(),
				getTargetingConditional());
			if (editPart != null)
				editPart = editPart.getTargetEditPart(getTargetRequest());
			boolean changed = getTargetEditPart() != editPart;
			setTargetEditPart(editPart);
			return changed;
		} else
			return false;
	}
	
}
