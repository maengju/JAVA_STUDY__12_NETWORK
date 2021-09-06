package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ChatHandler extends Thread {
	private Socket socket;
	private List<ChatHandler> list;
	private BufferedReader br;
	private PrintWriter pw;

	public ChatHandler(Socket socket, List<ChatHandler> list) throws IOException {
		this.socket = socket;
		this.list = list;
		
		//IO
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	@Override
	public void run() {
		String nickName = null;
		String line = null;
		
		try {
			nickName = br.readLine();
			broadcast(nickName+"님 입장하였습니다");//모든 클라이언트에게 보내기
			
			while(true) {
				//받는 쪽
				line = br.readLine();
				
				if(line==null || line.toLowerCase().equals("quit")) {
					break;
				}
				
				//보내는 쪽
				broadcast("["+nickName+"] " + line);//모든 클라이언트에게 보내기
				
			}//while
			
			//quit를 보낸 클라이언트에게 quit를 보내고 종료한다.
			pw.println("quit");
			pw.flush();
			
			br.close();
			pw.close();
			socket.close();
			
			//남아있는 클라이언트에게 퇴장메세지를 보낸다.
			list.remove(this);
			broadcast(nickName+"님 퇴장하였습니다");//모든 클라이언트에게 보내기
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcast(String msg) {
		for(ChatHandler handler : list) {
			handler.pw.println(msg);
			handler.pw.flush();
		}
		
	}
}



















