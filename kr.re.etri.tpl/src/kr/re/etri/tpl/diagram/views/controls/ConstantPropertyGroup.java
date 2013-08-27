package kr.re.etri.tpl.diagram.views.controls;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ConstantPropertyGroup extends ItemElementPropertyGroup {

	private Combo typeCombo;

	private Label labelStmt;
	private Text textStmt;

	public ConstantPropertyGroup(Composite parent, int style) {
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

//-----------------------------------------------------------------------------

		labelStmt = new Label(this, SWT.LEFT);
		labelStmt.setText("Initialize");
		rect1 = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				80, 20);
		labelStmt.setBounds(rect1);
		rect2 = rect1;

		textStmt = new Text(this, SWT.BORDER);
		rect1 = new Rectangle(
				descTextRt.x,
				rect2.y,
				30, 80);
		textStmt.setBounds(rect1);
		rect2 = rect1;

		int width = rect2.x + rect2.width + 5;
		int height = rect2.y + rect2.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelStmt.dispose();
		textStmt.dispose();
	}

	protected void onResize() {
		super.onResize();
		
		Point size = getSize();
		Rectangle stmtTextRt;

		stmtTextRt = textStmt.getBounds();
		stmtTextRt.width = size.x - stmtTextRt.x;
		textStmt.setBounds(stmtTextRt);
	}

	protected void hookControl() {
		super.hookControl();

		typeCombo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof Constant)) {
					return;
				}

				Constant item = (Constant)getModel();
				if(typeCombo.getText().equals(item.getType()) == false) {
					RTMType type = RTMType.get(typeCombo.getText().trim());
					if(type == null) {
						typeCombo.setText(((Constant)getModel()).getType());
						return;
					}
					
					// KJH 20100916, type 갱신 안되던 문제 수정
					PropertyContainer prop;
					int featureId = TaskModelPackage.CONSTANT__TYPE;
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

		textStmt.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof Constant)) {
					return;
				}
				
				Constant constItem = (Constant)getModel();
				constItem.setInitValue(textStmt.getText());
			}
		});
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof Constant) {
			Constant constItem = (Constant)model;

			boolean enabled = constItem.isIncluded() == false;
			
			typeCombo.setItems(RTMType.getInputTypes());

			String value;
			value = constItem.getType();
			typeCombo.setText(value != null ? value : "");

			typeCombo.setEnabled(enabled);
			
			value = constItem.getInitValue();
			textStmt.setText(value != null ? value : "");
			
			textStmt.setEnabled(enabled);

		}
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.CONSTANT__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.CONSTANT__INIT_VALUE:
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
