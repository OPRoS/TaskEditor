package kr.re.etri.tpl.diagram.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

public interface ICollapsableFigure extends IFigure {
	/**
	 * Whether or not included
	 * @return model state
	 */
	public int getModelState();
	
	/**
	 * Toggle State (Shape)
	 */
	public void toggleState();
	
	public Rectangle getChildRectangle();
}
