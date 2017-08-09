package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";
	private static final String ERROR_400_ROOT = "/error/400.html";
	private static final String ERROR_404_ROOT = "/error/404.html";
	private Socket socket;
	
	//생성자
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	
	//쓰레드 런 함수 오버라이딩 
	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );

			// get IOStream
			BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8") );
			OutputStream os = socket.getOutputStream();
			
			String request = null;
			while(true) {
				String line = br.readLine();
				
				if ( line == null || "".equals( line) ) {
					break;
				}
				
				if ( request == null) {
					request = line;
					break;
				}
			}
			
			consoleLog( "[Request] : "+request );
			
			//요청 분석
			String[] tokens = request.split(" ");
			if ("GET".equals( tokens[0] ) ) {
				responseStaticResource( os, tokens[1], tokens[2] );
			} else {
				response400Error(os, tokens[2]);   
				consoleLog("#Bad Request");
			}

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}
	
	
	// http/1.1 200 OK \r\n    -Get방식 호출 
	private void responseStaticResource( OutputStream os, String url, String protocol ) throws IOException {
		//Get일 경우 요청한 URL 찍어보기 
		consoleLog("GET-URL : "+url);
		
		//디폴트 경로 설정 
		if ("/".equals( url) ) {
			url = "/index.html";
		}
		
		//임시(혼자)로 login페이지이동시 join.html을 연결해보자
		if ("/user/login.html".equals( url )) {
			url = "/user/join.html";
		}
		
		//파일 확인
		File file = new File( DOCUMENT_ROOT + url );
		if( file.exists() == false ) {
			//파일이 존재하지 않아..
			consoleLog("#404 Error");
			response404Error(os, protocol);
			return;
		}
		
		sendResponse(file, os,  protocol, "200 OK");
	}
	
	
	// http/1.1 400 bad request \r\n
	private void response400Error( OutputStream os, String protocol) throws IOException {
		File file = new File(DOCUMENT_ROOT + ERROR_400_ROOT);
		sendResponse(file, os,  protocol, "400 BAD Request");
	}
	
	
	// HTTP/1.1 404 file not found \r\n
	private void response404Error( OutputStream os, String protocol) throws IOException {
		File file = new File(DOCUMENT_ROOT + ERROR_404_ROOT);
		sendResponse(file, os, protocol, "404 File NotFound");
	}
	
		
	//해당 파일 존재유무 확인 및 헤더/바디 데이터 전송 
	private void sendResponse(File file, OutputStream os, String protocol, String msg) throws IOException {
		byte[] body = Files.readAllBytes(file.toPath());
		String mimeType = Files.probeContentType( file.toPath() );
		
		// header 전송
		os.write( (protocol+" "+msg+" \r\n").getBytes( "UTF-8" ) );
		os.write( ("Content-Type:"+mimeType+"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		os.write( "\r\n".getBytes() );
		
		// body 전송
		os.write( body );
	}
	
	
	//로그 함수
	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
	
	
}