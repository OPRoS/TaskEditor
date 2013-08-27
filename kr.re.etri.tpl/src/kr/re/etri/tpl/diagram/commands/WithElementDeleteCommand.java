package kr.re.etri.tpl.diagram.commands;

import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.util.EList;

public class WithElementDeleteCommand extends ShapeModelDeleteCommand {

	private int oldPosition;
	private WithElement nextItem;
	private ReferElement refNext;
	
	public WithElementDeleteCommand(ItemElement parentModel, ItemElement child) {
		super(parentModel, child);
	}

	public WithElementDeleteCommand(ItemElement parentModel, ReferElement child) {
		super(parentModel, child);
	}

	@Override
	public void execute() {
		ItemElement realParent;
		
		if (parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else {
			realParent = parentModel;
		}
		
		if (realParent instanceof ConnectorElement) {
			EList<WithElement> withList = ((ConnectorElement)realParent).getWiths();
			oldPosition = withList.indexOf(realItem);
			try {
				nextItem = withList.get(oldPosition + 1);
			} catch(IndexOutOfBoundsException e) {}
		}
		else if (realParent instanceof TaskElement) {	// KJH 20110816
			EList<ItemElement> withList = ((TaskElement)realParent).getItems();
			oldPosition = withList.indexOf(realItem);
		}
		
		if ((parentModel instanceof ReferElement) && (nextItem != null)) {
			for (Iterator<ItemElement> iter = ((ReferElement)parentModel).getItems().iterator(); iter.hasNext(); ) {
				ItemElement itemRef = iter.next();
				if ((itemRef instanceof ReferElement) && 
						((ReferElement)itemRef).getRealModel() == nextItem) {
					refNext = (ReferElement)itemRef;
					break;
				}
			}
		}
		
		super.execute();
	}

	@Override
	protected void redoReal() {
		ItemElement realParent;
		
		if(realItem.isIncluded()) {
			redoIncludedReal();
			return;
		}

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else {
			realParent = parentModel;
		}
		
		boolean isContained = false;
		if (realParent instanceof ConnectorElement) {
			isContained = ((ConnectorElement)realParent).getWiths().contains(realItem);
		}
		else if (realParent instanceof TaskElement) {	// KJH 20110816
			isContained = ((TaskElement)realParent).getItems().contains(realItem);
		}
		
		// remove the child and disconnect its connections
		if (isContained) {
			EList<ReferElement> refList;
			removeConnections(sourceConnections);
			if (nextItem != null) {
				refList = nextItem.getReferences();
				for (Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
					ReferElement itemRef = itemIter.next();
					
					reconnectConnections(targetConnections, itemRef);
				}
			}
			else {
				reconnectConnections(targetConnections, null);
			}

			// Include모델은 삭제하지 않는다.
			// Container 에서 Item 을 삭제한다.
			if (realParent instanceof ConnectorElement) {
				wasRemoved = ((ConnectorElement)realParent).getWiths().remove(realItem);
			}
			else if (realParent instanceof TaskElement) {	// KJH 20110816
				wasRemoved = ((TaskElement)realParent).getItems().remove(realItem);
			}
			
			// Item 을 참조 Item 들을 삭제한다.
			// 참조 Item 들을 포함하는 Container 에서 참조 Item 을 삭제한다.
			refList = realItem.getReferences();
			for(Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
				ReferElement itemRef = itemIter.next();
				ItemElement parentRef = itemRef.getParent();
				
				if(parentRef instanceof ReferElement) {
					((ReferElement)parentRef).getItems().remove(itemRef);
				}
			}
		}
	}

	@Override
	protected void redoRef() {
		ItemElement realParent;

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else {
			return;
		}
		
		boolean isContained = false;
		if(realParent instanceof ConnectorElement) {
			isContained = ((ConnectorElement)realParent).getWiths().contains(realItem);
		}
		else if (realParent instanceof TaskElement) {	// KJH 20110816
			isContained = ((TaskElement)realParent).getItems().contains(realItem);
		}
		
		if (isContained) {
			if (realItem.isIncluded() == false) {
				if (realParent instanceof ConnectorElement) {
					((ConnectorElement)realParent).getWiths().remove(realItem);
				}
				else if (realParent instanceof TaskElement) {
					((TaskElement)realParent).getItems().remove(realItem);
				}
			}
		}
		
		if(parentModel instanceof ReferElement) {
			isContained = ((ReferElement)parentModel).getItems().contains(refItem);
		}
		
		if (isContained) {
			removeConnections(sourceConnections);
			// KJH 20110529, reconnect
			reconnectConnections(targetConnections, refNext);
			
			if(parentModel instanceof ReferElement) {
				wasRemoved = ((ReferElement)parentModel).getItems().remove(refItem);
			}
			
			realItem.getReferences().remove(refItem);
		}
	}

	@Override
	protected void undoReal() {
		ItemElement realParent;

		if(realItem.isIncluded()) {
			undoIncludedReal();
			return;
		}

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else {
			realParent = parentModel;
		}

		EList<ReferElement> refList;
		// connection
		addConnections(sourceConnections);
		refList = realItem.getReferences();
		for (Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
			ReferElement itemRef = itemIter.next();

			reconnectConnections(targetConnections, itemRef);
		}
		// connection
		
		refList = realItem.getReferences();
		for(Iterator<ReferElement> iter = refList.iterator(); iter.hasNext(); ) {
			ReferElement itemRef = iter.next();
			ItemElement parentRef = itemRef.getParent();
			
			if(parentRef instanceof ReferElement) {
				((ReferElement)parentRef).getItems().add(itemRef);
			}
		}

		if (realParent instanceof ConnectorElement) {
			((ConnectorElement)realParent).getWiths().add(oldPosition, (WithElement)realItem);
		}
		else if (realParent instanceof TaskElement) {	// KJH 20110816
			((TaskElement)realParent).getItems().add(oldPosition, (WithElement)realItem);
		}
	}

	@Override
	protected void undoRef() {
		ItemElement realParent;

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else {
			realParent = parentModel;
		}

		realItem.getReferences().add(refItem);
		
		if(parentModel instanceof ReferElement) {
			((ReferElement)parentModel).getItems().add(refItem);
		}
		
		if(refItem instanceof LinkedElement) {
			addConnections(sourceConnections);
			// KJH 20110529, reconnect
			reconnectConnections(targetConnections, refItem);
		}

		if(realItem.isIncluded() == false && refItem.getAttribute() != ReferAttribute.EXTERNAL) {
			if (realParent instanceof ConnectorElement) {
				((ConnectorElement)realParent).getWiths().add(oldPosition, (WithElement)realItem);
			}
			else if (realParent instanceof TaskElement) {	// KJH 20110816
				((TaskElement)realParent).getItems().add(oldPosition, realItem);
			}
		}

	}
	
	protected void reconnectConnections(List connections, LinkedElement newTarget) {
		if (newTarget != null) {
			for (Iterator iter = connections.iterator(); iter.hasNext();) {
				ConnectionElement conn = (ConnectionElement) iter.next();

				LinkedElement source = conn.getSource();
				if(source.getSourceConnections() != null) {
					if (source.getSourceConnections().contains(conn) == false) {
						source.getSourceConnections().add(conn);
					}
				}
				
				LinkedElement target = conn.getTarget();
				if(target.getTargetConnections() != null) {
					target.getTargetConnections().remove(conn);
				}
				conn.setTarget(newTarget);
				conn.setTarget2(newTarget);
				if(newTarget.getTargetConnections() != null) {
					if (newTarget.getTargetConnections().contains(conn) == false) {
						newTarget.getTargetConnections().add(conn);
					}
				}
			}
		}
		else {
			for (Iterator iter = connections.iterator(); iter.hasNext();) {
				ConnectionElement conn = (ConnectionElement) iter.next();

				LinkedElement source = conn.getSource();
				if(source.getSourceConnections() != null) {
					source.getSourceConnections().remove(conn);
				}
				
				LinkedElement target = conn.getTarget();
				if(target.getTargetConnections() != null) {
					target.getTargetConnections().remove(conn);
				}
			}
		}
	}

}
