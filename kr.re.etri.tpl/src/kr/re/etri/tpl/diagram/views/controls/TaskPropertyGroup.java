package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.widgets.Composite;

public class TaskPropertyGroup extends BlockElementPropertyGroup {
	private static Logger logger = Logger.getLogger(TaskPropertyGroup.class);

//	Label initialLabel;
//	Combo initialTask;

	public TaskPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();
		logger.debug(this.getLayout());
//		Rectangle stmtLabelRt, stmtTextRt;
//		Rectangle initialLabelRt, initialComboRt;
//
//		stmtLabelRt = stmtLabel.getBounds();
//		stmtTextRt = stmtText.getBounds();
//		
//		initialLabel = new Label(this, SWT.NONE);
//		initialLabel.setText("Initial Bhv");	// KJH 20101017, Inital -> Initial
//		initialLabelRt = new Rectangle(stmtLabelRt.x,
//				stmtTextRt.y + stmtTextRt.height + 5,
//				80, 20);
//		initialLabel.setBounds(initialLabelRt);
//		
//		initialTask = new Combo(this, SWT.BORDER);
//		initialComboRt = new Rectangle(stmtTextRt.x,
//				initialLabelRt.y,
//				stmtTextRt.width, 20);
//		initialTask.setBounds(initialComboRt);
//
//		int width = initialLabelRt.x + initialLabelRt.width + 5;
//		int height = initialLabelRt.y + initialLabelRt.height + 5;
//		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

//		initialLabel.dispose();
//		initialTask.dispose();

	}

	protected void onResize() {
		super.onResize();

//		Point size = getSize();
//
//		Rectangle initialComboRt;
//
//		initialComboRt = initialTask.getBounds();
//
//		initialComboRt.width = size.x - initialComboRt.x;
//		initialTask.setBounds(initialComboRt);
		
	}

	protected void hookControl() {
		super.hookControl();

//		initialTask.addFocusListener(new FocusListener(){
//
//			@Override
//			public void focusGained(FocusEvent e) {
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(!(getModel() instanceof TaskElement)) {
//					return;
//				}
//
//				TaskElement task = (TaskElement)getModel();
//				if(initialTask.getText().equals(task.getInitialTask()) == false) {
//					
//					ModelDiagram rootModel = (ModelDiagram)TPLUtil.getRootModel((ItemElement)getModel());
//
//					List<ItemElement> itemList;
//					itemList = TPLUtil.getAllTaskElements(rootModel);
//					
//					TPLUtil.sortList(itemList);
//
//					boolean exists = false;
//					ItemElement selectedItem = null;
//					for(ItemElement item : itemList) {
//						if (item instanceof BehaviorElement) {
//							if(((BehaviorElement)item).getParams().size() > 0) {
//								continue;
//							}
//						}
//						else if (item instanceof ConnectorElement) {	// KJH 20110816
//							if (((ConnectorElement)item).getParams().size() > 0) {
//								continue;
//							}
//						}
//						if(item.getName().equals(initialTask.getText())) {
//							exists = true;
//							selectedItem = item;
//						}
//					}
//
//					if(exists) {
//						int featureId = TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK;
//						Object newValue = selectedItem;
//						setValue(featureId, newValue, "");
//					}
//				}
//			}
//		});
//
//		initialTask.addSelectionListener(new SelectionListener() {
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//			}
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//			}
//			
//		});
	}

	public void setModel(Object model) {
		super.setModel(model);

//		if(model instanceof TaskElement) {
//			TaskElement worker = (TaskElement)model;
//
//			initialTask.removeAll();
//
//			if(worker.isIncluded()) {
//				initialTask.setEnabled(false);
//				// KJH 20110419 s,
//				initialTask.setText((worker.getInitialTask() != null) ?
//						worker.getInitialTask().getName() : "");
//				// KJH 20110419 e,
//			}
//			else {
//				initialTask.setEnabled(true);
//				ModelDiagram rootModel = (ModelDiagram)TPLUtil.getRootModel((ItemElement)model);
//
//				List<ItemElement> itemList;
//				itemList = TPLUtil.getAllTaskElements(rootModel);
//				
//				TPLUtil.sortList(itemList);
//				logger.debug(itemList.size());
//				logger.debug(itemList);
//				for(ItemElement item : itemList) {
//					if (item instanceof BehaviorElement) {
//						if(((BehaviorElement)item).getParams().size() > 0) {
//							continue;
//						}
//					}
//					else if (item instanceof ConnectorElement) {	// KJH 20110816
//						if (((ConnectorElement)item).getParams().size() > 0) {
//							continue;
//						}
//					}
//					initialTask.add(item.getName());
//				}
//			}
//			// KJH 20110419 s,
//			initialTask.setText((worker.getInitialTask() != null) ?
//					worker.getInitialTask().getName() : "");
//			// KJH 20110419 e,
//		}
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
//			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
//				if (notification.getNewValue() instanceof ItemElement) {
//					ItemElement item = (ItemElement)notification.getNewValue();
//					if(initialTask.getText().equals(item.getName()) == false) {
//						initialTask.setText(item.getName());
//					}
//				}
//				break;
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
