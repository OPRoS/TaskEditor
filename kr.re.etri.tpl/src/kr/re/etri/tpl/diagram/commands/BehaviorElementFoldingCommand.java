package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;

/**
 * TaskElement �� Graphic Node �� ��ġ��/�ݱ⸦ �����ϴ� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class BehaviorElementFoldingCommand extends Command {
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
	public BehaviorElementFoldingCommand(ReferElement model, ChangeFoldingRequest req) {
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

		EList<ConnectionElement> taskConns = shapeModel.getSourceConnections();
		if(shapeModel.isCollapsed() == false) {
			ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
			conList.addAll(taskConns);
			
			for(ConnectionElement taskCon : conList) {
				LinkedElement linkSource = taskCon.getSource2();
				EList<ConnectionElement> linkConns = linkSource.getSourceConnections();
				taskCon.setSource(linkSource);
				linkConns.add(taskCon);				
			}
			
			unfold(shapeModel);
		}
		else {
			fold(shapeModel, taskConns);
		}
		
		executed = true;
	}
	
	private void fold(ReferElement refModel, EList<ConnectionElement> taskConns) {
		EList<ItemElement> itemList = refModel.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof LinkedElement) {
				LinkedElement lnElmt = (LinkedElement)item;
				EList<ConnectionElement> itemConns;
				itemConns = lnElmt.getSourceConnections();

				ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
				for(ConnectionElement con : itemConns) {
					ReferElement refItem = (ReferElement)con.getTarget();
					ItemElement target = refItem.getRealModel();
					if(!(target instanceof StateElement)) {
						conList.add(con);
					}
					else {
						con.setVisible(false);
					}
				}
				for(ConnectionElement con : conList) {
						con.setSource(shapeModel);
						taskConns.add(con);
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
				fold((ReferElement)item, taskConns);
			}
			
			item.setVisible(false);
		}
	}
	
	private void unfold(ReferElement refModel) {
		EList<ItemElement> itemList = refModel.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof LinkedElement) {
				LinkedElement lnElmt = (LinkedElement)item;
				EList<ConnectionElement> itemConns;
				itemConns = lnElmt.getSourceConnections();

				for(ConnectionElement con : itemConns) {
					ReferElement refItem = (ReferElement)con.getTarget();
					ItemElement target = refItem.getRealModel();
					if(target instanceof StateElement) {
						con.setVisible(true);
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
