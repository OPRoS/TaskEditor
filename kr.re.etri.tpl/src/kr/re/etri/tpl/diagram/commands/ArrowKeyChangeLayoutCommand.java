package kr.re.etri.tpl.diagram.commands;

import java.util.List;

import kr.re.etri.tpl.diagram.editparts.behavior.BItemElementEditPart;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.gef.commands.Command;

public class ArrowKeyChangeLayoutCommand extends Command {

	private List selectedEditPartList;
	
	private Integer differenceWidthOrX;
	
	private Integer differenceHeightOrY;
	
	private boolean bTranslate = true;
	
	private boolean bChanged;
	
	public ArrowKeyChangeLayoutCommand (List selectedEditPartList,
			Integer diferenceWidthOrX, Integer diferenceHeightOrY) {
		super();
		this.selectedEditPartList = selectedEditPartList;
		this.differenceWidthOrX = diferenceWidthOrX;
		this.differenceHeightOrY = diferenceHeightOrY;
	}
	
	public ArrowKeyChangeLayoutCommand (List selectedEditPartList,
			Integer diferenceWidthOrX, Integer diferenceHeightOrY, boolean bTranslate) {
		this(selectedEditPartList, diferenceWidthOrX, diferenceHeightOrY);
		this.bTranslate = bTranslate;
	}

	@Override
	public boolean canExecute() {
		return selectedEditPartList != null && differenceWidthOrX != null && differenceHeightOrY != null;
	}

	@Override
	public void execute() {
		changeLayout(false);
	}
	
	/** 
	 * 레이아웃을 변경합니다. 
	 * @param bUndo 
	 */
	private void changeLayout(boolean bUndo) { 
		for (Object selectedObject : selectedEditPartList) { 

			if (selectedObject instanceof BItemElementEditPart) { 
				BItemElementEditPart elementPart = (BItemElementEditPart) selectedObject; 

				Object model = elementPart.getModel(); 

				if (model instanceof ReferElement) { 
					ReferElement childElement = (ReferElement) model; 

					int wx = bUndo ? -differenceWidthOrX : differenceWidthOrX; 
					int hy = bUndo ? -differenceHeightOrY : differenceHeightOrY; 

					if (bTranslate) { 
						childElement.setX2(childElement.getX());
						childElement.setX(childElement.getX() + wx);
						childElement.setY2(childElement.getY());
						childElement.setY(childElement.getY() + hy);
						
					} else { 
						childElement.setWidth2(childElement.getWidth());
						childElement.setWidth(childElement.getWidth() + wx);
						childElement.setHeight2(childElement.getHeight());
						childElement.setHeight(childElement.getHeight() + hy);
					} 
				} 
			} 
		} 

		bChanged = !bUndo; 
	}	

	@Override
	public void redo() { 
		changeLayout(false); 
	} 

	@Override
	public boolean canUndo() { 
		return bChanged; 
	} 

	@Override
	public void undo() { 
		changeLayout(true); 
	}

}
