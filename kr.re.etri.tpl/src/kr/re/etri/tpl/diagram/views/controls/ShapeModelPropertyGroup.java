package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.diagram.views.ItemPropertySheetEntry;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.IPropertySource;

public class ShapeModelPropertyGroup extends ItemElementPropertyGroup {

	Label labelX;
	Text textX;
	Label labelY;
	Text textY;
	Label labelWidth;
	Text textWidth;
	Label labelHeight;
	Text textHeight;
	
	boolean visible = false;

	public ShapeModelPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect1, rect2;
		Rectangle descLabelRt, descTextRt;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();

		labelX = new Label(this, SWT.CENTER);
		labelX.setText("X");
		rect1 = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				30, 20);
		labelX.setBounds(rect1);
		rect2 = rect1;
		
		textX = new Text(this, SWT.BORDER);
		rect1 = new Rectangle(
				rect2.x + rect1.width + 5,
				rect2.y,
				30, 20);
		textX.setBounds(rect1);
		rect2 = rect1;
		
		labelY = new Label(this, SWT.CENTER);
		labelY.setText("Y");
		rect1 = new Rectangle(
				rect2.x + rect1.width + 10,
				rect2.y,
				30, 20);
		labelY.setBounds(rect1);
		rect2 = rect1;
		
		textY = new Text(this, SWT.BORDER);
		rect1 = new Rectangle(
				rect2.x + rect1.width + 5,
				rect2.y,
				30, 20);
		textY.setBounds(rect1);
		rect2 = rect1;
		
		labelWidth = new Label(this, SWT.CENTER);
		labelWidth.setText("Width");
		rect1 = new Rectangle(
				rect2.x + rect1.width + 10,
				rect2.y,
				40, 20);
		labelWidth.setBounds(rect1);
		rect2 = rect1;
		
		textWidth = new Text(this, SWT.BORDER);
		rect1 = new Rectangle(
				rect2.x + rect1.width + 5,
				rect2.y,
				30, 20);
		textWidth.setBounds(rect1);
		rect2 = rect1;
		
		labelHeight = new Label(this, SWT.CENTER);
		labelHeight.setText("Height");
		rect1 = new Rectangle(
				rect2.x + rect1.width + 10,
				rect2.y,
				40, 20);
		labelHeight.setBounds(rect1);
		rect2 = rect1;
		
		textHeight = new Text(this, SWT.BORDER);
		rect1 = new Rectangle(
				rect2.x + rect1.width + 5,
				rect2.y,
				30, 20);
		textHeight.setBounds(rect1);
		rect2 = rect1;
		
		labelX.setVisible(visible);
		textX.setVisible(visible);
		labelY.setVisible(visible);
		textY.setVisible(visible);
		labelWidth.setVisible(visible);
		textWidth.setVisible(visible);
		labelHeight.setVisible(visible);
		textHeight.setVisible(visible);

//		int width = rect2.x + rect2.width + 5;
//		int height = rect2.y + rect2.height + 5;
//		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelX.dispose();
		textX.dispose();
		labelY.dispose();
		textY.dispose();
		labelWidth.dispose();
		textWidth.dispose();
		labelHeight.dispose();
		textHeight.dispose();
	}

	protected void onResize() {
		super.onResize();
	}

	protected void hookControl() {
		super.hookControl();

		textX.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				IPropertySource propertySource;
				ItemPropertySheetEntry entry;
				entry = (ItemPropertySheetEntry)textX.getData();
				if(entry == null) {
					return;
				}
				int featureId;
				featureId = ((Integer)entry.getDescriptor().getId()).intValue();
				
				propertySource = entry.getPropertySource(0);
				propertySource.setPropertyValue(
						Integer.valueOf(TaskModelPackage.SHAPE_ELEMENT__X),
						Integer.valueOf(textX.getText()));
			}
		});
		textY.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				IPropertySource propertySource;
				ItemPropertySheetEntry entry;
				entry = (ItemPropertySheetEntry)textY.getData();
				if(entry == null) {
					return;
				}
				int featureId;
				featureId = ((Integer)entry.getDescriptor().getId()).intValue();
				
				propertySource = entry.getPropertySource(0);
				propertySource.setPropertyValue(
						Integer.valueOf(TaskModelPackage.SHAPE_ELEMENT__Y),
						Integer.valueOf(textY.getText()));
			}
		});
		textWidth.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				IPropertySource propertySource;
				ItemPropertySheetEntry entry;
				entry = (ItemPropertySheetEntry)textWidth.getData();
				if(entry == null) {
					return;
				}
				int featureId;
				featureId = ((Integer)entry.getDescriptor().getId()).intValue();
				
				propertySource = entry.getPropertySource(0);
				propertySource.setPropertyValue(
						Integer.valueOf(TaskModelPackage.SHAPE_ELEMENT__WIDTH),
						Integer.valueOf(textWidth.getText()));
			}
		});
		textHeight.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				IPropertySource propertySource;
				ItemPropertySheetEntry entry;
				entry = (ItemPropertySheetEntry)textHeight.getData();
				if(entry == null) {
					return;
				}
				int featureId;
				featureId = ((Integer)entry.getDescriptor().getId()).intValue();
				
				propertySource = entry.getPropertySource(0);
				propertySource.setPropertyValue(
						Integer.valueOf(TaskModelPackage.SHAPE_ELEMENT__HEIGHT),
						Integer.valueOf(textHeight.getText()));
			}
		});
	}
	
	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof ShapeElement) {
			ShapeElement shape = (ShapeElement)model;
			textX.setText(String.valueOf(shape.getX()));
			textY.setText(String.valueOf(shape.getY()));
			textWidth.setText(String.valueOf(shape.getWidth()));
			textHeight.setText(String.valueOf(shape.getHeight()));
		}
	}
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
//				displayText = entry.getValueAsString();
//				textX.setText(displayText);
				break;
			case TaskModelPackage.SHAPE_ELEMENT__Y:
//				displayText = entry.getValueAsString();
//				textY.setText(displayText);
				break;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
//				displayText = entry.getValueAsString();
//				textWidth.setText(displayText);
				break;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
//				displayText = entry.getValueAsString();
//				textHeight.setText(displayText);
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}
