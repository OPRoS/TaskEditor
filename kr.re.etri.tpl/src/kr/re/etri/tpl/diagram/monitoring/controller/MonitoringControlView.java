package kr.re.etri.tpl.diagram.monitoring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.monitoring.MonitoringMessageClient;
import kr.re.etri.tpl.diagram.monitoring.log.ElementNameMaker;
import kr.re.etri.tpl.diagram.monitoring.log.MonitoringLogReader;
import kr.re.etri.tpl.diagram.monitoring.log.MonitoringLogView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class MonitoringControlView extends ViewPart implements SelectionListener{
	private static Logger logger = Logger.getLogger(MonitoringControlView.class);
	public static String VIEW_ID = "kr.re.etri.tpl.diagram.monitoring.MonitoringControlView";

	private TableViewer viewer;	
	private List<MonitoringElement> m_elements = new ArrayList<MonitoringElement>();
	private Button serverB, controllerB,statusB, removeB, requestB, stopB, resumeB, endB;
	private Composite m_parent;
	private Text serverIpT;
	private MonitoringLogView m_logView;
	private MonitoringMessageClient client;
	
	public static final String CHECKED_COLUMN = "checked";
	public static final String NAME_COLUMN = "name";
	public static final String TYPE_COLUMN = "type";

	private String[] columnNames = new String[] { 
		CHECKED_COLUMN, 
		NAME_COLUMN,
		TYPE_COLUMN
	};
	
	public MonitoringControlView(){
	}
	
	@Override
	public void createPartControl(final Composite parent1) {		
		ScrolledComposite sc = new ScrolledComposite(parent1, SWT.V_SCROLL |SWT.H_SCROLL );
		Composite parent = new Composite(sc, SWT.NONE);
		m_parent = parent;
		sc.setContent(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing =10;
		parent.setLayout(layout);
				
		GridData gridData = new GridData();
		gridData.horizontalSpan=4;
		gridData.heightHint = 280;
		gridData.horizontalAlignment=SWT.FILL;
		gridData.verticalAlignment=SWT.FILL;
		Composite compo = new Composite(parent,SWT.BORDER);
		compo.setLayout(new FillLayout());
		compo.setLayoutData(gridData);
		createViewer(compo);
		
		gridData = new GridData();
		gridData.heightHint = 20;
		gridData.widthHint = 55;
		Label serverIpL = new Label(parent,SWT.LEFT);
		serverIpL.setText("Server :");
		serverIpL.setLayoutData(gridData);
		
		gridData = new GridData();
		gridData.heightHint = 20;
		gridData.horizontalSpan= 3;
		gridData.horizontalAlignment=SWT.FILL;
		gridData.verticalAlignment=SWT.FILL;
		serverIpT = new Text(parent,SWT.BORDER);
		serverIpT.setEditable(false);
		serverIpT.setLayoutData(gridData);
		
		gridData = new GridData();
		gridData.heightHint = 30;
		gridData.widthHint = 75;
		gridData.horizontalSpan=0;
		serverB = new Button(parent,SWT.PUSH);
		serverB.setText("Server");
		serverB.setLayoutData(gridData);
		serverB.addSelectionListener(this);
		
		controllerB= new Button(parent,SWT.PUSH);
		controllerB.setText("Control");
		controllerB.setLayoutData(gridData);
		controllerB.addSelectionListener(this);
		
		gridData = new GridData();
		gridData.heightHint = 30;
		gridData.widthHint = 75;
		gridData.horizontalSpan=0;
		statusB = new Button(parent,SWT.PUSH);
		statusB.setText("Status");
		statusB.setLayoutData(gridData);
		statusB.addSelectionListener(this);
		statusB.setVisible(true);		
		
		removeB = new Button(parent,SWT.PUSH);
		removeB.setText("Remove");
		removeB.setLayoutData(gridData);
		removeB.addSelectionListener(this);
		removeB.setVisible(true);
		
		gridData = new GridData();
		gridData.heightHint = 30;
		gridData.widthHint = 75;
		requestB = new Button(parent,SWT.PUSH);
		requestB.setText("Request");
		requestB.setLayoutData(gridData);
		requestB.addSelectionListener(this);		
		requestB.setVisible(true);
		
		stopB = new Button(parent,SWT.PUSH);
		stopB.setText("Stop");
		stopB.setLayoutData(gridData);
		stopB.addSelectionListener(this);
		stopB.setVisible(true);
		
		resumeB = new Button(parent,SWT.PUSH);
		resumeB.setText("Resume");
		resumeB.setLayoutData(gridData);
		resumeB.addSelectionListener(this);
		resumeB.setVisible(true);
		
		endB = new Button(parent,SWT.PUSH);
		endB.setText("End");
		endB.setLayoutData(gridData);
		endB.addSelectionListener(this);
		endB.setVisible(true);	
		
		sc.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
	}

	public void add(Object element){
		if(viewer == null){
			logger.info("Viewer was not created.");
			return;
		}
		MonitoringElement elem = new MonitoringElement(element);
		if(!m_elements.contains(elem)){
			m_elements.add(elem);
			viewer.add(elem);
		}
		else{
			logger.info("Requested Element aleady exist");
		}
	}
	private Table createTable(Composite parent){
		Table table = new Table(parent,  SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.HIDE_SELECTION );

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		
		column.setText("!");
		column.setWidth(30);
	
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("Name");
		column.setWidth(200);
		
		column= new TableColumn(table,SWT.CENTER,2);
		column.setText("Type");
		column.setWidth(50);		
		
		return table;
	}
	
	private void createViewer(Composite parent) {
		Table t = createTable(parent);
		viewer = new TableViewer(t);
		
		viewer.setColumnProperties(columnNames);
		
		CellEditor[] editors = new CellEditor[1];
		editors[0] = new CheckboxCellEditor(t);
		
		viewer.setCellEditors(editors);
		viewer.setCellModifier(new MonitoringCellModifier(viewer));
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new MonitoringElementLabelProvider());
		viewer.setInput(m_elements);
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	private static final String NOTIFY="Notify";
	private static final String MESSAGE01="Was not Connected to Server.";
	private static final String MESSAGE02="Please input Server IP & Port.";
	private static final String MESSAGE06 ="Please input properly...";
	private static final String MESSAGE03="There is no Target elements.";
	private static final String MESSAGE04="Can not found MonitoringLogView. Please try again after open MonitoringLogView.";
	private static final String MESSAGE05="Cann't connect to server...";
	private static final String ZERO="0";
	private static final String UNLIMIT="unlimit";
	private MonitoringLogReader m_monitoringReader;
	private TaskControlReader m_taskControlReader;
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == serverB){
			logger.debug("Server button was clicked.");
			ServerSettingDialog dialog = new ServerSettingDialog(m_parent.getShell());
			dialog.open();
			if(dialog.isCanceled()){
				return;
			}
			List<String> address = dialog.getAddress();
			
			if(address.size() != 2){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE02+address.size());   
				return;
			}
			try{
				client = MonitoringMessageClient.open(address.get(0), Integer.parseInt(address.get(1)));
				m_monitoringReader = new MonitoringLogReader(findMonitoringLogView());
				
				client.addMessageListener(m_monitoringReader);
//				m_parent.getDisplay().asyncExec(client);
//				client.startProcess();
				new Thread(client).start();
				String addr = address.get(0)+" : "+address.get(1);
				serverIpT.setText("Connect to "+addr);
			}catch(IOException e1){
				logger.info(e1.getMessage());
				serverIpT.setText(MESSAGE05);
			}
		}
		else if(e.getSource() == controllerB){
			logger.debug("controller Button was clicked");
			
			if(client == null ||!client.isOpen()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			TaskController controller = new TaskController(m_parent, client);
			m_taskControlReader = new TaskControlReader(controller);
			client.addMessageListener(m_taskControlReader);
			controller.open();			
		}
		else if(e.getSource() == statusB){
			logger.debug("Status button was clicked");

			if(client == null ||!client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			String message = MessageMaker.makeStatusMessage();
			client.sendMessage(message);
		}
		else if(e.getSource() == removeB){
			logger.debug("Remove Item button was clicked");
			if(m_elements == null || m_elements.isEmpty()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE03);   
				return;
			}
			List<MonitoringElement> tempList = new ArrayList<MonitoringElement>();
			for(MonitoringElement elem : m_elements){
				if(!elem.getChecked()){
					tempList.add(elem);	
				}				
			}
			m_elements.clear();
			m_elements.addAll(tempList);
			viewer.setInput(m_elements);
		}
		else if(e.getSource() == requestB){
			logger.debug("Request button was clicked");
			
			if(client == null ||!client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			if(m_elements == null || m_elements.isEmpty()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE03);   
				return;
			}	
			
			RequestResumeSettingDialog dialog = new RequestResumeSettingDialog(m_parent.getShell());
			dialog.open();
			List<String> result = dialog.getResult();
			if(result.size() != 2){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE06);   
				return;
			}			

			m_logView = findMonitoringLogView();
			if(m_logView == null){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE04);   
				return;
			}
			
			for(MonitoringElement elem : m_elements){
				if(elem.getChecked()){
					String name = ElementNameMaker.getName(elem);	
					if(name.indexOf(".")== -1){
						name = name +".all";
					}
					logger.debug(name);
					String message = MessageMaker.makeRequestMessage(name, result.get(0), result.get(1));
					client.sendMessage(message);	
				}				
			}			
		}
		else if(e.getSource() == stopB){
			logger.debug("Suspend button was clicked");
			if(client == null ||!client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}			
			
			if(m_elements == null || m_elements.isEmpty()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE03);   
				return;
			}
			
			for(MonitoringElement elem : m_elements){
				if(elem.getChecked()){
					String name = ElementNameMaker.getName(elem);	
					if(name.indexOf(".")== -1){
						name = name +".all";
					}
					logger.debug(name);			
					String message = MessageMaker.makeStopMessage(name);
					client.sendMessage(message);
				}
			}
		}
		else if(e.getSource() == resumeB){
			logger.debug("Resume button was clicked");
			if(client == null ||!client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			if(m_elements == null || m_elements.isEmpty()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE03);   
				return;
			}

			RequestResumeSettingDialog dialog = new RequestResumeSettingDialog(m_parent.getShell());
			dialog.open();
			List<String> result = dialog.getResult();
			if(result.size() != 2){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE06);   
				return;
			}
			
			
			
			for(MonitoringElement elem : m_elements){
				if(elem.getChecked()){
					String name = ElementNameMaker.getName(elem);	
					if(name.indexOf(".")== -1){
						name = name +".all";
					}
					logger.debug(name);			
					String message = MessageMaker.makeResumeMessage(name, result.get(0), result.get(1));
					client.sendMessage(message);	
				}
			}
		}
		else if(e.getSource() == endB){
			logger.debug("End button was clicked");
			if(client == null ||!client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			if(m_elements == null || m_elements.isEmpty()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY,MESSAGE03);   
				return;
			}
			for(MonitoringElement elem : m_elements){
				if(elem.getChecked()){
					String name = ElementNameMaker.getName(elem);	
					if(name.indexOf(".")== -1){
						name = name +".all";
					}
					logger.debug(name);		
					String message = MessageMaker.makeEndMessage(name);
					client.sendMessage(message);	
				}
			}				
		}		
	}
	
	public MonitoringLogView findMonitoringLogView(){
		IWorkbench wb = PlatformUI.getWorkbench();		
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();		
		IWorkbenchPage wp = wbw.getActivePage();
		IViewReference[] refers = wp.getViewReferences();
		MonitoringLogView mView = null;
		for(int i =0; i < refers.length ; i++){
			if(refers[i].getId().equals(MonitoringLogView.VIEW_ID)){
				mView = (MonitoringLogView)refers[i].getView(true);
			}
		}
		if(mView == null){
			logger.info("Monitoring Viwer is deactive.");
		}
		
		return mView;
	}

}
