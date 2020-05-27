package controlCarClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serial.SerialConnect;
//시리얼포트를 통해서 데이터가 전송되었을 때 실행되는 클래스. 전송되서 들어오는 데이터를 읽는 역할만 함
public class SerialListener implements SerialPortEventListener {
	BufferedInputStream bis;//캔통신으로 input되는 데이터를 읽기 위해 오픈된
	Socket socket;
	OutputStream os;
	PrintWriter pw;
	
	public SerialListener(BufferedInputStream bis, Socket socket) {
		this.bis = bis;
		this.socket = socket;
		try {
			os = socket.getOutputStream();
			pw = new PrintWriter(os, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//데이터가 전송될 때마다 호출되는 메소드
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch(event.getEventType()) {
		//DATA_AVAILABLE => 데이터가 도착하면 어떤거를 하겠다는 의미
			case SerialPortEvent.DATA_AVAILABLE:
				byte[] readBuffer = new byte[128];
			try {
				while(bis.available()>0) {//데이터 전체를 읽기
					int numbyte = bis.read(readBuffer);//읽은 내용이 readBuffer에 셋팅됨
				}
				String data = new String(readBuffer);
				System.out.println("Can시리얼 포트로 전송된데이터=>"+data);
				if(pw!=null) {
					if(data.trim().equals(":U2800000000000000000000110041")){
						System.out.println("if문 들어옴");
						String msg = "success";
						String id = "11111";
						pw.println("job:"+msg+":car:"+id);
						pw.flush();
					}
				}
				//내가 추가한 부분.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
