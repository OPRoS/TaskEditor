package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.diagram.commands.ReplaceWithListCommand;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class WithPropertyGroup extends BlockElementPropertyGroup {
	Label prioLabel;
	Combo prioCombo;
	
	Label cycleLabel;
	Text cycleText;

	public WithPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect1, rect2;

		// KJH 20110527 s, priority
		rect1 = nameText.getBounds();
		rect1.width -= 85; 
		nameText.setBounds(rect1);
		rect2 = rect1;
		
		prioCombo = new Combo(this, SWT.BORDER);
		rect1 = new Rectangle(rect2.x+rect2.width+5, rect2.y, 80, rect2.height);
		prioCombo.setBounds(rect1);
		// KJH 20110527 e, priority
		
		Rectangle cycleLabelRt, cycleTextRt;
		
		rect1 = stmtLabel.getBounds();
		rect2 = stmtText.getBounds();
		
		cycleLabelRt = new Rectangle(
				rect1.x,
				rect2.y + rect2.height + 5,
				80,
				20);
		cycleLabel = new Label(this, SWT.NONE);
		cycleLabel.setText("Cycle");
		cycleLabel.setBounds(cycleLabelRt);
		
		cycleTextRt = new Rectangle(
				rect2.x,
				cycleLabelRt.y,
				rect2.width,
				20);
		cycleText = new Text(this, SWT.BORDER);
		cycleText.setBounds(cycleTextRt);

		int width = cycleTextRt.x + cycleTextRt.width + 5;
		int height = cycleTextRt.y + cycleTextRt.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		if (prioLabel != null)	prioLabel.dispose();
		if (prioCombo != null)	prioCombo.dispose();
		if (cycleLabel != null)	cycleLabel.dispose();
		if (cycleText != null)	cycleText.dispose();
	}

	protected void onResize() {
		super.onResize();
		
		Point size = getSize();
		Rectangle textRt, comboRt;

		textRt = nameText.getBounds();
		textRt.width = size.x - textRt.x - 85;
		nameText.setBounds(textRt);
		
		comboRt = prioCombo.getBounds();
		comboRt.x = textRt.x + textRt.width + 5;
		prioCombo.setBounds(comboRt);
		
		textRt = cycleText.getBounds();
		textRt.width = size.x - textRt.x;
		cycleText.setBounds(textRt);
	}

	protected void hookControl() {
		super.hookControl();

		prioCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!(getModel() instanceof WithElement)) {
					return;
				}
				
				// KJH 20110527 s, withs 순서 재배치
				WithElement with = (WithElement)getModel();
				ConnectorElement connector = (ConnectorElement)with.getParent();
				int newIndex = prioCombo.getSelectionIndex();
				
				if (newIndex > -1 && newIndex != connector.getWiths().indexOf(with)) {
					ReplaceWithListCommand cmd = new ReplaceWithListCommand(
							connector.getWiths(), newIndex, with);
					getCommandStack().execute(cmd);
				}
				// KJH 20110527 e, withs 순서 재배치
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		// KJH 20110727 s,
		cycleText.addFocusListener(new TextFocusAdapter(cycleText) {
			@Override
			public void focusLost(FocusEvent e) {
				if ((getModel() instanceof WithElement) == false || isDirty() == false) {
					return;
				}
				
				try {
					int newValue = Integer.parseInt(cycleText.getText());
					setValue(TaskModelPackage.WITH_ELEMENT__CYCLE, newValue, "");
				} catch(NumberFormatException ex) {
				}
			}
		});	// KJH 20110727 e,
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof WithElement) {
			WithElement with = (WithElement)model;

			if (with.getParent() instanceof ConnectorElement) {
				ConnectorElement connector = (ConnectorElement)with.getParent();
				prioCombo.removeAll();
				for (int i=0; i<connector.getWiths().size(); i++) {
					prioCombo.add(Integer.toString(i+1));
				}

				int prio = connector.getWiths().indexOf(with);
				if (prio == -1) {
					prio = 0;
				}
				
				prioCombo.select(prio);
			}
			
			cycleText.setText(Integer.toString(with.getCycle()));
		}
	}
}
