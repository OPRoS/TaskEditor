package kr.re.etri.tpl.diagram.monitoring.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ServerSettingDialog extends Dialog implements SelectionListener{
	Object result;
	private static final String NAME ="Server Setting";
	private Button m_okButton, m_cancelButton;
	private Text m_ipT, m_portT;
	private Shell m_dialog;
	private ArrayList<String> m_address;
	
	public ServerSettingDialog (Shell parent, int style) {
		super (parent, style);
	}
	public ServerSettingDialog (Shell parent) {
		super(parent); 
	}
	public Object open () {
		if(m_address != null){
			m_address.clear();
		}
		Shell parent = getParent();
	    m_dialog = new Shell(parent, SWT.DIALOG_TRIM
	        | SWT.APPLICATION_MODAL);
	    m_dialog.setText(NAME);
//	    m_dialog.setSize(250, 100);
	    m_dialog.setLayout(new FormLayout());
	    	    
	    Label titleL = new Label(m_dialog, SWT.LEFT);
	    titleL.setText("Enter Server IP and Port");
	    FormData formData = new FormData();
	    formData.top = new FormAttachment(0, 5);
	    formData.left = new FormAttachment(0, 5);
	    titleL.setLayoutData(formData);
	    
	    Label ipL = new Label(m_dialog, SWT.LEFT);
	    ipL.setText("IP :");
	    formData = new FormData();
	    formData.top = new FormAttachment(titleL,10);
	    formData.left = new FormAttachment(0, 5);
	    ipL.setLayoutData(formData);
	    
	    m_ipT = new Text(m_dialog, SWT.SINGLE | SWT.BORDER);
	    formData = new FormData();
	    formData.top = new FormAttachment(titleL,5);
	    formData.left = new FormAttachment(ipL, 5);
	    formData.right = new FormAttachment(65, 0);
	    m_ipT.setLayoutData(formData);
	    m_ipT.setText("129.254.170.200");
	    
	    Label portL = new Label(m_dialog, SWT.LEFT);
	    portL.setText("Port :");
	    formData = new FormData();
	    formData.top= new FormAttachment(titleL,10);
	    formData.left = new FormAttachment(m_ipT, 5);
	    portL.setLayoutData(formData);
	    
	    m_portT= new Text(m_dialog, SWT.SINGLE | SWT.BORDER);
	    formData = new FormData();
	    formData.top= new FormAttachment(titleL,5);
	    formData.left = new FormAttachment(portL, 5);
	    formData.right = new FormAttachment(100,-5);
	    m_portT.setLayoutData(formData);
	    m_portT.setText("6011");
	    	    
	    m_cancelButton = new Button(m_dialog, SWT.PUSH);
	    m_cancelButton.setText("Cancel");	    	   
	    formData = new FormData();
	    formData.right = new FormAttachment(100,-5);
	    formData.bottom = new FormAttachment(100,-5);
	    m_cancelButton.setLayoutData(formData);
	    
	    m_okButton = new Button(m_dialog, SWT.PUSH);
	    m_okButton.setText("Ok");
	    formData = new FormData();
	    formData.right = new FormAttachment(m_cancelButton, -5);
	    formData.bottom = new FormAttachment(100, -5);
	    m_okButton.setLayoutData(formData);	    
	    
	    m_okButton.addSelectionListener(this);
	    m_cancelButton.addSelectionListener(this);
	    Rectangle rec = parent.getBounds();
	    rec.x = rec.x+rec.width/2;
	    rec.y = rec.y+rec.height/2;
	    rec.width=250;
	    rec.height=100;
	    m_dialog.setBounds(rec);
	    
	    m_dialog.open();
	    Display display = parent.getDisplay();
	    while (!m_dialog.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	  
	    return "";

	}
	public List<String> getAddress(){
		if(m_address == null){
			return new ArrayList<String>();
		}
		return m_address;
	}
	private boolean isCanceled=false;
	public boolean isCanceled(){
		return isCanceled;
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {		
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == m_okButton){
			if(m_dialog != null ){
				m_address = new ArrayList<String>();
				m_address.add(m_ipT.getText());
				m_address.add(m_portT.getText());
				m_dialog.dispose();
			}
		}
		else if(e.getSource() == m_cancelButton){
			if(m_dialog != null){
				isCanceled=true;
				m_dialog.dispose();
			}
		}		
	}
	
	public static void main(String args[]){
		System.out.println("ddddddddddd");
		Display display = new Display();
		Shell shell = new Shell(display);

		ServerSettingDialog dialog = new ServerSettingDialog(shell);
		dialog.open();
		List<String> o = dialog.getAddress();
		for(String next : o){
			System.out.println(next);
		}
	}
}
