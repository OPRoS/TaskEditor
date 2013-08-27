package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.requests.CreateRequest;

public class WithElementCreateCommand extends ShapeElementCreateCommand {
	private ConnectionElement conn;
	
	public WithElementCreateCommand(ItemElement parentModel, CreateRequest req,
			Rectangle constraint) {
		super(parentModel, req, constraint);
		setLabel("with creation");
	}

	@Override
	public void execute() {
		if (request.getNewObject() instanceof WithElement){
			newShape = (WithElement) request.getNewObject();
		}
		else if (request.getNewObject() instanceof ReferElement){
			refItem = (ReferElement) request.getNewObject();
			newShape = refItem.getRealModel();
		}
		
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				if (newShape.getName() == null || newShape.getName().equals("")) {
					newShape.setName("run");	// KJH 20110728, With->Run
				}
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}
		
		makeShapeRefer(20, 20);
		
		redo();
		
		// KJH 20110527 s,
		ItemElement realParent = newShape.getParent();
		ReferElement refParent = (ReferElement)refItem.getParent();
		
		if (realParent instanceof ConnectorElement) {
			ConnectorElement connector = (ConnectorElement)realParent;
			
			EList<WithElement> withList = connector.getWiths();
			WithElement lastWith = null;
			if (withList != null && withList.size() > 1) {
				lastWith = withList.get(withList.size() - 2);
			}
			
			if (refParent != null && lastWith != null) {
				for (ReferElement refLastWith : lastWith.getReferences()) {
					if (refParent.getItems().contains(refLastWith)) {
						conn = ModelManager.getFactory().createConnectionElement();
						conn.setLineStyle(LineStyle.LINE_DASH);
						conn.setRelationship(RelationShip.TRANSITION);
						conn.setSourceEndPoint(LineEndPoint.NONE);
						conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);

						conn.setSource(refLastWith);
						conn.setSource2(refLastWith);
						conn.setTarget(refItem);
						conn.setTarget2(refItem);
						
						refLastWith.getSourceConnections().add(conn);
						refItem.getTargetConnections().add(conn);
						
						conn.setVisible(ConnectorType.SEQEXER == connector.getConType());
						
						break;
					}
				}
			}
		}	// KJH 20110527 e,
	}

	@Override
	public void redo() {
		super.redo();
		
		// KJH 20110504 s, set priority
		if (conn != null) {
			LinkedElement source = conn.getSource();
			LinkedElement target = conn.getTarget();
			source.getSourceConnections().add(conn);
			target.getTargetConnections().add(conn);

			ItemElement parentItem = newShape.getParent();
			if (parentItem instanceof ConnectorElement) {
				conn.setVisible(ConnectorType.SEQEXER == ((ConnectorElement)parentItem).getConType());
			}
		}
		// KJH 20110504 e, set priority
	}

	@Override
	public void undo() {
		// KJH 20110504 s, set priority
		if (conn != null) {
			LinkedElement source = conn.getSource();
			LinkedElement target = conn.getTarget();
			source.getSourceConnections().remove(conn);
			target.getTargetConnections().remove(conn);
			
			conn.setVisible(false);
		}
		// KJH 20110504 e, set priority
		
		super.undo();
	}
	
}
