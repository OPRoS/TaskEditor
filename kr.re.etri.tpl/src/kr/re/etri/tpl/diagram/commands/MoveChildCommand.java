package kr.re.etri.tpl.diagram.commands;

import java.util.List;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class MoveChildCommand extends Command {
	private ItemElement oldParent;
	private ItemElement newParent;
	private ItemElement childModel;
	private Rectangle oldBounds;
	private Rectangle newBounds;
	
	public MoveChildCommand(ItemElement parentModel, ItemElement childModel, Rectangle newBounds) {
		this.newParent = parentModel;
		this.childModel = childModel;
		this.newBounds = newBounds.getCopy();
	}

	@Override
	public boolean canExecute() {
		ReferElement refParent = null;
		ReferElement refChild = null;

		if (newParent instanceof ReferElement) {
			refParent = (ReferElement)newParent;
		}
		
		if (childModel instanceof ReferElement) {
			refChild = (ReferElement)childModel;
		}
		
		if (refParent != null && refChild != null) {
			List<ConnectionElement> conns = refChild.getSourceConnections();
			for (ConnectionElement conn : conns) {
				LinkedElement target = conn.getTarget();
				// 타 Behavior의 State와 Transition으로 연결될 경우 false
				if (RelationShip.TRANSITION == conn.getRelationship()) {
					if (refParent.getParent() != target.getParent()) {
						return false;
					}
				}
				
				// 새로운 parent와 기존에 연결되 있는 경우
				ItemElement p = refParent;
				for (; p != null ; p = p.getParent()) {
					if (p == target) {
						return false;
					}
				}
				
			}
		}
		
		return super.canExecute();
	}

	@Override
	public void execute() {
		if (childModel instanceof ReferElement) {
			ReferElement refChild = (ReferElement)childModel;
			
			oldParent = refChild.getParent();
			
			oldBounds = new Rectangle(
					refChild.getX(),
					refChild.getY(),
					refChild.getWidth(),
					refChild.getHeight()
					);
		}

		redo();
	}

	@Override
	public void redo() {
		ReferElement refParent = null;
		ItemElement realParent = null;
		ReferElement refChild = null;
		ItemElement realChild = null;

		if (newParent instanceof ReferElement) {
			refParent = (ReferElement)newParent;
			realParent = refParent.getRealModel();
		}
		else {
			realParent = newParent;
		}
		
		if (childModel instanceof ReferElement) {
			refChild = (ReferElement)childModel;
			realChild = refChild.getRealModel();
		}
		else {
			realChild = childModel;
		}
		
		if (realParent instanceof StateElement) {
			if (realChild instanceof ExpandTransElement) {
				realChild.setParent(realParent);
				((StateElement)realParent).getBifurcates().add((ExpandTransElement)realChild);
			}
		}
		
		if (refParent != null) {
			if (refChild != null) {
				refChild.setParent(refParent);
				refParent.getItems().add(refChild);
				
				if (newBounds != null) {
					refChild.setX(newBounds.x);
					refChild.setY(newBounds.y);
					refChild.setWidth(newBounds.width);
					refChild.setHeight(newBounds.height);
				}
			}
		}
	}

	@Override
	public void undo() {
		ReferElement refParent = null;
		ItemElement realParent = null;
		ReferElement refChild = null;
		ItemElement realChild = null;

		if (oldParent instanceof ReferElement) {
			refParent = (ReferElement)oldParent;
			realParent = refParent.getRealModel();
		}
		else {
			realParent = oldParent;
		}
		
		if (childModel instanceof ReferElement) {
			refChild = (ReferElement)childModel;
			realChild = refChild.getRealModel();
		}
		else {
			realChild = childModel;
		}
		
		if (realParent instanceof StateElement) {
			if (realChild instanceof ExpandTransElement) {
				realChild.setParent(realParent);
				((StateElement)realParent).getBifurcates().add((ExpandTransElement)realChild);
			}
		}
		
		if (refParent != null) {
			if (refChild != null) {
				refChild.setParent(refParent);
				refParent.getItems().add(refChild);
				
				if (oldBounds != null) {
					refChild.setX(oldBounds.x);
					refChild.setY(oldBounds.y);
					refChild.setWidth(oldBounds.width);
					refChild.setHeight(oldBounds.height);
				}
			}
		}
		
	}
	
}
