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
	Boolean power;

	public CanSerialListener(BufferedInputStream canBis, SerialConnect arduino, SerialConnect can) {
		this.canBis = canBis;
		this.arduino = arduino;
		this.can = can;
		
		arduinoOs = arduino.getOut();
		System.out.println("arduinoOs-------------------"+arduinoOs);
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
				//일단 데이터를 읽으면, print하는 기능을 넣었다. 
				//전송되는 메시지를 검사해서 적절하게 다른 장치를 제어하거나 
				//Car클라이언트 객체로 전달해서 서버로 전송되도록 처리 
				
				//캔으로 수신된 데이터가 0000000000000011면 LED끄기
				//캔으로 수신된 데이터가 0000000000000000이면 LED켜기
				
				/*
				 * 1. 아두이노와 시리얼통신할 수 있도록 output스트림얻기
				 * 	=> 생성자에서 한 번 작업할 수 있도록 처리
				 * 2. output스트림으로 '0', '1' 내보내기 
				 * 	=> CAN으로 수신된 데이터를 비교해서 
				 * 	:U28000000000000000000000003F
				 * 
				 */
				
				if(arduinoOs!=null) {
					System.out.println("===========>>>>>>>>");
					if(cData.trim().equals(":U2800000001000000000000000848")){
						//Auto_on
						power=true;
						System.out.println("상태:::"+power);
					}
					else if(cData.trim().equals(":U2800000001000000000000000949")){
						//Auto_off
						arduinoOs.write('9');
						power=false;
						System.out.println("상태:::"+power);
					}
					System.out.println("상태:::"+power);
					if(power=true) {
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
						}else if(cData.trim().equals(":U2800000001000000000000001142")){
							//velocity를 0으로 초기화
							arduinoOs.write('8');                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
						}
					}
					
					/*if(cData.trim().equals(":U2800000001000000000000000343")) {
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
					}else if(cData.trim().equals(":U2800000001000000000000000848")){
						//Auto_on
						arduinoOs.write('8');
					}
					else if(cData.trim().equals(":U2800000001000000000000000949")){
						//Auto_off
						arduinoOs.write('9');
					}*/
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
