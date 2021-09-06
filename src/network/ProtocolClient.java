package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProtocolClient {
	private Socket socket; 
	private BufferedReader br, keyboard;
	private BufferedWriter bw;
	
	
	public ProtocolClient() {
		try {
			socket = new Socket("192.168.0.2",9500); //소캣생성
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			
		} catch (UnknownHostException e) {
			System.out.println("서버를 찾을수 없습니다");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("서버와 연결할수없습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		
		String msg=null;
		String line = null;
		while(true) {
			
			try {
				//서버로 보내는쪽
				System.out.print("입력 : ");
				msg = keyboard.readLine(); ///한줄 , 엔터 전까지 읽어준다.
				
				bw.write(msg+"\n");//서버로 보내기 엔터를 추가로 넣어주어야한다 꼭"100:angel","200:angel","300:angel:hi"
				bw.flush();
				
				//서버에서 받는쪽
				line = br.readLine();//"angel님 입장","angel님 퇴장","angel : hi"
				System.out.println(line);
				
				String[] ar = msg.split(":"); // 100,angel  or 200,angel
				if(ar[0].equals("200")) {
					br.close();
					bw.close();
					socket.close();
					
					keyboard.close();
					System.exit(0);
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
		}
		
	}//protocolclient
	
	
	
	public static void main(String[] args) {
		new ProtocolClient();
	}

}
