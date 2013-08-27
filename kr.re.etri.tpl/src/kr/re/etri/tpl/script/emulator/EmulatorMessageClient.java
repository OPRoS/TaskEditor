package kr.re.etri.tpl.script.emulator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import kr.re.etri.tpl.MessageListener;

import org.apache.log4j.Logger;

public class EmulatorMessageClient implements Runnable {
	private static final Logger logger = Logger.getLogger(EmulatorMessageClient.class);
	private static SocketChannel m_socket ;
	private Selector m_selector;
	private List<MessageListener> m_messageListenerList;
	private AtomicBoolean m_isStopped= new AtomicBoolean(false);
	
	
	private EmulatorMessageClient(){};
	
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
	
	public static EmulatorMessageClient open(String ip , int port)throws IOException{
		EmulatorMessageClient client = new EmulatorMessageClient();

		if (!client.isConnected()) {
			client.connect(ip, port);
		}
		
		return client;		
	}
	
	public void stop(){
		m_isStopped.set(true);
	}
	
	@Override
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
								try {
								listener.update(message);
								} catch(Exception e) {
									System.out.println(message);
									e.printStackTrace();
								}
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
	
	public String getFormattedMessage(SelectionKey key) {
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
			stop();
		}
		if(num != 8){
			logger.debug("The length of header is not 8. lenght = " + num);
		}
		int length =0;
		try{
			length = Integer.parseInt(new String(head.array()));
		}catch(Throwable e){
			// KJH 20110228 s, 길이없는 데이터에 대해 한줄 모두 제거
			ByteBuffer waste = ByteBuffer.allocate(1);
			StringBuffer sb = new StringBuffer();
			sb.append(new String(head.array()));
			try {
				while ((channel.read(waste)) > 0) {
					sb.append(new String(waste.array()));
					if (sb.lastIndexOf("TPL-R> ") > -1 ||
							sb.lastIndexOf("\r\n") > -1 ||
							sb.lastIndexOf("\n") > -1) {
						waste.clear();
						break;
					}
					waste.clear();
				}
			} catch (IOException e1) {
			}
			// KJH 20110228 e, 길이없는 데이터에 대해 한줄 모두 제거
			logger.debug("For Input String: " + sb.toString());
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
		ByteBuffer buf = ByteBuffer.allocate(message.length());
		buf.put(message.getBytes());
		buf.flip();
		try {
			m_socket.write(buf);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		buf.clear();
	}
	
	public void sendMessage(ByteBuffer buf) {
		try {
			m_socket.write(buf);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
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
		if (!m_messageListenerList.contains(listener))
			m_messageListenerList.add(listener);
	}

}
