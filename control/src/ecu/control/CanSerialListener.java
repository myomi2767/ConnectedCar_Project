package ecu.control;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import ecu.control.ECUControl.CANWriteThread;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

//시리얼 통신을 통해서 데이터가 전송되었을 때 실행되는 클래스
//전송되어 들어오는 데이터를 읽는 작업만 한다. 
public class CanSerialListener implements SerialPortEventListener {
	BufferedInputStream canBis; //캔통신으로 input되는 데이터를 읽기 위해 오픈된 스트림, 캔통신할수 있는 객체
	OutputStream arduinoOs; //아두이노로 시리얼출력을 위해 작업
	SerialConnect arduino;
	SerialConnect can;
	
	String hexmessage;
	String result;
	String fromMaster;
	String fromSlave;
	String hexdata;
	int start;
	int end;

	public CanSerialListener(BufferedInputStream canBis, SerialConnect arduino, SerialConnect can) {
		this.canBis = canBis;
		this.arduino = arduino;
		this.can = can;
		
		arduinoOs = arduino.getOut();
		
	}
	//데이터가 전송될때마다 호출되는 메소드 
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch(event.getEventType()) { 
		case SerialPortEvent.DATA_AVAILABLE:
			//데이터가 도착하면 어떻게 할 것인지 정의
			byte[] CanreadBuffer = new byte[128];
			try {
				while(canBis.available()>0) {//타입 검사해서, data_avaliable이면 데이터를 지속적으로 읽음
					int numbyte = canBis.read(CanreadBuffer);
				}
				String cData = new String(CanreadBuffer);
				System.out.println("Can 시리얼 포트로 전송된 데이터:"+cData);
				
				if(arduinoOs!=null) {
					if (cData.trim().startsWith(":W2800000002")) {
						//하위 ECU가 데이터를 보낼 경우 id=00000002
						fromSlave = cData.trim();
					} else if (cData.trim().startsWith(":U2800000001")) {
						//상위 ECU가 데이터를 보낼 경우 id=00000001
						fromMaster = cData.trim();
					}
					//can에서 읽은 msg가 slave에서 넘어온 데이터와 같을 때
					if (cData.trim().equals(fromMaster)) {
						result = getHexToDec(cData);
						System.out.println("상위에서 보내온 데이터(Hex > Dec)=>" + result);
						if(result.charAt(0)=='S') {
							for(int i=0;i<result.length();i++) {
								arduinoOs.write(result.charAt(i));
							}
						}else {
							arduinoOs.write(result.charAt(0));
						}
						
					} else {
						System.out.println(":U28 ERROR");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getHexToDec(String hex) {
		StringBuilder sb = new StringBuilder();
		long v = 0;
		String str = "";
		String data = hex.substring(12, 28);
		for (int i = 0; i <= data.length() - 2; i += 2) {
			start = i;
			end = i + 2;
			//2글자(16진수) -> 10진수 v로 변환해준다.
			v = Long.parseLong(data.substring(start, end), 16);
			sb.append((char)v);
		}

		return sb.toString().trim();
	}
}
