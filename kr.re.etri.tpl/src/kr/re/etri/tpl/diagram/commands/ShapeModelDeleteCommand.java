package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;

/**
 * A command to remove a shape from its parent.
 * The command can be undone or redone.
 * @author sfline
 */
public class ShapeModelDeleteCommand extends Command {
	protected ItemElement realItem;
	
	/** ShapeDiagram to remove from. */
	protected final ItemElement parentModel;
	/** ShapeElement to remove. */
	protected final ReferElement refItem;
	/** Holds a copy of the outgoing connections of child. */
	protected List sourceConnections;
	/** Holds a copy of the incoming connections of child. */
	protected List targetConnections;
	private List references;
	/** True, if child was removed from its parent. */
	protected boolean wasRemoved;
	
	private boolean isRef;
	
	/**
	 * Create a command that will remove the shape from its parent.
	 * @param parent the ModelDiagram containing the child
	 * @param child    the ShapeElement to remove
	 * @throws IllegalArgumentException if any parameter is null
	 */
	public ShapeModelDeleteCommand(ItemElement parentModel, ReferElement child) {
		if (parentModel == null || child == null) {
			throw new IllegalArgumentException();
		}
		setLabel(String.format("%s deletion", child.getName()));
		this.parentModel = parentModel;
		this.refItem = child;
		this.realItem = child.getRealModel();
		this.isRef = true;
	}
	
	public ShapeModelDeleteCommand(ItemElement parentModel, ItemElement child) {
		if (parentModel == null || child == null) {
			throw new IllegalArgumentException();
		}
		setLabel(String.format("%s deletion", child.getName()));
		this.parentModel = parentModel;
		this.refItem = null;
		this.realItem = child;
		this.isRef = false;
	}
	
	/**
	 * Reconnects a List of Connections with their previous endpoints.
	 * @param connections a non-null List of connections
	 */
	protected void addConnections(List connections) {
		for (Iterator iter = connections.iterator(); iter.hasNext();) {
			ConnectionElement conn = (ConnectionElement) iter.next();
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

	private void backupConnections(boolean isSrc, ReferElement refItem, List dst) {
		List connList;

		if(isSrc) {
			connList = refItem.getSourceConnections(); 
		}
		else {
			connList = refItem.getTargetConnections();
		}
		
		for (Iterator iter = connList.iterator(); iter.hasNext();) {
			ConnectionElement conn = (ConnectionElement) iter.next();
			if(dst.contains(conn)) {
				continue;
			}

			dst.add(conn);
//
//			ItemElement srcItem = conn.getSource();
//			ItemElement dstItem = conn.getTarget();
//			if(isSrc) {
//				System.out.println(srcItem.getName() + " -> " + dstItem.getName());
//			}
//			else {
//				System.out.println(dstItem.getName() + " <- " + srcItem.getName());
//			}
		}

		List<ItemElement> childList = refItem.getItems();
		for(Iterator iter = childList.iterator(); iter.hasNext();) {
			ItemElement child = (ItemElement)iter.next();
			if(child instanceof ReferElement) {
				backupConnections(isSrc, (ReferElement)child, dst);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return wasRemoved;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		// store a copy of incoming & outgoing connections before proceeding
		if(refItem instanceof LinkedElement) {
			sourceConnections = new ArrayList<ConnectionElement>();
			targetConnections = new ArrayList<ConnectionElement>();

			backupConnections(true, (ReferElement)refItem, sourceConnections);
//			System.out.println("-------------------------------------");
			backupConnections(false, (ReferElement)refItem, targetConnections);
//			System.out.println("=====================================");
		}
		else {
			EList<ReferElement> refList = realItem.getReferences();

			sourceConnections = new ArrayList<ConnectionElement>();
			targetConnections = new ArrayList<ConnectionElement>();
			references = new ArrayList<ReferElement>();

			for(Iterator<ReferElement> iter = refList.iterator(); iter.hasNext(); ) {
				ReferElement itemRef = iter.next();
				backupConnections(true, (ReferElement)itemRef, sourceConnections);
//				System.out.println("-------------------------------------");
				backupConnections(false, (ReferElement)itemRef, targetConnections);
//				System.out.println("=====================================");
				references.add(itemRef);
			}
		}

		redo();
	}

	protected ItemElement getRootModel(ItemElement child) {
		ItemElement parent = child.getParent();
		if(parent == null) {
			return child;
		}

		return getRootModel(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		if(this.isRef) {
			redoRef();
		}
		else {
			redoReal();
		}
	}

	protected void redoRef() {
		ItemElement realParent, rootModel;

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
		}
		else {
			return;
		}

		boolean isContained = false;
		if(realParent instanceof ModelDiagram) {
			isContained = ((ModelDiagram)realParent).getItems().contains(realItem);
		}
		else if(realParent instanceof ReferElement) {
			isContained = ((ReferElement)realParent).getItems().contains(realItem);
		}
		else if(realParent instanceof TaskElement) {
			isContained = ((TaskElement)realParent).getItems().contains(realItem);
		}
		else if(realParent instanceof BehaviorElement) {
			// KJH 20110518 s, add
			if (realItem instanceof StateElement){
				isContained = ((BehaviorElement)realParent).getStates().contains((StateElement)realItem);
			}
			else if (realItem instanceof ExpandTransElement) {
				isContained = ((BehaviorElement)realParent).getBifurcates().contains((ExpandTransElement)realItem);
			}// KJH 20110518 e, add
		}
		// KJH 20110527 s, state\expand&trans
		else if (realParent instanceof StateElement) {
			if (realItem instanceof ExpandTransElement) {
				isContained = ((StateElement)realParent).getBifurcates().contains((ExpandTransElement)realItem);
			}
		}	// KJH 20110527 e, state\expand&trans
		// KJH 20110502 s, connector\with
		else if (realParent instanceof ConnectorElement) {
			isContained = ((ConnectorElement)realParent).getWiths().contains((WithElement)realItem);
		}// KJH 20110502 e, connector\with

		// Include�� ���� ���� �Ұ��������� ���� ���� ���� ������.
		// remove the child and disconnect its connections
		if (isContained) {
////			removeConnections(((LinkedElement)refItem).getSourceConnections());
////			removeConnections(((LinkedElement)refItem).getTargetConnections());
//			removeConnections(sourceConnections);
//			removeConnections(targetConnections);

			if(realItem.isIncluded() == false) {
				if(realParent instanceof ModelDiagram) {
					((ModelDiagram)realParent).getItems().remove(realItem);
//					if(realParent instanceof WorkerElement) {
//						((WorkerElement)realParent).getItems().remove(realItem);
//					}
				}
				else if(realParent instanceof ReferElement) {
					((ReferElement)realParent).getItems().remove(realItem);
				}
				else if(realParent instanceof TaskElement) {
					((TaskElement)realParent).getItems().remove(realItem);
				}
				else if(realParent instanceof BehaviorElement) {
					// KJH 20110518 s, modify
					if (realItem instanceof StateElement) {
						if(StateAttribute.INITIAL == ((StateElement)realItem).getAttribute()) {
							if(realItem == ((BehaviorElement)realParent).getInitialState()) {
								((BehaviorElement)realParent).setInitialState(null);
							}
						}
						((BehaviorElement)realParent).getStates().remove((StateElement)realItem);
					}
					else if (realItem instanceof ExpandTransElement) {
						((BehaviorElement)realParent).getBifurcates().remove((ExpandTransElement)realItem);
					}// KJH 20110518 e, modify
				}
				// KJH 20110527 s, state\expand&trans
				else if(realParent instanceof StateElement) {
					if (realItem instanceof ExpandTransElement) {
						((StateElement)realParent).getBifurcates().remove((ExpandTransElement)realItem);
					}
				}	// KJH 20110527 e, state\expand&trans
				// KJH 20110502 s, connector\with
				else if (realParent instanceof ConnectorElement) {
					((ConnectorElement)realParent).getWiths().remove((WithElement)realItem);
				}// KJH 20110502 e, connector\with
			}
		}

		if(parentModel instanceof SubDiagram) {
			isContained = ((SubDiagram)parentModel).getItems().contains(refItem);
		}
		else if(parentModel instanceof ReferElement) {
			isContained = ((ReferElement)parentModel).getItems().contains(refItem);
		}

		if(isContained) {
			removeConnections(sourceConnections);
			removeConnections(targetConnections);

			if(parentModel instanceof SubDiagram) {
				wasRemoved = ((SubDiagram)parentModel).getItems().remove(refItem);
			}
			else if(parentModel instanceof ReferElement) {
				wasRemoved = ((ReferElement)parentModel).getItems().remove(refItem);
			}
			
			realItem.getReferences().remove(refItem);
		}
	}

	protected void redoReal() {
		ItemElement realParent, rootModel;
		
		if(realItem.isIncluded()) {
			redoIncludedReal();
			return;
		}

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
			rootModel = getRootModel(realParent);
		}
		else {
			realParent = parentModel;
			rootModel = realParent;
		}

		boolean isContained = false;
		if(realParent instanceof ModelDiagram) {
			isContained = ((ModelDiagram)realParent).getItems().contains(realItem);
		}
		else if(realParent instanceof ReferElement) {
			isContained = ((ReferElement)realParent).getItems().contains(realItem);
		}
		else if(realParent instanceof TaskElement) {
			isContained = ((TaskElement)realParent).getItems().contains(realItem);
		}
		else if(realParent instanceof BehaviorElement) {
			isContained = ((BehaviorElement)realParent).getStates().contains((StateElement)realItem);
		}
		else if(realParent instanceof ModelElement) {
			if(realItem instanceof Symbol) {
				isContained = ((ModelElement)realParent).getSymbols().contains((Symbol)realItem);
			}
			else if(realItem instanceof Constant) {
				isContained = ((ModelElement)realParent).getConstants().contains((Constant)realItem);
			}
			else if(realItem instanceof Function) {
				isContained = ((ModelElement)realParent).getFunctions().contains((Function)realItem);
			}
			else if(realItem instanceof ModelElement) {
				isContained = ((ModelElement)realParent).getModels().contains((ModelElement)realItem);
			}
		}
		// KJH 20100629 s, Models view���� numItem ���� �� undo �ǵ��� �߰�
		else if(realParent instanceof EnumElement) {
			if(realItem instanceof EnumItemElement) {
				isContained = ((EnumElement)realParent).getEnumItem().contains((EnumItemElement)realItem);
			}
		}
		// KJH 20100629 e, Models view���� Parameter ���� �� undo �ǵ��� �߰� 
		else if(realParent instanceof Function) {
			if(realItem instanceof Parameter) {
				isContained = ((Function)realParent).getParams().contains((Parameter)realItem);
			}
		}
		// KJH 20110529 s, state\expandtrans
		else if (realParent instanceof StateElement) {
			if (realItem instanceof ExpandTransElement) {
				isContained = ((StateElement)realParent).getBifurcates().contains((ExpandTransElement)realItem);
			}
		}	// KJH 20110529 e, state\expandtrans
		// KJH 20110529 s, connector\with
		else if (realParent instanceof ConnectorElement) {
			isContained = ((ConnectorElement)realParent).getWiths().contains((WithElement)realItem);
		}	// KJH 20110529 s, connector\with

		// remove the child and disconnect its connections
		if (isContained) {
			// ������ �����Ѵ�.
			removeConnections(sourceConnections);
			removeConnections(targetConnections);

			// Include���� �������� �ʴ´�.
			// Container ���� Item �� �����Ѵ�.
			int itemState = realItem.getItemState();
			if(realItem.isIncluded() == false) {
				if(realParent instanceof ModelDiagram) {
					wasRemoved = ((ModelDiagram)realParent).getItems().remove(realItem);
//					if(realParent instanceof WorkerElement) {
//						((WorkerElement)realParent).getItems().remove(realItem);
//					}
				}
				else if(realParent instanceof ReferElement) {
					wasRemoved = ((ReferElement)realParent).getItems().remove(realItem);
				}
				else if(realParent instanceof TaskElement) {
					wasRemoved = ((TaskElement)realParent).getItems().remove(realItem);
				}
				else if(realParent instanceof BehaviorElement) {
					if(StateAttribute.INITIAL == ((StateElement)realItem).getAttribute()) {
						if(realItem == ((BehaviorElement)realParent).getInitialState()) {
							((BehaviorElement)realParent).setInitialState(null);
						}
					}
					wasRemoved = ((BehaviorElement)realParent).getStates().remove((StateElement)realItem);
				}
				else if(realParent instanceof ModelElement) {
					if(realItem instanceof Symbol) {
						wasRemoved = ((ModelElement)realParent).getSymbols().remove((Symbol)realItem);
					}
					else if(realItem instanceof Constant) {
						wasRemoved = ((ModelElement)realParent).getConstants().remove((Constant)realItem);
					}
					else if(realItem instanceof Function) {
						wasRemoved = ((ModelElement)realParent).getFunctions().remove((Function)realItem);
					}
					else if(realItem instanceof ModelElement) {
						wasRemoved = ((ModelElement)realParent).getModels().remove((ModelElement)realItem);
					}
				}
				// KJH 20100629 s, Models view���� Delete�޴��� EnumItem �������� �ʴ� ����
				else if(realParent instanceof EnumElement) {
					if(realItem instanceof EnumItemElement) {
						wasRemoved = ((EnumElement)realParent).getEnumItem().remove((EnumItemElement)realItem);
					}
				}
				// KJH 20100629 e, Models view���� Delete�޴��� Parameter �������� �ʴ� ���� 
				else if(realParent instanceof Function) {
					if(realItem instanceof Parameter) {
						wasRemoved = ((Function)realParent).getParams().remove((Parameter)realItem);
					}
				}
				// KJH 20110529 s, state\expandtrans
				else if (realParent instanceof StateElement) {
					if (realItem instanceof ExpandTransElement) {
						wasRemoved = ((StateElement)realParent).getBifurcates().remove((ExpandTransElement)realItem);
					}
				}	// KJH 20110529 e, state\expandtrans
				// KJH 20110529 s, connector\with
				else if (realParent instanceof ConnectorElement) {
					wasRemoved = ((ConnectorElement)realParent).getWiths().remove((WithElement)realItem);
				}	// KJH 20110529 s, connector\with
			}

			// Item �� ���� Item ���� �����Ѵ�.
			// ���� Item ���� �����ϴ� Container ���� ���� Item �� �����Ѵ�.
			EList<ReferElement> refList = realItem.getReferences();
			for(Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
				ReferElement itemRef = itemIter.next();
				ItemElement parentRef = itemRef.getParent();

				if(parentRef instanceof SubDiagram) {
					((SubDiagram)parentRef).getItems().remove(itemRef);
					ItemElement parentReal = parentRef.getParent();
					// WorkerElement�� Task ���̾�׷��� �׸��� ���Ͽ�
					// ModelDiagram�� ���ԵǾ� �ִ� Task, Action�� �ڽ����� �����Ѵ�.
					// (ReferElement�� �ƴ�)
					if(parentReal instanceof TaskElement) {
						((TaskElement)parentReal).getItems().remove(realItem);
					}
				}
				else if(parentRef instanceof ReferElement) {
					((ReferElement)parentRef).getItems().remove(itemRef);
					ItemElement parentReal = parentRef.getParent();
					if(parentReal instanceof TaskElement) {
						((TaskElement)parentReal).getItems().remove(realItem);
					}
				}
				else {
					System.out.println("���� �� ���� ��Ȳ");
				}
			}
			
			// Item �� ���� �� ���̹Ƿ�  Item �� ���̾�׷����� �׷��� ���� Item ����
			// ���� Item���� ���� ������ �����Ѵ�.
			// ���� SubDiagram �� ModelDiagra, WorkerElement���� ������,
			// SubDiagram�� �� ���ִ� Item���� Task, Action�� ���� ������ ���̴�.
			// �ʿ�� �����Ͽ��� ��.
			SubDiagram subDrm = realItem.getSubDiagram();
			if(subDrm != null) {
				refList = subDrm.getItems();
				for(Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
					// ���̾�׷��� ���Ե� ���� ��
					ReferElement itemRef = itemIter.next();
					// ���� ��
					ItemElement itemReal = itemRef.getRealModel();
	
					// ���� �𵨿��� ���� �� ������ �����Ѵ�.
					itemReal.getReferences().remove(itemRef);
	
					// �ڽ� �𵨵��� ���� �� ������ �����Ѵ�.
					// ���� ���� ���� �ڽ����� ���� ���� Task�� ���� ���� �� ���̴�. 
					EList<ItemElement> childRefList = itemRef.getItems();
					for(Iterator<ItemElement> childIter = childRefList.iterator(); childIter.hasNext(); ) {
						//�ڽ� ���� ���� ��
						ItemElement childRef = childIter.next();
						// �ڽ� ���� ���� ��
						ItemElement childReal = ((ReferElement)childRef).getRealModel();
						
						childReal.getReferences().remove(childRef);
					}
				}
			}
		}
	}

	protected void redoIncludedReal() {
		ItemElement realParent, rootModel;

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
			rootModel = getRootModel(realParent);
		}
		else {
			realParent = parentModel;
			rootModel = realParent;
		}

		// ������ �����Ѵ�.
		removeConnections(sourceConnections);
		removeConnections(targetConnections);

		// Include���� �������� �ʴ´�.

		// Item �� ���� Item ���� �����Ѵ�.
		// ���� Item ���� �����ϴ� Container ���� ���� Item �� �����Ѵ�.
		EList<ReferElement> refList = realItem.getReferences();
		for(Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
			ReferElement itemRef = itemIter.next();
			ItemElement parentRef = itemRef.getParent();

			if(parentRef instanceof SubDiagram) {
				wasRemoved = ((SubDiagram)parentRef).getItems().remove(itemRef);
			}
			else if(parentRef instanceof ReferElement) {
				wasRemoved = ((ReferElement)parentRef).getItems().remove(itemRef);
			}
			else {
				System.out.println("���� �� ���� ��Ȳ");
			}
		}
		
		// ���� ���� IncludedElement�� �ڽ����� ���� ��������
		// ReferElement���� �����ϴ� Container���� �������� �����Ѵ�.
		// Redo�� references�κ��� �����Ѵ�.
		refList.clear();
			
		// Item�� �������� ������  Item �� ���̾�׷����� �׷��� ���� Item ����
		// ���� Item���� ���� ������ �����Ѵ�.
		// ���� SubDiagram �� ModelDiagram, WorkerElement���� ������,
		// SubDiagram�� �� ���ִ� Item���� Task, Action�� ���� ������ ���̴�.
		// �ʿ�� �����Ͽ��� ��.
		SubDiagram subDrm = realItem.getSubDiagram();
		if(subDrm != null) {
			refList = subDrm.getItems();
			for(Iterator<ReferElement> itemIter = refList.iterator(); itemIter.hasNext(); ) {
				// ���̾�׷��� ���Ե� ���� ��
				ReferElement itemRef = itemIter.next();
				// ���� ��
				ItemElement itemReal = itemRef.getRealModel();

				// ���� �𵨿��� ���� �� ������ �����Ѵ�.
				itemReal.getReferences().remove(itemRef);

				// �ڽ� �𵨵��� ���� �� ������ �����Ѵ�.
				// ���� ���� ���� �ڽ����� ���� ���� Task�� ���� ���� �� ���̴�. 
				EList<ItemElement> childRefList = itemRef.getItems();
				for(Iterator<ItemElement> childIter = childRefList.iterator(); childIter.hasNext(); ) {
					//�ڽ� ���� ���� ��
					ItemElement childRef = childIter.next();
					// �ڽ� ���� ���� ��
					ItemElement childReal = ((ReferElement)childRef).getRealModel();
					
					childReal.getReferences().remove(childRef);
				}
			}
		}
	}
	
	/**
	 * Disconnects a List of Connections from their endpoints.
	 * @param connections a non-null List of connections
	 */
	protected void removeConnections(List connections) {
		for(Iterator iter = connections.iterator(); iter.hasNext(); ) {
			ConnectionElement conn = (ConnectionElement)iter.next();

			LinkedElement source = conn.getSource();
			source.getSourceConnections().remove(conn);

			LinkedElement target = conn.getTarget();
			target.getTargetConnections().remove(conn);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if(this.isRef) {
			undoRef();
		}
		else {
			undoReal();
		}
	}
	
	protected void undoRef() {
		ItemElement realParent, rootModel;

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
		}
		else {
			return;
		}

		// Include �� ���� �������� �ʾ����Ƿ� Redo�� �߰����� �ʴ´�.
		int itemState = realItem.getItemState();
		// KJH 20100903, undo�� refItem.getAttribute() != ReferAttribute.EXTERNAL �� ��� realModel �߰����� �ʵ��� ����
		if(realItem.isIncluded() == false && refItem.getAttribute() != ReferAttribute.EXTERNAL) {
			if(rootModel instanceof ModelDiagram) {
				((ModelDiagram)rootModel).getItems().add(realItem);
				if(realParent instanceof TaskElement) {
					((TaskElement)realParent).getItems().add(realItem);
				}
			}
			else if(rootModel instanceof ReferElement) {
				((ReferElement)rootModel).getItems().add(realItem);
			}
			else if(rootModel instanceof TaskElement) {
				((TaskElement)rootModel).getItems().add(realItem);
			}
			else if(rootModel instanceof BehaviorElement) {
				if (realItem instanceof StateElement) {
					if(StateAttribute.INITIAL == ((StateElement)realItem).getAttribute()) {
						((BehaviorElement)rootModel).setInitialState((StateElement)realItem);
					}
					((BehaviorElement)rootModel).getStates().add((StateElement)realItem);
				}
				else if (realItem instanceof ExpandTransElement) {
					((BehaviorElement)rootModel).getBifurcates().add((ExpandTransElement)realItem);
				}
			}
			// KJH 20110529 s, state\expandtrans
			else if (realParent instanceof StateElement) {
				if (realItem instanceof ExpandTransElement) {
					((StateElement)realParent).getBifurcates().add((ExpandTransElement)realItem);
				}
			}	// KJH 20110529 e, state\expandtrans
			// KJH 20110529 s, connector\with
			else if (realParent instanceof ConnectorElement) {
				((ConnectorElement)realParent).getWiths().add((WithElement)realItem);
			}	// KJH 20110529 s, connector\with

		}

		if(parentModel instanceof SubDiagram) {
			((SubDiagram)parentModel).getItems().add(refItem);
		}
		else if(parentModel instanceof ReferElement) {
			((ReferElement)parentModel).getItems().add(refItem);
		}
		
		realItem.getReferences().add(refItem);

		if(refItem instanceof LinkedElement) {
			addConnections(sourceConnections);
			addConnections(targetConnections);
		}
	}

	protected void undoReal() {
		ItemElement realParent, rootModel;
		
		if(realItem.isIncluded()) {
			undoIncludedReal();
			return;
		}

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
			rootModel = getRootModel(realParent);
		}
		else {
			realParent = parentModel;
			rootModel = realParent;
		}

		// Include �� ���� �������� �ʾ����Ƿ� Redo�� �߰����� �ʴ´�.
		int itemState = realItem.getItemState();
		if(realItem.isIncluded() == false) {
			if(rootModel instanceof ModelDiagram) {
				((ModelDiagram)rootModel).getItems().add(realItem);
				if(realParent instanceof TaskElement) {
					((TaskElement)realParent).getItems().add(realItem);
				}
			}
			else if(rootModel instanceof ReferElement) {
				((ReferElement)rootModel).getItems().add(realItem);
			}
			else if(rootModel instanceof TaskElement) {
				((TaskElement)rootModel).getItems().add(realItem);
			}
			else if(rootModel instanceof BehaviorElement) {
				if (realItem instanceof StateElement) {
					if(StateAttribute.INITIAL == ((StateElement)realItem).getAttribute()) {
						((BehaviorElement)rootModel).setInitialState((StateElement)realItem);
					}
					((BehaviorElement)rootModel).getStates().add((StateElement)realItem);
				}
				else if (realItem instanceof ExpandTransElement) {
					((BehaviorElement)rootModel).getBifurcates().add((ExpandTransElement)realItem);
				}
			}
			else if(rootModel instanceof ModelElement) {
				if(realItem instanceof Symbol) {
					((ModelElement)rootModel).getSymbols().add((Symbol)realItem);
				}
				else if(realItem instanceof Constant) {
					((ModelElement)rootModel).getConstants().add((Constant)realItem);
				}
				else if(realItem instanceof Function) {
					((ModelElement)rootModel).getFunctions().add((Function)realItem);
				}
				else if(realItem instanceof ModelElement) {
					((ModelElement)rootModel).getModels().add((ModelElement)realItem);
				}
			}
			// KJH 20100629 s, Models view���� EnumItem ���� �� undo �ǵ��� �߰�
			else if(realParent instanceof EnumElement) {
				if(realItem instanceof EnumItemElement) {
					((EnumElement)realParent).getEnumItem().add((EnumItemElement)realItem);
				}
			}
			// KJH 20100629 s, Models view���� Parameter ���� �� undo �ǵ��� �߰� 
			else if(realParent instanceof Function) {
				if(realItem instanceof Parameter) {
					((Function)realParent).getParams().add((Parameter)realItem);
				}
			}
			// KJH 20110529 s, state\expandtrans
			else if (realParent instanceof StateElement) {
				if (realItem instanceof ExpandTransElement) {
					((StateElement)realParent).getBifurcates().add((ExpandTransElement)realItem);
				}
			}	// KJH 20110529 e, state\expandtrans
			// KJH 20110529 s, connector\with
			else if (realParent instanceof ConnectorElement) {
				((ConnectorElement)realParent).getWiths().add((WithElement)realItem);
			}	// KJH 20110529 s, connector\with

		}

		EList<ReferElement> refList = realItem.getReferences();
		for(Iterator<ReferElement> iter = refList.iterator(); iter.hasNext(); ) {
			ReferElement itemRef = iter.next();
			ItemElement parentRef = itemRef.getParent();
			
			if(parentRef instanceof SubDiagram) {
				((SubDiagram)parentRef).getItems().add(itemRef);
			}
			else if(parentRef instanceof ReferElement) {
				((ReferElement)parentRef).getItems().add(itemRef);
			}
			else {
				System.out.println("���� �� ���� ��Ȳ");
			}
		}

		addConnections(sourceConnections);
		addConnections(targetConnections);
	}

	protected void undoIncludedReal() {
		ItemElement realParent, rootModel;

		if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
			rootModel = getRootModel(realParent);
		}
		else {
			realParent = parentModel;
			rootModel = realParent;
		}

		// Include �� ���� �������� �ʾ����Ƿ� Redo�� �߰����� �ʴ´�.

		// ReferElement���� �����Ͽ����Ƿ� ����� references�κ��� �����Ѵ�.
		for(Iterator<ReferElement> iter = references.iterator(); iter.hasNext(); ) {
			ReferElement itemRef = iter.next();
			ItemElement parentRef = itemRef.getParent();
			
			if(parentRef instanceof SubDiagram) {
				((SubDiagram)parentRef).getItems().add(itemRef);
				realItem.getReferences().add(itemRef);
			}
			else if(parentRef instanceof ReferElement) {
				((ReferElement)parentRef).getItems().add(itemRef);
				realItem.getReferences().add(itemRef);
			}
			else {
				System.out.println("���� �� ���� ��Ȳ");
			}
		}

		addConnections(sourceConnections);
		addConnections(targetConnections);
	}
}