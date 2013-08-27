
package kr.re.etri.tpl.diagram.outline;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.properties.sources.ParameterPropertySource;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Parameter �� ���� TreeEditPart Ŭ�����̴�.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class ParameterTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * Parameter �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model Parameter instance
	 */
	public ParameterTreeEditPart(Parameter model) {
		super(model);
	}
	
	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof Parameter) {
				return new ParameterPropertySource((Parameter)obj);
			}
		}
		return super.getAdapter(key);
	}
	
	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return Parameter ��
	 * 
	 * @Override
	 */
	protected Parameter getCastedModel() {
		return (Parameter) getModel();
	}

	/**
	 * EditPart ���� �ڽ� �𵨵��� ��ȯ�Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}


	/**
	 * baseImage �� �⺻���� ���� ���¿� ���� Image �� ���� �����Ѵ�. 
	 * @param shape ��
	 * @return Image
	 * 
	 * @Override
	 */
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.PARAM_16).createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof Parameter) {
			GC imageGc = new GC(image);

			Pattern bgPattern = new Pattern(image.getDevice(), boundingRect.x, boundingRect.y,
					boundingRect.width, boundingRect.height, 
					ColorConstants.white, 0x00, ColorConstants.white, 0x00);

			imageGc.setBackgroundPattern(bgPattern);

			imageGc.drawImage(baseImage, 0, 0);

			if(shape.isIncluded()) {
				FontData []fd = new FontData[]{new FontData("Candara", 8, SWT.BOLD)};
				Font font = new Font(null, fd);
				imageGc.setFont(font);

				imageGc.setForeground(readOnlyColor);
				imageGc.drawString("R", 1, 5);
			}

			imageGc.setBackgroundPattern(null);

			bgPattern.dispose();
			imageGc.dispose();
		}

		return image;
	}

	/**
	 * Tree Node �� ǥ�õ� �̸��� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 * 
	 * @Override
	 */
	protected String getText() {
		Parameter param =  getCastedModel();
		return String.format("%s %s", param.getType(), param.getName());
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		// this will cause an invocation of getImage() and getText(), see below
		this.refreshChildren();
		this.refreshVisuals();
	}
}