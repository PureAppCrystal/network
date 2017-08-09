package echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		final int SERVER_PORT = 8090;
		//1. 서버 소켓 생성
		ServerSocket serverSocket = null;
		
		
		
		
		try {
			serverSocket = new ServerSocket();
			
			//2. 바인딩 
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			
			serverSocket.bind( new InetSocketAddress(localhostAddress, SERVER_PORT) );
			consoleLog("Binding " + localhostAddress+":"+SERVER_PORT);
			
			while ( true ) {
				//3. 연결 요청 기다림 (accept) - blocking
				Socket socket = serverSocket.accept();
				new EchoServerReceiveThread( socket ).start();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false ) { 
					serverSocket.close(); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void consoleLog( String msg) {
		System.out.println("[Server] ["+Thread.currentThread().getId()+"] "+msg  );
	}

}
