package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	
	public EchoServerReceiveThread( Socket socket) {
		this.socket = socket;
	}
	
	
	private void consoleLog( String msg) {
		System.out.printf("[Server] [%05d] %s \n",getId(), msg  );
	}
	
	
	@Override
	public void run() {

		//4. 연결 성공
		InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		int remoteHostPort = remoteSocketAddress.getPort();
		String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();
		consoleLog("Connected From " + remoteHostAddress+":"+remoteHostPort);
		
		//데이터
		InputStream is = null;
		OutputStream os = null;
		
		try {
			//5. I/O Stream 받아오기 
			is = socket.getInputStream();
			os = socket.getOutputStream();
			BufferedReader br = new BufferedReader( new InputStreamReader( is , "utf-8"));
			PrintWriter pw = new PrintWriter( os, true );
			
			while( true ) {
				//6. 데이터 읽기 (read)
				String msg = br.readLine();
				
				if( msg == null ) {
					consoleLog("DisConnection By Client");
					break;
				}
				
				// 7. 데이터 쓰기 (write) 
				consoleLog("Received : " + msg );
				pw.println( msg );
			}
			
		} catch( SocketException e ) {
			//상대편이 소켓을 정상적으로 닫지 않고 종료한 경우 
			consoleLog("Sudden Closed By Client ");
		} catch (IOException e ) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false ) { 
					socket.close(); 
				}
			} catch (IOException e ) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
