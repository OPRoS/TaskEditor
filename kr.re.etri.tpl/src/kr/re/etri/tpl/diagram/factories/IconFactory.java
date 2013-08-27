package kr.re.etri.tpl.diagram.factories;

import java.util.HashMap;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;

public class IconFactory {
	private static HashMap<Long, Image> imageMap = new HashMap<Long, Image>();
	protected static Color readOnlyColor = new Color(null, 255, 128, 64);

	public IconFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Image getImage(int featureId, int option) {
		// ��ϵ� �̹����� �ִٸ� ã�Ƽ� ��ȯ�Ѵ�.
		Image image = findImage(featureId, option);
		if(image != null) {
			return image;
		}

		// �̹����� ������ ��� �̹����� �˻��Ѵ�.
		// ��� �̹����� Model ID������ �˻��Ѵ�.
		long baseKey = (((long)featureId) & 0xFFFFFFFF)<< 32;
		Image baseImage = imageMap.get(baseKey);
		if(image == null) {
			// ���ٸ� �����Ѵ�.
			baseImage = IconFactory.getBaseImage(featureId, option);
			// Model ID�� Ű�� ��� �̹����� ����Ѵ�.
			imageMap.put(baseKey, baseImage);
		}

		// include�� ���� Read Only �̹����� �����Ѵ�.
		if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
			image = IconFactory.createCombinedImage(baseImage, readOnlyColor, "R");
		}
		else {
			image = IconFactory.createImage(baseImage);
		}
		
		return image;
	}
	
	public static Image findImage(int featureId, int option) {
		// Model ID�� Option ���� Ű�� �����Ѵ�.
		long key = (((long)featureId) & 0xFFFFFFFF)<< 32;
		
		if(featureId == TaskModelPackage.CONNECTION_ELEMENT) {
			option |= (option & ItemState.ITEM_INCLUDED);
			option |= (option & ItemState.RELATION_MASK);
		}

		key |= option;

		Image image = imageMap.get(key);
		
		return image;
	}
	
	public static void registImage(int featureId, int option, Image image) {
		// Model ID�� Option ���� Ű�� �����Ѵ�.
		long key = (((long)featureId) & 0xFFFFFFFF)<< 32;
		
		if(featureId == TaskModelPackage.CONNECTION_ELEMENT) {
			option |= (option & ItemState.ITEM_INCLUDED);
			option |= (option & ItemState.RELATION_MASK);
		}

		key |= option;

		imageMap.put(key, image);
	}

	public static Image getBaseImage(int featureId, int option) {
		Image image = null;
		if(featureId == TaskModelPackage.CONNECTION_ELEMENT) {
			if((option & ItemState.RELATION_MASK) == ItemState.TRANSITION) {
				image = TaskModelPlugin.getImageDescriptor("icons/solidopenarrow16.gif").createImage();
			}
			else if((option & ItemState.RELATION_MASK) == ItemState.TASK_CALL) {
				image = TaskModelPlugin.getImageDescriptor("icons/solidclosedarrow16.gif").createImage();
			}
			else if((option & ItemState.RELATION_MASK) == ItemState.ACTION_CALL) {
				image = TaskModelPlugin.getImageDescriptor("icons/dotopenarrow16.gif").createImage();
			}
		}
		
		if(image == null) {
			image = imageMap.get(-1L);
			if(image == null) {
				image = TaskModelPlugin.getImageDescriptor("icons/blank.gif").createImage();
				imageMap.put(-1L, image);
			}
		}
		
		return image;
	}

	public static Image createImage(Image  baseImage) {
		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		
		GC imageGc = new GC(image);

		Pattern bgPattern = new Pattern(image.getDevice(), boundingRect.x, boundingRect.y,
				boundingRect.width, boundingRect.height, 
				ColorConstants.white, 0x00, ColorConstants.white, 0x00);

		imageGc.setBackgroundPattern(bgPattern);

		imageGc.drawImage(baseImage, 0, 0);

		imageGc.setBackgroundPattern(null);

		bgPattern.dispose();
		imageGc.dispose();

		return image;
	}

	public static Image createCombinedImage(Image  baseImage, Color color, String str) {
		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		
		GC imageGc = new GC(image);

		Pattern bgPattern = new Pattern(image.getDevice(), boundingRect.x, boundingRect.y,
				boundingRect.width, boundingRect.height, 
				ColorConstants.white, 0x00, ColorConstants.white, 0x00);

		imageGc.setBackgroundPattern(bgPattern);

		imageGc.drawImage(baseImage, 0, 0);

		FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
		Font font = new Font(null, fd);
		imageGc.setFont(font);

		imageGc.setForeground(color);
		imageGc.drawString(str, 1, 5);

		imageGc.setBackgroundPattern(null);

		bgPattern.dispose();
		imageGc.dispose();

		return image;
	}
}
