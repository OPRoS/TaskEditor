package kr.re.etri.tpl.diagram.figures;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.KeyEvent;
import org.eclipse.draw2d.KeyListener;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ToggleFigure extends RectangleFigure {
	private ICollapsableFigure parent;
	private EditPart editPart;
	/** ¥›»˘ ªÛ≈¬¿« æ∆¿Ãƒ‹ */
	private Image collapsedImage;
	/** ∆Ó√ƒ¡¯ ªÛ≈¬¿« æ∆¿Ãƒ‹ */
	private Image expandedImage;
	
	public ToggleFigure(ICollapsableFigure parent, EditPart editPart) {
		this.parent = parent;
		this.editPart = editPart;
		
		ImageDescriptor imageDescriptor;
		imageDescriptor = TaskModelPlugin.getImageDescriptor("icons/club/minn.png");
		collapsedImage = imageDescriptor.createImage();
//		collapsedImage.setBackground(ColorConstants.lightBlue);
		
		imageDescriptor = TaskModelPlugin.getImageDescriptor("icons/club/maxx.png");
		expandedImage = imageDescriptor.createImage();
		
		setLineWidth(1);
		setBorder(new MarginBorder(1) {
			@Override
			public void paint(IFigure figure, Graphics g, Insets insets) {
				g.setLineStyle(Graphics.LINE_SOLID);
				g.setLineWidth(1);
				g.setForegroundColor(ColorConstants.buttonLightest);
				Rectangle r = getPaintRectangle(figure, insets);
				r.resize(-1, -1);
				g.drawLine(r.x, r.y, r.right(), r.y);
				g.drawLine(r.x, r.y, r.x, r.bottom());
				g.setForegroundColor(ColorConstants.buttonDarker);
				g.drawLine(r.x, r.bottom(), r.right(), r.bottom());
				g.drawLine(r.right(), r.y, r.right(), r.bottom());
			}
		});
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent ke) {
				if((ke.getState() & KeyEvent.CONTROL) != 0) {
					return;
				}
				if((ke.getState() & KeyEvent.SHIFT) != 0) {
					return;
				}
				
				if(ke.keycode != 0x20 && ke.keycode != 0x13) {
					return;
				}
				
				ToggleFigure.this.parent.toggleState();
			}

			@Override
			public void keyReleased(KeyEvent ke) {
			}
			
		});
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClicked(MouseEvent me) {
			}
			
			@Override
			public void mousePressed(MouseEvent me) {
				if(me.button << 19 != MouseEvent.BUTTON1) {
					return;
				}
				ToggleFigure.this.parent.toggleState();
			}

			@Override
			public void mouseReleased(MouseEvent me) {
			}
		});
	}

	protected void fillShape(Graphics graphics){
		super.fillShape(graphics);
		
		ReferElement refItem = (ReferElement)editPart.getModel();
		int modelState = parent.getModelState();

		if((modelState & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
			graphics.drawImage(collapsedImage, getBounds().x, getBounds().y);
		}
		else {
			if(refItem.isCollapsed() == false) {
				// ∆Ó√ƒ¡¯ ªÛ≈¬ø°º≠ ¥›»˘ ªÛ≈¬∑Œ
				graphics.drawImage(collapsedImage, getBounds().x, getBounds().y);
			}
			else {
				// ¥›»˘ ªÛ≈¬ø°º≠ ∆Ó√ƒ¡¯ ªÛ≈¬∑Œ
				graphics.drawImage(expandedImage, getBounds().x, getBounds().y);
			}
		}
	}

	@Override
	protected void outlineShape(Graphics graphics) {
//		double scale = graphics.getAbsoluteScale();
//		graphics.setLineWidthFloat((float)(scale * getLineWidthFloat()));
//	
//		float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
//	    int inset1 = (int)Math.floor(lineInset);
//	    int inset2 = (int)Math.ceil(lineInset);
//
//	    Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
//	    r.x += inset1; 
//	    r.y += inset1; 
//	    r.width -= inset1 + inset2 + 1;
//	    r.height -= inset1 + inset2 + 1;
//	    graphics.translate(0.51f, 0.51f);
//	    graphics.drawRectangle(r);
//		super.outlineShape(graphics);
	}

	public Rectangle getDefaultBounds() {
		int width = 0;
		int height = 0;
		if (collapsedImage != null) {
			width = Math.max(width, collapsedImage.getBounds().width);
			height = Math.max(height, collapsedImage.getBounds().height);
		}
		if (expandedImage != null) {
			width = Math.max(width, expandedImage.getBounds().width);
			height = Math.max(height, expandedImage.getBounds().height);
		}
		
		return new Rectangle(0, 0, width, height);
	}
}
