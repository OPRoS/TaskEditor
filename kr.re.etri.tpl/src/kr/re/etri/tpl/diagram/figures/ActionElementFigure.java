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
 * ModelDiagram 에 표시될 ActionElement 의 Figure 클래스이다. 
 * @author sfline
 */
public class ActionElementFigure extends Ellipse {
	/** 모델 요소의 이름을 표시할 Label */
	private Label nameLabel;
	/** 모델의 상태 */
	private int modelState = 0;

	/**
	 * ActionElement 의 Figure 를 생성한다.
	 */
	public ActionElementFigure() {
		super();

		setBorder(new MarginBorder(3));

		setLayoutManager(new BehaviorElementLayout());

		nameLabel = new Label();
		nameLabel.setLabelAlignment(PositionConstants.CENTER);
		add(nameLabel);

		// Layout 변경시 처리할 Listener 를 등록한다. 
		this.addLayoutListener(new LayoutListener.Stub() {

			/**
			 * 무효화한다.
			 * @param container Layout 이 변경되는 Figure
			 * @Override
			 */
			public void invalidate(IFigure container) {
			}
			
			/**
			 * Layout 을 변경한다.
			 * @param container Layout 이 변경되는 Figure
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
			 * Layout 변경 후 처리
			 * @param container Layout 이 변경되는 Figure
			 * @Override 
			 */
			public void postLayout(IFigure container) {
				// TODO Auto-generated method stub
				
			}

			/**
			 * child Figure 가 삭제될 때 호출된다.
			 * @param child 삭제된 child Figure
			 * @Override
			 */
			public void remove(IFigure child) {
			}

			/**
			 * child Figure 에 대한 constraint 를 적용한다.
			 * @param child 변경될 child Figure
			 * @param constraint child Figure 에 대한 contraint
			 */
			public void setConstraint(IFigure child, Object constraint) {
			}
			
		});
	}

	/**
	 * ActionElement 의 모델 상태를 설정한다.
	 * ActionElement 가 include 되었다면 이름 등 일부는 수정 될 수 없다.
	 * @param state 모델의 상태
	 */
	public void setModelState(int state) {
		this.modelState = state;
	}

	/**
	 * nameLabel 에 표시할 이름을 설정한다.
	 * @param name 표시할 이름
	 */
	public void setName(String name) {
		nameLabel.setText(name);
	}
	
	/**
	 * 이름을 표시할 Figure 를 반환한다.
	 * @return 이름을 표시할 Figure
	 */
	public Label getLabelFigure() {
		return nameLabel;
	}

	/**
	 * Figure 의 내부를 Decoration 한다. paintFigure()에서 호출한다.
	 * @param graphics Figure 의 내부에 대한 Graphcis instance 
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
	 * Figure 를 그린다.
	 * @see org.eclipse.draw2d.Shape#paintFigure(Graphics)
	 * @Override
	 */
	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);

	}

	/**
	 * Figure 의 최소 크기를 반환한다.
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
	 * Figure 의 foreground color 를 이용하여 Outline 을 그린다.
	 * @param graphics Outline 을 그릴 Graphics instance
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
