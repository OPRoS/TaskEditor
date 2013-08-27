package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

public class ReferElementCreateCommand extends Command{
	/** �ڽ� ���� �߰��� �θ� �� */
	protected final ItemElement parentModel;
	/** �θ� �𵨿� �߰��� �ڽ� �𵨿� ���� ���� �� */
	protected ReferElement refItem;
	/** �θ� �� ��ҿ� �߰��� �ڽ� �� */ 
	protected ItemElement newShape;
	/** �θ� �𵨿� ���ο� �ڽ� ���� �߰��� Request */
	protected final CreateRequest request;
	/** ���ο� �ڽ� ����� �߰� ���� ���� */
	protected boolean shapeAdded;
	/** Diagram�� include�� ���� */
//	IFile file;
	
	IncludedElement incElement;	// KJH 20101015,
	
	protected Rectangle m_constraint;
	
	public ReferElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		this.parentModel = parentModel;
		this.request = req;
		this.m_constraint = constraint;
	}

	@Override
	public void execute() {
		// KJH 20100903 s, ReferCreationTool.executeCurrentCommand()���� ó��
//		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		Shell shell = window.getShell();
//		IEditorInput editorInput = window.getActivePage().getActiveEditor().getEditorInput();
//
//		BehaviorSelectionDialog dialog = new BehaviorSelectionDialog(shell, editorInput);
//		if (IDialogConstants.OK_ID != dialog.open()) {
//			return;
//		}
//
//		newShape = dialog.getBehavior();
		refItem = (ReferElement)this.request.getNewObject();
		if (refItem != null)
			refItem.setRealModel(newShape);
		
		// KJH 20100826,  add states
//		if (newShape instanceof BehaviorElement) {
//			BehaviorElement behavior = (BehaviorElement)newShape;
//			
//			for (StateElement state : behavior.getStates()) {
//				ReferElement clone = (state.getReferences() != null && state.getReferences().size() > 0) ?
//						state.getReferences().get(0) : null;
//
//				ReferElement refChild = ModelManager.getFactory().createReferElement();
//
//				if (clone != null) {
//					refChild.setX(clone.getX());
//					refChild.setY(clone.getY());
//					refChild.setWidth(clone.getWidth());
//					refChild.setHeight(clone.getHeight());
//				}
//				else {
//					refChild.setWidth(100);
//					refChild.setHeight(55);
//				}
//				
//				refChild.setAttribute(ReferAttribute.EXTERNAL);
//				refChild.setParent(refItem);
//				refChild.setRealModel(state);
//				state.getReferences().add(refChild);
//				refItem.getItems().add(refChild);
//			}
//		}
		
		Dimension size = request.getSize();
		if(size != null) {
//			makeShapeRefer(size.width, size.height);
			makeShapeRefer(120, 50);
		}
		else {
			makeShapeRefer(120, 50);	// BehaviorElementCreateCommand�� ���� ������
		}

		redo();
	}

	@Override
	public boolean canUndo() {
		return shapeAdded;
	}

	@Override
	public void redo() {
		SubDiagram subDiagram = null;
		ItemElement rootModel = null;

		if (parentModel instanceof SubDiagram) {
			subDiagram = (SubDiagram)parentModel;
			rootModel = subDiagram.getParent();
		}
		else if (parentModel instanceof ModelDiagram) {
			subDiagram = ((ModelDiagram)parentModel).getSubDiagram();
			rootModel = (ModelDiagram)parentModel;
		}

		if (subDiagram != null) {
			newShape.getReferences().add(refItem);
			refItem.setParent(subDiagram);
			shapeAdded = subDiagram.getItems().add(refItem);
		}
		
		// KJH 20101012, include
		if (rootModel instanceof ModelDiagram && incElement != null) {
			ModelDiagram modelDiagram = (ModelDiagram)rootModel;
			EList<IncludedElement> incList = ((ModelDiagram)rootModel).getIncludeItems();
			boolean isExist = false;

			String path = incElement.getIncludePath();

			for (IncludedElement incElmt : incList) {
				if(TPLUtil.isExists(incElmt, path)) {
					isExist = true;
					break;
				}
			}
			
			if (!isExist) {
				modelDiagram.getIncludeItems().add(incElement);
				incElement.setParent(modelDiagram);		// KJH 20101019, �߰�
			}
		}
		
	}

	@Override
	public void undo() {
		if (shapeAdded && refItem != null) {
			SubDiagram subDiagram = null;
			ItemElement rootModel = null;
			if (parentModel instanceof SubDiagram) {
				subDiagram = (SubDiagram)parentModel;
				rootModel = subDiagram.getParent();
			}
			else if (parentModel instanceof ModelDiagram) {
				subDiagram = ((ModelDiagram)parentModel).getSubDiagram();
				rootModel = parentModel;
			}
			
			if (subDiagram != null) {
				newShape.getReferences().remove(refItem);
				refItem.setParent(null);
				subDiagram.getItems().remove(refItem);
				shapeAdded = false;
			}
			
			if (rootModel instanceof ModelDiagram && incElement != null) {
				ModelDiagram modelDiagram = (ModelDiagram)rootModel;
				
				EList<IncludedElement> incList = modelDiagram.getIncludeItems();
				
				String path = incElement.getIncludePath();
				
				for (IncludedElement incElmt : incList) {
					if (TPLUtil.isExists(incElmt, path)) {
						incElmt.setParent(null);	// KJH 20101019, �߰�
						modelDiagram.getIncludeItems().remove(incElmt);
						break;
					}
				}
			}
		}
	}
	
	protected void makeShapeRefer(int defaultWidth, int defaultHeight) {
		Point locPt = m_constraint.getLocation();
		if((parentModel instanceof ReferElement)||(parentModel instanceof SubDiagram)) {
			TaskModelFactory factory = ModelManager.getFactory();
			
			// KJH 20100722 s, ReferElement�� �Ѿ�� �� �ֵ��� ����, ReferElement�� �Ѿ�� ��� �ٽ� �������� ����
			if (refItem == null) {
				refItem = factory.createReferElement();
				refItem.setRealModel(newShape);
			}
			// KJH 20100722 e, ReferElement�� �Ѿ�� �� �ֵ��� ����, ReferElement�� �Ѿ�� ��� �ٽ� �������� ����
			if(newShape instanceof BehaviorElement) {
				refItem.setCollapsed(true);
				if(defaultWidth > 120) {
					refItem.setWidth2(120);
					refItem.setCollapsed(false);
				}
				else {
					refItem.setWidth2(300);
				}
				if(defaultHeight > 50) {
					refItem.setHeight2(50);
					refItem.setCollapsed(false);
				}
				else {
					refItem.setHeight2(200);
				}
			}

			IFigure contentFigure = new Figure();
			contentFigure.translateToAbsolute(new Rectangle(10, 10, 10, 10));
			if(parentModel instanceof ReferElement) {
				Point tmpTp = Point.SINGLETON;
				tmpTp.x = locPt.x;
				tmpTp.y = locPt.y;
				tmpTp.performTranslate(-((ReferElement)parentModel).getX(), -((ReferElement)parentModel).getY());
				refItem.setX(tmpTp.x);
				refItem.setY(tmpTp.y);
			}
			else {
				refItem.setX(locPt.x);
				refItem.setY(locPt.y);
			}

			// Get desired location and size from the request
//			if(request.getSize() != null) { // we'll use default size if it's null
//				refItem.setHeight(request.getSize().height);
//				refItem.setWidth(request.getSize().width);
//			}
//			else {
				refItem.setWidth(defaultWidth);
				refItem.setHeight(defaultHeight);
//			}
		}
	}
	
	// KJH 20100903, ReferCreationTool.executeCurrentCommand()���� ȣ��
	public void setNewShape(ItemElement newShape) {
		this.newShape = newShape;
	}
	
	// KJH 20101012, ReferCreationTool.executeCurrentCommand()���� ȣ��
	//               Diagram�� include�ϴ� tpl����
//	public void setFile(IFile file) {
//		this.file = file;
//	}

	public void setIncludedElement(IncludedElement incElement) {
		this.incElement = incElement;
	}
}
