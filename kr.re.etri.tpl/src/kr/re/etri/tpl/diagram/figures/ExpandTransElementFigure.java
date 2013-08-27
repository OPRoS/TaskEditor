package kr.re.etri.tpl.diagram.figures;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class ExpandTransElementFigure extends Ellipse {
	private static Logger logger = Logger.getLogger(ExpandTransElementFigure.class);
	/** WithElement ���� �̸��� ǥ���� Label */
	private Label nameLabel;
	
	public ExpandTransElementFigure() {
		super();
		
		addLayoutListener(new ExpandTransElementFigureLayoutListener());
	}
	@Override
	protected void fillShape(Graphics graphics) {
		super.fillShape(graphics);
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		Color lineColor;
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.EXPAND_TRANS_ELEMENT, DiagramConfiguration.VALID);
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
	 * Figure �� Layout ���� ó���� ������ Listener
	 * @author KJH 20110503
	 */
	private class ExpandTransElementFigureLayoutListener extends LayoutListener.Stub {

		@Override
		public boolean layout(IFigure container) {
			if (container instanceof WithElementFigure) {
				
			}
			return false;
		}
		
	}
}
