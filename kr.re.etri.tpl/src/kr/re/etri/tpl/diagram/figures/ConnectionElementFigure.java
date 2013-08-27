package kr.re.etri.tpl.diagram.figures;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Connection 모델에 대한 Figure 클래스이다.
 * @author sfline
 */
public class ConnectionElementFigure extends PolylineConnection {
	private static Logger logger =
		 Logger.getLogger(ConnectionElementFigure.class.getName());

	/** connection 의 이름을 표시할 Label */
	private Label nameLabel;

	/**
	 * Connection 모델에 대한 Figure 를 생성한다.
	 */
	public ConnectionElementFigure() {
		super();
		addPoint(new Point(50, 50));
		
		nameLabel = new Label();
		
		nameLabel.setLabelAlignment(PositionConstants.MIDDLE);
		add(nameLabel, new MidpointLocator(this, 0));
	}

	/**
	 * Figure 에 표시할 이름을 설정한다.
	 * @param name Figure 에 표시할 이름
	 */
	public void setName(String name) {
		if(name == null) {
			return;
		}
				
		int len = name.length();
		FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
		Font font = new Font(null, fd);
		nameLabel.setFont(font);
//		int n = TextUtilities.INSTANCE.getLargestSubstringConfinedTo(name, nameLabel.getFont(), 80);
		int n = TextUtilities.INSTANCE.getLargestSubstringConfinedTo(name, font, 80);
//		Dimension size = TextUtilities.INSTANCE.getTextExtents(nameLabel.getText(), nameLabel.getFont());

		String text;
		
		if(n < len){
			text = String.format("%s...", name.substring(0,n));
		}
		else {
			text = name;
		}
		nameLabel.setText(text);
	}
	
	/**
	 * 이름을 표시할 Label 을 반환한다.
	 * @return 이름을 표시할 Label
	 */
	public Label getLabelFigure() {
		return nameLabel;
	}
	
	@Override
	public void setParent(IFigure p) {
		super.setParent(p);
		
	}
	public void paintFigure(Graphics graphics) {
		int init = graphics.getAntialias();
		graphics.setAntialias(SWT.ON);
		super.paintFigure(graphics);
		graphics.setAntialias(init);
	}
}
