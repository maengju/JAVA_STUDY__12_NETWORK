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
		//출력 스트림을 먼저 생성해야 한다.(입장메세지가 안 뜬다)
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		String nickName = null;
		InfoDTO dto = null; //받는 InfoDTO
		
		try {
			while(true) {
				//받는 쪽
				dto = (InfoDTO)ois.readObject();
				
				if(dto.getCommand() == Info.JOIN) {
					nickName = dto.getNickName();
					
					//나를 포함해서 모든 클라이언트에게 입장메세지를 보내기
					InfoDTO sendDTO = new InfoDTO(); //보내는 InfoDTO
					sendDTO.setCommand(Info.SEND);
					sendDTO.setMessage(nickName+"님 입장하였습니다");
					broadcast(sendDTO);//모든 클라이언트에게 보내기
					
				}else if(dto.getCommand() == Info.EXIT) {
					break;
					
				}else if(dto.getCommand() == Info.SEND) {
					//나를 포함해서 모든 클라이언트에게 메세지 보낸다
					InfoDTO sendDTO = new InfoDTO();
					sendDTO.setCommand(Info.SEND);
					sendDTO.setMessage("["+nickName+"] " + dto.getMessage());
					broadcast(sendDTO);
				}
			}//while
			
			//quit를 보낸 클라이언트에게 quit를 보내고 종료한다.
			InfoDTO sendDTO = new InfoDTO();
			
			sendDTO.setCommand(Info.EXIT);
			oos.writeObject(sendDTO);
			oos.flush();
			
			ois.close();
			oos.close();
			socket.close();
			
			//남아있는 클라이언트에게 퇴장메세지를 보낸다.
			list.remove(this);
			
			sendDTO.setCommand(Info.SEND);
			sendDTO.setMessage(nickName+"님 퇴장하였습니다");
			broadcast(sendDTO);//모든 클라이언트에게 보내기
			
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
