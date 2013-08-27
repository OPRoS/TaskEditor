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
 * StateElement �𵨿� ���� Figure Ŭ�����̴�.
 * @author sfline
 */
public class StateElementFigure extends RoundedRectangle {
	private static Logger logger = Logger.getLogger(StateElementFigure.class);
	/** StateElement ���� icon */
	private ImageFigure iconFigure;	// KJH 20110810
	/** StateElement ���� �̸��� ǥ���� Label */
	private Label nameLabel;
	/** StateElement ���� StateAttribute */
	StateAttribute attribute;
	/** StateElement �� ���� */
	private int modelState = 0;
		
	/**
	 * StateElement �𵨿� ���� Figure �� �����Ѵ�.
	 * @param attribute StateElement ���� �Ӽ�
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
			 * Layout ���� �� ó��
			 * @param container Layout �� ����Ǵ� Figure
			 * @Override 
			 */
			public void postLayout(IFigure container) {
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
	 * StateElement �� �� ���¸� �����Ѵ�.
	 * StateElement �� include �Ǿ��ٸ� �̸� �� �Ϻδ� ���� �� �� ����.
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
	 * Figure �� �׸���.
	 * fillShape() -> outlineShape() ������ ȣ���Ѵ�.
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
