package android.remote.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiverThread extends Thread{
	Socket client;
	BufferedReader br;
	PrintWriter pw;
	SerialArduinoControl serialObj;
	OutputStream os;
	public ReceiverThread(Socket client) {
		this.client = client;
		try {
			//클라이언트가 보내오는 메시지를 읽기 위한 스트림
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//클라이언트에게 메시지를 전송하기 위한 스트림
			pw = new PrintWriter(client.getOutputStream(), true);
			
			serialObj = new SerialArduinoControl();
			serialObj.connect("COM5");
			os = serialObj.getOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while(true) {
			try {
				String msg = br.readLine();
				System.out.println("클라이언트가 보낸 메시지:"+msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
