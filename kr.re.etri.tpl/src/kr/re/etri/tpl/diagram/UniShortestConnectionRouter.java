package kr.re.etri.tpl.diagram;

import kr.re.etri.tpl.diagram.figures.StateElementFigure;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class UniShortestConnectionRouter extends AbstractRouter{
	private static Logger logger =
		 Logger.getLogger(UniShortestConnectionRouter.class.getName());
	private ShortestConnectionRouter router;

	public UniShortestConnectionRouter() {
		router = new ShortestConnectionRouter();
	}
	
	public void addContainer(IFigure container){
		router.addContainer(container);
	}
	
	public void route(Connection connection) {
		router.route(connection);		
		
		IFigure source = connection.getSourceAnchor().getOwner();
		IFigure target = connection.getTargetAnchor().getOwner();
		
		logger.debug(source);
		logger.debug(target);
		
		if(source != null &&source.equals(target)){
			drawRectangleThis(connection);
		}
	}
	
	private void drawRectangleThis(Connection con){
		logger.debug("drawThis start....");
		IFigure source = con.getSourceAnchor().getOwner();
		IFigure target = con.getTargetAnchor().getOwner();
		
		if(!source.equals(target)){
			logger.debug("Source  does not equal target.");
			return;
		}
		
		if(!(source instanceof StateElementFigure)){
			logger.debug("Not StateElementFigure type.");
			return;
		}
		
		Rectangle rect = source.getBounds();
		logger.debug("Source : X = "+rect.x+", Y = "+rect.y+", Width = "+rect.width+", Height = "+rect.height);
		
		PointList resultPointList = new PointList();
		
		int size = 15;
		int startX = rect.x+ size;
		Point startP = new Point(startX, rect.y);
		resultPointList.addPoint(startP);
		
		Point p1 = new Point(startP.x, rect.y -size);
		resultPointList.addPoint(p1);
		
		Point p2 = new Point(rect.x-size, p1.y);
		resultPointList.addPoint(p2);
		
		Point p3 = new Point(p2.x, rect.y +size);
		resultPointList.addPoint(p3);
		
		int endY = rect.y+size;
		Point endP = new Point(rect.x, endY);
		resultPointList.addPoint(endP);
		
		con.setPoints(resultPointList);
		
		
	}
	
	private void drawRoundThis(Connection con){
		logger.debug("drawRectangleThis start....");
		IFigure source = con.getSourceAnchor().getOwner();
		IFigure target = con.getTargetAnchor().getOwner();
		
		if(!source.equals(target)){
			logger.debug("Source  does not equal target.");
			return;
		}
		
		if(!(source instanceof StateElementFigure)){
			logger.debug("Not StateElementFigure type.");
			return;
		}
		
		Rectangle rect = source.getBounds();
		logger.debug("Source : X = "+rect.x+", Y = "+rect.y+", Width = "+rect.width+", Height = "+rect.height);
		
		int startX = rect.x+ rect.width/8*3;
		Point startP = new Point(startX, rect.y);
		
		int endX = rect.x+rect.width/8*5;
		Point endP = new Point(endX, rect.y);
		
		PointList resultPointList = new PointList();
		resultPointList.addPoint(startP);
		
		//회전 중심잡기
		
		double centerX = (startX + endX)/2;
		double centerY = rect.y - (endX-startX)*Math.sin(Math.toRadians(60));
		
		for (int i = 1; i < 310; i += 10) {
			double tempX = (Math.cos(Math.toRadians(i)) * (startX - centerX) + -Math
					.sin(Math.toRadians(i))
					* (rect.y - centerY))
					+ centerX;
			double tempY = (Math.sin(Math.toRadians(i)) * (startX - centerX) + Math
					.cos(Math.toRadians(i))
					* (rect.y - centerY))
					+ centerY;

			resultPointList.addPoint(new Point(tempX, tempY));
		}
		
		con.setPoints(resultPointList);
		
		
	}
	public Object getConstraint(Connection connection) {
		return router.getConstraint(connection);
	}

	public int getSpacing() {
		return router.getSpacing();
	}

	public void invalidate(Connection connection) {
		router.invalidate(connection);
	}

	public void remove(Connection connection) {
		router.remove(connection);
	}

	public void setConstraint(Connection connection, Object constraint) {
		router.setConstraint(connection, constraint);
	}

	public void setSpacing(int spacing) {
		router.setSpacing(spacing);
	}
}
