package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ChatHandlerObject extends Thread {
	private Socket socket;
	private List<ChatHandlerObject> list;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public ChatHandlerObject(Socket socket, List<ChatHandlerObject> list) throws IOException {
		this.socket = socket;
		this.list = list;
		
		//IO
		//��� ��Ʈ���� ���� �����ؾ� �Ѵ�.(����޼����� �� ���)
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		String nickName = null;
		InfoDTO dto = null; //�޴� InfoDTO
		
		try {
			while(true) {
				//�޴� ��
				dto = (InfoDTO)ois.readObject();
				
				if(dto.getCommand() == Info.JOIN) {
					nickName = dto.getNickName();
					
					//���� �����ؼ� ��� Ŭ���̾�Ʈ���� ����޼����� ������
					InfoDTO sendDTO = new InfoDTO(); //������ InfoDTO
					sendDTO.setCommand(Info.SEND);
					sendDTO.setMessage(nickName+"�� �����Ͽ����ϴ�");
					broadcast(sendDTO);//��� Ŭ���̾�Ʈ���� ������
					
				}else if(dto.getCommand() == Info.EXIT) {
					break;
					
				}else if(dto.getCommand() == Info.SEND) {
					//���� �����ؼ� ��� Ŭ���̾�Ʈ���� �޼��� ������
					InfoDTO sendDTO = new InfoDTO();
					sendDTO.setCommand(Info.SEND);
					sendDTO.setMessage("["+nickName+"] " + dto.getMessage());
					broadcast(sendDTO);
				}
			}//while
			
			//quit�� ���� Ŭ���̾�Ʈ���� quit�� ������ �����Ѵ�.
			InfoDTO sendDTO = new InfoDTO();
			
			sendDTO.setCommand(Info.EXIT);
			oos.writeObject(sendDTO);
			oos.flush();
			
			ois.close();
			oos.close();
			socket.close();
			
			//�����ִ� Ŭ���̾�Ʈ���� ����޼����� ������.
			list.remove(this);
			
			sendDTO.setCommand(Info.SEND);
			sendDTO.setMessage(nickName+"�� �����Ͽ����ϴ�");
			broadcast(sendDTO);//��� Ŭ���̾�Ʈ���� ������
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void broadcast(InfoDTO sendDTO) {
		for(ChatHandlerObject handler : list) {
			try {
				handler.oos.writeObject(sendDTO);
				handler.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
