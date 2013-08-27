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

public class RequestResumeSettingDialog extends Dialog implements SelectionListener{
	Object result;
	private static final String NAME ="Request && Resume Setting";
	private Button m_okButton, m_cancelButton;
	private Text m_afterT, m_iterationT;
	private Shell m_dialog;
	private ArrayList<String> m_result;
	
	public RequestResumeSettingDialog (Shell parent, int style) {
		super (parent, style);
	}
	public RequestResumeSettingDialog (Shell parent) {
		super(parent); 
	}
	public Object open () {
		if(m_result != null){
			m_result.clear();
		}
		Shell parent = getParent();
	    m_dialog = new Shell(parent, SWT.DIALOG_TRIM
	        | SWT.APPLICATION_MODAL);
	    m_dialog.setText(NAME);
	    m_dialog.setLayout(new FormLayout());
	    	    
	    Label titleL = new Label(m_dialog, SWT.LEFT);
	    titleL.setText("Input After and Iteration");
	    FormData formData = new FormData();
	    formData.top = new FormAttachment(0, 5);
	    formData.left = new FormAttachment(0, 5);
	    titleL.setLayoutData(formData);
	    
	    Label afterL = new Label(m_dialog, SWT.LEFT);
	    afterL.setText("After :");
	    formData = new FormData();
	    formData.top = new FormAttachment(titleL,10);
	    formData.left = new FormAttachment(0, 5);
	    afterL.setLayoutData(formData);
	    
	    m_afterT = new Text(m_dialog, SWT.SINGLE | SWT.BORDER);
	    formData = new FormData();
	    formData.top = new FormAttachment(titleL,5);
	    formData.left = new FormAttachment(afterL, 5);
	    formData.right = new FormAttachment(45, 0);
	    m_afterT.setLayoutData(formData);
	    m_afterT.setText("0");
	    
	    Label iterationL = new Label(m_dialog, SWT.LEFT);
	    iterationL.setText("Iteration :");
	    formData = new FormData();
	    formData.top= new FormAttachment(titleL,10);
	    formData.left = new FormAttachment(m_afterT, 5);
	    iterationL.setLayoutData(formData);
	    
	    m_iterationT= new Text(m_dialog, SWT.SINGLE | SWT.BORDER);
	    formData = new FormData();
	    formData.top= new FormAttachment(titleL,5);
	    formData.left = new FormAttachment(iterationL, 5);
	    formData.right = new FormAttachment(100,-5);
	    m_iterationT.setLayoutData(formData);
	    m_iterationT.setText("0");
	    	    
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
	public List<String> getResult(){
		if(m_result == null){
			return new ArrayList<String>();
		}
		return m_result;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {		
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() == m_okButton){
			if(m_dialog != null ){
				m_result = new ArrayList<String>();
				m_result.add(m_afterT.getText());
				m_result.add(m_iterationT.getText());
				m_dialog.dispose();
			}
		}
		else if(e.getSource() == m_cancelButton){
			if(m_dialog != null){
				m_dialog.dispose();
			}
		}		
	}
	
	public static void main(String args[]){
		System.out.println("ddddddddddd");
		Display display = new Display();
		Shell shell = new Shell(display);

		RequestResumeSettingDialog dialog = new RequestResumeSettingDialog(shell);
		dialog.open();
		List<String> o = dialog.getResult();
		if(o instanceof List){
			List<String> addr = (List<String>)o;
			for(String next : addr){
				System.out.println(next);
			}
		}
		else{
			System.out.println("Unsupported type...");
		}

	}
}
