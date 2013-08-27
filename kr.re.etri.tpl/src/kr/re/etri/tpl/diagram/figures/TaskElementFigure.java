package kr.re.etri.tpl.diagram.figures;

import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
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
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.ScrollPaneLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
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
 * WorkerElement �𵨿� ���� Figure Ŭ�����̴�.
 * @author sfline
 */
public class TaskElementFigure extends RectangleFigure implements ICollapsableFigure {
	private static Logger logger = Logger.getLogger(TaskElementFigure.class);
	/** �� Figure �� ��ġ��/�ݱ� ��ȯ�� ��ư */
	private ToggleFigure button;
	/** TaskElement ���� EditPart */
	private final EditPart editPart;
	/** TaskElement ���� icon */
	private ImageFigure iconFigure;	// KJH 20110810
	/** TaskElement ���� �̸��� ǥ���� Label */
	private Label nameLabel;
	/** TaskElement �� child ���� Figure �� ǥ�õ� Figure */
	private ScrollPane scrollpane;
	/** Layout ������ ó���� Listener */
	private LayoutListener layoutListener;
	/** �� Figure �� ��ġ��/�ݱ� ��û */
	private ChangeFoldingRequest req;
	/** TaskElement ���� ���� */
	private int modelState = 0;
	/** TaskElement �� Laytout Manager */
	private LayoutManager layoutManager = new BehaviorElementLayout();
	
	public TaskElementFigure(EditPart editPart) {
		super();
		
		ReferElement refItem = (ReferElement) editPart.getModel();
		TaskElement realItem = (TaskElement) refItem.getRealModel();
		
		this.editPart = editPart;
		
		setBorder(new MarginBorder(2));
		setLayoutManager(new XYLayout());
		
		scrollpane = new ScrollPane();

//		scrollpane.setBorder(new SchemeBorder(SchemeBorder.SCHEMES.ETCHED));
		scrollpane.getViewport().setContentsTracksWidth(true);
		scrollpane.setMinimumSize(new Dimension(0, 0));
		scrollpane.setLayoutManager(new ScrollPaneLayout());
		scrollpane.setHorizontalScrollBarVisibility(ScrollPane.AUTOMATIC);
		scrollpane.setVerticalScrollBarVisibility(ScrollPane.AUTOMATIC);
		Figure contentsFigure = new Figure();
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

		int drawState = modelState;
		Color lineColor, labelColor;
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.WORKER_ELEMENT, drawState);
		labelColor = dgmCfg.getItemLabelColor(TaskModelPackage.WORKER_ELEMENT, drawState);

		// KJH 20110810 s, icon
		ImageDescriptor imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.TASK_16);
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
		
		button = new ToggleFigure(this, editPart);
		button.setOpaque(true);
		button.setForegroundColor(ColorConstants.lightBlue);
		button.setBackgroundColor(labelColor);
		add(button);
		
		layoutListener = new TaskElementFigureLayoutListener();
		addLayoutListener(layoutListener);
		
	}

	/**
	 * TaskElement �� �� ���¸� �����Ѵ�.
	 * TaskElement �� include �Ǿ��ٸ� �̸�, ��ġ��/�ݱ� �� �Ϻδ� ���� �� �� ����.
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
	 * TaskElement ���� �ڽ� �𵨵��� Figure �� ǥ���� Figure �� ��ȯ�Ѵ�.
	 * @return	�ڽ� �𵨵��� Figure �� ǥ���� Figure
	 */
	public IFigure getContentPane() {
		return scrollpane.getContents();
	}

	/**
	 * Figure �� ǥ�� ���¸� �����Ѵ�.
	 */
	public void updateFigure() {
		ReferElement refItem = (ReferElement)editPart.getModel();
		ItemElement realItem = refItem.getRealModel();

		if(refItem.isCollapsed() == false && scrollpane.isVisible() == false) {
			scrollpane.setVisible(true);
		}
		else if(refItem.isCollapsed() != false && scrollpane.isVisible() != false){
			scrollpane.setVisible(false);
		}

		nameLabel.setText(realItem.getName());
	}

	/**
	 * Figure �� ��ġ��/�ݱ� ���� ������ ��û�Ѵ�.
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
		
		Dimension oriBound = Dimension.SINGLETON;
		oriBound.width = refItem.getWidth2();
		oriBound.height = refItem.getHeight2();
		req.setCollapsed(!refItem.isCollapsed());
		req.setLocation(rect.getLocation());
		req.setResizeDirection(refItem.isCollapsed() ?
				PositionConstants.SOUTH_EAST : PositionConstants.NORTH_WEST);
		req.setSizeDelta(new Dimension(oriBound.width-rect.width, oriBound.height-rect.height));

		Command cmd = editPart.getCommand(req);
		EditPartViewer viewer = editPart.getViewer();
		DefaultEditDomain domain = (DefaultEditDomain)viewer.getEditDomain();
		
		CommandStack cmdStack = domain.getCommandStack();
		cmdStack.execute(cmd);

		req = null;
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
	 * Figure �� �׸���.
	 * fillShape() -> outlineShape() ������ ȣ���Ѵ�.
	 * @see org.eclipse.draw2d.Shape#paintFigure(Graphics)
	 * @Override
	 */
	// KJH 20110210 s, add// �밢���� �׷��� �� �ε巴�� ó��
	public void paintFigure(Graphics graphics) {
		int beforeAntialias = graphics.getAntialias();
		graphics.setAntialias(SWT.ON);
		super.paintFigure(graphics);
		graphics.setAntialias(beforeAntialias);
	}	// KJH 20110210 e, add// �밢���� �׷��� �� �ε巴�� ó��

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
//
//	@Override
//	public IFigure findFigureAt(int x, int y, TreeSearch search) {
//		return super.findFigureAt(x, y, search);
//	}

	/**
	 * �ڽ� �𵨵��� Figure ���� Bound �� ����Ͽ� ��ȯ�Ѵ�.
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
//			return new Rectangle();	// KJH 20110511, del
		}

 		IFigure contentsFigure = this.getContentPane();
		Rectangle contentsRect = contentsFigure.getClientArea();
		List<IFigure> childFigures = contentsFigure.getChildren();
		minX = maxX = contentsRect.x;
		minY = maxY = contentsRect.y;
		for(int i=0; i<childFigures.size(); i++) {
			IFigure childFigure = childFigures.get(i);
			Rectangle rect = childFigure.getBounds();
			
			if (i == 0) {
				minX = rect.x;
				maxX = rect.x + rect.width;
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

//		int bx = (figureRect.width - contentsRect.width);
//		int by = (figureRect.height - contentsRect.height);
//		retRect = new Rectangle(minX-bx/2,
//				minY-by/2,
//				maxX - minX+bx,
//				maxY - minY+by);
		
		// contentsRect�� ���� �������
		retRect = new Rectangle(minX - contentsRect.x,
				minY - contentsRect.y,
				maxX - minX,
				maxY - minY);
		
		return retRect;
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
	 * Figure �� ���θ� Decoration �Ѵ�. paintFigure()���� ȣ���Ѵ�.
	 * @param graphics Figure �� ���ο� ���� Graphcis instance 
	 * @Override
	 */
	// KJH 20110429 s, modify
	protected void fillShape(Graphics graphics){
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		Color oldBGColor = getBackgroundColor();
		Color newBGColor = nameLabel.getBackgroundColor();
		
		Rectangle f = Rectangle.SINGLETON.setBounds(nameLabel.getBounds());
		f.x = getClientArea().x;	// KJH 20110810
		f.width = getClientArea().width;
		
		graphics.fillRectangle(getBounds());
		graphics.setBackgroundColor(newBGColor);
		graphics.fillRectangle(f);
		graphics.setBackgroundColor(oldBGColor);
	}// KJH 20110429 e, modify

	/**
	 * Figure �� foreground color �� �̿��Ͽ� Outline �� �׸���.
	 * @param graphics Outline �� �׸� Graphics instance
	 * @see org.eclipse.draw2d.Shape#outlineShape(Graphics)
	 */
	// KJH 20110429 s, modify
	protected void outlineShape(Graphics graphics) {
		int drawState = modelState;
		Color lineColor;
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		lineColor = dgmCfg.getItemLineColor(TaskModelPackage.CONNECTOR_ELEMENT, drawState);
		graphics.setForegroundColor(lineColor);
		
//		double scale = graphics.getAbsoluteScale();
//		graphics.setLineWidthFloat((float)(scale*getLineWidthFloat()));
		
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
	// KJH 20110210 e, modify
	
	/**
	 * Figure �� Layout ���� ó���� ������ Listener
	 * @author KJH
	 *
	 */
	private class TaskElementFigureLayoutListener extends LayoutListener.Stub {

		/**
		 * Layout �� �����Ѵ�.
		 * @param container TaskElement ���� Figure
		 */
		public boolean layout(IFigure container) {
			if (container instanceof TaskElementFigure) {
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
	 * @author KJH 20110429
	 * @Override
	 */
	public int getModelState() {
		return modelState;
	}

	/**
	 * @author KJH 20110429
	 * @Override
	 */
	public void toggleState() {
		changeState();
	}

	
	
//	private static Logger logger = Logger.getLogger(TaskElementFigure.class);
//	/** WorkerElement ���� �̸��� ǥ���� Label */
//	private Label nameLabel;
//	
//	/**
//	 * WorkerElement �𵨿� ���� Figure �� �����Ѵ�.
//	 */
//	public TaskElementFigure() {
//		super();
//
//		setBorder(new MarginBorder(2));
//		setLayoutManager(new XYLayout());
////		setLayoutManager(new BehaviorElementLayout());
//
//		nameLabel = new Label();
//		nameLabel.setLabelAlignment(PositionConstants.CENTER);
//
//		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
//		Color lineColor = dgmCfg.getItemLineColor(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID);
//		nameLabel.setBackgroundColor(lineColor);
//		nameLabel.setOpaque(true);
//		add(nameLabel);
//		
////		nameLabel.setBackgroundColor();
//
//		// Layout ����� ó���� Listener �� ����Ѵ�. 
//		this.addLayoutListener(new LayoutListener.Stub() {
//
//			/**
//			 * ��ȿȭ�Ѵ�.
//			 * @param container Layout �� ����Ǵ� Figure
//			 * @Override
//			 */
//			public void invalidate(IFigure container) {
//			}
//
//			/**
//			 * Layout �� �����Ѵ�.
//			 * @param container Layout �� ����Ǵ� Figure
//			 * @Override
//			 */
//			public boolean layout(IFigure container) {
//				if(container instanceof TaskElementFigure) {
//					// KJH 20110418 s, paintFigure->layout
//					Rectangle f = Rectangle.SINGLETON;
//					Rectangle r = container.getClientArea();
//					f.x = r.x + lineWidth / 2;
//					f.y = r.y + lineWidth / 2;
//					f.width = r.width - lineWidth;
//					f.height = r.height - lineWidth;
//
//					corner.width = f.width/4;
//					corner.height = f.height;
//					// KJH 20110418 e, paintFigure->layout
//					
//					Rectangle clientRect, labelRect, paneRect;
//					clientRect =  container.getBounds();
//					logger.debug("Width = "+clientRect.width+" ,Height = "+ clientRect.height);
//					
//					labelRect = new Rectangle(corner.width/2, 0, clientRect.width - corner.width, 24);
//					paneRect = new Rectangle(0, 0, clientRect.width, clientRect.height);
//
//					labelRect = labelRect.translate(clientRect.getLocation());
//					nameLabel.setBounds(labelRect);
//
//					paneRect = paneRect.translate(clientRect.getLocation());
////					scrollpane.setBounds(paneRect);
//				}
//				return false;
//			}
//		});
//	}
//
//	/**
//	 * nameLabel �� ǥ���� �̸��� �����Ѵ�.
//	 * @param name ǥ���� �̸�
//	 */
//	public void setName(String name) {
//		nameLabel.setText(name);
//	}
//
//	/**
//	 * �̸��� ǥ���� Figure �� ��ȯ�Ѵ�.
//	 * @return �̸��� ǥ���� Figure
//	 */
//	public Label getLabelFigure() {
//		return nameLabel;
//	}
//
//	/**
//	 * Figure �� ���θ� Decoration �Ѵ�. paintFigure()���� ȣ���Ѵ�.
//	 * @param graphics Figure �� ���ο� ���� Graphcis instance 
//	 * @Override
//	 */
//	protected void fillShape(Graphics graphics){
//		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
//		Color oldBGColor = getBackgroundColor();
//		Color newBGColor = dgmCfg.getItemLineColor(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID); 
//		
//		Rectangle f = Rectangle.SINGLETON.setBounds(nameLabel.getBounds());
//		Point loc = getLocation();
//		
//		// KJH 20110418 s, arc
//		double scaleY = corner.height / 200.0f;
//		double scaleX = corner.width / 200.0f;
//		PointList arcList = new PointList();
//		Transform tr = new Transform();
//		
//		int cnt = 128;
//		tr.setRotation(Math.PI*((360/cnt)/180.0));
//		Point pt = new Point(0, 100);
//		for(int i = 0; i < cnt; i += 1) {
//			pt = tr.getTransformed(pt);
//			arcList.addPoint(pt.x, pt.y);
//		}
//		
//		PolygonDecoration poly = new PolygonDecoration();
//		poly.setTemplate(arcList);
//		poly.setScale(scaleX, scaleY);
//		arcList = poly.getPoints();
//		arcList.translate(corner.width/2, corner.height/2);
//		
//		PointList subList = new PointList();
//		arcList.translate(loc);
//		for (int i=0; i<arcList.size(); i++) {
//			Point p = arcList.getPoint(i);
//			if (p.y < f.y+f.height) {
//				subList.addPoint(p);
//			}
//		}
//		Point p = subList.getFirstPoint();
//		if (p.y < f.y+f.height) {
//			subList.insertPoint(new Point(p.x, f.y+f.height), 0);
//		}
//		p = subList.getLastPoint();
//		if (p.y < f.y+f.height) {
//			subList.addPoint(p.x, f.y+f.height);
//		}
//		// KJH 20110418 e, arc
//
//		graphics.fillRoundRectangle(getBounds(), corner.width, corner.height);
//		graphics.setBackgroundColor(newBGColor);
//		graphics.fillRectangle(f);
//		graphics.fillPolygon(subList);
//		subList.translate(f.width, 0);
//		graphics.fillPolygon(subList);
//		graphics.setBackgroundColor(oldBGColor);
//	}
//
//	/**
//	 * Figure �� �׸���.
//	 * fillShape() -> outlineShape() ������ ȣ���Ѵ�.
//	 * @see org.eclipse.draw2d.Shape#paintFigure(Graphics)
//	 * @Override
//	 */
//	public void paintFigure(Graphics graphics) {
//		int beforeAntialias = graphics.getAntialias();
//		graphics.setAntialias(SWT.ON);
//		super.paintFigure(graphics);
//		graphics.setAntialias(beforeAntialias);
//	}
//
//	/**
//	 * Figure �� �ּ� ũ�⸦ ��ȯ�Ѵ�.
//	 * @see org.eclipse.draw2d.Figure#getMinimumSize(int, int)
//	 * @Override
//	 */
//	public Dimension getMinimumSize(int wHint, int hHint)
//	{
//		Dimension minSize;
//		minSize = super.getMinimumSize(wHint, hHint);
//		
//		return minSize;
//	}
//
//	/**
//	 * Figure �� foreground color �� �̿��Ͽ� Outline �� �׸���.
//	 * @param graphics Outline �� �׸� Graphics instance
//	 * @see org.eclipse.draw2d.Shape#outlineShape(Graphics)
//	 */
//	protected void outlineShape(Graphics graphics) {
//		int drawState = 0;
//		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
//		Color lineColor = dgmCfg.getItemLineColor(TaskModelPackage.WORKER_ELEMENT, drawState);
//		
//		graphics.setForegroundColor(lineColor);
//		graphics.setLineWidth(lineWidth);
//		super.outlineShape(graphics);
//	}

}
