package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;

public class TaskElementFoldingCommand extends Command {

	/** 새로운 크기와 위치 */
	private final Rectangle newBounds;
	/** 이전 크기와 위치 */
	private Rectangle oldBounds;
	/** 펼침/닫힘 상태(tree 면 닫힘 상태, 그렇지 않으면 펼침 상태 */
	private boolean collapsed;
	/** 펼치기/닫기 대상 Graphical Node 모델 */
	private final ReferElement shapeModel;
	/** 펼치기/닫기 요청 Request */
	private final ChangeFoldingRequest request;
	/** command 의 실행 성공 여부 */
	private boolean executed;
	
	/**
	 * Graphical Node 의 펼치기/닫기를 수행하는 command 를 생성한다.
	 * @param model 펼치기/닫기 대상 Graphical Node 모델
	 * @param req     펼치기/닫기 요청 Request
	 * @throws IllegalArgumentException 임의 parameter 가 null 이면 발생한다.
	 */
	public TaskElementFoldingCommand(ReferElement model,
			ChangeFoldingRequest req) {
		if (model == null || req == null) {
			throw new IllegalArgumentException();
		}
		this.shapeModel = model;
		this.request = req;
		Dimension size = req.getSizeDelta();
		this.newBounds = new Rectangle(shapeModel.getX(),
										shapeModel.getY(),
										shapeModel.getWidth()+size.width,
										shapeModel.getHeight() + size.height);
		this.collapsed = req.isCollapsed();
		ItemElement realItem = shapeModel.getRealModel();
		setLabel(String.format("%s folding(resize)", realItem.getName()));
	}

	/**
	 * 이 command 가 실행 가능한지 여부를 반환한다.
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
	 * 이 command 가 Undo 가 가능한지 여부를 반환한다.
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return executed;
	}
	
	/**
	 * 이 command 를 실행한다.
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
	 * 이 command 를 Redo 한다.
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
		
		EList<ConnectionElement> conConns = shapeModel.getSourceConnections();
		if(shapeModel.isCollapsed() == false) {
			ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
			conList.addAll(conConns);
			
			for(ConnectionElement con : conList) {
				LinkedElement linkSource = con.getSource2();
				EList<ConnectionElement> linkConns = linkSource.getSourceConnections();
				con.setSource(linkSource);
				linkConns.add(con);
			}
			
			EList<ItemElement> itemList = shapeModel.getItems();
			for(ItemElement item : itemList) {
				if(item instanceof LinkedElement) {
					LinkedElement lnElmt = (LinkedElement)item;
		
					lnElmt.setX(lnElmt.getX2());
					lnElmt.setY(lnElmt.getY2());
					lnElmt.setWidth(lnElmt.getWidth2());
					lnElmt.setHeight(lnElmt.getHeight2());
					lnElmt.setCollapsed(shapeModel.isCollapsed());
				}	
				item.setVisible(true);
			}
		}
		else {
			EList<ItemElement> itemList = shapeModel.getItems();
			for(ItemElement item : itemList) {
				if(item instanceof LinkedElement) {
					LinkedElement lnElmt = (LinkedElement)item;
					EList<ConnectionElement> itemConns;
					itemConns = lnElmt.getSourceConnections();
	
					ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
					for(ConnectionElement con : itemConns) {
						ReferElement refItem = (ReferElement)con.getTarget();
						ItemElement target = refItem.getRealModel();
						if(!(target instanceof WithElement)) {
							conList.add(con);
						}
						else {
							con.setVisible(false);
						}
					}
					for(ConnectionElement con : conList) {
						con.setSource(shapeModel);
						conConns.add(con);
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
				item.setVisible(false);
			}
		}
		
		executed = true;
	}
	
	/**
	 * 이 command 를 Undo 한다.
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

		EList<ConnectionElement> conConns = shapeModel.getSourceConnections();
		if(shapeModel.isCollapsed() == false) {
			ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
			conList.addAll(conConns);
			
			for(ConnectionElement con : conList) {
				LinkedElement linkSource = con.getSource2();
				EList<ConnectionElement> linkConns = linkSource.getSourceConnections();
				con.setSource(linkSource);
				linkConns.add(con);				
			}
			
			EList<ItemElement> itemList = shapeModel.getItems();
			for(ItemElement item : itemList) {
				if(item instanceof LinkedElement) {
					LinkedElement lnElmt = ((LinkedElement)item);
	
					lnElmt.setX(lnElmt.getX2());
					lnElmt.setY(lnElmt.getY2());
					lnElmt.setWidth(lnElmt.getWidth2());
					lnElmt.setHeight(lnElmt.getHeight2());
					lnElmt.setCollapsed(shapeModel.isCollapsed());
				}

				item.setVisible(true);
			}
		}
		else {
			EList<ItemElement> itemList = shapeModel.getItems();
			for(ItemElement item : itemList) {
				if(item instanceof LinkedElement) {
					LinkedElement lnElmt = ((LinkedElement)item);
					EList<ConnectionElement> itemConns;
					itemConns = lnElmt.getSourceConnections();
	
					ArrayList<ConnectionElement> conList = new ArrayList<ConnectionElement>();
					for(ConnectionElement con : itemConns) {
						ReferElement refItem = (ReferElement)con.getTarget();
						ItemElement target = refItem.getRealModel();
						if(!(target instanceof WithElement)) {
							conList.add(con);
						}
						else {
							con.setVisible(false);
						}
					}
					for(ConnectionElement con : conList) {
							con.setSource(shapeModel);
							conConns.add(con);
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
				
				item.setVisible(false);
			}
		}
		
		executed = false;
	}

}
