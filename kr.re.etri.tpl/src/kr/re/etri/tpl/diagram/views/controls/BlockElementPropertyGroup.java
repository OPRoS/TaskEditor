package kr.re.etri.tpl.diagram.views.controls;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BlockElementPropertyGroup extends ItemElementPropertyGroup {
	Label stmtLabel;
	Text stmtText;

	public BlockElementPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect1, rect2;
		Rectangle descLabelRt, descTextRt;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();

		stmtLabel = new Label(this, SWT.CENTER);
		stmtLabel.setText("Body");
		rect1 = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				30, 20);
		stmtLabel.setBounds(rect1);
		rect2 = rect1;

		stmtText = new Text(this, SWT.V_SCROLL|SWT.BORDER);
		rect1 = new Rectangle(descTextRt.x,	rect2.y,
				60, 60);
		stmtText.setBounds(rect1);
		rect2 = rect1;
		
		nameText.setEnabled(false);

		int width = rect2.x + rect2.width + 5;
		int height = rect2.y + rect2.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		stmtLabel.dispose();
		stmtText.dispose();
	}

	protected void onResize() {
		super.onResize();
		
		Point size = getSize();
		Rectangle stmtTextRt;

		stmtTextRt = stmtText.getBounds();
		stmtTextRt.width = size.x - stmtTextRt.x;
		stmtText.setBounds(stmtTextRt);
	}

	protected void hookControl() {
		super.hookControl();

		stmtText.addFocusListener(new TextFocusAdapter(stmtText) {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof BlockElement) || !isDirty()) {
					return;
				}
				
				BlockElement block = (BlockElement)getModel();
				List<String> oldValue = new ArrayList<String>();
				List<String> newValue = new ArrayList<String>();

				oldValue.addAll(block.getStatements());
				String[] texts = stmtText.getText().split("\r\n");
				for (String text : texts) {
					newValue.add(text);
				}

				PropertyContainer property = new PropertyContainer(
						PropertyContainer.REPLACEALL,
						TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT,
						newValue,
						oldValue);
				setListValue(property, "");
			}
		});

	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof BlockElement) {
			BlockElement block = (BlockElement)model;

			List<String> strList = block.getStatements();
			StringBuilder sb = new StringBuilder();
			if(strList.size() > 0){
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}
			}
			stmtText.setText(sb.toString());
		}
	}

}
