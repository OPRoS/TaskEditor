package kr.re.etri.tpl.diagram.views.controls;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.properties.sources.StateActionPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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

public class ConnectionPropertyGroup extends LineElementPropertyGroup {
	private static Logger logger =
		 Logger.getLogger(ConnectionPropertyGroup.class.getName());
	
	protected Label labelSource;
	protected Label sourceName;
	protected Label labelTarget;
	protected Label targetName;
	protected Label labelExpression;
	protected Text  expressionText;
	
	protected Label priorityL;
	protected Combo priorityCombo;
	
	private Adapter stateActionMonitor;

	FocusListener focusListner = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {	}

		@Override
		public void focusLost(FocusEvent e) {
			if(!(getModel() instanceof ItemElement)) {
				return;
			}
			
			String inputStr = expressionText.getText();
			ConnectionElement conn = (ConnectionElement)getModel();
			PropertyContainer prop;

			if(RelationShip.TASK_CALL == conn.getRelationship() || RelationShip.TRANSITION == conn.getRelationship()) {
				// Transition이면 ConnectionElement의 Condition을 변경한다.
				// 문법 검사는 ConnectionElementEditPart에서 하게 된다.
				String[] strArray = inputStr.split("\r\n");
				ArrayList<String> newValue = new ArrayList<String>();
				for(String str : strArray) {
					str = str.trim();
					if(str.length() > 0) {
						newValue.add(str);
					}
				}
				ArrayList<String> oldValue = new ArrayList<String>();
				oldValue.addAll(conn.getCondition());

				int type, featureId;
				type = PropertyContainer.REPLACEALL;
				featureId = TaskModelPackage.CONNECTION_ELEMENT__CONDITION;
				prop = new PropertyContainer(type, featureId, newValue, oldValue);
				setListValue(prop, "set transition");
			}
			else if(RelationShip.ACTION_CALL == conn.getRelationship()) {
				// Action call, Task call이면 StateAction의 Statement을 변경한다.
				// 문법 검사는 StateEditPart에서 하게 된다.
				ReferElement refItem2 = (ReferElement)conn.getSource2();
				ItemElement source2 = refItem2.getRealModel();

				StateAction stateAction = null;
				if(source2 instanceof StateElement) {
					stateAction = ((StateElement)source2).getStay();
				}

				if(stateAction != null) {
					String[] strArray = inputStr.split("\r\n");
					ArrayList<String> newValue = new ArrayList<String>();
					for(String str : strArray) {
						str = str.trim();
						if(str.length() > 0) {
							newValue.add(str);
						}
					}
					ArrayList<String> oldValue = new ArrayList<String>();
					oldValue.addAll(stateAction.getStatements());
					
					int type, featureId;
					type = PropertyContainer.REPLACEALL;
					featureId = TaskModelPackage.STATE_ACTION__STATEMENTS;
					prop = new PropertyContainer(type, featureId, newValue, oldValue);
					
					StateActionPropertySource propSrc = new StateActionPropertySource(stateAction);
					setListValue(prop, propSrc, "set do");
				}
			}
		}
	};

	SelectionListener selectionListener= new SelectionListener(){

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object o = getModel();
			if(! (o instanceof ConnectionElement)){
				return;
			}
			
			ConnectionElement model = (ConnectionElement)o;

			String oldValue = model.getName();
			String newValue = priorityCombo.getText();
			
			int type, featureId;
			type = PropertyContainer.REPLACEALL;
			featureId = TaskModelPackage.ITEM_ELEMENT__NAME;
			PropertyContainer prop = new PropertyContainer(type, featureId, newValue, oldValue);
			setValue(featureId,  newValue, "");
//			setValue(prop, "set priority");
			logger.info("Set priorty value : NewValue ="+newValue+", ModelValue = "+model.getName() );				
		}
	};
	
	public ConnectionPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect,rect1, rect2;
		Rectangle descLabelRt, descTextRt;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();
		
		priorityL = new Label(this,SWT.NONE);
		priorityL.setText("Priority");
		
		rect = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				80, 20);
		priorityL.setBounds(rect);
		
		priorityCombo = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
		rect = new Rectangle(descTextRt.x, descTextRt.y+descTextRt.height+5, 80, 20);
		priorityCombo.add("",0);
		for(int i =1; i< 21 ; i++){
			priorityCombo.add(""+i,i);
		}
		priorityCombo.setBounds(rect);		
		
		priorityCombo.addSelectionListener(selectionListener);

		labelExpression = new Label(this, SWT.NONE);
		labelExpression.setText("Condition");
		rect1 = new Rectangle(descLabelRt.x, rect.y+rect.height+5,
				80, 20);
		labelExpression.setBounds(rect1);
		rect2 = rect1;

		expressionText = new Text(this, SWT.V_SCROLL|SWT.BORDER);
		
		rect1 = new Rectangle(descTextRt.x, rect1.y,
				80, 60);
		expressionText.setBounds(rect1);
		rect2 = rect1;

		labelSource = new Label(this, SWT.NONE);
		labelSource.setText("Source");
		rect1 = new Rectangle(descLabelRt.x, rect2.y+rect2.height+5,
				80, 20);
		labelSource.setBounds(rect1);
		rect2 = rect1;

		sourceName = new Label(this, SWT.VERTICAL|SWT.CENTER|SWT.BORDER);
		rect1 = new Rectangle(descTextRt.x,rect2.y,
				150, 20);
		sourceName.setBounds(rect1);
		rect2 = rect1;

		labelTarget = new Label(this, SWT.NONE);
		labelTarget.setText("Target");
		rect1 = new Rectangle(descLabelRt.x, rect2.y+rect2.height+5,
				80, 20);
		labelTarget.setBounds(rect1);
		rect2 = rect1;
		
		targetName = new Label(this, SWT.VERTICAL|SWT.CENTER|SWT.BORDER);
		rect1 = new Rectangle(descTextRt.x,rect2.y,
				150, 20);
		targetName.setBounds(rect1);
		rect2 = rect1;

		int width = rect2.x + rect2.width + 5;
		int height = rect2.y + rect2.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelSource.dispose();
		sourceName.dispose();
		labelTarget.dispose();
		targetName.dispose();
		labelExpression.dispose();
		expressionText.dispose();
		priorityL.dispose();
		priorityCombo.dispose();
		
	}

	protected void onResize() {
		super.onResize();
		Point size = getSize();
		
		Rectangle rect;
		rect = expressionText.getBounds();
		rect.width = size.x - rect.x;
		expressionText.setBounds(rect);
	}

	protected void hookControl() {
		super.hookControl();

		stateActionMonitor = new Adapter(){

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
//				ConnectionPropertyGroup.this.notifyChanged(notification);
				int type = notification.getEventType();
				int featureId = notification.getFeatureID(TaskModelPackage.class);
				switch(type) {
				case Notification.SET:
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					switch(featureId) {
					case TaskModelPackage.STATE_ACTION__STATEMENTS:
						setModel(getModel());
						break;
					}
					break;
				}
			}

			@Override
			public void setTarget(Notifier newTarget) {
				// TODO Auto-generated method stub
				
			}
		};

		expressionText.addFocusListener(focusListner);
	}

	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);

		if(getModel() instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)getModel();
			ReferElement refItem = (ReferElement)conn.getSource2();
			ItemElement realItem = refItem.getRealModel();
			
			addNotifyListener(realItem);
		}
	}

	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);

		if(getModel() instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)getModel();
			ReferElement refItem = (ReferElement)conn.getSource2();
			ItemElement realItem = refItem.getRealModel();
			
			removeNotifyListener(realItem);
		}
	}

	private void addNotifyListener(ItemElement item) {
		if(item == null) {
			return;
		}

		StateAction stateAction = null;
		if(item instanceof StateElement) {
			stateAction = ((StateElement)item).getStay();
		}
		else if(item instanceof StateElement) {
			stateAction = (StateAction)item;
		}
		if(stateAction != null) {
			stateAction.eAdapters().add(stateActionMonitor);
		}
	}

	private void removeNotifyListener(ItemElement item) {
		if(item == null) {
			return;
		}

		StateAction stateAction = null;
		if(item instanceof StateElement) {
			stateAction = ((StateElement)item).getStay();
		}
		else if(item instanceof StateElement) {
			stateAction = (StateAction)item;
		}
		if(stateAction != null) {
			stateAction.eAdapters().remove(stateActionMonitor);
		}
	}

	public void setModel(Object model) {
		super.setModel(model);

		// Connection은 이름을 편집할 수 없다.
		nameText.setEnabled(false);

		if(model instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)model;
			
			ReferElement refSource = (ReferElement)conn.getSource();
			ItemElement source = refSource.getRealModel();
			ReferElement refTarget = (ReferElement)conn.getTarget();
			ItemElement target = refTarget.getRealModel();

			if(source != null) {
				if(source.getName() != null) {
					sourceName.setText(source.getName());
				}
				else {
					sourceName.setText("");
				}
			}
			else {
				sourceName.setText("");
			}
			if(target != null) {
				if(target.getName() != null) {
					targetName.setText(target.getName());
				}
				else {
					targetName.setText("");
				}
			}
			else {
				targetName.setText("");
			}
			
			logger.info("Priority 초기화 : priority = "+conn.getName());	
			
			String priority = conn.getName();
			if(priority != null && !priority.equals("")){
				int pri = Integer.parseInt(priority);
				if(pri >0){
					priorityCombo.select(pri);
				}
			}
			else {
				priorityCombo.select(0);
			}
			
			
			if(RelationShip.TRANSITION == conn.getRelationship() ){
				nameText.setText("");

				labelExpression.setText("Condition");

				List<String> strList = conn.getCondition();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}
				expressionText.setText(sb.toString());
				
				
////////////////////////////////////////////////////////////////////////////////////////////////////
				if(source == target){
					return;
				}
				ItemElement targetEle = target;	// KJH 20110520, Refer->Item
				EList<ReferElement> rEleList = targetEle.getReferences();
				for(ReferElement refItem : rEleList){
					EList<ConnectionElement> connEList = refItem.getSourceConnections();
					logger.debug(connEList );
					for(ConnectionElement connection : connEList){
						
						ReferElement referE = (ReferElement)connection.getTarget();
						ItemElement tar22 = referE.getRealModel();
						if(source == tar22){
							int y = referE.getY();
							referE.setY(y+1);
							int x = referE.getX();
							referE.setX(x+1);
							logger.info("Move Target State.");		
							break;
						}
					}
				}
				
				ItemElement sourceEle = source;	// KJH 20110520, Refer->Item
				rEleList = sourceEle.getReferences();
				for(ReferElement refItem : rEleList){
					EList<ConnectionElement> connEList = refItem.getSourceConnections();
					logger.debug(connEList );
					int count =0;
					for(ConnectionElement connection : connEList){						
						ReferElement referE = (ReferElement)connection.getTarget();
						ItemElement tar22 = referE.getRealModel();
						if(target == tar22){
							count++;
							if(count == 2){
								int y = referE.getY();
								referE.setY(y+1);
								int x = referE.getX();
								referE.setX(x+1);
								logger.info("Move Target State.");		
								break;
							}
						}
					}
				}
////////////////////////////////////////////////////////////////////////////////////////////////////
			}
			else if(RelationShip.TASK_CALL == conn.getRelationship()) {
				nameText.setText("");

				labelExpression.setText("Condition");

				List<String> strList = conn.getCondition();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}
				expressionText.setText(sb.toString());
			}
			else if( RelationShip.ACTION_CALL == conn.getRelationship()) {
									
				labelExpression.setText("Statement");

				ReferElement refSource2 = (ReferElement)conn.getSource();
				ItemElement source2 = refSource2.getRealModel();

				StateAction stateAction = null;
				if(source instanceof StateElement) {
					stateAction = ((StateElement)source2).getStay();
				}
				
				if(stateAction != null) {
					
					List<String> strList = stateAction.getStatements();
					StringBuilder sb = new StringBuilder();
					for(String str : strList) {
						sb.append(str).append("\r\n");
					}
					expressionText.setText(sb.toString());
				}
				else {
					expressionText.setText("");
				}
			}
		}
	}

	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();
		if(notifier instanceof StateAction) {
			setModel(getModel());
			return;
		}
		
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			logger.info("Notification.SET");
			switch(featureId) {
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
				setModel(getModel());
				break;				
			case TaskModelPackage.CONNECTION_ELEMENT_FEATURE_COUNT:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			logger.info("Notification.ADD");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			logger.info("Notification.ADD_MANY");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			logger.info("Notification.REMOVE");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			logger.info("Notification.REMOVE_MANY");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}


/* action		: modify
 * editor		: mykim
 * date			: 2009.6.9.
 * description	: modify label
 * origin code  :	
 * 
package kr.re.etri.tpl.views.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import kr.re.etri.tpl.diagram.properties.sources.StateActionPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.util.PropertyContainer;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
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

public class ConnectionPropertyGroup extends LineElementPropertyGroup {
	private static Logger logger =
		 Logger.getLogger(ConnectionPropertyGroup.class.getName());

	
	protected Label labelSource;
	protected Label sourceName;
	protected Label labelTarget;
	protected Label targetName;
	protected Label labelExpression;
	protected Text  expressionText;
	
	protected Label priorityL;
	protected Combo priorityCombo;
	
	private Adapter stateActionMonitor;

	FocusListener focusListner = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(!(getModel() instanceof ItemElement)) {
				return;
			}
			
			String inputStr = expressionText.getText();
			ConnectionElement conn = (ConnectionElement)getModel();
			PropertyContainer prop;

			if(RelationShip.TRANSITION == conn.getRelationship()) {
				// Transition이면 ConnectionElement의 Condition을 변경한다.
				// 문법 검사는 ConnectionElementEditPart에서 하게 된다.
				String[] strArray = inputStr.split("\r\n");
				ArrayList<String> newValue = new ArrayList<String>();
				for(String str : strArray) {
					str = str.trim();
					if(str.length() > 0) {
						newValue.add(str);
					}
				}
				ArrayList<String> oldValue = new ArrayList<String>();
				oldValue.addAll(conn.getCondition());

				int type, featureId;
				type = PropertyContainer.REPLACEALL;
				featureId = TaskModelPackage.CONNECTION_ELEMENT__CONDITION;
				prop = new PropertyContainer(type, featureId, newValue, oldValue);
				setListValue(prop, "set transition");
			}
			else if(RelationShip.TASK_CALL == conn.getRelationship() ||
				RelationShip.ACTION_CALL == conn.getRelationship()) {
				// Action call, Task call이면 StateAction의 Statement을 변경한다.
				// 문법 검사는 StateEditPart에서 하게 된다.
				ReferElement refItem2 = (ReferElement)conn.getSource2();
				ItemElement source2 = refItem2.getRealModel();

				StateAction stateAction = null;
				if(source2 instanceof StateElement) {
					stateAction = ((StateElement)source2).getDo();
				}

				if(stateAction != null) {
					String[] strArray = inputStr.split("\r\n");
					ArrayList<String> newValue = new ArrayList<String>();
					for(String str : strArray) {
						str = str.trim();
						if(str.length() > 0) {
							newValue.add(str);
						}
					}
					ArrayList<String> oldValue = new ArrayList<String>();
					oldValue.addAll(stateAction.getStatements());
					
					int type, featureId;
					type = PropertyContainer.REPLACEALL;
					featureId = TaskModelPackage.STATE_ACTION__STATEMENTS;
					prop = new PropertyContainer(type, featureId, newValue, oldValue);
					
					StateActionPropertySource propSrc = new StateActionPropertySource(stateAction);
					setListValue(prop, propSrc, "set do");
				}
			}
		}
	};

	SelectionListener selectionListener= new SelectionListener(){

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object o = getModel();
			if(! (o instanceof ConnectionElement)){
				return;
			}
			
			ConnectionElement model = (ConnectionElement)o;

			String oldValue = model.getName();
			String newValue = priorityCombo.getText();
			
			int type, featureId;
			type = PropertyContainer.REPLACEALL;
			featureId = TaskModelPackage.ITEM_ELEMENT__NAME;
			PropertyContainer prop = new PropertyContainer(type, featureId, newValue, oldValue);
			setValue(featureId,  newValue, "");
//			setValue(prop, "set priority");
			logger.info("Set priorty value : NewValue ="+newValue+", ModelValue = "+model.getName() );				
		}
	};
	
	public ConnectionPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect,rect1, rect2;
		Rectangle descLabelRt, descTextRt;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();
		
		priorityL = new Label(this,SWT.NONE);
		priorityL.setText("Priority");
		
		rect = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				80, 20);
		priorityL.setBounds(rect);
		
		priorityCombo = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
		rect = new Rectangle(descTextRt.x, descTextRt.y+descTextRt.height+5, 80, 20);
		for(int i =1; i< 21 ; i++){
			priorityCombo.add(""+i,i-1);
		}
		priorityCombo.setBounds(rect);		
		
		priorityCombo.addSelectionListener(selectionListener);

		labelExpression = new Label(this, SWT.NONE);
		labelExpression.setText("Condition");
		rect1 = new Rectangle(descLabelRt.x, rect.y+rect.height+5,
				80, 20);
		labelExpression.setBounds(rect1);
		rect2 = rect1;

		expressionText = new Text(this, SWT.V_SCROLL|SWT.BORDER);
		
		rect1 = new Rectangle(descTextRt.x, rect1.y,
				80, 100);
		expressionText.setBounds(rect1);
		rect2 = rect1;

		labelSource = new Label(this, SWT.NONE);
		labelSource.setText("Source");
		rect1 = new Rectangle(descLabelRt.x, rect2.y+rect2.height+5,
				80, 20);
		labelSource.setBounds(rect1);
		rect2 = rect1;

		sourceName = new Label(this, SWT.VERTICAL|SWT.CENTER|SWT.BORDER);
		rect1 = new Rectangle(descTextRt.x,rect2.y,
				150, 20);
		sourceName.setBounds(rect1);
		rect2 = rect1;

		labelTarget = new Label(this, SWT.NONE);
		labelTarget.setText("Target");
		rect1 = new Rectangle(descLabelRt.x, rect2.y+rect2.height+5,
				80, 20);
		labelTarget.setBounds(rect1);
		rect2 = rect1;
		
		targetName = new Label(this, SWT.VERTICAL|SWT.CENTER|SWT.BORDER);
		rect1 = new Rectangle(descTextRt.x,rect2.y,
				150, 20);
		targetName.setBounds(rect1);
		rect2 = rect1;

		int width = rect2.x + rect2.width + 5;
		int height = rect2.y + rect2.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelSource.dispose();
		sourceName.dispose();
		labelTarget.dispose();
		targetName.dispose();
		labelExpression.dispose();
		expressionText.dispose();
		priorityL.dispose();
		priorityCombo.dispose();
		
	}

	protected void onResize() {
		super.onResize();
		Point size = getSize();
		
		Rectangle rect;
		rect = expressionText.getBounds();
		rect.width = size.x - rect.x;
		expressionText.setBounds(rect);
	}

	protected void hookControl() {
		super.hookControl();

		stateActionMonitor = new Adapter(){

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
//				ConnectionPropertyGroup.this.notifyChanged(notification);
				int type = notification.getEventType();
				int featureId = notification.getFeatureID(TaskModelPackage.class);
				switch(type) {
				case Notification.SET:
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					switch(featureId) {
					case TaskModelPackage.STATE_ACTION__STATEMENTS:
						setModel(getModel());
						break;
					}
					break;
				}
			}

			@Override
			public void setTarget(Notifier newTarget) {
				// TODO Auto-generated method stub
				
			}
		};

		expressionText.addFocusListener(focusListner);
	}

	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);

		if(getModel() instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)getModel();
			ReferElement refItem = (ReferElement)conn.getSource2();
			ItemElement realItem = refItem.getRealModel();
			
			addNotifyListener(realItem);
		}
	}

	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);

		if(getModel() instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)getModel();
			ReferElement refItem = (ReferElement)conn.getSource2();
			ItemElement realItem = refItem.getRealModel();
			
			removeNotifyListener(realItem);
		}
	}

	private void addNotifyListener(ItemElement item) {
		if(item == null) {
			return;
		}

		StateAction stateAction = null;
		if(item instanceof StateElement) {
			stateAction = ((StateElement)item).getDo();
		}
		else if(item instanceof StateElement) {
			stateAction = (StateAction)item;
		}
		if(stateAction != null) {
			stateAction.eAdapters().add(stateActionMonitor);
		}
	}

	private void removeNotifyListener(ItemElement item) {
		if(item == null) {
			return;
		}

		StateAction stateAction = null;
		if(item instanceof StateElement) {
			stateAction = ((StateElement)item).getDo();
		}
		else if(item instanceof StateElement) {
			stateAction = (StateAction)item;
		}
		if(stateAction != null) {
			stateAction.eAdapters().remove(stateActionMonitor);
		}
	}

	public void setModel(Object model) {
		super.setModel(model);

		// Connection은 이름을 편집할 수 없다.
		nameText.setEnabled(false);

		if(model instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)model;
			ReferElement refSource = (ReferElement)conn.getSource();
			ItemElement source = refSource.getRealModel();
			ReferElement refTarget = (ReferElement)conn.getTarget();
			ItemElement target = refTarget.getRealModel();
			if(source != null) {
				if(source.getName() != null) {
					sourceName.setText(source.getName());
				}
				else {
					sourceName.setText("");
				}
			}
			else {
				sourceName.setText("");
			}
			if(target != null) {
				if(target.getName() != null) {
					targetName.setText(target.getName());
				}
				else {
					targetName.setText("");
				}
			}
			else {
				targetName.setText("");
			}
			
			logger.info("Priority 초기화 : priority = "+conn.getName());	
			
			String priority = conn.getName();
			if(priority != null && !priority.equals("")){
				int pri = Integer.parseInt(priority);
				if(pri >0){
					priorityCombo.select(pri-1);
				}
			}
			else {
				priorityCombo.select(0);
			}

			if(RelationShip.TRANSITION == conn.getRelationship() ||
					RelationShip.TASK_CALL == conn.getRelationship()) {
				// TRANSITION, TASK_CALL은 이름을 표시하지 않는다.
				nameText.setText("");

				labelExpression.setText("Condition");

				List<String> strList = conn.getCondition();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}
				expressionText.setText(sb.toString());
			}	
			else if(RelationShip.TASK_CALL == conn.getRelationship() ||
					RelationShip.ACTION_CALL == conn.getRelationship()) {
				
				
				labelExpression.setText("Statement");

				ReferElement refSource2 = (ReferElement)conn.getSource();
				ItemElement source2 = refSource2.getRealModel();

				StateAction stateAction = null;
				if(source instanceof StateElement) {
					stateAction = ((StateElement)source2).getDo();
				}
				
				if(stateAction != null) {
					
					List<String> strList = stateAction.getStatements();
					StringBuilder sb = new StringBuilder();
					for(String str : strList) {
						sb.append(str).append("\r\n");
					}
					expressionText.setText(sb.toString());
				}
				else {
					expressionText.setText("");
				}
			}
		}
	}

	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();
		if(notifier instanceof StateAction) {
			logger.info("StateAction");
			setModel(getModel());
			return;
		}
		
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			logger.info("Notification.SET");
			switch(featureId) {
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
				setModel(getModel());
			
//				if(getModel() instanceof ConnectionElement) {
//					ConnectionElement conn = (ConnectionElement)getModel();
//					
//					if(RelationShip.TRANSITION == conn.getRelationship()) {
//						// Source가 바뀌었다고 Condition이 바뀌지 않음.
//						// 여긴 실행되지 않아야 함.
//						List<String> strList = conn.getCondition();
//						StringBuilder sb = new StringBuilder();
//						for(String str : strList) {
//							sb.append(str).append("\r\n");
//						}
//						this.expressionText.setText(sb.toString());
//					}
//					else if(RelationShip.TASK_CALL == conn.getRelationship() ||
//							RelationShip.ACTION_CALL == conn.getRelationship()) {
//						removeNotifyListener((ReferElement)notification.getOldValue(), (ReferElement)conn.getSource2());
//						addNotifyListener((ReferElement)notification.getNewValue(), (ReferElement)conn.getSource2());
//
//						ReferElement refItem = (ReferElement)conn.getSource2();
//						if(refItem != null) {
//							ItemElement source = refItem.getRealModel();
//
//							StateAction stateAction = null;
//							if(source instanceof StateElement) {
//								stateAction = ((StateElement)source).getDo();
//								
//								List<String> strList = stateAction.getStatements();
//								StringBuilder sb = new StringBuilder();
//								for(String str : strList) {
//									sb.append(str).append("\r\n");
//								}
//
//								this.expressionText.setText(sb.toString());
//							}
//						}
//					}
//				}
				break;
				
			case TaskModelPackage.CONNECTION_ELEMENT_FEATURE_COUNT:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			logger.info("Notification.ADD");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			logger.info("Notification.ADD_MANY");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			logger.info("Notification.REMOVE");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			logger.info("Notification.REMOVE_MANY");
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}
*/








