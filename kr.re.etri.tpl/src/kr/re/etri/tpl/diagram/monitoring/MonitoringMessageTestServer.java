package kr.re.etri.tpl.diagram.monitoring;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class MonitoringMessageTestServer {
	private static Logger logger = Logger
			.getLogger(MonitoringMessageTestServer.class);
	private Selector selector;
	private int port;
	private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

	public MonitoringMessageTestServer() {
	}

	public void start(int port) throws Exception {
		selector = Selector.open();
		ServerSocketChannel server = ServerSocketChannel.open();
		ServerSocket socket = server.socket();

		SocketAddress addr = new InetSocketAddress(port);
		socket.bind(addr);

		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			int num = selector.select();

			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();

				if (key.isAcceptable()) {
					// Accept the new connection
					ServerSocketChannel ssc = (ServerSocketChannel) key
							.channel();
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);

					// Add the new connection to the selector
					SelectionKey newKey = sc.register(selector,
							SelectionKey.OP_READ|SelectionKey.OP_WRITE);
					
					String message ="00000130RES-start;HEAD:@num[540]@time[19-7-47]@target[hello_world.test.trans];DATA:in;ATTR:@name[obstacle.left]@type[int]@value[3];RES-end";
					
					ByteBuffer buf = ByteBuffer.allocate(1204);
					buf.put(message.getBytes());
					buf.flip();
					sc.write(buf);
					System.out.println("SEND : ");
					it.remove();

					System.out.println("Got connection from " + sc);
				} 
				else if (key.isReadable()) {
					// Read the data
					SocketChannel sc = (SocketChannel) key.channel();

					// Echo data
					int bytesEchoed = 0;
					while (true) {
						echoBuffer.clear();
						int r = sc.read(echoBuffer);

						if (r <= 0) {
							break;
						}

						echoBuffer.flip();

						sc.write(echoBuffer);
						bytesEchoed += r;
					}

					System.out.println("Echoed " + bytesEchoed + " from " + sc);

					it.remove();
				}
				else if(key.isWritable()){
	//				Thread.sleep(3000);
					SocketChannel sc = (SocketChannel) key.channel();
					if(key == null){
						throw new IllegalArgumentException("key is NULL.");
					}
					String message ="00000130RES-start;HEAD:@num[540]@time[19-7-47]@target[hello_world.test.trans];DATA:in;ATTR:@name[obstacle.left]@type[int]@value[3];RES-end";
					
					ByteBuffer buf = ByteBuffer.allocate(1204);
					buf.put(message.getBytes());
					buf.flip();
					sc.write(buf);
					System.out.println("SEND : ");
					it.remove();
				}
			}
		}
	}

	public static void main(String args[]) throws Exception{
		System.out.println("00000129RES-start;HEAD:@num[540]@time[19-7-47]@target[hello_world.test.trans];DATA:in;ATTR:@name[obstacle.left]@type[int]@value[3];RES-end".getBytes().length);
		MonitoringMessageTestServer server = new MonitoringMessageTestServer();
		server.start(8080);
	}
}
