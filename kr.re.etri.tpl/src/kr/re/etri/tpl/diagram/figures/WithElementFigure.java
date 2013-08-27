package kr.re.etri.tpl.diagram.figures;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

public class WithElementFigure extends RoundedRectangle {
	private static Logger logger = Logger.getLogger(WithElementFigure.class);
	/** WithElement 모델의 이름을 표시할 Label */
	private Label nameLabel;
	
	public WithElementFigure() {
		super();
		
		addLayoutListener(new WithElementFigureLayoutListener());
	}
	
	@Override
	protected void fillShape(Graphics graphics) {
		Display display = Display.getCurrent();
		Rectangle boundingRect;
		boundingRect = getBounds();
		Point topLeft = boundingRect.getTopLeft();
		Point bottomRight = boundingRect.getBottomRight();
		Pattern pattern;
		Color bgColor = graphics.getBackgroundColor();
		pattern = new Pattern(display, topLeft.x/*+boundingRect.width/2*/, topLeft.y,
				bottomRight.x/*-boundingRect.width/2*/, bottomRight.y, 
				bgColor, 0xff, ColorConstants.white, 0xff);
		graphics.setBackgroundPattern(pattern);
		super.fillShape(graphics);
		graphics.setBackgroundPattern(null);
		pattern.dispose();
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		Color lineColor;
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.WITH_ELEMENT, DiagramConfiguration.VALID);
		graphics.setForegroundColor(lineColor);
		
//		double scale = graphics.getAbsoluteScale();
//		graphics.setLineWidthFloat((float)(scale*getLineWidthFloat()));
		
		super.outlineShape(graphics);
	}

	@Override
	public void paintFigure(Graphics graphics) {
		int beforeAntialias = graphics.getAntialias();
		graphics.setAntialias(SWT.ON);
		super.paintFigure(graphics);
		graphics.setAntialias(beforeAntialias);
	}

	/**
	 * Figure 의 Layout 변경 처리를 수행할 Listener
	 * @author KJH 20110503
	 */
	private class WithElementFigureLayoutListener extends LayoutListener.Stub {

		@Override
		public boolean layout(IFigure container) {
			if (container instanceof WithElementFigure) {
				
			}
			return false;
		}
		
	}
}
