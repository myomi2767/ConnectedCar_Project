package can;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import server.AndroidClient;
import server.SerialArduinoListener;
import server.SerialCANListener;
import server.SerialClient;

//테스트할 때 이거 실행하기
public class CANReadWriteTest {
	SerialClient serialClient;// CAN시리얼 포트 연결
	OutputStream out; // CAN과 output통신할 스트림
	InputStream in;
	BufferedInputStream bis;
	//CommPort commPort;
	SerialCANListener listener;

	public CANReadWriteTest(String portname, String appName) {
		try {
			CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(portname);
			if(commPortIdentifier.isCurrentlyOwned()) {
				//System.out.println("CAN포트사용 불가");
			}else {
				//
				System.out.println("CAN포트사용 가능");
				CommPort commPort = commPortIdentifier.open(appName,5000);
					if(commPort instanceof SerialPort) {
						//System.out.println(commPort);
						SerialPort serialPort = (SerialPort)commPort;
						serialPort.setSerialPortParams(38400,
														SerialPort.DATABITS_8,
														SerialPort.STOPBITS_1,
														SerialPort.PARITY_NONE);
						in = serialPort.getInputStream();
						bis = new BufferedInputStream(in);
						out = serialPort.getOutputStream();
						listener = new SerialCANListener(bis);
						
						try {
							serialPort.addEventListener(listener);
							serialPort.notifyOnDataAvailable(true);
						} catch (TooManyListenersException e) {
							e.printStackTrace();
						}
						new Thread(new CANWriteThread()).start();
					//	new ReceiveThread().start();
					}else {
						//System.out.println("CanPort만 작업할 수 있습니다.");
					}
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setAndroidClient(AndroidClient client) {
		listener.setAndroidClient(client);
	}

	// 온도가 올라가면 두번째 캔 장비에 연결되어있는 led한테 작업을 시키겠다 <- 이런식으로 쓸때 send메소드 사용
	public void send(String msg) {
		System.out.println("canread:::" + msg);
		new Thread(new CANWriteThread(msg)).start();
	}
	
	/*public String readMsg() {
		return listener.getMessage();
	}*/

	class CANWriteThread implements Runnable {
		String data;

		CANWriteThread() {
			// 처음에 수신가능 설정
			this.data = ":G11A9\r";
		}

		CANWriteThread(String msg) {
			// 메시지를 보낼때마다 사용
			this.data = convert_data(msg);
		}

		// 메시지를 구성하는 메소드
		// msg = msg의 id + 데이터
		public String convert_data(String msg) {// 여기에 라인트레이서에 보낼 속도값 처리하기!!
			msg = msg.toUpperCase();// 메시지를 대문자로 변환
			msg = "W28" + msg;// W28은 송신 데이터의 구분 기호임.
			// msg = W28 00000000 0000000000000000
			// 데이터프레임에 대한 체크섬을 생성 - 앞뒤문자 빼고 나머지를 더한 후 0XFF로 &연산
			char[] data_arr = msg.toCharArray();
			int sum = 0;
			for (int i = 0; i < data_arr.length; i++) {
				sum = sum + data_arr[i];
			}
			sum = (sum & 0xff); // &연산
			// 보낼 메시지 최종 완성
			String result = ":" + msg + Integer.toHexString(sum).toUpperCase() + "\r";// 이게
																						// can통신할
			System.out.println("메인에서 서브로 보낼 메시지 : " + result);																	// 때
																						// 규칙임,
																						// Integer.toHexString(sum)
																						// ->
																						// 체크섬
			return result;
		}

		@Override
		public void run() {
			byte[] outputdata = data.getBytes();// 데이터를 내보낼 땐 바이트 배열로 내보내자.
			
			try {// 수신준비 완료했다고 브로드캐스트
				out.write(outputdata);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}