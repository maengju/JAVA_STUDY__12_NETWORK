package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServerObject {
	private ServerSocket serverSocket;
	private List<ChatHandlerObject> list;
	
	public ChatServerObject() {
		try {
			serverSocket = new ServerSocket(9500);
			System.out.println("�����غ�Ϸ�");
			
			list = new ArrayList<ChatHandlerObject>();
			
			while(true) {
				Socket socket = serverSocket.accept();//����ä�� Ŭ���̾�Ʈ�� ������ ������ ������ش�(���������)
				System.out.println("���� �Ǿ����ϴ�.");
				
				ChatHandlerObject handler = new ChatHandlerObject(socket, list); //������ ����
				handler.start(); //������ ���� - ������ ����(run())
				
				list.add(handler);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServerObject();
	}
}
