package kr.re.etri.tpl.diagram.editparts;

import kr.re.etri.tpl.diagram.editors.draw2d.TPLScalableFreeformLayeredPane;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;

/**
 * 
 * @author KJH 20110922
 *
 */
public class TPLScalableFreeformRootEditPart extends ScalableFreeformRootEditPart{

	@Override
	protected ScalableFreeformLayeredPane createScaledLayers() {
		TPLScalableFreeformLayeredPane layers = new TPLScalableFreeformLayeredPane();
		layers.add(createGridLayer(), GRID_LAYER);
		layers.add(getPrintableLayers(), PRINTABLE_LAYERS);
		layers.add(new FeedbackLayer(), SCALED_FEEDBACK_LAYER);
		return layers;
	}

class FeedbackLayer
extends FreeformLayer
{
	FeedbackLayer() {
		setEnabled(false);
	}
}
}
