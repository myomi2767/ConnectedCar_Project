package slave;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

//시리얼 통신을 통해서 데이터가 전송되었을 때 실행되는 클래스
//전송되어 들어오는 데이터를 읽는 작업만 한다. 
public class CanSerialListener implements SerialPortEventListener {
	BufferedInputStream canBis; //캔통신으로 input되는 데이터를 읽기 위해 오픈된 스트림, 캔통신할수 있는 객체
	OutputStream arduinoOs; //아두이노로 시리얼출력을 위해 작업
	SerialConnect arduino;
	SerialConnect can;

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
				System.out.println("Can 시리얼 포트로 전송된 데이터: "+cData);

				if(arduinoOs!=null) {
				
					if(cData.trim().equals(":U2800000001000000000000000343")) {
						//speed30
						System.out.println("찍히니");
						arduinoOs.write('1');
					}else if(cData.trim().equals(":U2800000001000000000000000444")) {
						//speed60
						System.out.println("찍히니");
						arduinoOs.write('2');
					}else if(cData.trim().equals(":U2800000001000000000000000545")){
						//speed90
						arduinoOs.write('3');
					}else if(cData.trim().equals(":U2800000001000000000000000141")){
						//속도 +3
						arduinoOs.write('4');
					}
					else if(cData.trim().equals(":U2800000001000000000000000242")){
						//속도 -3
						arduinoOs.write('5');
					}
					else if(cData.trim().equals(":U2800000001000000000000000646")){
						//차간거리 50cm미만
						arduinoOs.write('6');
					}
					else if(cData.trim().equals(":U2800000001000000000000000747")){
						//차간거리 50cm이상
						arduinoOs.write('7');
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
