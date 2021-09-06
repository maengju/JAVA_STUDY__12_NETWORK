package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLMain2 {

	public static void main(String[] args) throws MalformedURLException , IOException{
		URL url = new URL("http://www.goldria.net/m/product_list.html?xcode=012");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String line = null;
		
		//14k��°� �� ������ ���Ͻÿ�
		int count=0;
		while((line = br.readLine())!=null){
			line.toLowerCase();
			
			int index =0;
			while((index=line.indexOf("14k",index))!=-1) {//1�ٿ��� 14k�� ������ ����
				index = index + "14k".length();
				count++;
			}
		}//while

		
		System.out.println("14k�� ���� = "+count);
		br.close();
	}

}
