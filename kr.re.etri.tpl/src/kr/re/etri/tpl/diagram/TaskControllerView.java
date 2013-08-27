package kr.re.etri.tpl.diagram;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import kr.re.etri.tpl.diagram.dialogs.SWTResourceManager;
import kr.re.etri.tpl.diagram.dialogs.SocketUtil;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.part.ViewPart;

public class TaskControllerView extends ViewPart{

	public static final String VIEW_ID = "kr.re.etri.tpl.diagram.TaskControllerView";

	private Composite m_parent;
	private Composite parent;
	
//	private Shell dialogShell;
	private Group group1;
	private Text text1;
	private Button bPwd;
	private Button bHome;
	private Button bExit;
	private Button bConnect;
	private Text sample8;
	private Text sample7;
	private Text sample6;
	private Label label8;
	private Button bR_All;
	private Button bRight;
	private Button bSendRequest;
	private Label label7;
	private Label label6;
	private Label label5;
	private Text sample5;
	private Label label4;
	private Button bStop;
	private Group group3;
	private Button bUnload;
	private Button bRun;
	private Label label1;
	private Text sample4;
	private Label label3;
	private Button bLoadTask;
	private Button bLoad;
	private Text sample3;
	private Text sample2;
	private Text sample1;
	private Label label2;
	private Group group2;
	private Button bRemove;
	private Button bUpload;
	private TableViewer tableUpload;
	private Button bSelect;
	private Button bChdir;
	private Button bDir;
	private TableColumn tableColumn1;
	private String filePath = "./";
	
	private ArrayList fileList= new ArrayList();
	
	private Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    public InputStream m_is;
	public OutputStream m_os;
	StringBuffer message;
	boolean isTotalSuccess;
	
	public String console = "TPL-R> ";
	public String home = "./";
	public String path = "./";

	MessageConsoleStream stream;
	MessageConsole mconsole;
	IConsoleManager manager;
	
	public TaskControllerView(){
		
	}
	
	@Override
	public void createPartControl(final Composite parent1) {
		ScrolledComposite sc = new ScrolledComposite(parent1, SWT.V_SCROLL |SWT.H_SCROLL );
		parent = new Composite(sc, SWT.NONE);
		m_parent = parent;
		sc.setContent(parent);

		mconsole = new MessageConsole("Console View", null);
		manager = (IConsoleManager)ConsolePlugin.getDefault().getConsoleManager();
		manager.addConsoles(new IConsole[]{mconsole});
		manager.showConsoleView(mconsole);
		stream = mconsole.newMessageStream();
		System.setOut(new PrintStream(stream));
//		stream.println("안녕하세요~ 오오~");
		
//		parent = parent2.getShell();
//		dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

//		GridLayout layout = new GridLayout();
//		layout.numColumns = 4;
//		layout.verticalSpacing = 10;
//		layout.horizontalSpacing =10;
		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
//			SWTResourceManager.registerResourceUser(parent);
		}
		
		FormLayout layout = new FormLayout();
		parent.setLayout(layout);
		parent.layout();
		parent.pack();
//		parent.setSize(547, 734);
//		parent.setText("Task Controller");
//		{
//			bExit = new Button(parent, SWT.PUSH | SWT.CENTER);
//			FormData bExitLData = new FormData();
//			bExitLData.left =  new FormAttachment(0, 1000, 467);
//			bExitLData.top =  new FormAttachment(0, 1000, 666);
//			bExitLData.width = 60;
//			bExitLData.height = 22;
//			bExit.setLayoutData(bExitLData);
//			bExit.setText("Exit");
//			
//			bExit.addSelectionListener(new SelectionListener() {
//
//				public void widgetSelected(SelectionEvent e)
//				{
//					try{
//						if(socket==null)
//							parent.dispose();
//						else if(!socket.isClosed()){
//							disconnect();
//							parent.dispose();
//						}
//						else
//							parent.dispose();
//					}
//					catch(Exception ex){
//						ex.printStackTrace();
//					}
//					
//				}
//
//				public void widgetDefaultSelected(SelectionEvent e)
//				{
//				}
//
//			});
//		}
		{
			bConnect = new Button(parent, SWT.PUSH | SWT.CENTER);
			FormData bConnectLData = new FormData();
			bConnectLData.left =  new FormAttachment(0, 1000, 204);
			bConnectLData.top =  new FormAttachment(0, 1000, 551);
			bConnectLData.width = 82;
			bConnectLData.height = 22;
			bConnect.setLayoutData(bConnectLData);
			bConnect.setText("(Re)connect");
			
			bConnect.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
										
					String cmd = "exit";
					reqnres();
//					sample6.setText(sample6.getText()+console);
//					reqNres(cmd, "");
//					char buffer[] = new char[100];
				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
				}

			});
		}
		{
			sample8 = new Text(parent, SWT.MULTI | SWT.WRAP| SWT.BORDER);
			FormData sample8LData = new FormData();
			sample8LData.left =  new FormAttachment(0, 1000, 106);
			sample8LData.top =  new FormAttachment(0, 1000, 551);
			sample8LData.width = 82;
			sample8LData.height = 12;
			sample8.setLayoutData(sample8LData);
			sample8.setText("6011");
			sample8.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
		}
		{
			sample7 = new Text(parent, SWT.MULTI | SWT.WRAP| SWT.BORDER);
			FormData sample7LData = new FormData();
			sample7LData.left =  new FormAttachment(0, 1000, 12);
			sample7LData.top =  new FormAttachment(0, 1000, 551);//669
			sample7LData.width = 82;
			sample7LData.height = 12;
			sample7.setLayoutData(sample7LData);
			sample7.setText("127.0.0.1");
			sample7.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
			sample7.setOrientation(SWT.HORIZONTAL);
		}
		{
			sample6 = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			FormData sample6LData = new FormData();
			sample6LData.left =  new FormAttachment(0, 1000, 12);
			sample6LData.top =  new FormAttachment(0, 1000, 385);//503
			sample6LData.width = 500;
			sample6LData.height = 135;
			sample6.setLayoutData(sample6LData);
			sample6.setText("");
			sample6.setEditable(false);
			sample6.setFont(SWTResourceManager.getFont("굴림", 9, 0, false, false));
			sample6.getHorizontalBar().setPageIncrement(sample6.getHorizontalBar().getMaximum());
		}
		{
			label8 = new Label(parent, SWT.NONE);
			FormData label8LData = new FormData();
			label8LData.left =  new FormAttachment(0, 1000, 12);
			label8LData.top =  new FormAttachment(0, 1000, 369);//487
			label8LData.width = 42;
			label8LData.height = 12;
			label8.setLayoutData(label8LData);
			label8.setText("Results");
		}
//		{
//			group3 = new Group(parent, SWT.NONE);
//			FormLayout group3Layout = new FormLayout();
//			group3.setLayout(group3Layout);
//			FormData group3LData = new FormData();
//			group3LData.left =  new FormAttachment(0, 1000, 12);
//			group3LData.top =  new FormAttachment(0, 1000, 369);
//			group3LData.width = 509;
//			group3LData.height = 91;
//			group3.setLayoutData(group3LData);
//			group3.setText("Monitoring");
//			{
//				bRight = new Button(group3, SWT.PUSH | SWT.CENTER);
//				FormData bRightLData = new FormData();
//				bRightLData.left =  new FormAttachment(0, 1000, 438);
//				bRightLData.top =  new FormAttachment(0, 1000, 64);
//				bRightLData.width = 28;
//				bRightLData.height = 22;
//				bRight.setLayoutData(bRightLData);
//				bRight.setText(">>");
//			}
//			{
//				bSendRequest = new Button(group3, SWT.PUSH | SWT.CENTER);
//				FormData bSendRequestLData = new FormData();
//				bSendRequestLData.left =  new FormAttachment(0, 1000, 409);
//				bSendRequestLData.top =  new FormAttachment(0, 1000, 36);
//				bSendRequestLData.width = 91;
//				bSendRequestLData.height = 22;
//				bSendRequest.setLayoutData(bSendRequestLData);
//				bSendRequest.setText("Send Request");
//				
//				bSendRequest.addSelectionListener(new SelectionListener() {
//
//					public void widgetSelected(SelectionEvent e)
//					{						
//						String str = sample5.getText();
//						sample6.append(str+"\r\n");
//						send(str+"\r\n");
//						if(receive()){
//							receive();	// 두번 보내기때문에 임시로 두번 받도록 처리
//							sample6.append(console);							
//						}
//					}
//
//					public void widgetDefaultSelected(SelectionEvent e)
//					{
//					}
//
//				});
//			}
//			{
//				label7 = new Label(group3, SWT.NONE);
//				FormData label7LData = new FormData();
//				label7LData.left =  new FormAttachment(0, 1000, 9);
//				label7LData.top =  new FormAttachment(0, 1000, 70);
//				label7LData.width = 417;
//				label7LData.height = 12;
//				label7.setLayoutData(label7LData);
//				label7.setText("Example:REQ-start;CMD:req;ATTR:@target[hello.all]@iter[100];REQ-end");
//			}
//			{
//				label6 = new Label(group3, SWT.NONE);
//				FormData label6LData = new FormData();
//				label6LData.left =  new FormAttachment(0, 1000, 9);
//				label6LData.top =  new FormAttachment(0, 1000, 46);
//				label6LData.width = 250;
//				label6LData.height = 12;
//				label6.setLayoutData(label6LData);
//				label6.setText("$attribute={@target[..]@after[..]@iter[..]}");
//			}
//			{
//				label5 = new Label(group3, SWT.NONE);
//				FormData label5LData = new FormData();
//				label5LData.left =  new FormAttachment(0, 1000, 9);
//				label5LData.top =  new FormAttachment(0, 1000, 28);
//				label5LData.width = 250;
//				label5LData.height = 12;
//				label5.setLayoutData(label5LData);
//				label5.setText("$command={req,bStop,resume,end,status}");
//			}
//			{
//				sample5 = new Text(group3, SWT.MULTI | SWT.WRAP| SWT.BORDER);
//				FormData sample5LData = new FormData();
//				sample5LData.left =  new FormAttachment(0, 1000, 67);
//				sample5LData.top =  new FormAttachment(0, 1000, 8);
//				sample5LData.width = 433;
//				sample5LData.height = 12;
//				sample5.setLayoutData(sample5LData);
//				sample5.setText("REQ-start;CMD:$command;ATTR:$attribute;REQ-end");
//				sample5.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
//			}
//			{
//				label4 = new Label(group3, SWT.NONE);
//				FormData label4LData = new FormData();
//				label4LData.left =  new FormAttachment(0, 1000, 9);
//				label4LData.top =  new FormAttachment(0, 1000, 10);
//				label4LData.width = 46;
//				label4LData.height = 12;
//				label4.setLayoutData(label4LData);
//				label4.setText("Request");
//				label4.setFont(SWTResourceManager.getFont("굴림", 9, 0, false, false));
//			}
//			{
//				bR_All = new Button(group3, SWT.PUSH | SWT.CENTER);
//				FormData bR_AllLData = new FormData();
//				bR_AllLData.left =  new FormAttachment(0, 1000, 472);
//				bR_AllLData.top =  new FormAttachment(0, 1000, 64);
//				bR_AllLData.width = 26;
//				bR_AllLData.height = 22;
//				bR_All.setLayoutData(bR_AllLData);
//				bR_All.setText(">|");
//			}
//		}
		{
			group2 = new Group(parent, SWT.NONE);
			FormLayout group2Layout = new FormLayout();
			group2.setLayout(group2Layout);
			FormData group2LData = new FormData();
			group2LData.left =  new FormAttachment(0, 1000, 12);
			group2LData.top =  new FormAttachment(0, 1000, 244);
			group2LData.width = 509;
			group2LData.height = 98;
			group2.setLayoutData(group2LData);
			group2.setText("Remote Control");
			{
				bUnload = new Button(group2, SWT.PUSH | SWT.CENTER);
				FormData bUnloadLData = new FormData();
				bUnloadLData.left =  new FormAttachment(0, 1000, 296);
				bUnloadLData.top =  new FormAttachment(0, 1000, 70);
				bUnloadLData.width = 60;
				bUnloadLData.height = 22;
				bUnload.setLayoutData(bUnloadLData);
				bUnload.setText("Unload");
				
				bUnload.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "undeploy "+sample4.getText();
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
//							org.eclipse.ui.console
//							Console console = System.console();
//							console.writer();
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bRun = new Button(group2, SWT.PUSH | SWT.CENTER);
				FormData runLData = new FormData();
				runLData.left =  new FormAttachment(0, 1000, 368);
				runLData.top =  new FormAttachment(0, 1000, 70);
				runLData.width = 60;
				runLData.height = 22;
				bRun.setLayoutData(runLData);
				bRun.setText("Run");
				
				bRun.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "run "+sample4.getText();
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bStop = new Button(group2, SWT.PUSH | SWT.CENTER);
				FormData stopLData = new FormData();
				stopLData.left =  new FormAttachment(0, 1000, 440);
				stopLData.top =  new FormAttachment(0, 1000, 70);
				stopLData.width = 60;
				stopLData.height = 22;
				bStop.setLayoutData(stopLData);
				bStop.setText("Stop");
				
				bStop.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "stop "+sample4.getText();
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				sample4 = new Text(group2, SWT.MULTI | SWT.WRAP| SWT.BORDER);
				FormData sample4LData = new FormData();
				sample4LData.left =  new FormAttachment(0, 1000, 99);
				sample4LData.top =  new FormAttachment(0, 1000, 73);
				sample4LData.width = 180;
				sample4LData.height = 12;
				sample4.setLayoutData(sample4LData);
				sample4.setText("demo1");
				sample4.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
			}
			{
				label3 = new Label(group2, SWT.NONE);
				FormData label3LData = new FormData();
				label3LData.left =  new FormAttachment(0, 1000, 9);
				label3LData.top =  new FormAttachment(0, 1000, 75);
				label3LData.width = 78;
				label3LData.height = 12;
				label3.setLayoutData(label3LData);
				label3.setText("Task Name : ");
				label3.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
			}
			{
				bLoadTask = new Button(group2, SWT.PUSH | SWT.CENTER);
				FormData loadTaskLData = new FormData();
				loadTaskLData.left =  new FormAttachment(0, 1000, 9);
				loadTaskLData.top =  new FormAttachment(0, 1000, 36);
				loadTaskLData.width = 114;
				loadTaskLData.height = 22;
				bLoadTask.setLayoutData(loadTaskLData);
				bLoadTask.setText("Loaded Task Info.");
				
				bLoadTask.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "list";
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bLoad = new Button(group2, SWT.PUSH | SWT.CENTER);
				FormData loadLData = new FormData();
				loadLData.left =  new FormAttachment(0, 1000, 440);
				loadLData.top =  new FormAttachment(0, 1000, 7);
				loadLData.width = 60;
				loadLData.height = 22;
				bLoad.setLayoutData(loadLData);
				bLoad.setText("Load");
				
				bLoad.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "deploy "+sample3.getText();
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				sample3 = new Text(group2, SWT.MULTI | SWT.WRAP| SWT.BORDER);
				FormData sample3LData = new FormData();
				sample3LData.left =  new FormAttachment(0, 1000, 169);
				sample3LData.top =  new FormAttachment(0, 1000, 10);
				sample3LData.width = 265;
				sample3LData.height = 12;
				sample3.setLayoutData(sample3LData);
				sample3.setText("hw_task.tpl");
			}
			{
				label2 = new Label(group2, SWT.NONE);
				FormData label2LData = new FormData();
				label2LData.left =  new FormAttachment(0, 1000, 9);
				label2LData.top =  new FormAttachment(0, 1000, 12);
				label2LData.width = 154;
				label2LData.height = 12;
				label2.setLayoutData(label2LData);
				label2.setText("Task File : $TASK_HOME/ ");
				label2.setFont(SWTResourceManager.getFont("굴림", 9, 0, false, false));
			}
		}
		{
			group1 = new Group(parent, SWT.NONE);
			FormLayout group1Layout = new FormLayout();
			group1.setLayout(group1Layout);
			FormData group1LData = new FormData();
			group1LData.left =  new FormAttachment(0, 1000, 12);
			group1LData.top =  new FormAttachment(0, 1000, 12);
			group1LData.width = 509;
			group1LData.height = 205;
			group1.setLayoutData(group1LData);
			group1.setText("Task File Updload");
			{
				sample2 = new Text(group1, SWT.BORDER);
				FormData sample2LData = new FormData();
				sample2LData.left =  new FormAttachment(0, 1000, 338);
				sample2LData.top =  new FormAttachment(0, 1000, 33);
				sample2LData.width = 111;
				sample2LData.height = 12;
				sample2.setLayoutData(sample2LData);
				sample2.setText("");
				sample2.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
			}
			{
				sample1 = new Text(group1, SWT.LINE_SOLID | SWT.BORDER);
				FormData sample1LData = new FormData();
				sample1LData.left =  new FormAttachment(0, 1000, 9);
				sample1LData.top =  new FormAttachment(0, 1000, 33);
				sample1LData.width = 318;
				sample1LData.height = 12;
				sample1.setLayoutData(sample1LData);
				sample1.setText("$TASK_HOME(Push 'pwd' button)");
				sample1.setEditable(false);
				sample1.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
			}
			{
				label1 = new Label(group1, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 10);
				label1LData.top =  new FormAttachment(0, 1000, 60);
				label1LData.width = 77;
				label1LData.height = 12;
				label1.setLayoutData(label1LData);
				label1.setText("Local File");
				label1.setFont(SWTResourceManager.getFont("굴림", 10, 0, false, false));
			}
			{
				bRemove = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData removeLData = new FormData();
				removeLData.left =  new FormAttachment(0, 1000, 368);
				removeLData.top =  new FormAttachment(0, 1000, 177);
				removeLData.width = 58;
				removeLData.height = 22;
				bRemove.setLayoutData(removeLData);
				bRemove.setText("Remove");
				
				bRemove.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{
//						sample6.append("remove\r\n");
//						stream.println("remove\r\n");
						System.out.println("Mapping Button Push");
						ISelection iSelection = tableUpload.getSelection();
						if(((StructuredSelection)iSelection).getFirstElement() == null){
							System.out.println("Warning! dont Selected Table");
							return;
						}
						Object obj1 = ((StructuredSelection)iSelection).getFirstElement();
						fileList.remove(obj1);
						tableUpload.setInput(fileList.toArray());
						
						System.out.println();
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bUpload = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData uploadLData = new FormData();
				uploadLData.left =  new FormAttachment(0, 1000, 440);
				uploadLData.top =  new FormAttachment(0, 1000, 177);
				uploadLData.width = 52;
				uploadLData.height = 22;
				bUpload.setLayoutData(uploadLData);
				bUpload.setText("Upload");
				
				bUpload.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{
						if(socket == null || socket.isClosed())
							return;
						sample6.append("upload\r\n");
						stream.println("upload\r\n");
						ISelection iSelection = tableUpload.getSelection();
						if(((StructuredSelection)iSelection).getFirstElement() == null){
							System.out.println("Warning! dont Selected Table");
							return;
						}
						send("upload\r\n");
						StructuredSelection ss = (StructuredSelection)iSelection;
						Object[] lists = ss.toArray();
						ArrayList list = new ArrayList();
						for(int i=0; i<lists.length; i++){
//							ArrayList array = (ArrayList)((StructuredSelection)iSelection).getFirstElement();
							list.add(lists[i]);
//							String path = (String)array.get(1);
//							File file = new File(path);
//							if (file.canRead()) {
								
						}
						fileUpload(list);
						receive();
						sample6.append(console);
						stream.print(console);
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				FormData tableUploadLData = new FormData();
				tableUploadLData.left =  new FormAttachment(0, 1000, 9);
				tableUploadLData.top =  new FormAttachment(0, 1000, 84);
				tableUploadLData.width = 474;
				tableUploadLData.height = 64;
				final Table table = new Table(group1, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
				tableUpload = buildAndLayoutTable(table);
				attachContentProvider(tableUpload);
				attachLabelProvider(tableUpload);
				tableUpload.getControl().setLayoutData(tableUploadLData);				
				tableUpload.setInput(fileList.toArray());				
			}
			{
				bSelect = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData selectLData = new FormData();
				selectLData.left =  new FormAttachment(0, 1000, 87);
				selectLData.top =  new FormAttachment(0, 1000, 53);
				selectLData.width = 47;
				selectLData.height = 22;
				bSelect.setLayoutData(selectLData);
				bSelect.setText("Select");
				
				bSelect.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{
//						sample6.setText(sample6.getText()+"select\r\n");
						System.out.println("Mapping Button Push");
						FileDialog fsd = new FileDialog(m_parent.getShell(),SWT.SAVE);
						fsd.setFilterExtensions(new String[] {"*.tpl","*.*"});
						String str = fsd.open();
						;
						int index = str.lastIndexOf("\\");
						ArrayList file = new ArrayList();
						file.add(fsd.getFileName());
						file.add(str);
						fileList.add(file);
						tableUpload.setInput(fileList.toArray());
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bChdir = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData chdirLData = new FormData();
				chdirLData.left =  new FormAttachment(0, 1000, 456);
				chdirLData.top =  new FormAttachment(0, 1000, 31);
				chdirLData.width = 44;
				chdirLData.height = 22;
				bChdir.setLayoutData(chdirLData);
				bChdir.setText("chdir");
				
				bChdir.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{	
						if(sample2.getText().equals(""))
							return;
						String str = "chdir "+path+sample2.getText()+"/";
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
							sample2.setText("");
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bHome = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData homeLData = new FormData();
				homeLData.left =  new FormAttachment(0, 1000, 456);
				homeLData.top =  new FormAttachment(0, 1000, 7);
				homeLData.width = 44;
				homeLData.height = 22;
				bHome.setLayoutData(homeLData);
				bHome.setText("Home");
				
				bHome.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "home ";
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
							sample1.setText(home);
							path = home;
							sample2.setText("");
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bDir = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData dirLData = new FormData();
				dirLData.left =  new FormAttachment(0, 1000, 279);
				dirLData.top =  new FormAttachment(0, 1000, 7);
				dirLData.width = 48;
				dirLData.height = 22;
				bDir.setLayoutData(dirLData);
				bDir.setText("Dir");
				
				bDir.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "dir";
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample6.append(console);
							stream.print(console);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				bPwd = new Button(group1, SWT.PUSH | SWT.CENTER);
				FormData pwdLData = new FormData();
				pwdLData.left =  new FormAttachment(0, 1000, 225);
				pwdLData.top =  new FormAttachment(0, 1000, 7);
				pwdLData.width = 48;
				pwdLData.height = 22;
				bPwd.setLayoutData(pwdLData);
				bPwd.setText("Pwd");
				
				bPwd.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e)
					{						
						String str = "pwd ";
						sample6.append(str+"\r\n");
						stream.println(str);
						send(str+"\r\n");
						if(receive()){
							sample1.setText(path);
							sample6.append(console);
							stream.print(console);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e)
					{
					}

				});
			}
			{
				text1 = new Text(group1, SWT.NONE);
				FormData text1LData = new FormData();
				text1LData.left =  new FormAttachment(0, 1000, 9);
				text1LData.top =  new FormAttachment(0, 1000, 11);
				text1LData.width = 204;
				text1LData.height = 12;
				text1.setLayoutData(text1LData);
				text1.setText("Robot's Current(Upload Target)Dir : ");
				text1.setEditable(false);
				text1.setFont(SWTResourceManager.getFont("굴림", 9, 0, false, false));
			}
		}
//		parent.setLocation(parent.toDisplay(100, 100));
//		parent.open();
		sc.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
//		Display display = parent.getDisplay();
//		while (!parent.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
//		viewer.getControl().setFocus();
	}

	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(5, 5, false));
		layout.addColumnData(new ColumnWeightData(20, 20, false));
		layout.addColumnData(new ColumnWeightData(50, 150, false));
		table.setLayout(layout);
		TableColumn spaceColumn = new TableColumn(table, SWT.CENTER);
		spaceColumn.setText("");
		TableColumn steroColumn = new TableColumn(table, SWT.CENTER);
		steroColumn.setText("File Name");
		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
		nameColumn.setText("File Path");

		table.setHeaderVisible(true);
		return tableViewer;
	}
	
	private void attachContentProvider(TableViewer viewer) {
		viewer.setContentProvider(
				new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return (Object[]) inputElement;
					}
					public void dispose() {
					}
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
//						System.out.println("input change");
					}
				});
	}
	
	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						
						switch (columnIndex) {
						case 0:							
							return "";
						case 1:
							if(element instanceof ArrayList){
								ArrayList al =  (ArrayList)element;
								String str1 = (String)al.get(0);
								return str1;
							}
							return "";		
						case 2:
							if(element instanceof ArrayList){
								ArrayList al =  (ArrayList)element;
								String str1 = (String)al.get(1);
								return str1;
							}
							return "";
						default:
							return "Invalid column: " + columnIndex;
						}
					}
					public void addListener(ILabelProviderListener listener) {
						System.out.println();
					}
					public void dispose() {
					}
					public boolean isLabelProperty(Object element, String property) {
						return false;
					}
					public void removeListener(ILabelProviderListener lpl) {
					}
				});
	}
	
	public void myInit(){
		int st= -1;
		String curPwd = "", curCmd = "";
		reqnres();
	}
	public void reqNres(String cmd, String arg){
		try{
			socket = new Socket("127.0.0.1", 6011);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void reqnres() {
        try {
        	if(socket !=null && !socket.isClosed()){
        		disconnect();
        		sample6.append("Disconnect\r\n");
        		stream.println("Disconnect\r\n");
        	}
        	else{
	            connect("127.0.0.1", 6011);
	            sample6.append("Connect\r\n");
	            stream.println("Connect\r\n");
	            message = new StringBuffer();
//	            message.append("GET /Forum/ HTTP/1.1\r\n");
//	            message.append("Host: 127.0.0.1\r\n");
//	            message.append("connect\r\n");
//	            sendMessage(new String(message));
	            sample6.setText("");
	            receive();
	            sample6.append(console);
	            stream.print(console);
        	}
//            System.out.println(receiveMessage());

//            disconnect();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	public void init() {
        socket = null;
        bis = null;
        bos = null;
    }

    public void connect(String host, int port) throws UnknownHostException, IOException {
        
    	socket = new Socket(InetAddress.getByName(host), port);
        if(socket!=null){
//        	initResource();
        	m_os = socket.getOutputStream();        	
			m_is = socket.getInputStream();	
	        bis = new BufferedInputStream(m_is);
	        bos = new BufferedOutputStream(m_os);	        
        }                
    }
    
//    public void initResource() throws UnknownHostException, IOException{
//    	if(socket != null){
//    		if(socket.getOutputStream()!=null)
//    			m_os = socket.getOutputStream();
//    		else{
//    			
//    		}
//			m_is = socket.getInputStream();	
//	        bis = new BufferedInputStream(m_is);
//	        bos = new BufferedOutputStream(m_os);
//    	}
//    }

    public void connect(String host, int port, int localPort) throws UnknownHostException, IOException {
        socket = new Socket(InetAddress.getByName(host), port, InetAddress.getLocalHost(), localPort);
    }
    
    public void send(String str){
    	try{
    		
    		message = new StringBuffer();
            message.append(str);
            if(!str.equals("EOJ:"))
            	stream.println(str);
    		sendMessage(new String(message));
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public boolean receive(){
    	try{
   		if(m_is == null)
   			return false;
    	String str = SocketUtil.read_line4(m_is);
    	while(str.lastIndexOf("00000007TPL-R> ")==-1){
			if(str!=null && !str.trim().equals("")){
				String mm = str;
				if(str.startsWith("00000004") || str.startsWith("00000216") || str.startsWith("00001446")
						|| str.startsWith("00002010") || str.startsWith("00000084")|| str.startsWith("00000384")
						|| str.startsWith("00000370")|| str.startsWith("00000094")|| str.startsWith("00000792")
						|| str.startsWith("00000415")|| str.startsWith("00000087")|| str.startsWith("00000691")
						|| str.startsWith("00000373")|| str.startsWith("00000654")|| str.startsWith("00000211")
						|| str.startsWith("00000085")|| str.startsWith("00000510")|| str.startsWith("00000051")
						|| str.startsWith("00001533")|| str.startsWith("00000023")|| str.startsWith("00000021")){
					mm =  str.substring(8, str.length());					
				}
				else if(str.startsWith("00000011")||str.startsWith("00000013")){
					mm =  str.substring(8, str.length());
					sample1.setText(mm);
					path = mm;
				}
				else if(str.startsWith("00000015")){
					mm =  str.substring(8, str.length());
					sample1.setText(mm);
					path = mm;
				}
				else if(str.startsWith("00000019")){
					mm =  str.substring(8, str.length());
					sample1.setText(mm);
					path = mm;
				}
				else if(str.startsWith("00000037")){
					mm =  str.substring(8, str.length());					
					sample1.setText(mm);
				}
				sample6.append(mm+"\r\n");
				stream.println(mm);
//				System.out.println("응답"+mm);
			}
			str = SocketUtil.read_line4(m_is);
    	}
    	int index = str.lastIndexOf("00000007TPL-R> ");
    	if(index>0){
    		String mm = str.substring(8, index);
    		sample6.append(mm+"\r\n");
    		stream.println(mm);
//			System.out.println("응답"+mm);
    	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    
    public void receive2(){
    	try{
    		String str = SocketUtil.read_line3(m_is);
        	while(!str.equals("00000007TPL-R>")){
    			if(str!=null && !str.trim().equals("")){
    				sample6.append(str+"\r\n");
    				stream.println(str);
//    				System.out.println("응답"+str);
    			}
    			str = SocketUtil.read_line3(m_is);
        	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public void sendMessage(String message) throws IOException {
    	if(bos != null){
	        bos.write(message.getBytes());
	        bos.flush();
    	}
    }

    public void disconnect() throws IOException {
        bis.close();
        bos.close();
        socket.close();        
    }
    
	public boolean cmd(File hsd){
		try{
			String name = hsd.getName();
			byte[] bt = name.getBytes();
			int len = (int)hsd.length();
			name = "fname%"+hsd.getName()+"%"+len+":";
//			byte[] bt = new byte[name.length()];
//			for(int i=0; i<name.length(); i++)
//				bt[i] = (byte)name.ch(index)(i);
//			System.out.println(hsd.getHeader());
			if(m_os != null){
				m_os.write(name.getBytes(),0,name.getBytes().length);
				m_os.flush();
			}

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
    
    public void fileUpload(ArrayList array){
    	boolean isExit=false;
    	try{
    	
    	if(array!=null){
			isTotalSuccess = true;
			
			for(int i=0; i<array.size(); i++){
				if(isExit)
					break;
				ArrayList list = (ArrayList)array.get(i);
				String path = (String)list.get(1);
				String name = (String)list.get(0);
				File hsd	 = new File(path);
				if(!hsd.exists())
					break;
				String fileName = hsd.getName();
				boolean isTotalSuccess = cmd(hsd);

				if(isTotalSuccess){
					//						ProjectManager.getInstance().getConsole().appendMessage2("헤더 전송: "+hsd.getStringFileDownLoadHeader2());

					isTotalSuccess = cmdfiledownload_file(hsd);
					if(!isTotalSuccess){
						isExit = true;
						
						break;
					}

//					this.addMsg("파일 전송: "+fileName+ "size["+hsd.payloadSize+"]");
//					if(monitor!=null){
//						monitor.subTask("파일 전송: "+hsd.file_name+ " size["+hsd.payloadSize+"]");
//						monitor.worked(INCREMENT);
//					}
					//						ProjectManager.getInstance().getConsole().appendMessage2("파일 전송: "+hsd.file_name+ " size["+hsd.payloadSize+"]");


				}
				else{

					break;
				}
//				receive();				
			}	
    	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
			isTotalSuccess = false;
//			this.addMsg("파일 전송 실패");
    	}
//    	if(isTotalSuccess){
//			if(errMsg.equals(""))
//			this.addMsg("파일 전송 완료");
//		}
		isExit = true;
		send("EOJ:");
		
	}
    
    public boolean cmdfiledownload_file(File hsd)
	{
		FileInputStream fis = null;		
		boolean isResult = true;
		try
		{
			fis = new FileInputStream(hsd);			
			bos = new BufferedOutputStream(m_os);
			bis = new BufferedInputStream(fis); 
			//			byte[] snd_buf = new byte[4079];
			ByteBuffer buffer = ByteBuffer.allocate(80); //220바이트 공간 설정
			System.out.println("order==>"+buffer.order());

			long nFileSize = 0;			
			int nIndex = 1;
			int nRead;
			long nRemaint;
			nFileSize = Long.valueOf(hsd.length());
			int nEnd = (int)nFileSize/80;
			nRemaint = nFileSize%80;
			if( nRemaint != 0 )
				nEnd++;
			else
				nRemaint = 80;



			for(int i=0; ; )
			{


				if(nIndex<=nEnd){
					if( nEnd == nIndex )
					{
						i = bis.read(buffer.array(), 0, (int)nRemaint);

					}
					else if( nEnd != nIndex )
					{
						if( nEnd != 0)
							i = bis.read(buffer.array(), 0, 80);
						else
							i = 0;
					}
					if( i == -1 )
						break;

					if( nEnd == nIndex )
					{
						bos.write(buffer.array(),0, (int)nRemaint);
						bos.flush();
						break;

					}
					else if(nEnd!=0)
					{
						bos.write(buffer.array());

					}
					bos.flush();
				}

				//				// 확인 수신
				//				String str = SocketUtil.read_line(m_is);
				//				if(str!=null && !str.trim().equals("")){
				//					hrd = new HeaderReceiveData();
				//					hrd.setHeaderReceiveData(str);
				//					break;
				//				}

				if(nEnd == 0)
					break;

				nIndex++;
			}

			fis.close();
			bis.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			isResult = false;
		}
		finally{
			try{
				fis.close();
				bis.close();				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return isResult;
	}

}
