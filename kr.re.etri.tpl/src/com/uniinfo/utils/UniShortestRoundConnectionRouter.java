package com.uniinfo.utils;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class UniShortestRoundConnectionRouter {
	private ShortestPathConnectionRouter router;

	public UniShortestRoundConnectionRouter(IFigure container) {
		router = new ShortestPathConnectionRouter(container);
	}

	public void route(Connection connection) {
		router.route(connection);
		PointList pointList = connection.getPoints().getCopy();
		PointList resultPointList = new PointList();

		Point start = pointList.getFirstPoint();
		Point end = pointList.getLastPoint();
		System.err.println("SIZE  :  " + pointList.size());
		// if(start.equals(end)){
		//				
		// System.out.println(pointList.size()+"DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		// pointList.setPoint(new Point(start.x,start.y-40), 1);
		// resultPointList.addPoint(start);
		// resultPointList.addPoint(new Point(start.x,start.y-80));
		// connection.setPoints(pointList);
		// return;
		//				
		// }

		if (pointList.size() > 2) {
			// pointList.setPoint(p,pointList.size()-2);
		} else if (pointList.size() == 2) {
			// 회전 중심잡기

//			System.out.println("START : " + start.x + ", " + start.y);
//			System.out.println("END : " + end.x + ", " + end.y);

			double gradient = ((double) (end.y - start.y)) / (end.x - start.x);
//			System.out.println("Gradient == " + gradient);
			// if(gradient >0){
			// 호의 중심 잡기
			double x = (Math.cos(Math.toRadians(60)) * (end.x - start.x) + -(Math
					.sin(Math.toRadians(60)) * (end.y - start.y)))
					+ start.x;
			double y = (Math.sin(Math.toRadians(60)) * (end.x - start.x) + Math
					.cos(Math.toRadians(60))
					* (end.y - start.y))
					+ start.y;

//			System.out.println("X == " + x);
//			System.out.println("Y == " + y);
			Point center = new Point(x, y);

			// pointList.setPoint(center, 1);
			//					
			// pointList.addPoint(end);
			//					
			resultPointList.addPoint(start);

			for (int i = 1; i < 60; i += 2) {
				x = (Math.cos(Math.toRadians(i)) * (start.x - center.x) + -Math
						.sin(Math.toRadians(i))
						* (start.y - center.y))
						+ center.x;
				y = (Math.sin(Math.toRadians(i)) * (start.x - center.x) + Math
						.cos(Math.toRadians(i))
						* (start.y - center.y))
						+ center.y;

				resultPointList.addPoint(new Point(x, y));
			}
			resultPointList.addPoint(end);
			connection.setPoints(resultPointList);
			return;
			// }

		} else {
//			System.out.println("pointList size == " + pointList.size());
		}
		connection.setPoints(pointList);
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
