package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;

import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;

public class ItemElementFoldingCommand extends Command {

	/** ���ο� ũ��� ��ġ */
	private final Rectangle newBounds;
	/** ���� ũ��� ��ġ */
	private Rectangle oldBounds;
	/** ��ħ/���� ����(tree �� ���� ����, �׷��� ������ ��ħ ���� */
	private boolean collapsed;
	/** ��ġ��/�ݱ� ��� Graphical Node �� */
	private final ReferElement shapeModel;
	/** ��ġ��/�ݱ� ��û Request */
	private final ChangeFoldingRequest request;
	/** command �� ���� ���� ���� */
	private boolean executed;

	/**
	 * Graphical Node �� ��ġ��/�ݱ⸦ �����ϴ� command �� �����Ѵ�.
	 * @param model ��ġ��/�ݱ� ��� Graphical Node ��
	 * @param req     ��ġ��/�ݱ� ��û Request
	 * @throws IllegalArgumentException ���� parameter �� null �̸� �߻��Ѵ�.
	 */
	public ItemElementFoldingCommand(ReferElement model, ChangeFoldingRequest req) {
		if (model == null || req == null) {
			throw new IllegalArgumentException();
		}
		this.shapeModel = model;
		this.request = req;
		Dimension size = req.getSizeDelta();
		this.newBounds = new Rectangle(shapeModel.getX(),
										shapeModel.getY(),
										shapeModel.getWidth() + size.width,
										shapeModel.getHeight() + size.height);
		this.collapsed = req.isCollapsed();
		ItemElement realItem = shapeModel.getRealModel();
		setLabel(String.format("%s folding(resize)", realItem.getName()));
	}
	
	/**
	 * �� command �� ���� �������� ���θ� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		Object type = request.getType();
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type) 
				|| RequestConstants.REQ_RESIZE.equals(type)
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type));
	}

	/**
	 * �� command �� Undo �� �������� ���θ� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return executed;
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldBounds = new Rectangle(
				shapeModel.getX(), 
				shapeModel.getY(), 
				shapeModel.getWidth(), 
				shapeModel.getHeight());

		redo();
	}

	/**
	 * �� command �� Redo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		shapeModel.setX(newBounds.x);
		shapeModel.setY(newBounds.y);
		shapeModel.setWidth2(shapeModel.getWidth());
		shapeModel.setHeight2(shapeModel.getHeight());
		shapeModel.setWidth(newBounds.width);
		shapeModel.setHeight(newBounds.height);
		shapeModel.setCollapsed(this.collapsed);

		EList<ConnectionElement> srcConns = shapeModel.getSourceConnections();
		if(shapeModel.isCollapsed() == false) {
			ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
			conList.addAll(srcConns);
			
			for(ConnectionElement conn : conList) {
				LinkedElement linkSource = conn.getSource2();
				EList<ConnectionElement> linkConns = linkSource.getSourceConnections();
				conn.setSource(linkSource);
				linkConns.add(conn);
				conn.setSourceEndPoint(LineEndPoint.NONE);
			}

			unfold(shapeModel);
		}
		else {
			fold(shapeModel, srcConns);
		}
		
		executed = true;
	}
	
	/**
	 * �� command �� Undo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		shapeModel.setX(oldBounds.x);
		shapeModel.setY(oldBounds.y);
		shapeModel.setWidth2(shapeModel.getWidth());
		shapeModel.setHeight2(shapeModel.getHeight());
		shapeModel.setHeight(oldBounds.height);
		shapeModel.setWidth(oldBounds.width);
		shapeModel.setCollapsed(!this.collapsed);

		EList<ConnectionElement> srcConns = shapeModel.getSourceConnections();
		if(shapeModel.isCollapsed() == false) {
			ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
			conList.addAll(srcConns);
			
			for(ConnectionElement conn : conList) {
				LinkedElement linkSource = conn.getSource2();
				EList<ConnectionElement> linkConns = linkSource.getSourceConnections();
				conn.setSource(linkSource);
				linkConns.add(conn);
			}
			
			unfold(shapeModel);
		}
		else {
			fold(shapeModel, srcConns);
		}
		
		executed = true;
	}
	
	private void fold(ReferElement refModel, EList<ConnectionElement> srcConns) {
		EList<ItemElement> itemList = refModel.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof LinkedElement) {
				LinkedElement lnElmt = (LinkedElement)item;
				EList<ConnectionElement> itemConns;
				itemConns = lnElmt.getSourceConnections();

				ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
				for(ConnectionElement conn : itemConns) {
					ReferElement refItem = (ReferElement)conn.getTarget();
					ItemElement target = refItem.getRealModel();
					if(target instanceof BehaviorElement ||
							target instanceof ConnectorElement ||
							target instanceof TaskElement) {
						conList.add(conn);
					}
					else {
						conn.setVisible(false);
					}
				}
				for(ConnectionElement conn : conList) {
					conn.setSource(shapeModel);
					conn.setSourceEndPoint(LineEndPoint.OPENED_CIRCLE);
					srcConns.add(conn);
				}
				
				lnElmt.setX2(lnElmt.getX());
				lnElmt.setY2(lnElmt.getY());
				lnElmt.setWidth2(lnElmt.getWidth());
				lnElmt.setHeight2(lnElmt.getHeight());
				lnElmt.setX(0);
				lnElmt.setY(0);
				lnElmt.setWidth(0);
				lnElmt.setHeight(0);
				lnElmt.setCollapsed(shapeModel.isCollapsed());
			}
			
			if(item instanceof ReferElement) {
				fold((ReferElement)item, srcConns);
			}
			
			item.setVisible(false);
		}
	}
	
	private void unfold(ReferElement refModel) {
		ItemElement realModel = refModel.getRealModel();
		EList<ItemElement> itemList = refModel.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof LinkedElement) {
				LinkedElement lnElmt = (LinkedElement)item;
				EList<ConnectionElement> itemConns;
				itemConns = lnElmt.getSourceConnections();

				for(ConnectionElement conn : itemConns) {
					ReferElement refItem = (ReferElement)conn.getTarget();
					ItemElement target = refItem.getRealModel();
					if(target instanceof WithElement &&
							realModel instanceof ConnectorElement) {
						ConnectorElement connector = (ConnectorElement)realModel;
						conn.setVisible(ConnectorType.SEQEXER == connector.getConType());
					}
					if(target instanceof StateElement) {
						conn.setVisible(true);
					}
				}

				lnElmt.setX(lnElmt.getX2());
				lnElmt.setY(lnElmt.getY2());
				lnElmt.setWidth(lnElmt.getWidth2());
				lnElmt.setHeight(lnElmt.getHeight2());
				lnElmt.setCollapsed(shapeModel.isCollapsed());
			}
			
			if (item instanceof ReferElement) {
				unfold((ReferElement)item);
			}

			item.setVisible(true);
		}
	}

}