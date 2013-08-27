package kr.re.etri.tpl.diagram.dialogs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SocketUtil {

	public static final int INTPUTSTREAM_READ_RETRY_COUNT = 10;

	public SocketUtil() {}

	static ByteArrayOutputStream EOB_bout = new ByteArrayOutputStream();

	public static ByteArrayOutputStream getBout() {
		return EOB_bout;
	}

	/** 
	 * The <code>read_data</code> method of <code>SocketUtil</code> reads the
	 * specified length of bytes from the given input stream.
	 *
	 * @param      in   an inputstream
	 * @param      len  the number of bytes read.
	 * @return     The specified number of bytes read until the end of
	 *             the stream is reached.
	 * @exception  IOException  if an I/O error or unexpected EOF occurs
	 */
	public static final byte[] read_data(InputStream in, int len) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
	
		int bcount = 0;
		byte[] buf = new byte[2048];
		int read_retry_count = 0;
		while( bcount < len ) {
			int n = in.read(buf,0, len-bcount < 2048 ? len-bcount : 2048 );
			if ( n > 0 ) { bcount += n; bout.write(buf,0,n); }
			// What would like to do if you've got an unexpected EOF before
			// reading all data ?
			//else if (n == -1) break;
			else if ( n == -1 ) throw 
			new IOException("inputstream has returned an unexpected EOF");
			else  { // n == 0
				if (++read_retry_count >= INTPUTSTREAM_READ_RETRY_COUNT)
					throw new IOException("inputstream-read-retry-count( " +
							INTPUTSTREAM_READ_RETRY_COUNT + ") exceed !");
			}
		}
		bout.flush();
		return bout.toByteArray();
	}

	/** 
	 * The <code>read_data</code> method of <code>SocketUtil</code> reads all
	 * the bytes from the given inputstream until the given input stream 
	 * has not returned an EOF(end-of-stream) indicator.
	 *
	 * @param      in   an inputstream
	 * @return     all bytes read if the end of the stream is reached.
	 * @exception  IOException  if an I/O error occurs
	 */
	public static final byte[] read_data(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int bcount = 0;
		byte[] buf = new byte[2048];
		int read_retry_count = 0;
		while( true ) {
			int n = in.read(buf);
			if ( n > 0 ) { bcount += n; bout.write(buf,0,n); }
			else if (n == -1) break;
			else  { // n == 0
				if (++read_retry_count >= INTPUTSTREAM_READ_RETRY_COUNT)
					throw new IOException("inputstream-read-retry-count( " +
							INTPUTSTREAM_READ_RETRY_COUNT + ") exceed !");
			}
		}
		bout.flush();
		return bout.toByteArray();
	}

	/**
	 * Read a line of text.  A line is considered to be terminated by a line
	 * feed ('\n') or a carriage return followed immediately by a linefeed.
	 *
	 * @return     A String containing the contents of the line, not including
	 *             any line-termination characters, or null if the end of the
	 *             stream has been reached
	 *
	 * @exception  IOException  If an I/O error occurs
	 */
	public static final String read_line(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) bout.write((byte)b);
			if (b == '\n') break;
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
		
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	} 
	
	public static final String read_line2(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) bout.write((byte)b);
			if (b == '\n') break;
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.allocate(bout.size());
//		String str = new String(buffer2.array());
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	} 
	
	public static final String read_line3(InputStream in) throws IOException {		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		String tplr = "";
		int count=0;
		while( true ) {			
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) {
				bout.write((byte)b);
			}
			if (b == '\n') {
				tplr = "";
				break;
			}
			if(bout.toString().equals("00000007TPL-R> 00000017Invalid command")){
				bout.flush();
				bout = new ByteArrayOutputStream();
				count++;
				b = in.read();
				b = in.read();
			}
			if(count==3){
				if(bout.toString().equals("00000007TPL-R>")){
					break;
				}
			}
//			if(b == 'T' || b == 'P' || b == 'L' || b=='R' || b=='>')
//				tplr = tplr+ Integer.toString(b);
//			if(tplr.endsWith("TPL-R>")) 
//				break;
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
		
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	}
	
	public static final String read_line4(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) {
				bout.write((byte)b);
			}
			if (b == '\n') {
				break;
			}
			if(bout.toString().endsWith("TPL-R> ")){
				break;
			}
//			else if(bout.toString().lastIndexOf("00000007TPL-R> ")>0){
//				String mm = bout.toString();
//				int index = mm.lastIndexOf("00000007TPL-R> ");
//				mm = mm.substring(0, index);
//				return mm;
//			}
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
//		System.out.println(bout.toString());
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	}
	
	public static final String read_line5(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) {
				bout.write((byte)b);
			}
			if (b == '\n') {
				break;
			}
			if(bout.toString().endsWith("00000007TPL-R> ")){
				break;
			}
//			else if(bout.toString().lastIndexOf("00000007TPL-R> ")>0){
//				String mm = bout.toString();
//				int index = mm.lastIndexOf("00000007TPL-R> ");
//				mm = mm.substring(0, index);
//				return mm;
//			}
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
		System.out.println(bout.toString());
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	}
	
	public static final String read_EOB(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
//		System.out.println("read EOB in");
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) {
				bout.write((byte)b);				
			}
//			if (b == '\n') {
//				break;
//			}
			if(bout.toString().endsWith("RES-EOB")){
//				System.out.println("all read EOB ");
				break;
			}
//			else if(bout.toString().lastIndexOf("00000007TPL-R> ")>0){
//				String mm = bout.toString();
//				int index = mm.lastIndexOf("00000007TPL-R> ");
//				mm = mm.substring(0, index);
//				return mm;
//			}
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	}
	
	public static final String read_Res(InputStream in) throws IOException {		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
//		System.out.println("read EOB in");
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) {
				bout.write((byte)b);
			}
//			if (b == '\n') {
//				break;
//			}
			if(bout.toString().endsWith("RES-end")){
//				System.out.println("all read EOB ");
				break;
			}
			if(bout.toString().endsWith("RES-EOB")){
//				System.out.println("all read EOB ");
				break;
			}
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	}
}
