package slave;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

//시리얼 통신을 통해서 데이터가 전송되었을 때 실행되는 클래스
//전송되어 들어오는 데이터를 읽는 작업만 한다. 
public class ArduinoSerialListener implements SerialPortEventListener {
	BufferedInputStream arduinoBis; 
	OutputStream canOs; //아두이노로 시리얼출력을 위해 작업
	SerialConnect arduino;
	SerialConnect can;
	String canData;
	String sum;
	String zerosum;
	String HexCode;
	String id="";
	String aData;
	char[] data_arr;
	
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
			HexCode ="";
			sum = "";
			zerosum = "";
			//데이터가 도착하면 어떻게 할 것인지 정의
			byte[] ArduinoreadBuffer = new byte[1024];
			try {
				arduinoBis.read(ArduinoreadBuffer, 0, 1024);
				aData = new String(ArduinoreadBuffer);
				System.out.println("Arduino 시리얼 포트로 전송된 데이터:"+aData);//아두이노에서 50을 보내면 aData에 50이 찍힘			
				
				String HexCode = convertToHex(aData);
				
				if(canOs!=null) {//rc카에서 나오는 속도값을 CAN통신을 거쳐서 태블릿 계기판으로 보내줄 곳
						id = "00000002";
						canData = HexCode.trim();
						String message = id+canData;
						System.out.println("Slave가 보낸 메시지 =>>>" + message);
						send(message);
					}
					
					/*if(aData.trim().length()==3) {
						canData = "0000000000000"+aData.trim();
					}else if(aData.trim().length()==2) {
						canData = "00000000000000"+aData.trim();
					}else if(aData.trim().length()==1) {
						canData = "000000000000000"+aData.trim();
					}*/
					//canData = "00000000000000"+aData.trim();
					
					/*if(aData.trim().equals("success")) {
						String id = "00000000";
						String canData = "0000000000001100";
						String message = id+canData;
						System.out.println(message);
						send(message);
					}*/
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String convertToHex(String aData) {
		data_arr = aData.trim().toUpperCase().toCharArray();//대문자로 변경 후 한 글자씩 data_arr에 넣기
		for (int i = 0; i < data_arr.length; i++) {
			sum = sum + Integer.toHexString(data_arr[i]);//data_arr에서 값 하나씩 빼서 hex로 변환
			
			
		}
		System.out.println("hex 변환"+sum);

		for( int i = 0; i < 16 - sum.length();i++) {
			zerosum = zerosum + "0";
		}
		String result = zerosum.concat(sum);//16개에 맞춰서 => 0000000... + hex값	
		return result;
	}
	
	public void send(String msg) {
		new Thread(new CANWriteThread(msg)).start();

	}

	class CANWriteThread implements Runnable {
		String data; // 송신할 데이터를 저장할 변수

		public CANWriteThread(String msg) { // =======메세지 보낼때마다 설정
			this.data = convert_data(msg);
			System.out.println("생성자 안: " + data);//data는 W2800000001000000000000005047
		}

		// msg = msg의 id + 데이터
		public String convert_data(String msg) { // 내가 보낼 메세지를 캔통신으로 송신할 수 있게 변환
			System.out.println("엠에스쥐 = "+ msg);//msg는 000000010000000000000050
			msg = msg.toUpperCase(); // 메시지를 대문자로 변환
			msg = "W28" + msg; // 메세지를 보낼 때는 'W'를 붙여줘야 한다. //송신데이터의 구분기호를 추가
			// msg W28 00000000 0000000000000000
			// 데이터프레임에 대한 체크섬을 생성 - 앞뒤 문자 빼고 나머지를 더한 후
			// 0xff로 & 연산
			System.out.println("W28 엠에스쥐 = "+ msg); //msg는 W28000000010000000000000050
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
			System.out.println("리절트 = "+ result);//result는 W2800000001000000000000005047
			return result;
		}

		@Override
		public void run() {
			byte[] outputdata = data.getBytes();
			System.out.println("또 다른 Can에게 보내기 전의 outputdata : " + outputdata);//outputdata는 [B@33e1ab78
			try {
				System.out.println("또 다른 Can에게 보낼 데이터 : " + outputdata.toString());//outputdata.toString() [B@33e1ab78
				
				canOs.write(outputdata);//can에 데이터 보내는 애
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
}
