package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private ServerSocket serverSocket;
	private List<ChatHandler> list;
	
	public ChatServer() {
		try {
			serverSocket = new ServerSocket(9500);
			System.out.println("서버준비완료");
			
			list = new ArrayList<ChatHandler>();
			
			while(true) {
				Socket socket = serverSocket.accept();//낚아채서 클라이언트와 연결할 소켓을 만들어준다(연결대기상태)
				System.out.println("연결 되었습니다.");
				
				ChatHandler handler = new ChatHandler(socket, list); //스래드 생성
				handler.start(); //스래드 시작 - 스래드 실행(run())
				
				list.add(handler);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ChatServer();
	}

}




