package slave;

import java.io.IOException;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.SerialPort;

public class ECUControl {
	SerialConnect canConnect; // CAN시리얼포트 연결
	SerialConnect arduinoConnect; // arduino시리얼 포트 연결
	OutputStream out; // CAN과 output통신할 스트림
	public static void main(String[] args) {
		ECUControl canObj = new ECUControl();
	}
	
	public ECUControl() {
		canConnect = new SerialConnect();
		canConnect.connect("COM22", "Can");
		arduinoConnect = new SerialConnect();
		arduinoConnect.connect("COM5", "Arduino");
		
		// input, output작업을 하기 위해 리스터를 port에 연결
		SerialPort canCommport = (SerialPort) canConnect.getCommPort();
		CanSerialListener canListener = new CanSerialListener(canConnect.getBis(), 
				arduinoConnect, canConnect);
		
		SerialPort arduinoCommport = (SerialPort) arduinoConnect.getCommPort();
		ArduinoSerialListener arduinoListener = new ArduinoSerialListener(arduinoConnect.getBis(),
				arduinoConnect, canConnect);
		try {
			canCommport.addEventListener(canListener);
			canCommport.notifyOnDataAvailable(true);
			arduinoCommport.addEventListener(arduinoListener);
			arduinoCommport.notifyOnDataAvailable(true);
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// output스트림 얻기
		out = canConnect.getOut();

		// 처음 CAN통신을 위한 준비 작업을 할 때에는 수신가능한 메시지를 보낼 수 있도록
		new Thread(new CANWriteThread()).start(); // 무한루프 안돌리고 한번 보내면 끝
		System.out.println("CAN통신이 시작되었습니다");
	} // end 생성자

	class CANWriteThread implements Runnable {
		String data; // 송신할 데이터를 저장할 변수

		CANWriteThread() { // ==================처음에 수신가능 설정
			this.data = ":G11A9\r";
		}

		@Override
		public void run() {
			byte[] outputdata = data.getBytes();
			try {
				out.write(outputdata);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	
}
