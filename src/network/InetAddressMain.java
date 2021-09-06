package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressMain {

	public static void main(String[] args) throws UnknownHostException {
		//ip주소를 얻어오는 클래스 InetAddress
		InetAddress naver = InetAddress.getByName("www.naver.com"); //InetAddress는 기본생성자가 없다.그러므로 메소드를 이용해서 생성을 많이한다.
		System.out.println("naver ip = "+naver.getHostAddress());
		System.out.println();
		
		InetAddress local = InetAddress.getLocalHost();
		System.out.println("localhost ip = "+local.getHostAddress());
		System.out.println();
		
		InetAddress[] daum = InetAddress.getAllByName("www.daum.net");
		
		for(InetAddress ia : daum) {
			System.out.println("DAUM ip = "+ia.getHostAddress());
			
		}
		
	}

}
