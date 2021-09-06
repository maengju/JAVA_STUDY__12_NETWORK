package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressMain {

	public static void main(String[] args) throws UnknownHostException {
		//ip�ּҸ� ������ Ŭ���� InetAddress
		InetAddress naver = InetAddress.getByName("www.naver.com"); //InetAddress�� �⺻�����ڰ� ����.�׷��Ƿ� �޼ҵ带 �̿��ؼ� ������ �����Ѵ�.
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
