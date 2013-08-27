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
 * Mouse �� Drag �Ͽ� Connection �� �����ϴ� Tool Ŭ�����̴�.
 * 
 * @author sfline
 *
 */
public class RTMConnectionDragCreationTool extends ConnectionDragCreationTool {
	/**
	 * Connection ���� Tool Ŭ������ �����Ѵ�.
	 */
	public RTMConnectionDragCreationTool() {
		super();
	}

	/**
	 * Connection ���� Tool Ŭ������ �����Ѵ�.
	 * @param factory Connection ���� ������ Factory
	 */
	public RTMConnectionDragCreationTool(CreationFactory factory) {
		super(factory);
	}

	/**
	 * mouse ��ư�� ���� �� ȣ��ȴ�. 
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
	 * ���� �߰��� ���� Viewer ���� �����Ѵ�.
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
