package kr.re.etri.tpl.diagram.factories;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.swt.graphics.Color;

public class DecoratioinFactory {

	private static PointList triangleList = new PointList();
	private static PointList squareList = new PointList();
	private static PointList circleList = new PointList();
	
	static {
		triangleList.addPoint(-10, -10);
		triangleList.addPoint(0, 0);
		triangleList.addPoint(-10, 10);

		squareList.addPoint(-10, -5);
		squareList.addPoint(0, -5);
		squareList.addPoint(0, 5);
		squareList.addPoint(-10, 5);

		Transform tr = new Transform();
		int cnt = 36;
		tr.setRotation(Math.PI*((360/cnt)/180.0));
		Point pt = new Point(0, 10);
		for(int i = 0; i < cnt; i += 1) {
			circleList.addPoint(pt.x-5, pt.y);
			pt = tr.getTransformed(pt);
		}
	}

	private DecoratioinFactory() {
		
	}

	public static RotatableDecoration createArrowDecoration() {
		PolylineDecoration dec;
		dec = new PolylineDecoration();
		dec.setTemplate(triangleList);
		dec.setScale(0.8, 0.4);
		
		return dec;
	}

	public static RotatableDecoration createOpenedArrowDecoration() {
		PolygonDecoration dec;
		dec = new PolygonDecoration();
		dec.setTemplate(triangleList);
		dec.setScale(0.8, 0.4);
		dec.setFill(true);
		dec.setBackgroundColor(new Color(null, 255, 255, 255));

		return dec;
	}

	public static RotatableDecoration createClosedArrowDecoration() {
		PolygonDecoration dec;
		dec = new PolygonDecoration();
		dec.setTemplate(triangleList);
		dec.setScale(0.8, 0.4);
		dec.setFill(true);
		dec.setBackgroundColor(new Color(null, 0, 0, 0));

		return dec;
	}

	public static RotatableDecoration createOpenedSquareDecoration() {
		PolygonDecoration dec;
		dec = new PolygonDecoration();
		dec.setTemplate(squareList);
		dec.setScale(0.6, 0.6);
		dec.setFill(true);
		dec.setBackgroundColor(new Color(null, 255, 255, 255));

		return dec;
	}

	public static RotatableDecoration createClosedSquareDecoration() {
		PolygonDecoration dec;
		dec = new PolygonDecoration();
		dec.setTemplate(squareList);
		dec.setScale(0.6, 0.6);
		dec.setFill(true);
		dec.setBackgroundColor(new Color(null, 0, 0, 0));

		return dec;
	}

	public static RotatableDecoration createOpenedCircleDecoration() {
		PolygonDecoration dec;
		dec = new PolygonDecoration();
		dec.setTemplate(circleList);
		dec.setScale(0.35, 0.35);
		dec.setFill(true);
		dec.setBackgroundColor(new Color(null, 255, 255, 255));

		return dec;
	}

	public static RotatableDecoration createClosedCircleDecoration() {
		PolygonDecoration dec;
		dec = new PolygonDecoration();
		dec.setTemplate(circleList);
		dec.setScale(0.3, 0.3);
		dec.setFill(true);
		dec.setBackgroundColor(new Color(null, 0, 0, 0));

		return dec;
	}

	public static PointList getTransformed(PointList pointList, double xAmount, double yAmount) {
		PointList pl = pointList.getCopy();
		int size = pl.size();
		for(int index = 0; index < size; index += 1) {
			Point pt = pl.getPoint(index);
			pt.scale(xAmount, yAmount);
			pl.setPoint(pt, index);
		}
		
		return pl;
	}
	
	public static void drawRoundRectangle(Graphics graphics, Rectangle rect, int cx, int cy, Color lineColor, int nOutline) {
		Color bgColor;

		bgColor = graphics.getBackgroundColor();
		Rectangle rndRect = Rectangle.SINGLETON;

		int lineWidth = 1;//graphics.getLineWidth();
		int offset = 1;
		rndRect.x = rect.x+lineWidth/2;
		rndRect.y = rect.y+lineWidth/2;
		rndRect.width = rect.width-lineWidth;
		rndRect.height = rect.height-lineWidth;

		// LeftTop Corner
		for(int i = 0; i < nOutline; i += 1) {
			graphics.setForegroundColor(lineColor);
			graphics.drawRoundRectangle(rndRect, cx, cy);
			rndRect.x+=lineWidth;
			rndRect.y+=lineWidth;
			rndRect.width-=lineWidth*2;
			rndRect.height-=lineWidth*2;
			graphics.setForegroundColor(bgColor);
			graphics.drawRoundRectangle(rndRect, cx, cy);
			rndRect.x+=lineWidth;
			rndRect.y+=lineWidth;
			rndRect.width-=lineWidth-2;
			rndRect.height-=lineWidth-2;
		}
	}
	
	public static void drawEllipse(Graphics graphics, Rectangle rect, Color lineColor, int nOutline) {
		Color bgColor;
		
		bgColor = graphics.getBackgroundColor();
		
		int offset = 1;
		int x = rect.x-offset;
		int y = rect.y-offset;
		int cx = rect.width+offset;
		int cy = rect.height+offset;

		// LeftTop Corner
		for(int i = 0; i < nOutline; i += 1) {
			graphics.setForegroundColor(lineColor);
			graphics.drawArc(x+=offset, y+=offset, cx-=offset, cy-=offset, 90, 90);
			graphics.setForegroundColor(bgColor);
			graphics.drawArc(x+=offset, y+=offset, cx-=offset, cy-=offset, 90, 90);
		}

		// RightTop Corner
		x = rect.x;
		y = rect.y-offset;
		cx = rect.width+offset;
		cy = rect.height+offset;

		for(int i = 0; i < nOutline; i += 1) {
			graphics.setForegroundColor(lineColor);
			graphics.drawArc(x, y+=offset, cx-=offset, cy-=offset, 0, 90);
			graphics.setForegroundColor(bgColor);
			graphics.drawArc(x, y+=offset, cx-=offset, cy-=offset, 0, 90);
		}

		// LeftBottom Corner
		x = rect.x-offset;
		y = rect.y;
		cx = rect.width+offset;
		cy = rect.height+offset;

		for(int i = 0; i < nOutline; i += 1) {
			graphics.setForegroundColor(lineColor);
			graphics.drawArc(x+=offset, y, cx-=offset, cy-=offset, 180, 90);
			graphics.setForegroundColor(bgColor);
			graphics.drawArc(x+=offset, y, cx-=offset, cy-=offset, 180, 90);
		}
		
		// RightBottom Corner
		x = rect.x;
		y = rect.y;
		cx = rect.width+offset;
		cy = rect.height+offset;

		for(int i = 0; i < nOutline; i += 1) {
			graphics.setForegroundColor(lineColor);
			graphics.drawArc(x, y, cx-=offset, cy-=offset, 270, 90);
			graphics.setForegroundColor(bgColor);
			graphics.drawArc(x, y, cx-=offset, cy-=offset, 270, 90);
		}
	}
}
