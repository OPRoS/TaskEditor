package kr.re.etri.tpl.diagram.figures;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

/**
 * ModelDiagram �� ǥ�õ� ActionElement �� Figure Ŭ�����̴�. 
 * @author sfline
 */
public class ActionElementFigure extends Ellipse {
	/** �� ����� �̸��� ǥ���� Label */
	private Label nameLabel;
	/** ���� ���� */
	private int modelState = 0;

	/**
	 * ActionElement �� Figure �� �����Ѵ�.
	 */
	public ActionElementFigure() {
		super();

		setBorder(new MarginBorder(3));

		setLayoutManager(new BehaviorElementLayout());

		nameLabel = new Label();
		nameLabel.setLabelAlignment(PositionConstants.CENTER);
		add(nameLabel);

		// Layout ����� ó���� Listener �� ����Ѵ�. 
		this.addLayoutListener(new LayoutListener.Stub() {

			/**
			 * ��ȿȭ�Ѵ�.
			 * @param container Layout �� ����Ǵ� Figure
			 * @Override
			 */
			public void invalidate(IFigure container) {
			}
			
			/**
			 * Layout �� �����Ѵ�.
			 * @param container Layout �� ����Ǵ� Figure
			 * @Override
			 */
			public boolean layout(IFigure container) {
				if(container instanceof ActionElementFigure) {
					Rectangle clientRect, labelRect, paneRect;
					clientRect =  container.getClientArea();
					
					labelRect = new Rectangle(0, 0, clientRect.width, clientRect.height);
					paneRect = new Rectangle(0, 0, clientRect.width, clientRect.height);

					labelRect = labelRect.translate(clientRect.getLocation());
					nameLabel.setBounds(labelRect);

					paneRect = paneRect.translate(clientRect.getLocation());
//					scrollpane.setBounds(paneRect);
				}
				return false;
			}

			/**
			 * Layout ���� �� ó��
			 * @param container Layout �� ����Ǵ� Figure
			 * @Override 
			 */
			public void postLayout(IFigure container) {
				// TODO Auto-generated method stub
				
			}

			/**
			 * child Figure �� ������ �� ȣ��ȴ�.
			 * @param child ������ child Figure
			 * @Override
			 */
			public void remove(IFigure child) {
			}

			/**
			 * child Figure �� ���� constraint �� �����Ѵ�.
			 * @param child ����� child Figure
			 * @param constraint child Figure �� ���� contraint
			 */
			public void setConstraint(IFigure child, Object constraint) {
			}
			
		});
	}

	/**
	 * ActionElement �� �� ���¸� �����Ѵ�.
	 * ActionElement �� include �Ǿ��ٸ� �̸� �� �Ϻδ� ���� �� �� ����.
	 * @param state ���� ����
	 */
	public void setModelState(int state) {
		this.modelState = state;
	}

	/**
	 * nameLabel �� ǥ���� �̸��� �����Ѵ�.
	 * @param name ǥ���� �̸�
	 */
	public void setName(String name) {
		nameLabel.setText(name);
	}
	
	/**
	 * �̸��� ǥ���� Figure �� ��ȯ�Ѵ�.
	 * @return �̸��� ǥ���� Figure
	 */
	public Label getLabelFigure() {
		return nameLabel;
	}

	/**
	 * Figure �� ���θ� Decoration �Ѵ�. paintFigure()���� ȣ���Ѵ�.
	 * @param graphics Figure �� ���ο� ���� Graphcis instance 
	 * @Override
	 */
	protected void fillShape(Graphics graphics){
//		super.fillShape(graphics);

		Display display = Display.getCurrent();
		Rectangle boundingRect = getClientArea();
		Point topLeft = boundingRect.getTopLeft();
		Point bottomRight = boundingRect.getBottomRight();
		Pattern pattern;
		Color bgColor = graphics.getBackgroundColor();
//		pattern = new Pattern(display, topLeft.x+boundingRect.width/2, topLeft.y,
//				bottomRight.x-boundingRect.width/2, topLeft.y+boundingRect.height, 
//				bgColor, 0xEE, ColorConstants.lightGray, 0x00);
		pattern = new Pattern(display, topLeft.x+boundingRect.width/2, topLeft.y,
				bottomRight.x-boundingRect.width/2, topLeft.y+boundingRect.height, 
				bgColor, 0xFF, ColorConstants.white, 0xFF);
		graphics.setBackgroundPattern(pattern);
//		graphics.fillRoundRectangle(boundingRect, boundingRect.width, boundingRect.height);
		super.fillShape(graphics);
		graphics.setBackgroundPattern(null);
		pattern.dispose();

	}

	/**
	 * Figure �� �׸���.
	 * @see org.eclipse.draw2d.Shape#paintFigure(Graphics)
	 * @Override
	 */
	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);

	}

	/**
	 * Figure �� �ּ� ũ�⸦ ��ȯ�Ѵ�.
	 * @see org.eclipse.draw2d.Figure#getMinimumSize(int, int)
	 * @Override
	 */
	public Dimension getMinimumSize(int wHint, int hHint)
	{
		Dimension minSize;
		minSize = super.getMinimumSize(wHint, hHint);
		
		return minSize;
	}

	/**
	 * Figure �� foreground color �� �̿��Ͽ� Outline �� �׸���.
	 * @param graphics Outline �� �׸� Graphics instance
	 * @see org.eclipse.draw2d.Shape#outlineShape(Graphics)
	 */
	protected void outlineShape(Graphics graphics) {
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		Color lineColor;
//		lineColor = ColorConstants.buttonDarker;
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.ACTION_ELEMENT, modelState);
		
		Rectangle f = Rectangle.SINGLETON;
		Rectangle r = getBounds();
		f.x = r.x + lineWidth / 2;
		f.y = r.y + lineWidth / 2;
		f.width = r.width - lineWidth;
		f.height = r.height - lineWidth;

		int cx = f.width;
		int cy = f.height;

		graphics.setForegroundColor(lineColor);
		graphics.drawArc(new Rectangle(f.x, f.y, cx, cy), 90, 90);
		graphics.drawLine(f.x+cx/2, f.y, f.x+f.width-cx/2, f.y);
		graphics.setForegroundColor(ColorConstants.buttonLightest);
		graphics.drawArc(new Rectangle(f.x+1, f.y+1, cx, cy), 90, 90);
		graphics.drawLine(f.x+cx/2, f.y+1, f.x+f.width-cx/2, f.y+1);

		graphics.setForegroundColor(lineColor);
		graphics.drawArc(new Rectangle(f.x+f.width-cx, f.y, cx, cy), 0, 90);
		graphics.drawLine(f.x+f.width, f.y+cy/2, f.x+f.width, f.y+f.height-cy/2);
		graphics.setForegroundColor(ColorConstants.buttonLightest);
		graphics.drawArc(new Rectangle(f.x+f.width-1-cx, f.y+1, cx, cy), 0, 90);
		graphics.drawLine(f.x+f.width-1, f.y+cy/2, f.x+f.width-1, f.y+f.height-cy/2);
		
		graphics.setForegroundColor(lineColor);
		graphics.drawArc(new Rectangle(f.x, f.y+f.height-cy, cx, cy), 180, 90);
		graphics.drawLine(f.x+cx/2, f.y+f.height, f.x+f.width-cx/2, f.y+f.height);
		graphics.setForegroundColor(ColorConstants.buttonLightest);
		graphics.drawArc(new Rectangle(f.x+1, f.y+f.height-1-cy, cx, cy), 180, 90);
		graphics.drawLine(f.x+cx/2, f.y+f.height-1, f.x+f.width-cx/2, f.y+f.height-1);

		graphics.setForegroundColor(lineColor);
		graphics.drawArc(new Rectangle(f.x+f.width-cx, f.y+f.height-cy, cx, cy), 270, 90);
		graphics.drawLine(f.x, f.y+cy/2, f.x, f.y+f.height-cy/2);
		graphics.setForegroundColor(ColorConstants.buttonLightest);
		graphics.drawArc(new Rectangle(f.x+f.width-1-cx, f.y+f.height-1-cy, cx, cy), 270, 90);
		graphics.drawLine(f.x+1, f.y+cy/2, f.x+1, f.y+f.height-cy/2);
	}

	private class ToggleFigure extends RectangleFigure {
		@Override
		public void paintFigure(Graphics graphics) {
			super.paintFigure(graphics);

			Rectangle rt = this.getClientArea();
			graphics.drawRectangle(rt.x, rt.y, rt.width*2/3, rt.height*2/3);
			graphics.drawRectangle(rt.x, rt.y, rt.width*1/3, rt.height*1/3);
		}
	}
}
