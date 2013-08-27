package kr.re.etri.tpl.script.editors.highlighting;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class TPLColorManager {

	private Map<RGB,Color> colorMap = new HashMap<RGB,Color>(10);

	public void dispose() {
		for(Color next : colorMap.values()){
			next.dispose();
		}
	}
	public Color getColor(RGB rgb) {
		Color color = (Color) colorMap.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorMap.put(rgb, color);
		}
		return color;
	}
}
