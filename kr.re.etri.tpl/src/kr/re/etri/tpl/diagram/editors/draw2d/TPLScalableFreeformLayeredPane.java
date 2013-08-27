package kr.re.etri.tpl.diagram.editors.draw2d;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;

/**
 * 
 * @author KJH 20110922
 *
 */
public class TPLScalableFreeformLayeredPane extends ScalableFreeformLayeredPane {

	@Override
	protected void paintClientArea(Graphics graphics) {
		if (getChildren().isEmpty())
			return;
		if (getScale() == 1.0) {
			super.paintClientArea(graphics);
		} else {
			TPLScaledGraphics g = new TPLScaledGraphics(graphics);
			boolean optimizeClip = getBorder() == null || getBorder().isOpaque();
			if (!optimizeClip)
				g.clipRect(getBounds().getCropped(getInsets()));
			g.scale(getScale());
			g.pushState();
			paintChildren(g);
			g.dispose();
			graphics.restoreState();
		}
	}

}
