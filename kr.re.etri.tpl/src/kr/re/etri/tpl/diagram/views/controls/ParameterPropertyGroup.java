package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ParameterPropertyGroup extends ItemElementPropertyGroup {

	private Combo typeCombo;

	public ParameterPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		Rectangle nameLabelRt, nameTextRt;
		Rectangle descLabelRt, descTextRt;
		Rectangle typeComboRt;
		Rectangle rect1, rect2;

		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name");
		nameLabelRt = new Rectangle(0, 3, 80, 20);
		nameLabel.setBounds(nameLabelRt);

		typeCombo = new Combo(this, SWT.BORDER);
		typeComboRt = new Rectangle(
				nameLabelRt.x + nameLabelRt.width + 10,
				nameLabelRt.y,
				80,
				20);
		typeCombo.setBounds(typeComboRt);

		nameText = new Text(this, SWT.BORDER);
		nameTextRt = new Rectangle(
				typeComboRt.x + typeComboRt.width + 3,
				nameLabelRt.y,
				150,
				20);
		nameText.setBounds(nameTextRt);

		descLabel = new Label(this, SWT.NONE);
		descLabel.setText("Description");
		descLabelRt = new Rectangle(
				nameLabelRt.x,
				nameLabelRt.y + nameLabelRt.height + 5,
				80,
				20);
		descLabel.setBounds(descLabelRt);

		descText = new Text(this, SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		descTextRt = new Rectangle(descLabelRt.x + descLabelRt.width + 10,
				descLabelRt.y,
				150,
				60);
		descText.setBounds(descTextRt);

		int width = descTextRt.x + descTextRt.width + 5;
		int height = descTextRt.y + descTextRt.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		typeCombo.dispose();
	}

	protected void onResize() {
		super.onResize();
	}

	protected void hookControl() {
		super.hookControl();

		typeCombo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof Parameter)) {
					return;
				}

				Parameter item = (Parameter)getModel();
				if(typeCombo.getText().equals(item.getType()) == false) {

					int featureId = TaskModelPackage.PARAMETER__TYPE;
					RTMType type = RTMType.get(typeCombo.getText().trim());
					if(type == null) {
						typeCombo.setText(((Parameter)getModel()).getType());
						return;
					}

					setValue(featureId, type.getName(), "");
				}
			}
		});

		typeCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
			}
			
		});
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof Parameter) {
			Parameter param = (Parameter)model;

			boolean enabled = param.isIncluded() == false;
			
			typeCombo.setItems(RTMType.getInputTypes());

			String value;
			value = param.getType();
			typeCombo.setText(value != null ? value : "");

			typeCombo.setEnabled(enabled);
		}
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.PARAMETER__TYPE:
				setModel(getModel());
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
