package kr.re.etri.tpl.diagram.monitoring.controller;

import java.io.IOException;

import kr.re.etri.tpl.diagram.monitoring.MonitoringMessageClient;
import kr.re.etri.tpl.script.task.controller.TaskControlView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class TaskController implements SelectionListener{
	private static Logger logger = Logger.getLogger(TaskControlView.class);
	public static String VIEW_ID = "kr.re.etri.tpl.script.task.controller.TaskControlView";
	private Text m_resultsT, m_taskFileT,m_taskNameT;
	private Button m_loadB, m_unloadB, m_startB, m_stopB;
	private Composite m_parent;
	private Display m_display;
	Shell m_dialog;
	private MonitoringMessageClient m_client;
	
	public TaskController(Composite parent, MonitoringMessageClient client){
//		super(parent);
		m_parent = parent;
		m_client = client;
	}	
	
	public Display getDisplay(){
		return m_display;
	}
	public Text getResultsText(){
		return m_resultsT;
	}
	public Object open() {	
		m_display = PlatformUI.createDisplay();
		
		m_dialog = new Shell(m_display);
		
		m_dialog.setText("Controller");
		
		FormLayout layout = new FormLayout();
		layout.marginWidth =10;
		layout.marginHeight =10;		
		m_dialog.setLayout(layout);
		
		m_resultsT = new Text(m_dialog, SWT.MULTI
		          | SWT.BORDER
		          | SWT.H_SCROLL
		          | SWT.V_SCROLL);
		FormData formData= new FormData();
		formData.left = new FormAttachment(0,0);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(55,0);
		formData.bottom = new FormAttachment(100,0);
		m_resultsT.setLayoutData(formData);
		m_resultsT.setEditable(false);		
		
		Group taskGroup = new Group(m_dialog, SWT.SHADOW_ETCHED_IN);
		FormLayout taskLayout = new FormLayout();
		layout.marginWidth =10;
		layout.marginHeight =10;		
		taskGroup.setLayout(taskLayout);
		
		formData = new FormData();
		formData.left = new FormAttachment(m_resultsT,10);
		formData.top = new FormAttachment(0, 0);
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
				
		 Rectangle rec = m_parent.getBounds();
		    rec.x = rec.x+rec.width/2;
		    rec.y = rec.y+rec.height/2;
		    rec.width=700;
		    rec.height=400;
		    m_dialog.setBounds(rec);
		    
		    m_dialog.open();
//		    Display display = m_parent.getDisplay();
		    while (!m_dialog.isDisposed()) {
		      if (!m_display.readAndDispatch())
		        m_display.sleep();
		    }
		  
		return null;
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
		if(e.getSource() == m_loadB){
			logger.debug("Load button was clicked");

			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE01);   
				return;
			}
			String path = m_taskFileT.getText();
			if(path == null || path.equals("")){
				MessageDialog.openInformation(m_dialog.getShell(), NOTIFY, MESSAGE02);   
				return;
			}
			
			m_client.sendMessage("deploy "+path+"\r\n");
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
		}
		else if(e.getSource() == m_unloadB){
			logger.debug("Unload button was clicked");
			if(!m_client.isConnected()){
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE01);   
				return;
			}
			
			String path = m_taskFileT.getText();
			if(path == null || path.equals("")){
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE02);   
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
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE01);   
				return;
			}
			
			String name = m_taskNameT.getText();
			if(name == null || name.equals("")){
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE03);   
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
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE01);   
				return;
			}
			
			String name = m_taskNameT.getText();
			if(name == null || name.equals("")){
				MessageDialog.openInformation(m_dialog, NOTIFY, MESSAGE03);   
				return;
			}
			
			m_client.sendMessage("stop "+name+"\r\n");
			
			String message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			message = m_client.getFormattedMessage();			
			m_resultsT.append(message);
			try {
				m_client.stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
		}
	}

}
