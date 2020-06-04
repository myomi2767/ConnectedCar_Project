package latteserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import can.CANReadWriteTest;

public class AndroidControlServer {
	private ServerSocket server;
	private AndroidClient androidClient;
	private SerialClient serialClient;
	CANReadWriteTest canReadWriteTest;
	public void connect() {
		try {
			server = new ServerSocket(50000);
			//캔통신을 할 수 있도록 객체를 생성해서 SerialPort가 사용가능인지 체크
			//아두이노 포트가 열리도록
			canReadWriteTest = new CANReadWriteTest("COM18","MasterCan");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Thread th = new Thread(new Runnable(){
			@Override
			public void run() {
				Socket client;
				try {
					client = server.accept();
					androidClient = new AndroidClient(client);
					serialClient = new SerialClient(androidClient,canReadWriteTest);
					//안드로이드와 시리얼 연결
					androidClient.setSerialClient(serialClient,	canReadWriteTest);
					

					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		th.start();
		
	}
	public static void main(String[] args) {
		new AndroidControlServer().connect();
	}

}
