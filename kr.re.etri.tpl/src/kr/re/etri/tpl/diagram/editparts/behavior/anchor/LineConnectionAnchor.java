package kr.re.etri.tpl.diagram.editparts.behavior.anchor;

import java.util.List;

import kr.re.etri.tpl.diagram.figures.ConnectionElementFigure;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class LineConnectionAnchor extends AbstractConnectionAnchor {
	private Point reference;
	
	/**
	 * @author KJH 20110406
	 * @param editpart
	 */
	public LineConnectionAnchor(IFigure figure) {
		super(figure);
	}

	@Override
	public void ancestorMoved(IFigure figure) {
//		fireAnchorMoved();
	}

	@Override
	public Point getLocation(Point reference) {
//		if (this.reference == null)	// KJH 20110408, Anchor갱신되게 삭제함
		this.reference = getReferencePoint();
		return this.reference;
	}

	/**
	 * @author KJH 20110406
	 * @Override
	 */
	public ConnectionElementFigure getOwner() {
		return (ConnectionElementFigure)super.getOwner();
	}

	@Override
	public Point getReferencePoint() {
		ConnectionElementFigure conn = getOwner();
		ConnectionRouter router = conn.getConnectionRouter();
		Point start = conn.getStart();
		Point end = conn.getEnd();
		conn.translateToAbsolute(start);
		conn.translateToAbsolute(end);
		
		List constraint = (List)router.getConstraint(conn);
		if (constraint != null && constraint.size() > 0) {
			end = (Point)constraint.get(0);
		}

		PointList pl = new PointList();
		pl.addPoint(start);
		pl.addPoint(end);
		return reference = pl.getBounds().getCenter();
	}

}
