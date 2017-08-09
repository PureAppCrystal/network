package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		
		while (true) {
			try {
				System.out.print(">");
				
				Scanner scn = new Scanner(System.in);
				String host = scn.nextLine();
				
				if (host.equals("exit")) {
					break;
				}

				InetAddress[] inetAddresses = InetAddress.getAllByName(host);

				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}

			} catch (UnknownHostException e) {
				System.out.println("알수없는 호스트 : " + e);
			}
		}
	}

}
