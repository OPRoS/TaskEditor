package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Direction;
import kr.re.etri.tpl.taskmodel.Symbol;
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

public class SymbolPropertyGroup extends ItemElementPropertyGroup {

	private Combo dirCombo;

	private Combo typeCombo;

	public SymbolPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		Rectangle nameLabelRt, nameTextRt;
		Rectangle descLabelRt, descTextRt;
		Rectangle dirComboRt, typeComboRt;

		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name");
		nameLabelRt = new Rectangle(0, 3, 80, 20);
		nameLabel.setBounds(nameLabelRt);

		dirCombo = new Combo(this, SWT.BORDER);
		dirComboRt = new Rectangle(
				nameLabelRt.x + nameLabelRt.width + 10,
				nameLabelRt.y,
				50,
				20);
		dirCombo.setBounds(dirComboRt);

		typeCombo = new Combo(this, SWT.BORDER);
		typeComboRt = new Rectangle(
				dirComboRt.x + dirComboRt.width + 10,
				dirComboRt.y,
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

		dirCombo.dispose();
		typeCombo.dispose();
	}

	protected void onResize() {
		super.onResize();
	}

	protected void hookControl() {
		super.hookControl();

		dirCombo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof Symbol)) {
					return;
				}

				Symbol item = (Symbol)getModel();
				if(dirCombo.getText().equals(item.getType()) == false) {

					int selIdx = dirCombo.getSelectionIndex();
					if(0 <= selIdx) {
						Direction dir = Direction.get(selIdx);
						PropertyContainer prop;
						int featureId = TaskModelPackage.SYMBOL__DIRECTION;
						prop = new PropertyContainer(PropertyContainer.SET, featureId, dir, item.getDirection());
						setValue(prop, "");
					}
				}
			}
		});

		dirCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
			}
			
		});

		typeCombo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof Symbol)) {
					return;
				}

				Symbol item = (Symbol)getModel();
				if(typeCombo.getText().equals(item.getType()) == false) {
					RTMType type = RTMType.get(typeCombo.getText().trim());
					
					if(type == null) {
						typeCombo.setText(item.getType());
						return;
					}

					PropertyContainer prop;
					int featureId = TaskModelPackage.SYMBOL__TYPE;
					prop = new PropertyContainer(PropertyContainer.SET, featureId, type.getName(), item.getType());
					setValue(prop, "");
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

		if(model instanceof Symbol) {
			Symbol symbol = (Symbol)model;
			String value;

			boolean enabled = symbol.isIncluded() == false;

			dirCombo.removeAll();
			for(Direction dir : Direction.VALUES) {
				dirCombo.add(dir.getName());
			}
			Direction dir = symbol.getDirection();
			if(dir != null) {
				value = dir.getName();
				dirCombo.setText(value != null ? value : "");
			}
			dirCombo.setEnabled(enabled);

			typeCombo.removeAll();
			typeCombo.setItems(RTMType.getInputTypes());

			value = symbol.getType();
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
			case TaskModelPackage.SYMBOL__DIRECTION:
			case TaskModelPackage.SYMBOL__TYPE:
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
