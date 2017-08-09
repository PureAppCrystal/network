package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {
		final int SERVER_PORT = 8080;
		//1. 서버 소켓 생성
		ServerSocket serverSocket = null;
		
		
		
		
		try {
			serverSocket = new ServerSocket();
			
			//2. 바인딩 
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			
			serverSocket.bind( new InetSocketAddress(localhostAddress, SERVER_PORT) );
			System.out.println("[Server] binding " + localhostAddress+":"+SERVER_PORT);
			
			//3. 연결 요청 기다림 (accept) - blocking
			Socket socket = serverSocket.accept();
			
			//4. 연결 성공
			InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			int remoteHostPort = remoteSocketAddress.getPort();
			String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();
			System.out.println("[Server] Connected from " + remoteHostAddress+":"+remoteHostPort);
			
			//데이터
			InputStream is = null;
			OutputStream os = null;
			
			try {
				//5. I/O Stream 받아오기 
				is = socket.getInputStream();
				os = socket.getOutputStream();
				
				while( true ) {
					//6. 데이터 읽기 (read)
					byte[] buffer = new byte[256];
					int readByteCount = is.read( buffer); // block
					
					if( readByteCount <= -1) {
						System.out.println("[Server] DisConnection by Client");
						break;
					}
					
					String data = new String( buffer, 0, readByteCount, "utf8" );
					System.out.println( "[Server] Received : " + data );
					os.write(data.getBytes("utf-8") );
				}
			} catch( SocketException e ) {
				//상대편이 소켓을 정상적으로 닫지 않고 종료한 경우 
				System.out.println("[Server] sudden closed by client ");
			} catch (IOException e ) {
				e.printStackTrace();
			} finally {
				try {
//					if ( is != null ) { is.close(); }
//					if ( os != null ) { os.close(); }
					if (socket != null && socket.isClosed() == false ) { 
						socket.close(); 
					}
				} catch (IOException e ) {
					e.printStackTrace();
				}
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

}
