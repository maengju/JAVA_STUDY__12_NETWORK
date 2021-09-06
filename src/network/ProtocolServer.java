package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ProtocolServer {
	private ServerSocket serverSocket;
	private BufferedReader br;
	private BufferedWriter bw;
	private Socket socket;
	
	
	public ProtocolServer() {
		
		
		try {
			serverSocket = new ServerSocket(9500);
			System.out.println("Server Ready");
			
			socket = serverSocket.accept(); // 클라이언트 접속 확인후 연결할 소켓을 생성
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			
		} catch (IOException e) {
			System.out.println("클라이언트와 연결이 안되었습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		String line=null;
		while(true) {
			
			try {
				
				//받는족
				line = br.readLine();
				
				//보내는쪽
				String[] ar = line.split(":");
				if(ar[0].equals(Protocol.ENTER)) {//100
					bw.write(ar[1]+"님 입장\n");
					bw.flush();
					
				}else if (ar[0].equals(Protocol.EXIT)) {
					bw.write(ar[1]+"님 퇴장\n");
					bw.flush();
					
					br.close();
					bw.close();
					socket.close();
					
					System.exit(0);
					
				}else if (ar[0].equals(Protocol.SEND_MESSAGE)) {
					bw.write(ar[1]+":"+ar[2]+"\n");
					bw.flush();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}//ProtocolServer()
	
	public static void main(String[] args) {
		new ProtocolServer();
		
	}
	
}
