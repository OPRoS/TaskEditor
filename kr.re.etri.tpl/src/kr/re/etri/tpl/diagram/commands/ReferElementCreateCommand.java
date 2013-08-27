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
	/** 자식 모델이 추가될 부모 모델 */
	protected final ItemElement parentModel;
	/** 부모 모델에 추가될 자식 모델에 대한 참조 모델 */
	protected ReferElement refItem;
	/** 부모 모델 요소에 추가될 자식 모델 */ 
	protected ItemElement newShape;
	/** 부모 모델에 새로운 자식 모델을 추가할 Request */
	protected final CreateRequest request;
	/** 새로운 자식 노드의 추가 성공 여부 */
	protected boolean shapeAdded;
	/** Diagram에 include될 파일 */
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
		// KJH 20100903 s, ReferCreationTool.executeCurrentCommand()에서 처리
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
			makeShapeRefer(120, 50);	// BehaviorElementCreateCommand와 같은 사이즈
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
				incElement.setParent(modelDiagram);		// KJH 20101019, 추가
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
						incElmt.setParent(null);	// KJH 20101019, 추가
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
			
			// KJH 20100722 s, ReferElement도 넘어올 수 있도록 수정, ReferElement로 넘어온 경우 다시 생성하지 않음
			if (refItem == null) {
				refItem = factory.createReferElement();
				refItem.setRealModel(newShape);
			}
			// KJH 20100722 e, ReferElement도 넘어올 수 있도록 수정, ReferElement로 넘어온 경우 다시 생성하지 않음
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
	
	// KJH 20100903, ReferCreationTool.executeCurrentCommand()에서 호출
	public void setNewShape(ItemElement newShape) {
		this.newShape = newShape;
	}
	
	// KJH 20101012, ReferCreationTool.executeCurrentCommand()에서 호출
	//               Diagram에 include하는 tpl파일
//	public void setFile(IFile file) {
//		this.file = file;
//	}

	public void setIncludedElement(IncludedElement incElement) {
		this.incElement = incElement;
	}
}
