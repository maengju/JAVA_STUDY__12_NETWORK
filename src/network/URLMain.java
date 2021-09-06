package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLMain {

	public static void main(String[] args) throws IOException {

		URL url = new URL("https://www.naver.com/index.html");

		System.out.println("프로토콜 = "+url.getProtocol());
		System.out.println("Host = "+ url.getHost());
		System.out.println("Port = "+url.getPort());
		System.out.println("기본 Port = "+url.getDefaultPort());
		System.out.println("File = "+url.getFile());
		System.out.println();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String line = null;
		while((line = br.readLine())!=null){
			System.out.println(line);
		}//while
		br.close();
				
	}

}
