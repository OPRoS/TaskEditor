package kr.re.etri.tpl.diagram.editparts.behavior.anchor;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class TPLConnectionAnchor extends AbstractConnectionAnchor {

	private Point reference;
	
	public TPLConnectionAnchor(IFigure figure) {
		super(figure);
	}

	protected Rectangle getBox() {
		return getOwner().getBounds();
	}

	@Override
	public Point getLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBox());
		r.translate(-1, -1);
		r.resize(1, 1);

		getOwner().translateToAbsolute(r);
		float centerX = r.x + 0.5f * r.width;
		float centerY = r.y + 0.5f * r.height;
		
		if (r.isEmpty() || (reference.x == (int)centerX && reference.y == (int)centerY))
			return new Point((int)centerX, (int)centerY);  //This avoids divide-by-zero

		float dx = reference.x - centerX;
		float dy = reference.y - centerY;
		
		//r.width, r.height, dx, and dy are guaranteed to be non-zero. 
		float scale = 0.5f / Math.max(Math.abs(dx) / r.width, Math.abs(dy) / r.height);

		dx *= scale;
		dy *= scale;
		centerX += dx;
		centerY += dy;

		return new Point(Math.round(centerX), Math.round(centerY));
	}

	@Override
	public Point getReferencePoint() {
		if (reference == null) {
			Point ref = getBox().getCenter();
			getOwner().translateToAbsolute(ref);
			return ref;
		}
		return reference;
	}

	public Point getReference() {
		return reference;
	}

	public void setReference(Point reference) {
		this.reference = reference;
	}
	
}
