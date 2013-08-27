package kr.re.etri.tpl.diagram.figures;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * StateElement 모델에 대한 Figure 클래스이다.
 * @author sfline
 */
public class StateElementFigure extends RoundedRectangle {
	private static Logger logger = Logger.getLogger(StateElementFigure.class);
	/** StateElement 모델의 icon */
	private ImageFigure iconFigure;	// KJH 20110810
	/** StateElement 모델의 이름을 표시할 Label */
	private Label nameLabel;
	/** StateElement 모델의 StateAttribute */
	StateAttribute attribute;
	/** StateElement 의 상태 */
	private int modelState = 0;
		
	/**
	 * StateElement 모델에 대한 Figure 를 생성한다.
	 * @param attribute StateElement 모델의 속성
	 */
	public StateElementFigure(StateAttribute attribute) {
		super();

		this.attribute = attribute;
		
		setBorder(new MarginBorder(2));
//		setBorder(new FocusBorder());

		setLayoutManager(new XYLayout());

		int drawState = modelState;
		Color lineColor, labelColor;
		
		drawState |= attribute.getValue();
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.STATE_ELEMENT, drawState);
		labelColor = dgmCfg.getItemLabelColor(TaskModelPackage.STATE_ELEMENT, drawState);
		
		// KJH 20110810 s, icon
		ImageDescriptor imageDescriptor;
		if (attribute == StateAttribute.INITIAL) {
			imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.INITSTATE_16);
		}
		else if (attribute == StateAttribute.TARGET) {
			imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.GOALSTATE_16);
		}
		else {
			imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.STATE_16);
		}
		Image iconImage = imageDescriptor.createImage();
		iconFigure = new ImageFigure(iconImage);
		iconFigure.setOpaque(true);
		iconFigure.setBackgroundColor(labelColor);
		add(iconFigure);
		// KJH 20110810 e, icon

		nameLabel = new Label();
		nameLabel.setLabelAlignment(PositionConstants.LEFT);
		nameLabel.setBackgroundColor(labelColor);
		nameLabel.setOpaque(true);
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
				if(container instanceof StateElementFigure) {
					Rectangle clientRect, labelRect;
					Rectangle iconRect;	// KJH 20110810
					clientRect =  container.getClientArea();
					logger.debug("Width = "+clientRect.width+" ,Height = "+ clientRect.height);
					
					iconRect = new Rectangle(0, 2, 16, 16);	// KJH 20110810
					labelRect = new Rectangle(18, 0, clientRect.width - 18, 24);
					
					if (StateAttribute.TARGET == StateElementFigure.this.attribute) {
					    int lineWidth2 = lineWidth + 1;
					
					    labelRect.x += lineWidth2; 
					    labelRect.y += lineWidth2; 
					    labelRect.width -= lineWidth2 * 2;
					    labelRect.height -= lineWidth2;
					    
					    iconRect.translate(lineWidth2, lineWidth2);
					}
					
					labelRect = labelRect.translate(clientRect.getLocation());
					nameLabel.setBounds(labelRect);
					logger.debug("Width = "+labelRect.width+" ,Height = "+ labelRect.height);
					
					iconRect = iconRect.translate(clientRect.getLocation());
					iconFigure.setBounds(iconRect);
				}
				return false;
			}

			/**
			 * Layout 변경 후 처리
			 * @param container Layout 이 변경되는 Figure
			 * @Override 
			 */
			public void postLayout(IFigure container) {
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
	 * StateElement 의 모델 상태를 설정한다.
	 * StateElement 가 include 되었다면 이름 등 일부는 수정 될 수 없다.
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
		Color oldBGColor = getBackgroundColor();
		Color newBGColor = nameLabel.getBackgroundColor();
		
		Rectangle f = Rectangle.SINGLETON.setBounds(nameLabel.getBounds());
		f.x = getBounds().x;	// KJH 20110810
		f.width = getBounds().width;
		if (StateAttribute.TARGET == StateElementFigure.this.attribute) {
			int lineWidth2 = lineWidth + 1; 
			f.x += lineWidth2;
			f.width -= lineWidth2 * 2;
		}
		
		graphics.fillRoundRectangle(getBounds(), corner.width, corner.height);
		graphics.setBackgroundColor(newBGColor);
		graphics.fillRoundRectangle(f, corner.width, corner.height);
		graphics.setBackgroundColor(oldBGColor);
		super.outlineShape(graphics);
	}

	/**
	 * Figure 를 그린다.
	 * fillShape() -> outlineShape() 순으로 호출한다.
	 * @see org.eclipse.draw2d.Shape#paintFigure(Graphics)
	 * @Override
	 */
	public void paintFigure(Graphics graphics) {
		int beforeAntialias = graphics.getAntialias();
		graphics.setAntialias(SWT.ON);
		super.paintFigure(graphics);
		graphics.setAntialias(beforeAntialias);
	}

	/**
	 * @author KJH 20110512
	 * @Override
	 */
	protected void paintChildren(Graphics graphics) {
		IFigure child;

		Rectangle clip = Rectangle.SINGLETON;
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure)getChildren().get(i);
			if (child.isVisible() && child.intersects(graphics.getClip(clip))) {
//				if (child instanceof ExpandTransElementFigure) {
//					graphics.setClip(child.getBounds());
//				} else {
					graphics.clipRect(child.getBounds());
//				}
				child.paint(graphics);
				graphics.restoreState();
			}
		}
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
		int drawState = modelState;
		Color lineColor;
		
		drawState |= attribute.getValue();
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.STATE_ELEMENT, drawState);
		graphics.setForegroundColor(lineColor);

//		double scale = graphics.getAbsoluteScale();
//		graphics.setLineWidthFloat((float)(scale*getLineWidthFloat()));
		
		super.outlineShape(graphics);
		
		if(StateAttribute.TARGET == attribute) {
			float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
			int inset1 = (int)Math.floor(lineInset);
			int inset2 = (int)Math.ceil(lineInset);
			int lineWidth2 = lineWidth + 1;

			Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
			r.x += inset1 + lineWidth2;
			r.y += inset1 + lineWidth2;
			r.width -= inset1 + inset2 + lineWidth2 * 2;
			r.height -= inset1 + inset2 + lineWidth2 * 2;

			graphics.drawRoundRectangle(r, Math.max(0, corner.width - (int)lineInset), Math.max(0, corner.height - (int)lineInset));
		}
		
	}
	
}
