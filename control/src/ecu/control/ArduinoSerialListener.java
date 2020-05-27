package ecu.control;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import ecu.control.ECUControl.CANWriteThread;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

//시리얼 통신을 통해서 데이터가 전송되었을 때 실행되는 클래스
//전송되어 들어오는 데이터를 읽는 작업만 한다. 
public class ArduinoSerialListener implements SerialPortEventListener {
	BufferedInputStream arduinoBis; 
	OutputStream canOs; //아두이노로 시리얼출력을 위해 작업
	SerialConnect arduino;
	SerialConnect can;

	public ArduinoSerialListener(BufferedInputStream arduinoBis, SerialConnect arduino, SerialConnect can) {
		this.arduinoBis = arduinoBis;
		this.arduino = arduino;
		this.can = can;
		
		canOs = can.getOut();
		
	}
	//데이터가 전송될때마다 호출되는 메소드 
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch(event.getEventType()) { 
		case SerialPortEvent.DATA_AVAILABLE:
			//데이터가 도착하면 어떻게 할 것인지 정의
			byte[] ArduinoreadBuffer = new byte[1024];
			try {
				arduinoBis.read(ArduinoreadBuffer, 0, 1024);
				String aData = new String(ArduinoreadBuffer);
				System.out.println("Arduino 시리얼 포트로 전송된 데이터:"+aData);
				if(canOs!=null) {
					if(aData.trim().equals("success")) {
						String id = "00000000";
						String canData = "0000000000001100";
						String message = id+canData;
						System.out.println(message);
						send(message);
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void send(String msg) {
		new Thread(new CANWriteThread(msg)).start();

	}

	class CANWriteThread implements Runnable {
		String data; // 송신할 데이터를 저장할 변수

		CANWriteThread(String msg) { // =======메세지 보낼때마다 설정
			this.data = convert_data(msg);
			System.out.println(data);
		}

		// msg = msg의 id + 데이터
		public String convert_data(String msg) { // 내가 보낼 메세지를 캔통신으로 송신할 수 있게 변환
			msg = msg.toUpperCase(); // 메시지를 대문자로 변환
			msg = "W28" + msg; // 메세지를 보낼 때는 'W'를 붙여줘야 한다. //송신데이터의 구분기호를 추가
			// msg W28 00000000 0000000000000000
			// 데이터프레임에 대한 체크섬을 생성 - 앞뒤 문자 빼고 나머지를 더한 후
			// 0xff로 & 연산
			char[] data_arr = msg.toCharArray();
			int sum = 0;
			for (int i = 0; i < data_arr.length; i++) {
				// System.out.println("data_arr[i]"+data_arr[i]);
				sum = sum + data_arr[i];
			}
			sum = (sum & 0xff);
			// 보낼 메시지를 최종 완성
			String result = ":" + // 첫문자
					msg + Integer.toHexString(sum).toUpperCase() + "\r"; // checkSum을 만들었다.
			return result;
		}

		@Override
		public void run() {
			byte[] outputdata = data.getBytes();
			try {
				canOs.write(outputdata);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	
}
