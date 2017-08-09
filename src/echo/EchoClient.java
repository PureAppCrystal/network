package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP   = "192.168.1.22";
	private static final int    SERVER_PORT = 8090;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = new Scanner( System.in );
		
		try {
			socket = new Socket();
			// 2. Server Connect			
			socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT) );
			
			// 3. IO Receive
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader( new InputStreamReader( is, "utf-8") );
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( os, "utf-8" ), true);
			
			// 4. Write/Read
			while ( true ) {
				System.out.print(">> ");
				String msg = scanner.nextLine();
				
				if( "exit".equals(msg)) {
					break;
				}
				
				//메세지 보내기 
				pw.println( msg );
				
				//에코 메세지받기
				String echoMsg = br.readLine();
				if (echoMsg == null) {
					System.out.println("[Client] Disconnection by Server");
					break;
				}
				
				System.out.println("<< "+echoMsg );
			}
			
			
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner.close();
		}
	}

}
