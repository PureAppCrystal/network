package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		InetAddress inetAddr = null;
		try {
			inetAddr = InetAddress.getLocalHost();
			String hostName = inetAddr.getHostName();
			String hostAddr = inetAddr.getHostAddress();
			byte[] addrs = inetAddr.getAddress();
			
			System.out.println("host Name : " + hostName);
			System.out.println("host Address : " + hostAddr);
			
			
			
			for (int i=0; i<addrs.length; i++) {
				System.out.print( addrs[i] & 0x000000ff  );
				if(i<3) {
					System.out.print(".");
				}
			}
			
		} catch (UnknownHostException e) {
			System.out.println("Host정보를 찾을 수 없습니다 : " + e );
		}

	}

}
