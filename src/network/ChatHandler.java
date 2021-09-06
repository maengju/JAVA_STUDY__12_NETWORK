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
			broadcast(nickName+"�� �����Ͽ����ϴ�");//��� Ŭ���̾�Ʈ���� ������
			
			while(true) {
				//�޴� ��
				line = br.readLine();
				
				if(line==null || line.toLowerCase().equals("quit")) {
					break;
				}
				
				//������ ��
				broadcast("["+nickName+"] " + line);//��� Ŭ���̾�Ʈ���� ������
				
			}//while
			
			//quit�� ���� Ŭ���̾�Ʈ���� quit�� ������ �����Ѵ�.
			pw.println("quit");
			pw.flush();
			
			br.close();
			pw.close();
			socket.close();
			
			//�����ִ� Ŭ���̾�Ʈ���� ����޼����� ������.
			list.remove(this);
			broadcast(nickName+"�� �����Ͽ����ϴ�");//��� Ŭ���̾�Ʈ���� ������
			
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



















