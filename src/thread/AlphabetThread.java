package thread;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class AlphabetThread extends Thread {
	Socket socket;
	List<PrintWriter> list;
	
	
	public AlphabetThread(Socket socket, List<PrintWriter> list) {
		this.socket = socket;
		this.list = list;
	}
	
	
	@Override
	public void run() {
		
		/*
		//동기화?ㅇㅇ
		synchronized( list ) {
			int count = list.size();
			for( int i=0; i<count; i++) {
				PrintWriter pw = list.get( i);
				pw.println( "MSG 둘리 안녕 ");
			}
		}
		
		//동기화?ㅇㅇ
		synchronized( list ) {
			list.remove(pw);
		}
		*/
		
		
		for ( char c='a'; c<='z'; c++ ) {
			System.out.print( c );
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
