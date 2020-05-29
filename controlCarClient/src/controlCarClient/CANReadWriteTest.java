package controlCarClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.TooManyListenersException;

import gnu.io.SerialPort;
import serial.SerialConnect;
//테스트할 때 이거 실행하기
public class CANReadWriteTest {
	SerialConnect serialConnect;//CAN시리얼 포트 연결
	OutputStream out; // CAN과 output통신할 스트림
	Socket socket; //CAN에서 서버로 보내줄 pw
	String carId;
	
	public CANReadWriteTest(String portname, Socket socket, String carId) {
		this.socket = socket;
		this.carId = carId;
		//시리얼 통신을 위해 연결
		serialConnect = new SerialConnect();
		serialConnect.connect(portname, this.getClass().getName());
		
		//input, output작업을 하기 위해 리스ㄴㅓ를 port에 연결
		SerialPort commport = (SerialPort)serialConnect.getCommPort();
		SerialListener listener = new SerialListener(serialConnect.getBis(),socket,carId);
		try {
			commport.addEventListener(listener);
			commport.notifyOnDataAvailable(true);//데이터 발생될 때마다 알림
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		//OutputStream얻기
		out = serialConnect.getOut();
		
		//처음 CAN통신을 위한 준비 작업을 할 때는 수신가능한 메시지를 보낼 수 있도록
		new Thread(new CANWriteThread()).start();
	}//end 생성자
	
	//온도가 올라가면 두번째 캔 장비에 연결되어있는 led한테 작업을 시키겠다 <- 이런식으로 쓸때 send메소드 사용
	public void send(String msg) {
		new Thread(new CANWriteThread(msg)).start();
	}
	
	class CANWriteThread implements Runnable{
		String data;
		CANWriteThread(){
			//처음에 수신가능 설정
			this.data = ":G11A9\r";
		}
		
		CANWriteThread(String msg){
			//메시지를 보낼때마다 사용
			this.data = convert_data(msg);
		}
		//메시지를 구성하는 메소드
		//msg = msg의 id + 데이터
		public String convert_data(String msg) {
			msg = msg.toUpperCase();//메시지를 대문자로 변환
			msg = "W28"+msg;//W28은 송신 데이터의 구분 기호임.
			//msg = W28 00000000 0000000000000000
			//데이터프레임에 대한 체크섬을 생성 - 앞뒤문자 빼고 나머지를 더한 후 0XFF로 &연산
			char[] data_arr = msg.toCharArray();
			int sum = 0;
			for(int i = 0;i<data_arr.length; i++) {
				sum = sum + data_arr[i];
			}
			sum = (sum & 0xff); // &연산
			//보낼 메시지 최종 완성
			String result = ":" + msg + Integer.toHexString(sum).toUpperCase()+"\r";//이게 can통신할 때 규칙임, Integer.toHexString(sum) -> 체크섬
			return result;
		}
		
		@Override
		public void run() {
			byte[] outputdata = data.getBytes();//데이터를 내보낼 땐 바이트 배열로 내보내자.
			try {//수신준비 완료했다고 브로드캐스트
				out.write(outputdata);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}