package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP   = "192.168.1.22";
	private static final int    SERVER_PORT = 8080;

	public static void main(String[] args) {
		// 1. Socket Create
		Socket socket = new Socket();
		

		try {
			// 2. Server Connect			
			socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT) );
			
			// 3. IO Receive
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			// 4. Write/Read
			String data = "hello";
			os.write(data.getBytes("utf-8") );
			
			byte[] buffer = new byte[256];
			int readByteCount = is.read( buffer );
			if ( readByteCount <= -1 ) {
				System.out.println("[Client] disconnection by server");
				return;
			}
			
			data = new String( buffer, 0, readByteCount, "utf-8" );
			System.out.println("[Client] received : " + data );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}

}
