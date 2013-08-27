package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.diagram.commands.SetListValueCommand;
import kr.re.etri.tpl.diagram.commands.SetValueCommand;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.IPropertySource;

public class ItemElementPropertyGroup extends ViewForm {
	Label nameLabel;
	Text nameText;

	Label descLabel;
	Text descText;
	
	private Object model;
	
	private Adapter modelMonitor;
	
	protected ItemProviderAdapter itemProvider;

	private CommandStack commandStack;

	private IPropertySource propertySource;
	
	private Point minSize;
	
//	private RTMProblemLogger problemLogger;

	Listener listener;
	int[] events = new int[] {
			SWT.Dispose,
			SWT.Resize,  
			SWT.FocusIn,
			SWT.FocusOut,
			SWT.Activate,
			SWT.Deactivate};

	public ItemElementPropertyGroup(Composite parent, int style) {
		super(parent, style);

		minSize = new Point(0, 0);
		
		listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Dispose: onDispose(); break;
					case SWT.Resize: onResize(); break;
					case SWT.Activate: onActivate(); break;
					case SWT.Deactivate: onDeactivate(); break;
				}
			}
		};

		for (int i = 0; i < events.length; i++) {
			addListener(events[i], listener);
		}

	}

	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	public CommandStack getCommandStack() {
		return this.commandStack;
	}
	
	public void setPropertySource(IPropertySource propertySource) {
		this.propertySource = propertySource;
	}
	
	public IPropertySource getPropertySource() {
		return this.propertySource;
	}

	public void createControl() {
		createFormArea();

		hookControl();
	}
	
	protected void createFormArea() {
		Rectangle nameLabelRt, nameTextRt;
		Rectangle descLabelRt, descTextRt;

		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name");
		nameLabelRt = new Rectangle(0, 3, 80, 20);
		nameLabel.setBounds(nameLabelRt);

		nameText = new Text(this, SWT.BORDER);
		nameTextRt = new Rectangle(
				nameLabelRt.x + nameLabelRt.width + 10,
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
				40);
		descText.setBounds(descTextRt);

		int width = descTextRt.x + descTextRt.width + 5;
		int height = descTextRt.y + descTextRt.height + 5;
//		int width = nameTextRt.x + nameTextRt.width + 5;
//		int height = nameTextRt.y + nameTextRt.height + 5;
		setMinSize(width, height);
	}

	protected void hookControl() {
		modelMonitor = new Adapter(){

			@Override
			public Notifier getTarget() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isAdapterForType(Object type) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void notifyChanged(Notification notification) {
				ItemElementPropertyGroup.this.notifyChanged(notification);
			}

			@Override
			public void setTarget(Notifier newTarget) {
				// TODO Auto-generated method stub
				
			}
			
		};

		nameText.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof ItemElement)) {
					return;
				}

				ItemElement item = (ItemElement)getModel();
				if(nameText.getText().equals(item.getName()) == false) {
					int featureId = TaskModelPackage.ITEM_ELEMENT__NAME;
					Object newValue = nameText.getText();
					setValue(featureId, newValue, "");
				}
			}
		});

		nameText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int keyCode = e.keyCode;

				if((keyCode & ~SWT.KEY_MASK) != 0) {
					if(keyCode != SWT.KEYPAD_CR) {
						return;
					}
				}
				if(keyCode != SWT.KEYPAD_CR && (keyCode & SWT.KEY_MASK) != 0x0D && (keyCode & SWT.KEY_MASK) != 0x0A) {
					return;
				}

				if(!(getModel() instanceof ItemElement)) {
					return;
				}

				ItemElement item = (ItemElement)getModel();
				if(nameText.getText().equals(item.getName()) == false) {
					int featureId = TaskModelPackage.ITEM_ELEMENT__NAME;
					Object newValue = nameText.getText();
					setValue(featureId, newValue, "");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

		});

		descText.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof ItemElement)) {
					return;
				}

				ItemElement item = (ItemElement)getModel();
				// KJH 20101202 s, description==null 일 때 isDirty가 set되는 현상 수정
				String desc = item.getDescription() != null ? item.getDescription() : "";
				if(descText.getText().equals(desc) == false) {
//				if(descText.getText().equals(item.getDescription()) == false) {
				// KJH 20101202 e, description==null 일 때 isDirty가 set되는 현상 수정
					int featureId = TaskModelPackage.ITEM_ELEMENT__DESCRIPTION;
					Object newValue = descText.getText();
					setValue(featureId, newValue, "");
				}
			}
		});
/*		
		descText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.keyCode;
				
				if((keyCode & ~SWT.KEY_MASK) != 0) {
					if(keyCode != SWT.KEYPAD_CR) {
						return;
					}
				}
				if(keyCode != SWT.KEYPAD_CR && (keyCode & SWT.KEY_MASK) != 0x0D && (keyCode & SWT.KEY_MASK) != 0x0A) {
					return;
				}

				IPropertySource propertySource;
				ItemPropertySheetEntry entry;
				entry = (ItemPropertySheetEntry)descText.getData();
				if(entry == null) {
					return;
				}
				
				propertySource = entry.getPropertySource(0);
				String oldText;
				oldText = (String)propertySource.getPropertyValue(Integer.valueOf(TaskModelPackage.MODEL_ELEMENT__DESCRIPTION));
				if(oldText == null || oldText.equals(descText.getText()) == false) {
					propertySource.setPropertyValue(
							Integer.valueOf(TaskModelPackage.MODEL_ELEMENT__DESCRIPTION),
							descText.getText());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
*/
	}

	protected void hookIntoModel(EObject model) {
		if(model != null) {
			if(model.eAdapters().contains(modelMonitor) == false) {
				model.eAdapters().add(modelMonitor);
			}
		}
	}
	
	protected void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(modelMonitor);
		}
	}

	public void setModel(Object model) {
	
		unhookFromModel((EObject)getModel());
		Object oldModel = this.model;
		this.model = model;
		hookIntoModel((EObject)model);

		if(model instanceof ItemElement) {
			
			ItemElement item = (ItemElement) model;
			nameText.setText((item.getName() != null) ? item.getName() : "");
			descText.setText((item.getDescription() != null) ? item.getDescription() : "");

			boolean enabled = ((ItemElement)model).isIncluded() == false;
			nameText.setEditable(enabled);
			descText.setEditable(enabled);

//			IWorkbench wb = PlatformUI.getWorkbench();
//			IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
//			IWorkbenchPage wp = wbw.getActivePage();
//			IEditorPart editPart= wp.getActiveEditor();
//
//			if(!(editPart instanceof RTMDiagramEditor)) {
//				problemLogger = null;
//				return;
//			}
//
//			IEditorInput editorInput = editPart.getEditorInput();
//			IFile contentFile = ((FileEditorInput)editorInput).getFile();
//			
//			problemLogger = new RTMProblemLogger(contentFile, RTMUtil.getModelPath((ItemElement)model));
		}
	}
	
	public Object getModel() {
		return this.model;
	}
//	
//	public RTMProblemLogger getLogger() {
//		return this.problemLogger;
//	}

	protected void onDispose() {

		nameLabel.dispose();
		nameText.dispose();

		descLabel.dispose();
		descText.dispose();
		
		for (int i = 0; i < events.length; i++) {
			removeListener(events[i], listener);
		}
	}

	protected void onResize() {
		Point size = getSize();
		Rectangle nameTextRt;
		Rectangle descTextRt;

		nameTextRt = nameText.getBounds();
		nameTextRt.width = size.x - nameTextRt.x;
		nameText.setBounds(nameTextRt);

		descTextRt = descText.getBounds();
		descTextRt.width = size.x - descTextRt.x;
		descText.setBounds(descTextRt);
	}

	private void onActivate() {
		if(model instanceof EObject) {
			hookIntoModel((EObject)model);
		}
	}
	
	private void onDeactivate() {
		if(model instanceof EObject) {
			unhookFromModel((EObject)model);
		}
	}
	
	protected void setValue(int featureId, Object newValue, String label) {
		IPropertySource propertySource = getPropertySource();
		setValue(featureId, newValue, propertySource, label);
	}
	
	protected void setValue(int featureId, Object newValue, IPropertySource propertySource, String label) {
		CommandStack commandStack = getCommandStack();
		if(propertySource != null && commandStack != null) {
			SetValueCommand cmd = new SetValueCommand(label);
			cmd.setTarget(propertySource);
			cmd.setPropertyId(featureId);
			cmd.setPropertyValue(newValue);

			commandStack.execute(cmd);
		}
	}

	protected void setValue(PropertyContainer propety, String label) {
		setValue(propety.getFeatureId(), propety, label);
	}

	protected void setValue(PropertyContainer propety, IPropertySource propertySource, String label) {
		setValue(propety.getFeatureId(), propety, propertySource, label);
	}
	
	protected void setListValue(int featureId, Object newValue, String label) {
		IPropertySource propertySource = getPropertySource();
		setListValue(featureId, newValue, propertySource, label);
	}
	
	protected void setListValue(int featureId, Object newValue, IPropertySource propertySource, String label) {
		CommandStack commandStack = getCommandStack();
		if(propertySource != null && commandStack != null) {
			SetListValueCommand cmd = new SetListValueCommand(label);
			cmd.setTarget(propertySource);
			cmd.setPropertyId(featureId);
			cmd.setPropertyValue(newValue);

			commandStack.execute(cmd);
		}
	}

	protected void setListValue(PropertyContainer propety, String label) {
		setListValue(propety.getFeatureId(), propety, label);
	}

	protected void setListValue(PropertyContainer propety, IPropertySource propertySource, String label) {
		setListValue(propety.getFeatureId(), propety, propertySource, label);
	}
/*
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
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
*/
//	protected void valueChanged(int featureId, Object newValue, Object oldValue);
//	protected void valueAdded(int featureId, Object newValue);
//	protected void valueRemoved(int featureId, Object oldValue);
	
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				String newName = (String)notification.getNewValue();
				if(nameText.getText().equals(newName) == false) {
					nameText.setText(newName);
				}
				break;
			case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
				String newDesc = (String)notification.getNewValue();
				if(nameText.getText().equals(newDesc) == false) {
					descText.setText(newDesc);
				}
				break;
//			default:
//				super.valueChanged(featureId);
//				break;
			}
			break;
		case Notification.ADD:
			break;
		case Notification.ADD_MANY:
			break;
		case Notification.REMOVE:
			break;
		case Notification.REMOVE_MANY:
			break;
		}
	}

	protected void setMinSize(int width, int height) {
		if(minSize.x < width) {
			minSize.x = width;
		}
		if(minSize.y < height) {
			minSize.y = height;
		}
	}

	public Point getMinSize() {
		return minSize;
	}
}
