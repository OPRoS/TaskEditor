package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.grammar.ItemElementVerify;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;

public class UpdateDiagramCommand extends Command {
	private static Logger logger = Logger.getLogger(UpdateDiagramCommand.class);
	
	private ModelDiagram oldModelDiagram;
	
	private ModelDiagram newModelDiagram;
	
	private List<ItemElement> addList;
	private List<ItemElement> removeList;

	private List<ConnectionElement> addConnList;
	private List<ConnectionElement> removeConnList;

	private String oldScript;
	
	
	public UpdateDiagramCommand(ModelDiagram oldModelDiagram, ModelDiagram newModelDiagram) {
		this.oldModelDiagram = oldModelDiagram;
		this.newModelDiagram = newModelDiagram;
		
		addList = new ArrayList<ItemElement>();
		removeList = new ArrayList<ItemElement>();
		addConnList = new ArrayList<ConnectionElement>();
		removeConnList = new ArrayList<ConnectionElement>();
	}

	@Override
	public boolean canExecute() {
		return true;
	}
	@Override
	public void execute() {
		try {
			ItemElementVerify.verify(newModelDiagram);
			update(oldModelDiagram.getSubDiagram(), newModelDiagram.getSubDiagram());
			
			removeList.addAll(oldModelDiagram.getIncludeItems());
			removeList.addAll(oldModelDiagram.getSubDiagram().getItems());
			for (ItemElement item : oldModelDiagram.getItems()) {
				if (item.getReferences().size() == 0) {
					removeList.add(item);
				}
			}
			
			addList.addAll(newModelDiagram.getIncludeItems());
			addList.addAll(newModelDiagram.getSubDiagram().getItems());
			for (ItemElement item : newModelDiagram.getItems()) {
				if (item.getReferences().size() == 0) {
					addList.add(item);
				}
			}
			
			redo();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	@Override
	public void redo() {
		oldScript = oldModelDiagram.getScript();
		oldModelDiagram.setScript(newModelDiagram.getScript());
		
		for (ItemElement item : removeList) {
			if (item instanceof ReferElement) {
				ReferElement refItem = (ReferElement)item;
				backupConnections(refItem, removeConnList);
			}
		}
		removeConnections(removeConnList);
		
		for (ItemElement item : removeList) {
			if (item instanceof ReferElement) {
				ReferElement refItem = (ReferElement)item;
				ItemElement realItem = refItem.getRealModel();
				ModelDiagram modelDiagram = (ModelDiagram)realItem.getParent();
				SubDiagram subDiagram = modelDiagram.getSubDiagram();
				
				if (realItem instanceof IncludedElement) {
					if (modelDiagram.getIncludeItems().contains(realItem)) {
						modelDiagram.getIncludeItems().remove(realItem);
					}
				}
				else {
					if (modelDiagram.getItems().contains(realItem)) {
						modelDiagram.getItems().remove(realItem);
					}
				}
				realItem.setParent(null);
				
				for (ConnectionElement conn : refItem.getSourceConnections()) {
					conn.getSource().getSourceConnections().remove(conn);
					conn.getTarget().getTargetConnections().remove(conn);
				}
				for (ConnectionElement conn : refItem.getTargetConnections()) {
					conn.getSource().getSourceConnections().remove(conn);
					conn.getTarget().getTargetConnections().remove(conn);
				}

				if (subDiagram.getItems().contains(item)) {
					subDiagram.getItems().remove(item);
				}
				item.setParent(null);
			}
			else if (item instanceof IncludedElement) {
				ItemElement parent = item.getParent();
				if (parent instanceof ModelDiagram) {
					ModelDiagram modelDiagram = (ModelDiagram)parent;
					if (modelDiagram.getIncludeItems().contains(item)) {
						modelDiagram.getIncludeItems().remove(item);
					}
				}
				item.setParent(null);
			}
			else {
				ItemElement parent = item.getParent();
				if (parent instanceof ModelDiagram) {
					ModelDiagram modelDiagram = (ModelDiagram)parent;
					if (modelDiagram.getItems().contains(item)) {
						modelDiagram.getItems().remove(item);
					}
				}
				item.setParent(null);
			}
		}
		
		for (ItemElement item : addList) {
			if (item instanceof ReferElement) {
				ItemElement realItem = ((ReferElement)item).getRealModel();
				ModelDiagram modelDiagram = oldModelDiagram;
				SubDiagram subDiagram = modelDiagram.getSubDiagram();
				
				if (realItem instanceof IncludedElement) {
					modelDiagram.getIncludeItems().add((IncludedElement)realItem);
				}
				else {
					modelDiagram.getItems().add(realItem);
				}
				realItem.setParent(modelDiagram);
				
				subDiagram.getItems().add((ReferElement)item);
				item.setParent(subDiagram);
			}
			else if (item instanceof IncludedElement) {
				ModelDiagram modelDiagram = oldModelDiagram;
				modelDiagram.getIncludeItems().add((IncludedElement)item);
				item.setParent(modelDiagram);
			}
			else {
				ModelDiagram modelDiagram = oldModelDiagram;
				modelDiagram.getItems().add(item);
				item.setParent(modelDiagram);
			}
		}
		addConnections(addConnList);
		addConnList.clear();
	}
	@Override
	public void undo() {
		oldModelDiagram.setScript(oldScript);
		
		for (ItemElement item : addList) {
			if (item instanceof ReferElement) {
				ReferElement refItem = (ReferElement)item;
				backupConnections(refItem, addConnList);
			}
		}
		removeConnections(addConnList);

		for (ItemElement item : addList) {
			if (item instanceof ReferElement) {
				ReferElement refItem = (ReferElement)item;
				ItemElement realItem = refItem.getRealModel();
				ModelDiagram modelDiagram = (ModelDiagram)realItem.getParent();
				SubDiagram subDiagram = modelDiagram.getSubDiagram();

				if (realItem instanceof IncludedElement) {
					if (modelDiagram.getIncludeItems().contains(realItem)) {
						modelDiagram.getIncludeItems().remove(realItem);
					}
				}
				else {
					if (modelDiagram.getItems().contains(realItem)) {
						modelDiagram.getItems().remove(realItem);
					}
				}
				realItem.setParent(null);
				
				if (subDiagram.getItems().contains(item)) {
					subDiagram.getItems().remove(item);
				}
				item.setParent(null);
			}
			else if (item instanceof IncludedElement) {
				ItemElement parent = item.getParent();
				if (parent instanceof ModelDiagram) {
					ModelDiagram modelDiagram = (ModelDiagram)parent;
					if (modelDiagram.getIncludeItems().contains(item)) {
						modelDiagram.getIncludeItems().remove(item);
					}
				}
				item.setParent(null);
			}
			else {
				ItemElement parent = item.getParent();
				if (parent instanceof ModelDiagram) {
					ModelDiagram modelDiagram = (ModelDiagram)parent;
					if (modelDiagram.getItems().contains(item)) {
						modelDiagram.getItems().remove(item);
					}
				}
				item.setParent(null);
			}
		}
		
		for (ItemElement item : removeList) {
			if (item instanceof ReferElement) {
				ItemElement realItem = ((ReferElement)item).getRealModel();
				ModelDiagram modelDiagram = oldModelDiagram;
				SubDiagram subDiagram = modelDiagram.getSubDiagram();
				
				if (realItem instanceof IncludedElement) {
					modelDiagram.getIncludeItems().add((IncludedElement)realItem);
				}
				else {
					modelDiagram.getItems().add(realItem);
				}
				realItem.setParent(modelDiagram);
				
				subDiagram.getItems().add((ReferElement)item);
				item.setParent(subDiagram);
			}
			else if (item instanceof IncludedElement) {
				ModelDiagram modelDiagram = oldModelDiagram;
				modelDiagram.getIncludeItems().add((IncludedElement)item);
				item.setParent(modelDiagram);
			}
			else {
				ModelDiagram modelDiagram = oldModelDiagram;
				modelDiagram.getItems().add((IncludedElement)item);
				item.setParent(modelDiagram);
			}
		}
		addConnections(removeConnList);
		removeConnList.clear();
	}
	
	private void update(ItemElement oldModel, ItemElement newModel) throws Exception{
		Assert.isTrue(oldModel == null || oldModel.getClass().equals(newModel.getClass()));
		
		List<ReferElement> oldList = new ArrayList<ReferElement>();
		List<ReferElement> newList = new ArrayList<ReferElement>();
		List<ReferElement> tempList = new ArrayList<ReferElement>();

		if (oldModel instanceof SubDiagram) {
			oldList.addAll(((SubDiagram)oldModel).getItems());
		}
		else if (oldModel instanceof ReferElement) {
			for (ItemElement item : ((ReferElement)oldModel).getItems()) {
				if (item instanceof ReferElement) {
					oldList.add((ReferElement)item);
				}
			}
		}
		
		if (newModel instanceof SubDiagram) {
			newList.addAll(((SubDiagram)newModel).getItems());
		}
		else if (newModel instanceof ReferElement) {
			for (ItemElement item : ((ReferElement)newModel).getItems()) {
				if (item instanceof ReferElement) {
					newList.add((ReferElement)item);
				}
			}
			
			// KJH 20111004, expand&moveto에 대해 parent에 따라 기본위치 이동되도록 함
			for (ReferElement refItem : newList) {
				ItemElement realItem = refItem.getRealModel();
				if (realItem instanceof ExpandTransElement) {
					Point tmpPt = Point.SINGLETON;
					tmpPt.x = ((ReferElement)newModel).getX() - 8;
					tmpPt.y = ((ReferElement)newModel).getY() - 8;
					refItem.setX(tmpPt.x);
					refItem.setY(tmpPt.y);
					refItem.setX2(tmpPt.x);
					refItem.setY2(tmpPt.y);
				}
			}
		}
		
		// step1, same
		for (ReferElement newItem : newList) {
			if (tempList.contains(newItem)) {
				continue;
			}
			ReferElement oldItem = findSameElement(newItem, oldList);
			if (oldItem != null) {
				copyReferences(oldItem, newItem);
				refreshConnections(newItem);
				update(oldItem, newItem);
				oldList.remove(oldItem);
				tempList.add(newItem);
			}
		}
		
		// step2, similar
		for (ReferElement newItem : newList) {
			if (tempList.contains(newItem)) {
				continue;
			}
			ReferElement oldItem = findSimilarElement(newItem, oldList);
			if (oldItem != null) {
				copyReferences(oldItem, newItem);
				refreshConnections(newItem);
				update(oldItem, newItem);
				oldList.remove(oldItem);
				tempList.add(newItem);
			}
		}
		
		// step3, same name
		for (ReferElement newItem : newList) {
			if (tempList.contains(newItem)) {
				continue;
			}
			for (ReferElement oldItem : oldList) {
				if (oldItem.getRealModel().getClass().equals(newItem.getRealModel().getClass()) == false) {
					continue;
				}
				if (oldItem.getName() == null || oldItem.getName().equals(newItem.getName()) == false) {
					continue;
				}
				copyReferences(oldItem, newItem);
				refreshConnections(newItem);
				update(oldItem, newItem);
				oldList.remove(oldItem);
				tempList.add(newItem);
				break;
			}
		}
		
		for (ReferElement newItem : newList) {
			if (tempList.contains(newItem)) {
				continue;
			}

			Point p = getLocation(newItem, newList);
			newItem.setX(p.x);
			newItem.setX2(p.x);
			newItem.setY(p.y);
			newItem.setY2(p.y);
			
			refreshConnections(newItem);
			update(null, newItem);
		}
	}
	
	private void copyReferences(ReferElement oldItem, ReferElement newItem) {
		newItem.setX(oldItem.getX());
		newItem.setX2(oldItem.getX2());
		newItem.setY(oldItem.getY());
		newItem.setY2(oldItem.getY2());
		newItem.setWidth(oldItem.getWidth());
		newItem.setWidth2(oldItem.getWidth2());
		newItem.setHeight(oldItem.getHeight());
		newItem.setHeight2(oldItem.getHeight2());
		newItem.setCollapsed(oldItem.isCollapsed());
		newItem.setVisible(oldItem.isVisible());
	}
	
	private void refreshConnections(ReferElement refItem) {
		// 생성시 접힌 상태이므로 펼쳐진 상태가 되면 라인에 대한 처리 필요
		List<ConnectionElement> conns = new ArrayList<ConnectionElement>();
		conns.addAll(refItem.getSourceConnections());
		if (!refItem.isCollapsed()) {
			for (ConnectionElement conn : conns) {
				conn.getSource().getSourceConnections().remove(conn);
				conn.getSource2().getSourceConnections().add(conn);

				conn.setSource(conn.getSource2());
				conn.setVisible(true);
				
				if (conn.getSource().equals(conn.getSource2())) {
					conn.setSourceEndPoint(LineEndPoint.NONE);
				}
				
				// with의 transition은 conexer인 경우 보이지 않게 함
				if (refItem.getRealModel() instanceof WithElement) {
					ItemElement parent = refItem.getRealModel().getParent();

					if (parent instanceof ConnectorElement) {
						if (ConnectorType.CONEXER == ((ConnectorElement)parent).getConType() &&
								RelationShip.TRANSITION == conn.getRelationship()) {
							conn.setVisible(false);
						}
					}
				}
			}
		}
	}
	
//	private void update(ReferElement oldItem, ReferElement newItem) {
//		List<ItemElement> oldChildren = oldItem.getItems();
//		List<ItemElement> newChildren = newItem.getItems();
//		List<ItemElement> oldList = new ArrayList<ItemElement>();
//		List<ReferElement> newList = new ArrayList<ReferElement>();
//		oldList.addAll(oldChildren);
//
//		// step1. same
//		for (int i = 0; i < newChildren.size(); i++) {
//			ReferElement fItem = (ReferElement)newChildren.get(i);
//			if (newList.contains(fItem)) {
//				continue;
//			}
//			ReferElement sItem = findSameElement(fItem, oldList);
//			if (sItem != null) {
//				copyReferences(sItem, fItem);
//				refreshConnections(fItem);
//				update(sItem, fItem);
//				oldList.remove(sItem);
//				newList.add(fItem);
//			}
//		}
//		
//		// step2. similar
//		for (int i = 0; i < newChildren.size(); i++) {
//			ReferElement fItem = (ReferElement)newChildren.get(i);
//			if (newList.contains(fItem)) {
//				continue;
//			}
//			ReferElement sItem = findSimilarElement(fItem, oldList);
//			if (sItem != null) {
//				copyReferences(sItem, fItem);
//				refreshConnections(fItem);
//				update(sItem, fItem);
//				oldList.remove(sItem);
//				newList.add(fItem);
//			}
//		}
//		
//		// step3. same name
//		for (int i = 0; i < newChildren.size(); i++) {
//			ReferElement fItem = (ReferElement)newChildren.get(i);
//			if (newList.contains(fItem)) {
//				continue;
//			}
//			for (int j = 0; j < oldChildren.size(); j++) {
//				ReferElement sItem = (ReferElement)oldChildren.get(j);
//				if (!oldList.contains(sItem)) {
//					continue;
//				}
//				if (sItem.getRealModel().getClass().equals(fItem.getRealModel().getClass()) == false) {
//					continue;
//				}
//				if (sItem.getName() == null || sItem.getName().equals(fItem.getName()) == false) {
//					continue;
//				}
//				copyReferences(sItem, fItem);
//				refreshConnections(fItem);
//				update(sItem, fItem);
//				oldList.remove(sItem);
//				newList.add(fItem);
//			}
//		}
//		for (int i = 0; i < newChildren.size(); i++) {
//			ReferElement fItem = (ReferElement)newChildren.get(i);
//			if (newList.contains(fItem)) {
//				continue;
//			}
//			
//			Point p = getLocation(fItem, newList);
//			fItem.setX(p.x);
//			fItem.setX2(p.x);
//			fItem.setY(p.y);
//			fItem.setY2(p.y);
//			fItem.setCollapsed(newItem.isCollapsed());
//			refreshConnections(fItem);
//			update(null, fItem);
//		}
//	}
	
	private boolean isSameElement(ReferElement r1, ReferElement r2) {
		ItemElement real1 = r1.getRealModel();
		ItemElement real2 = r2.getRealModel();
		
		if (real1 == null || real2 == null) {
			return false;
		}
		if (real1.getClass().equals(real2.getClass()) == false) {
			return false;
		}
		
		List<ItemElement> l1 = r1.getItems();
		List<ItemElement> l2 = r2.getItems();
		
		if (l1.size() == l2.size()) {
			boolean res = true;
			for (int i = 0; i < l1.size(); i++) {
				ReferElement sItem = findSameElement((ReferElement)l1.get(i), l2);
				res = sItem != null;
			}
			if (res) {
				if (real1 instanceof WithElement) {	// KJH 20110930, WithElement의 이름이 모두 run인 문제
					ItemElement p1 = real1.getParent();
					ItemElement p2 = real2.getParent();
					int idx1 = -1;
					int idx2 = -1;
					
					if (p1 instanceof TaskElement && p2 instanceof TaskElement) {
						idx1 = ((TaskElement)p1).getItems().indexOf(real1);
						idx2 = ((TaskElement)p2).getItems().indexOf(real2);
					}
					else if (p1 instanceof ConnectorElement && p2 instanceof ConnectorElement) {
						idx1 = ((ConnectorElement)p1).getWiths().indexOf(real1);
						idx2 = ((ConnectorElement)p2).getWiths().indexOf(real2);
					}
					
					if (idx1 > -1 && idx1 == idx2) {
						return true;
					}
				}
				else if (r1.getName() == null || r1.getName().equals(r2.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private ReferElement findSameElement(ReferElement findedElement, List<? extends ItemElement> list) {
		for (ItemElement item : list) {
			if ((item instanceof ReferElement) == false) {
				continue;
			}
			
			if (isSameElement(findedElement, (ReferElement)item)) {
				return (ReferElement)item;
			}
		}
		
		return null;
	}
	
	private boolean isSimilarElement(ReferElement r1, ReferElement r2) {
		ItemElement real1 = r1.getRealModel();
		ItemElement real2 = r2.getRealModel();
		
		if (real1 == null || real2 == null) {
			return false;
		}
		if (real1.getClass().equals(real2.getClass()) == false) {
			return false;
		}
		
		List<ItemElement> l1 = r1.getItems();
		List<ItemElement> l2 = r2.getItems();
		
		if (l1.size() == l2.size()) {
			boolean res = true;
			for (int i = 0; i < l1.size(); i++) {
				ReferElement sItem = findSimilarElement((ReferElement)l1.get(i), l2);
				res = sItem != null;
			}
			if (res) {
				return true;
			}
		}
		return false;
	}
	
	private ReferElement findSimilarElement(ReferElement findedElement, List<? extends ItemElement> list) {
		for (ItemElement item : list) {
			if ((item instanceof ReferElement) == false) {
				continue;
			}
			
			if (isSimilarElement(findedElement, (ReferElement)item)) {
				return (ReferElement)item;
			}
		}
		
		return null;
	}
	
	private Point getLocation(ReferElement refItem, List<ReferElement> children) {
		if (refItem.getRealModel() instanceof ExpandTransElement) {	// KJH 20111004
			return getBorderLocation(refItem, children);
		}
		
		final int MARGIN = 5;
		
		List<Rectangle> rectangles = new ArrayList<Rectangle>();
		Rectangle initRect = new Rectangle(
				refItem.getX(),
				refItem.getY(),
				refItem.getWidth(),
				refItem.getHeight());
		rectangles.add(initRect);
		
		List<ReferElement> tempChildren = new ArrayList<ReferElement>();
		for (ReferElement child : children) {
			if (child != refItem) {
				tempChildren.add(child);
			}
		}
		
		while (tempChildren.size() > 0) {
			boolean isContained = false;
			for (int i = 0; i < tempChildren.size(); i++) {
				ReferElement child = tempChildren.get(i);
				Rectangle r = Rectangle.SINGLETON;
				r.x = child.getX();
				r.y = child.getY();
				r.width = child.getWidth();
				r.height = child.getHeight();

				List<Rectangle> remRectangles = new ArrayList<Rectangle>();
				List<Rectangle> addRectangles = new ArrayList<Rectangle>();
				for (Rectangle rect : rectangles) {
					if (rect.contains(r.getTopLeft()) ||
							rect.contains(r.getTopRight()) ||
							rect.contains(r.getBottomLeft()) ||
							rect.contains(r.getBottomRight()) ||
							r.contains(rect.getTopLeft()) ||
							r.contains(rect.getTopRight()) ||
							r.contains(rect.getBottomLeft()) ||
							r.contains(rect.getBottomRight())) {
						remRectangles.add(rect);
						addRectangles.add(new Rectangle(
								r.x + r.width + MARGIN,
								rect.y,
								rect.width,
								rect.height));
						addRectangles.add(new Rectangle(
								rect.x,
								r.y + r.height + MARGIN,
								rect.width,
								rect.height));
						isContained = true;
					}
				}
				rectangles.removeAll(remRectangles);
				rectangles.addAll(addRectangles);
				if (isContained) {
					// KJH 20110930, ItemElementImpl.equle()
					tempChildren.remove(i);
					break;
				}
			}
			
			if (isContained == false) {
				break;
			}
		}
		
		return rectangles.size() > 0 ? rectangles.get(0).getLocation() : initRect.getLocation();
	}
	
	private Point getBorderLocation(ReferElement refItem, List<ReferElement> children) {
		final int MARGIN = 3;
		Rectangle initRect = new Rectangle(
				refItem.getX(),
				refItem.getY(),
				refItem.getWidth(),
				refItem.getHeight());
		
		Rectangle parentRect = new Rectangle();
		if (refItem.getParent() instanceof ReferElement) {
			ReferElement refParent = (ReferElement)refItem.getParent();
			parentRect.x = refParent.getX() - 8;
			parentRect.y = refParent.getY() - 8;
			parentRect.width = refParent.getWidth();
			parentRect.height = refParent.getHeight();
		}
		
		List<ReferElement> tempChildren = new ArrayList<ReferElement>();
		for (ReferElement child : children) {
			if (child != refItem) {
				tempChildren.add(child);
			}
		}
		
		Rectangle rect = new Rectangle(initRect);
		int direction = 0;
		while (tempChildren.size() > 0) {
			for (int i = 0; i < tempChildren.size(); i++) {
				ReferElement child = tempChildren.get(i);
				Rectangle r = Rectangle.SINGLETON;
				r.x = child.getX();
				r.y = child.getY();
				r.width = child.getWidth();
				r.height = child.getHeight();
				
				if (rect.contains(r.getTopLeft()) ||
						rect.contains(r.getTopRight()) ||
						rect.contains(r.getBottomLeft()) ||
						rect.contains(r.getBottomRight()) ||
						r.contains(rect.getTopLeft()) ||
						r.contains(rect.getTopRight()) ||
						r.contains(rect.getBottomLeft()) ||
						r.contains(rect.getBottomRight())) {
					if (direction == 0) {	// TopLeft -> TopRight
						rect.x += rect.width + MARGIN;
					}
					else if (direction == 1) {	// TopRight -> BottomRight
						rect.y += rect.height + MARGIN;
					}
					else if (direction == 2) {	// BottomRight -> BottomLeft
						rect.x -= rect.width + MARGIN;
					}
					else if (direction == 3) {	// BottomLeft -> TopLeft
						rect.y -= rect.height + MARGIN;
					}
					
					if (parentRect.contains(rect.getLocation()) == false) {
						direction = (direction + 1) % 4;
						
						if (rect.x < parentRect.x) {
							rect.x = parentRect.x;
							rect.y -= rect.height;
						}
						if (rect.x > parentRect.x + parentRect.width) {
							rect.x = parentRect.x + parentRect.width;
							rect.y += rect.height;
						}
						if (rect.y < parentRect.y) {
							rect.y = parentRect.y;
							// 한바퀴 모두 돈 경우
						}
						if (rect.y > parentRect.y + parentRect.height) {
							rect.y = parentRect.y + parentRect.height;
							rect.x -= rect.width;
						}
					}
					
					// 한바퀴 모두 돈 경우
					if (rect.equals(initRect)) {
						return initRect.getLocation();
					}
					
					tempChildren.remove(i);
					break;
				}
			}
		}
		
		return rect.getLocation();
	}
	
	private void backupConnections(ReferElement refItem, List<ConnectionElement> dst) {
		EList<ConnectionElement> connList;

		connList = refItem.getSourceConnections(); 
		for (Iterator<ConnectionElement> iter = connList.iterator(); iter.hasNext();) {
			ConnectionElement conn = iter.next();
			if(dst.contains(conn)) {
				continue;
			}

			dst.add(conn);
		}
		
		connList = refItem.getTargetConnections();
		for (Iterator<ConnectionElement> iter = connList.iterator(); iter.hasNext();) {
			ConnectionElement conn = iter.next();
			if(dst.contains(conn)) {
				continue;
			}

			dst.add(conn);
		}

		List<ItemElement> childList = refItem.getItems();
		for(Iterator<ItemElement> iter = childList.iterator(); iter.hasNext();) {
			ItemElement child = (ItemElement)iter.next();
			if(child instanceof ReferElement) {
				backupConnections((ReferElement)child, dst);
			}
		}
	}
	
	private void addConnections(List<ConnectionElement> connections) {
		for (Iterator<ConnectionElement> iter = connections.iterator(); iter.hasNext();) {
			ConnectionElement conn = iter.next();
			LinkedElement source = conn.getSource();
			if(source.getSourceConnections() != null) {
				source.getSourceConnections().add(conn);
			}

			LinkedElement target = conn.getTarget();
			if(target.getTargetConnections() != null) {
				target.getTargetConnections().add(conn);
			}
		}
	}

	private void removeConnections(List<ConnectionElement> connections) {
		for(Iterator<ConnectionElement> iter = connections.iterator(); iter.hasNext(); ) {
			ConnectionElement conn = (ConnectionElement)iter.next();

			LinkedElement source = conn.getSource();
			source.getSourceConnections().remove(conn);

			LinkedElement target = conn.getTarget();
			target.getTargetConnections().remove(conn);
		}
	}

}
