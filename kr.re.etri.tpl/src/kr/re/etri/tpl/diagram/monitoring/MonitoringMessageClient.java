package kr.re.etri.tpl.diagram.monitoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import kr.re.etri.tpl.MessageListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MonitoringMessageClient implements Runnable{
	private static final Logger logger = Logger.getLogger(MonitoringMessageClient.class);
	private SocketChannel m_socket ;
	private Selector m_selector;
	private List<MessageListener> m_messageListenerList;
	private AtomicBoolean m_isStopped= new AtomicBoolean(false);
	
	private MonitoringMessageClient(){};
	public void connect(String ip, int port) throws IOException{
		SocketAddress addr = new InetSocketAddress(ip, port);
		m_selector = Selector.open();
		m_socket = SocketChannel.open();
		//Connection 실패 시 빠른 응답을 위해서  다음 코드를 십입했다. 이 코드는 500 밀리 세컨드 안에
		//Connection 결과를 리턴한다.
		m_socket.socket().connect(addr,500);
		//nonblocking 모드로 설정
		m_socket.configureBlocking(false);
		m_socket.register(m_selector, SelectionKey.OP_READ);
		m_messageListenerList=new ArrayList<MessageListener>();	
	}
	public static MonitoringMessageClient open(String ip , int port)throws IOException{ 
		MonitoringMessageClient client = new MonitoringMessageClient();	

		client.connect(ip, port);		
		return client;		
	}
	public void stop() throws IOException{
		m_isStopped.set(true);
	}
	public void run(){
		try{
			while(!m_isStopped.get()){
				m_selector.select();				
				Set<SelectionKey> keySet = m_selector.selectedKeys();
				Iterator<SelectionKey> it = keySet.iterator();
				
				while(it.hasNext()){
					SelectionKey key = it.next();
					
					if(key.isReadable()){
						if(m_messageListenerList.size()>0){
							String message = getFormattedMessage(key);
							for(MessageListener listener : m_messageListenerList){
								listener.update(message);
							}							
						}
						it.remove();
					}
				}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				Set<SelectionKey> keys = m_selector.keys();
				for(SelectionKey key: keys){
					key.channel().close();
				}
				m_selector.close();
				m_messageListenerList.clear();
			} catch (IOException e) {}
			
		}
	}
	public String getFormattedMessage(){
		ByteBuffer head = ByteBuffer.allocate(8);
		int num=0;
		try {
			num = m_socket.read(head);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		if(num != 8){
			logger.debug("The length of header is not 8. lenght = "+num);
			return "";
		}
		int length = Integer.parseInt(new String(head.array()));		
		ByteBuffer body = ByteBuffer.allocate(length);
		try {
			num = m_socket.read(body);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		if(length != num){
			logger.debug("Expected length = "+length +"   Responded length = "+num);
		}
		return new String(body.array());
	}
	
	public String getFormattedMessage(SelectionKey key){
		if(key == null){
			throw new IllegalArgumentException("key is NULL.");
		}
		SocketChannel channel = (SocketChannel)key.channel();
		ByteBuffer head = ByteBuffer.allocate(8);
		int num=0;
		try {
			num = channel.read(head);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		if(num != 8){
			logger.debug("The length of header is not 8. lenght = "+num);
			return "";
		}
		int length =0;
		try{
			length = Integer.parseInt(new String(head.array()));
		}catch(Throwable e){
			logger.debug(e.getMessage() + new String (head.array()));
			return "";
		}
		ByteBuffer body = ByteBuffer.allocate(length);
		try {
			num = channel.read(body);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		if(length != num){
			logger.debug("Expected length = "+length +"   Responded length = "+num);
		}
		return new String(body.array());
	}
	
	public void sendMessage(String message){
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(message.getBytes());
		buf.flip();
		try {
			m_socket.write(buf);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		buf.clear();
	}
	
	public boolean isConnected(){
		if(m_socket == null){
			return false;
		}
		return m_socket.isConnected();
	}
	
	public boolean isOpen(){
		if(m_socket == null){
			return false;
		}
		return m_socket.isOpen();
	}
	public void addMessageListener(MessageListener listener){
		m_messageListenerList.add(listener);
	}
	
	public static void main(String args[])throws Exception{
		InputStream propertiesInputStream = new FileInputStream(new File("D:\\tpl\\youandme\\kr.re.etri.tpl\\log4j.properties"));
		if (propertiesInputStream != null) {
			Properties props = new Properties();
			props.load(propertiesInputStream);
			propertiesInputStream.close();
			PropertyConfigurator.configure(props);		
		}
		else{
			System.out.println("Can not find log4j.properties");
		}
		MonitoringMessageClient client = MonitoringMessageClient.open("localhost",8080);
		client.addMessageListener(new MessageReader());
		client.sendMessage("00000008abcdedgh");
	}

}
