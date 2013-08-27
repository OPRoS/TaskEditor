package kr.re.etri.tpl.diagram.figures;

import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.ScrollPaneLayout;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * BehaviorElement 모델에 대한 Figure 클래스이다.
 * @author sfline
 */
public class BehaviorElementFigure extends RectangleFigure implements ICollapsableFigure {
	private static Logger logger = Logger.getLogger(BehaviorElementFigure.class);
	/** 이 Figure 를 펼치기/닫기 전환할 버튼 */
	private ToggleFigure button;
	/** BehaviorElement 모델의 EditPart */
	private final EditPart editPart;
	/** BehaviorElement 모델의 icon */
	private ImageFigure iconFigure;	// KJH 20110810
	/** BehaviorElement 모델의 이름을 표시할 Label */
	private Label nameLabel;
	/** BehaviorElement 의 child 모델의 Figure 가 표시될 Figure */
	private ScrollPane scrollpane;
	/** Layout 변경을 처리할 Listener */
	private LayoutListener layoutListener;
	/** 이 Figure 의 펼치기/닫기 요청 */
	private ChangeFoldingRequest req;
	/** BehaviorElement 모델의 상태 */
	private int modelState = 0;
	/** BehaviorElement 의 Laytout Manager */
	private LayoutManager layoutManager = new BehaviorElementLayout();

	// KJH 20100809, attribute
//	private BehaviorAttribute attribute;
	private ReferAttribute attribute;
	
	/**
	 * BehaviorElement 모델의 Figure 를 생성한다.
	 * @param editPart BehaviorElement 모델의 EditPart
	 */
	public BehaviorElementFigure(EditPart editPart) {
		super();
		
		ReferElement refItem = (ReferElement)editPart.getModel();
		ItemElement realItem = refItem.getRealModel();
		
		this.attribute = refItem.getAttribute();
		
		this.editPart = editPart;

		int drawState = modelState;
		Color lineColor, labelColor;
		
		if (realItem.isIncluded()) {
			drawState |= ItemState.ITEM_INCLUDED;
		}
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.TASK_ELEMENT, drawState);
		labelColor = dgmCfg.getItemLabelColor(TaskModelPackage.TASK_ELEMENT, drawState);

		setBorder(new MarginBorder(2));
		// KJH 20110421, construct, destruct 표시하기위해 layoutmanager 추가
		setLayoutManager(new XYLayout());

		scrollpane = new ScrollPane();

//		scrollpane.setBorder(new SchemeBorder(SchemeBorder.SCHEMES.ETCHED));
		scrollpane.getViewport().setContentsTracksWidth(true);
		scrollpane.setMinimumSize(new Dimension(0, 0));
		scrollpane.setLayoutManager(new ScrollPaneLayout());
		scrollpane.setHorizontalScrollBarVisibility(ScrollPane.AUTOMATIC);
		scrollpane.setVerticalScrollBarVisibility(ScrollPane.AUTOMATIC);
		IFigure contentsFigure = new Figure();
		contentsFigure.setLayoutManager(layoutManager);
		scrollpane.setContents(contentsFigure);
		scrollpane.getContents().setOpaque(true);

		add(scrollpane);
		if(refItem.isCollapsed() == false) {
			scrollpane.setVisible(true);
		}
		else {
			scrollpane.setVisible(false);
		}

		// KJH 20110810 s, icon
		ImageDescriptor imageDescriptor;
		if (ReferAttribute.NORMAL == attribute) {
			imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.BEHAVIOR_16);
		}
		else {
			imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.REFBEHAVIOR_16);
		}
		Image iconImage = imageDescriptor.createImage();
		iconFigure = new ImageFigure(iconImage);
		iconFigure.setOpaque(true);
		iconFigure.setBackgroundColor(labelColor);
		add(iconFigure);
		// KJH 20110810 e, icon
		
		nameLabel = new Label();
//		nameLabel.setBorder(new MarginBorder(3));
		nameLabel.setLabelAlignment(PositionConstants.LEFT);
		
		// KJH 20100803 s, name label background color
//		if(ReferAttribute.NORMAL == this.attribute) {
			nameLabel.setBackgroundColor(labelColor);
//		} else {
//			nameLabel.setBackgroundColor(new Color(null, 74, 138, 255));
//		}
		// KJH 20100803 e, name label background color

		nameLabel.setOpaque(true);
		
//		add(nameLabel, new Rectangle(2, 2, 80, 18));
		add(nameLabel);

		button = new ToggleFigure(this, editPart);
		// KJH 20100809 s, button border
		if (ReferAttribute.NORMAL == this.attribute) {
			button.setForegroundColor(ColorConstants.lightBlue);
		} else {
			button.setForegroundColor(ColorConstants.lightGray);
		}
		// KJH 20100809 e, button border
		button.setBackgroundColor(labelColor);
		button.setOpaque(true);
		add(button);

		layoutListener = new BehaviorElementFigureLayoutListener();
		this.addLayoutListener(layoutListener);
	}

	/**
	 * BehaviorElement 의 모델 상태를 설정한다.
	 * BehaviorElement 가 include 되었다면 이름, 펼치기/닫기 등 일부는 수정 될 수 없다.
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
	 * BehaviorElement 모델의 자식 모델들의 Figure 를 표시할 Figure 를 반환한다.
	 * @return	자식 모델들의 Figure 를 표시할 Figure
	 */
	public IFigure getContentPane() {
		return scrollpane.getContents();
	}

	/**
	 * Figure 의 표시 상태를 변경한다.
	 */
	public void updateFigure() {
		ReferElement refItem = (ReferElement)editPart.getModel();
		ItemElement realItem = refItem.getRealModel();

		if(refItem.isCollapsed() == false && scrollpane.isVisible() == false) {
			scrollpane.setVisible(true);
		}
		else if(refItem.isCollapsed() != false && scrollpane.isVisible() != false){
			scrollpane.setVisible(false);
//			scrollpane.setVisible(true);
		}

		nameLabel.setText(realItem.getName());
	}

	/**
	 * Figure 의 펼치기/닫기 상태 변경을 요청한다.
	 */
	private void changeState() {
		ReferElement refItem = (ReferElement)editPart.getModel();
		ItemElement realItem = refItem.getRealModel();

		if((modelState & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
			return;
		}

		req = new ChangeFoldingRequest(!refItem.isCollapsed(), RequestConstants.REQ_RESIZE);
		req.setEditParts(editPart);

		Rectangle rect = getBounds();
		
		// KJH 20110420 s, modify
		Dimension oriBound = Dimension.SINGLETON;
		oriBound.width = refItem.getWidth2();
		oriBound.height = refItem.getHeight2();
		req.setCollapsed(!refItem.isCollapsed());
		req.setLocation(rect.getLocation());
		req.setResizeDirection(refItem.isCollapsed() ?
				PositionConstants.SOUTH_EAST : PositionConstants.NORTH_WEST);
		req.setSizeDelta(new Dimension(oriBound.width-rect.width, oriBound.height-rect.height));

//		if(refItem.isCollapsed() == false) {
//			req.setCollapsed(!refItem.isCollapsed());
//			req.setLocation(rect.getLocation());
//			req.setResizeDirection(PositionConstants.NORTH_WEST);
//			req.setSizeDelta(new Dimension(120-rect.width, 25-rect.height));
//		}
//		else {
//			Rectangle oriBound = rect.getCopy();
//			oriBound.width = refItem.getWidth2();
//			oriBound.height = refItem.getHeight2();
//			req.setCollapsed(!refItem.isCollapsed());
//			req.setLocation(rect.getLocation());
//			req.setResizeDirection(PositionConstants.SOUTH_EAST);
//			req.setSizeDelta(new Dimension(oriBound.width-rect.width, oriBound.height-rect.height));
////			logger.debug("call validate()");
////			this.getParent().validate();
//		}
		// KJH 20110420 e, modify

		Command cmd = editPart.getCommand(req);
		EditPartViewer viewer = editPart.getViewer();
		DefaultEditDomain domain = (DefaultEditDomain)viewer.getEditDomain();
		
		CommandStack cmdStack = domain.getCommandStack();
		cmdStack.execute(cmd);

		req = null;
	}

	/**
	 * Figure 의 내부를 Decoration 한다. paintFigure()에서 호출한다.
	 * @param graphics Figure 의 내부에 대한 Graphcis instance 
	 * @Override
	 */
	protected void fillShape(Graphics graphics){
		// KJH 20110421 s, nameLabel 줄어들어도 background 칠해지도록 수정
		Color oldBGColor = getBackgroundColor();
		Color newBGColor = nameLabel.getBackgroundColor(); 

		Rectangle f = Rectangle.SINGLETON.setBounds(nameLabel.getBounds());
		f.x = getClientArea().x;	// KJH 20110810
		f.width = getClientArea().width;
		
		graphics.fillRectangle(getBounds());
		graphics.setBackgroundColor(newBGColor);
		graphics.fillRectangle(f);
		graphics.setBackgroundColor(oldBGColor);
		// KJH 20110421 e, nameLabel 줄어들어도 background 칠해지도록 수정
	}
	
	/**
	 * @author KJH 20110511
	 * @Override
	 */
	protected void paintChildren(Graphics graphics) {
		IFigure child;

		Rectangle clip = Rectangle.SINGLETON;
		for (int i = 0; i < getChildren().size(); i++) {
			child = (IFigure)getChildren().get(i);
			if (child.isVisible() && child.intersects(graphics.getClip(clip))) {
//				graphics.setClip(child.getBounds());
				graphics.clipRect(child.getBounds());
				child.paint(graphics);
				graphics.restoreState();
			}
		}
	}

	/**
	 * Figure 를 그린다.
	 * @see org.eclipse.draw2d.Shape#paintFigure(Graphics)
	 * @Override
	 */
	public void paintFigure(Graphics graphics) {
		int beforeAntialias = graphics.getAntialias();
		graphics.setAntialias(SWT.ON);
		super.paintFigure(graphics);
		graphics.setAntialias(beforeAntialias);
	
//그림자 그리기 ㅋㅋ
//		Rectangle rc = getBounds().getCopy() ;
//		translateToParent(rc);
//		rc.translate(5,5);
//
//		// draw the shadow
//		graphics.setBackgroundColor( ColorConstants.lightGray );
//		graphics.fillRectangle( rc );
//		Dimension de =this.corner;
//		graphics.fillRoundRectangle(rc, 12, 12);
		
	}

//	@Override
//	public boolean containsPoint(int x, int y) {
//		boolean b = super.containsPoint(x, y);
//		if (b) {
//			return b;
//		}
//		
//		List children = getChildren();
//		Iterator iter = children.iterator();
//		while (iter.hasNext()) {
//			Object next = iter.next();
//			if (next instanceof Figure) {
//				b = ((Figure)next).containsPoint(x, y);
//				if (b) {
//					return b;
//				}
//			}
//		}
//		
//		return false;
//	}
//
//	@Override
//	public Rectangle getClientArea(Rectangle rect) {
//		return super.getClientArea(rect);
//	}

	/**
	 * 자식 모델들의 Figure 들의 Bound 를 계산하여 반환한다.
	 * @return
	 * 
	 * @Override
	 */
	public Rectangle getChildRectangle() {
		int minX, maxX, minY, maxY;
		Dimension minSize;
		Rectangle retRect;
		minSize = super.getMinimumSize(120, 18);

		Rectangle figureRect = getBounds();
		minSize = new Dimension(minSize);
		ReferElement refItem = (ReferElement)editPart.getModel();
		ItemElement realItem = refItem.getRealModel();

		if(refItem.isCollapsed()) {
			return new Rectangle();
		}

 		IFigure contentsFigure = this.getContentPane();
		List<IFigure> childFigures = contentsFigure.getChildren();
		minX = maxX = minY = maxY = -1;
		for(IFigure childFigure : childFigures) {
			Rectangle rect = childFigure.getBounds();
			if(minX == -1) {
				minX = rect.x;
				maxX = rect.x + rect.width;
			}
			if(minY == -1) {
				minY = rect.y;
				maxY = rect.y + rect.height;
			}
			
			if(rect.x < minX) {
				minX = rect.x;
			}
			if(maxX < rect.x + rect.width) {
				maxX = rect.x + rect.width;
			}
			
			if(rect.y < minY) {
				minY = rect.y;
			}
			if(maxY < rect.y + rect.height) {
				maxY = rect.y + rect.height;
			}

			if(minSize.width < rect.x + rect.width) {
				minSize.width = rect.x + rect.width;
			}
			if(minSize.height < rect.y + rect.height) {
				minSize.height = rect.y + rect.height;
			}
		}

		Rectangle contentsRect = contentsFigure.getClientArea();

		int bx = (figureRect.width - contentsRect.width);
		int by = (figureRect.height - contentsRect.height);
		retRect = new Rectangle(minX-bx/2,
				minY-by/2,
				maxX - minX+bx,
				maxY - minY+by);
		
		return retRect;
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
		
		minSize = new Dimension(minSize);
		ReferElement refItem = (ReferElement)editPart.getModel();
		ItemElement realItem = refItem.getRealModel();

		if(req == null) {
			if(refItem.isCollapsed()) {
				return minSize;
			}
		}
		else if(req.isCollapsed()) {
			return minSize;
		}

 		IFigure contentsFigure = this.getContentPane();
		List<IFigure> childFigures = contentsFigure.getChildren();
		for(IFigure childFigure : childFigures) {
			// KJH 20110420 s,
			if (!(childFigure instanceof Shape)) {
				continue;
			}// KJH 20110420 e,
			
			Rectangle rect = childFigure.getBounds();

			if(minSize.width < rect.x + rect.width) {
				minSize.width = rect.x + rect.width;
			}
			if(minSize.height < rect.y + rect.height) {
				minSize.height = rect.y + rect.height;
			}
		}

		Rectangle figureRect = getBounds();
		Rectangle contentsRect = contentsFigure.getClientArea();
		minSize.width = minSize.width - contentsRect.x+(figureRect.width - contentsRect.width);
		minSize.height = minSize.height - contentsRect.y +(figureRect.height - contentsRect.height);

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
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.TASK_ELEMENT, drawState);
		graphics.setForegroundColor(lineColor);
		
//		double scale = graphics.getAbsoluteScale();
//		graphics.setLineWidthFloat((float) (scale*getLineWidthFloat()));
		
		float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
	    int inset1 = (int)Math.floor(lineInset);
	    int inset2 = (int)Math.ceil(lineInset);

	    Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
	    r.x += inset1; 
	    r.y += inset1;
	    r.width -= inset1 + inset2;
	    r.height -= inset1 + inset2;
//	    if (scale > 1.0) {
//	    	r.width--;
//	    	r.height--;
//	    }

	    graphics.drawRectangle(r);
//		super.outlineShape(graphics);
	}
	
	/**
	 * Figure 의 Layout 변경 처리를 수행할 Listener
	 * @author sfline
	 *
	 */
	private class BehaviorElementFigureLayoutListener extends LayoutListener.Stub {
		
		/**
		 * Layout 을 변경한다.
		 * @param container BehaviorElement 모델의 Figure
		 */
		public boolean layout(IFigure container) {
			if(container instanceof BehaviorElementFigure) {
				
				Rectangle clientRect, labelRect, toggleRect, paneRect;
				Rectangle iconRect;	// KJH 20110810

				clientRect = container.getClientArea();
				
				iconRect = new Rectangle(iconFigure.getImage().getBounds());
				toggleRect = new Rectangle(button.getDefaultBounds());
				toggleRect.translate(clientRect.width - toggleRect.width, 0);
				
				int hMin = Math.min(iconRect.height, toggleRect.height);
				int hMax = Math.max(iconRect.height, toggleRect.height);
				
				if (iconRect.height < hMax) {
					iconRect.y += (hMax - hMin) / 2;
				}
				if (toggleRect.height < hMax) {
					toggleRect.y += (hMax - hMin) / 2;
				}
				
				labelRect = new Rectangle(iconRect.width + 2, 0, clientRect.width, hMax);
				labelRect.width -= (labelRect.x + toggleRect.width);
				paneRect = new Rectangle(0, hMax, clientRect.width, clientRect.height - hMax);

				iconRect = iconRect.translate(clientRect.getLocation());
				iconFigure.setBounds(iconRect);
				
				toggleRect = toggleRect.translate(clientRect.getLocation());
				button.setBounds(toggleRect);

				labelRect = labelRect.translate(clientRect.getLocation());
				nameLabel.setBounds(labelRect);

				paneRect = paneRect.translate(clientRect.getLocation());
				scrollpane.setBounds(paneRect);
			}
			return false;
		}

		@Override
		public void postLayout(IFigure container) {
			// KJH 20110421 s,
			Rectangle r = Rectangle.SINGLETON.setBounds(nameLabel.getBounds());
			
			Iterator child = container.getChildren().iterator();
			while (child.hasNext()) {
				IFigure f = (IFigure)child.next();
				if (f instanceof ImageFigure) {
					Rectangle rec = f.getBounds();
					// KJH 20110816 s, modify
					if (r.y + r.height >= rec.y && r.y <= rec.y + rec.height) {
						if (r.x < rec.x && r.x + r.width >= rec.x) {
							r.width = rec.x - r.x - 1;
						}
					}	// KJH 20110816 e, modify
				}
			}
			
			nameLabel.setBounds(r);
			// KJH 20110421 e,
		}
	}

	/**
	 * @author 	KJH 20110429
	 * @Override
	 */
	public int getModelState() {
		return modelState;
	}// KJH 20110429 

	/**
	 * @author KJH 20110429
	 * @Override
	 */
	public void toggleState() {
		changeState();
	}

	@Override
	public void remove(IFigure figure) {
		// TODO Auto-generated method stub
		super.remove(figure);
	}

}
