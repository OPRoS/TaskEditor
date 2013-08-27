package kr.re.etri.tpl.script.task.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class TaskControlMessageClient {
	private static Logger logger = Logger.getLogger(TaskControlMessageClient.class);
	private SocketChannel socket ;
	public TaskControlMessageClient(){};
	public boolean connect(String ip, int port){
		SocketAddress addr = new InetSocketAddress(ip, port);
		
		try {
			socket = SocketChannel.open();
			socket.socket().connect(addr,500);
//			socket.configureBlocking(true);
//			return socket.connect(addr);
			return true;
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
			return false;
		}		
	}
	
	public String getMessage(){
		ByteBuffer buf = ByteBuffer.allocate(4096);
		StringBuffer sBuffer= new StringBuffer();
		try {
			socket.read(buf);
			buf.clear();			
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		byte[] message = buf.array();
		return new String(message);
	}
	public String getFormattedMessage(){
		ByteBuffer head = ByteBuffer.allocate(8);
		int num=0;
		try {
			num = socket.read(head);
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
			num = socket.read(body);
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
			socket.write(buf);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		buf.clear();
	}
	
	public boolean isConnected(){
		if(socket == null){
			return false;
		}
		return socket.isConnected();
	}
	
	public boolean isOpen(){
		if(socket == null){
			return false;
		}
		return socket.isOpen();
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public static void main(String args[]){
		TaskControlMessageClient client = new TaskControlMessageClient();
		client.connect("129.254.164.132",8080);
		client.sendMessage("간다간다");
		String returns = client.getMessage();
		System.out.println(returns);
	}

}
