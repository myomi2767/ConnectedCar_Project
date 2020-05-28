package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialCANListener implements SerialPortEventListener{
	BufferedInputStream bis;//캔통신으로 input되는 데이터를 읽기 위해 오픈된
	String msg;
	
	public SerialCANListener(BufferedInputStream bis) {
		this.bis = bis;
	//	serialClient = new SerialClient();
		//System.out.println("캔리스너 포트 불러오는 곳");
		msg = "";
		//내가 추가한 부분(아두이노의 데이터를 받기위한 작업)
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
				msg = data;
				System.out.println("Can에서 읽은 메시지 " + msg);
				//System.out.println("Can시리얼 포트로 전송된데이터=>"+data);
				//전송되는 메시지를 검사해서 적절하게 다른 장치를 제어하거나 Car클라이언트 객체로 전달해서 서버로 전송되도록 처리
				//캔으로 수신된 데이터가 0000000000000011이면 LED끄기
				//캔으로 수신된 데이터가 0000000000000000이면 LED켜기
				/*
				 * 1. 아두이노와 시리얼통신을 할 수 있도록 OUTPUT스트림얻기
				 *    => 생성자에서 한 번 작업할 수 있도록 처리
				 * 2. OUTPUT스트림으로 '0','1'내보내기
				 * 	   => CAN으로 수신된 데이터를 비교해서 
				 * 		  :U2800000000000000003F*/
				/*if(os!=null) {
					if(data.equals("U280000000000000000000000003F")) {
						os.write('1');
					}else {
						os.write('0');
					}
				}*/
				//내가 추가한 부분.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public String getMessage() {
		String newMsg = "";
		switch (msg) {
		case ":W2800000010000000000000000345":
			
			break;

		default:
			break;
		}
		return msg;
	}
}
