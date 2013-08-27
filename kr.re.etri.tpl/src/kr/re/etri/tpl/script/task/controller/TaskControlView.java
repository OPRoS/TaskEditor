package kr.re.etri.tpl.script.task.controller;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class TaskControlView extends ViewPart implements SelectionListener{
	private static Logger logger = Logger.getLogger(TaskControlView.class);
	public static String VIEW_ID = "kr.re.etri.tpl.script.task.controller.TaskControlView";

	private Text m_resultsT, m_serverIpT, m_serverPortT, m_taskFileT,m_taskNameT;
	private Button m_connectB, m_loadB, m_unloadB, m_startB, m_stopB,m_disconnectB;
	private Composite m_parent;
	
	private final TaskControlMessageClient m_client=new TaskControlMessageClient();
	
	public TaskControlView(){}
	
	@Override
	public void createPartControl(final Composite parent1) {		
		ScrolledComposite sc = new ScrolledComposite(parent1, SWT.V_SCROLL |SWT.H_SCROLL );
		Composite parent = new Composite(sc, SWT.NONE);
		m_parent = parent;
		sc.setContent(parent);
		
		FormLayout layout = new FormLayout();
		layout.marginWidth =10;
		layout.marginHeight =10;		
		parent.setLayout(layout);
		
		m_resultsT = new Text(parent, SWT.MULTI
		          | SWT.BORDER
		          | SWT.H_SCROLL
		          | SWT.V_SCROLL);
		FormData formData= new FormData();
		formData.left = new FormAttachment(0,0);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(70,0);
		formData.bottom = new FormAttachment(100,0);
		m_resultsT.setLayoutData(formData);
		m_resultsT.setEditable(false);

		Group serverGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
		FormLayout groupLayout = new FormLayout();
		layout.marginWidth =10;
		layout.marginHeight =10;		
		serverGroup.setLayout(groupLayout);
		
		formData = new FormData();
		formData.left = new FormAttachment(m_resultsT,10);
		formData.right = new FormAttachment(100,0);
		formData.top = new FormAttachment(0, 0);
		serverGroup.setLayoutData(formData);
		serverGroup.setText("Server");
		
		Label serverIpL = new Label(serverGroup,SWT.CENTER);
		serverIpL.setText("IP ");
		formData = new FormData();
		formData.left = new FormAttachment(0,0);
		formData.top = new FormAttachment(0, 3);
		serverIpL.setLayoutData(formData);
		
		m_serverPortT= new Text(serverGroup, SWT.SINGLE| SWT.BORDER);
		m_serverPortT.setText("6011");
		formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.left = new FormAttachment(80,0);
		formData.right = new FormAttachment(100,0);
		m_serverPortT.setLayoutData(formData);
		
		Label serverPortL = new Label(serverGroup,SWT.CENTER);
		serverPortL.setText(" Port ");
		formData = new FormData();
		formData.right = new FormAttachment(m_serverPortT,0);
		formData.top = new FormAttachment(0, 3);
		serverPortL.setLayoutData(formData);
		
		m_serverIpT= new Text(serverGroup, SWT.SINGLE| SWT.BORDER);
		m_serverIpT.setText("129.254.164.132");
		formData = new FormData();
		formData.left = new FormAttachment(serverIpL,3);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(serverPortL,-3);
		m_serverIpT.setLayoutData(formData);	
		
		m_disconnectB = new Button(serverGroup,SWT.PUSH);
		m_disconnectB.setText("Disconnect");
		formData = new FormData();
		formData.top = new FormAttachment(m_serverIpT, 3);	
		formData.right = new FormAttachment(100,0);		
		m_disconnectB.setLayoutData(formData);
		m_disconnectB.addSelectionListener(this);
		
		m_connectB = new Button(serverGroup,SWT.PUSH);
		m_connectB.setText("Connect");
		formData = new FormData();
		formData.top = new FormAttachment(m_serverIpT, 3);	
		formData.right = new FormAttachment(m_disconnectB,0);		
		m_connectB.setLayoutData(formData);
		m_connectB.addSelectionListener(this);		
		serverGroup.pack();

//-----------------------------------------------------------------------------------------//		
		
		Group taskGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
		FormLayout taskLayout = new FormLayout();
		layout.marginWidth =10;
		layout.marginHeight =10;		
		taskGroup.setLayout(taskLayout);
		
		formData = new FormData();
		formData.left = new FormAttachment(m_resultsT,10);
		formData.top = new FormAttachment(serverGroup, 10);
		formData.right = new FormAttachment(100,0);
		taskGroup.setLayoutData(formData);
		taskGroup.setText("Task");
		
		
		Label taskFileL = new Label(taskGroup,SWT.CENTER);
		taskFileL.setText(" File ");
		formData = new FormData();
		formData.top = new FormAttachment(0, 0);		
		formData.left = new FormAttachment(0,0);
		taskFileL.setLayoutData(formData);
		
		m_taskFileT= new Text(taskGroup, SWT.SINGLE| SWT.BORDER);
		m_taskFileT.setText("examples/helloworld/hello_world.worker");

		formData = new FormData();
		formData.left = new FormAttachment(taskFileL,0);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100,0);
		m_taskFileT.setLayoutData(formData);
		
		m_unloadB = new Button(taskGroup,SWT.PUSH);
		m_unloadB.setText("Unload");
		m_unloadB.addSelectionListener(this);
		formData = new FormData();
		formData.top = new FormAttachment(m_taskFileT, 5);		
		formData.right = new FormAttachment(100,0);		
		m_unloadB.setLayoutData(formData);	
						
		m_loadB = new Button(taskGroup,SWT.PUSH);
		m_loadB.setText("Load");
		m_loadB.addSelectionListener(this);
		formData = new FormData();
		formData.top = new FormAttachment(m_taskFileT, 5);		
//		formData.left= new FormAttachment(m_resultsT,5);
		formData.right = new FormAttachment(m_unloadB,-3);		
		m_loadB.setLayoutData(formData);
		
		Label taskNameL = new Label(taskGroup,SWT.CENTER);
		taskNameL.setText(" Name ");
		formData = new FormData();
		formData.top = new FormAttachment(m_unloadB, 10);		
		formData.left = new FormAttachment(0,0);
		taskNameL.setLayoutData(formData);
		
		m_taskNameT= new Text(taskGroup, SWT.SINGLE| SWT.BORDER);
		m_taskNameT.setText("hello");
		formData = new FormData();
		formData.left = new FormAttachment(taskNameL,3);
		formData.top = new FormAttachment(m_unloadB, 10);
		formData.right = new FormAttachment(100,0);
		m_taskNameT.setLayoutData(formData);
		
		m_stopB = new Button(taskGroup,SWT.PUSH);
		m_stopB.setText("Stop");
		m_stopB.addSelectionListener(this);
		formData = new FormData();
		formData.top = new FormAttachment(m_taskNameT, 5);		
		formData.right = new FormAttachment(100,0);		
		m_stopB.setLayoutData(formData);
		
		m_startB = new Button(taskGroup,SWT.PUSH);
		m_startB.setText("Start");
		m_startB.addSelectionListener(this);
		formData = new FormData();
		formData.top = new FormAttachment(m_taskNameT, 5);	
		formData.right = new FormAttachment(m_stopB,0);		
		m_startB.setLayoutData(formData);
		
		taskGroup.pack();
		
		sc.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	private static final String NOTIFY="Notify";
	private static final String MESSAGE01="Was not Connected to Server.";
	private static final String MESSAGE02="Correctly input File Path.";
	private static final String MESSAGE03="Correctly input Task Name.";
	private static final String MESSAGE04="Can not found MonitoringLogView. Please try again after open MonitoringLogView.";
	private static final String CONNECTED="  Connected";
	private static final String DISCONNECTED="  Disconnected";
	String text = "";

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == m_connectB){
			logger.debug("Connect button was clicked.");
			String ip = m_serverIpT.getText();
			String port = m_serverPortT.getText();
			
			boolean isConnected = m_client.connect(ip,Integer.parseInt(port));
			if(isConnected){
				String message = m_client.getFormattedMessage();
				m_resultsT.append(message);		
				message = m_client.getFormattedMessage();
				m_resultsT.append(message);		
			}
			else{
				m_resultsT.append("disconneted");
			}
//			String message = m_client.getMessage();
//			m_resultsT.append(message);	
			logger.debug("Connect button was clicked. END");
//			boolean isConnected = m_client.connect(ip,Integer.parseInt(port));
//			if(isConnected){
//				
//				m_parent.getDisplay().syncExec(new Runnable(){
//	
//					@Override
//					public void run() {
//						while(true){
//							String message=m_client.getMessage();
//							if(message!= null || !message.equals("")){
//								m_resultsT.append(message);	
//								break;
//							}
//							System.out.println("具具剧");
//						
//						}						
//					}
//					
//				});
//			
//
//			}
//			else{
//				m_resultsT.append("disconneted");
//			}
		}
		else if(e.getSource() == m_disconnectB){
			logger.debug("Disconnect button was clicked.");
			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			m_client.sendMessage("exit\r\n");
			
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			m_client.close();
		}
		else if(e.getSource() == m_loadB){
			logger.debug("Load button was clicked");

			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			String path = m_taskFileT.getText();
			if(path == null || path.equals("")){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE02);   
				return;
			}
			
			m_client.sendMessage("deploy "+path+"\r\n");
			
			
//			m_parent.getDisplay().syncExec(new Runnable(){
//				
//				@Override
//				public void run() {
//					while(true){
//						String message=m_client.getMessage();
//						if(message!= null || !message.equals("")){
//							System.out.println("具具剧 ="+message);
//							m_resultsT.append(message);	
//							break;
//						}
//					
//					
//					}						
//				}
//				
//			});
//			System.out.println(m_client.getMessage());
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
		}
		else if(e.getSource() == m_unloadB){
			logger.debug("Unload button was clicked");
			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			String path = m_taskFileT.getText();
			if(path == null || path.equals("")){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE02);   
				return;
			}
			
			m_client.sendMessage("undeploy "+path+"\r\n");
			
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
		}
		else if(e.getSource() == m_startB){
			logger.debug("Start button was clicked");
			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			String name = m_taskNameT.getText();
			if(name == null || name.equals("")){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE03);   
				return;
			}
			
			m_client.sendMessage("run "+name+"\r\n");
			
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
		}
		else if(e.getSource() == m_stopB){
			logger.debug("Stop button was clicked");
			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE01);   
				return;
			}
			
			String name = m_taskNameT.getText();
			if(name == null || name.equals("")){
				MessageDialog.openInformation(m_parent.getShell(), NOTIFY, MESSAGE03);   
				return;
			}
			
			m_client.sendMessage("stop "+name+"\r\n");
			
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
		}
	}
}
