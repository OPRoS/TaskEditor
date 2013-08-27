package kr.re.etri.tpl.diagram.commands;

import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.util.EList;

public class ReplaceWithListCommand extends ReplaceListCommand {

	public ReplaceWithListCommand(List list, int newPosition,
			WithElement object) {
		super(list, newPosition, object);
	}

	@Override
	public void redo() {
		super.redo();
		reconnection(oldPosition, newPosition);
	}

	@Override
	public void undo() {
		super.undo();
		reconnection(newPosition, oldPosition);
	}
	
	private void reconnection(int oldPos, int newPos) {
		
		WithElement with = (WithElement)object;
		ConnectorElement connector = (ConnectorElement)with.getParent();
		EList<WithElement> withList = connector.getWiths();
		
		if (withList.size() < 2) {
			return;
		}
		 
		for (ReferElement refItem : connector.getReferences()) {
			for (int i=0; i<withList.size(); i++) {
				WithElement curWith = withList.get(i);
				EList<ItemElement> refList = refItem.getItems();
				for (int j=0; j<refList.size(); j++) {
					ReferElement ref = (ReferElement)refList.get(j);
					if (ref.getRealModel() == curWith) {
						ref.getSourceConnections().clear();
						ref.getTargetConnections().clear();
					}
				}
			}
			
			for (int i=0; i<withList.size() - 1; i++) {
				WithElement curWith = withList.get(i);
				WithElement nextWith = withList.get(i + 1);
				ReferElement curRef = null;
				ReferElement nextRef = null;
				
				EList<ItemElement> refList = refItem.getItems();
				for (int j=0; j<refList.size(); j++) {
					ReferElement ref = (ReferElement)refList.get(j);
					if (ref.getRealModel() == curWith) {
						curRef = ref;
					}
					else if (ref.getRealModel() == nextWith) {
						nextRef = ref;
					}
				}
				
				if (curRef == null || nextRef == null) {
					continue;
				}
				
				ConnectionElement conn = ModelManager.getFactory().createConnectionElement();
				conn.setLineStyle(LineStyle.LINE_DASH);
				conn.setRelationship(RelationShip.TRANSITION);
				conn.setSourceEndPoint(LineEndPoint.NONE);
				conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);

				conn.setSource(curRef);
				conn.setSource2(curRef);
				conn.setTarget(nextRef);
				conn.setTarget2(nextRef);
				
				curRef.getSourceConnections().add(conn);
				nextRef.getTargetConnections().add(conn);
				
				conn.setVisible(ConnectorType.SEQEXER == connector.getConType());
			}
		}
	}

}
