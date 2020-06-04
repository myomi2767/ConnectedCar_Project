package latteserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialCANListener implements SerialPortEventListener {
	BufferedInputStream bis;// 캔통신으로 input되는 데이터를 읽기 위해 오픈된
	OutputStream os;
	String resultmsg;
	String speed;
	String hexmessage;
	String result;
	String fromMaster;
	String fromSlave;
	String hexdata;
	int start, end;
	StringTokenizer st;
	
	AndroidClient androidClient;
	SerialClient serialClient;//계기판 속도를 LCD로 넘끼끼

	public SerialCANListener(BufferedInputStream bis) {
		this.bis = bis;
		// serialClient = new SerialClient();
		// System.out.println("캔리스너 포트 불러오는 곳");
		resultmsg = "";
		// 내가 추가한 부분(아두이노의 데이터를 받기위한 작업)
	}

	// 데이터가 전송될 때마다 호출되는 메소드
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		// DATA_AVAILABLE => 데이터가 도착하면 어떤거를 하겠다는 의미
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[128];
			try {
				while (bis.available() > 0) {// 데이터 전체를 읽기
					int numbyte = bis.read(readBuffer);// 읽은 내용이 readBuffer에 셋팅됨
				}
				String data = new String(readBuffer);
				String msg = data;
				// CanListener가 w28과 U28 둘 다 읽기 때문에 나눠줘야한다.
				if (msg.trim().startsWith(":W2800000001")) {
					fromMaster = msg.trim();
					//System.out.println("Master가 Can시리얼 포트로 전송한데이터=>"+fromMaster);
				} else if (msg.trim().startsWith(":U2800000002")) {
					fromSlave = msg.trim();
					//System.out.println("Slave가 Can시리얼 포트로 전송한데이터=>"+fromSlave);
				}
				//can에서 읽은 msg가 slave에서 넘어온 데이터와 같을 때
				if (msg.trim().equals(fromSlave)) {
					result = getHexToDec(msg);
					System.out.println("slave에서 보내온 데이터:::> " + result);
					resultmsg = "speed:"+ result;
					androidClient.sendMessage(resultmsg);
					//byte[] speed =  result.GetBytes(result);
		
					//serialClient.sendMessageToArduino(speed);
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setAndroidClient(AndroidClient client) {
		this.androidClient = client;
	}
	public String getHexToDec(String hex) {
		long v = 0;
		String str = "";
		String data = hex.substring(12, 28);

		//System.out.println("id 뒤 메시지 16자리만 받기" + data);
		hexdata = String.valueOf(Integer.parseInt(data)).trim();
		//System.out.println("앞에 00000제외한 헥사 코드"+hexdata);
		for (int i = 0; i <= hexdata.length() - 2; i += 2) {
			start = i;
			end = i + 2;
			//2글자(16진수) -> 10진수 v로 변환해준다.
			v = Long.parseLong(hexdata.substring(start, end), 16);
			//slave가 보낸 real데이터 str 
			str = str + String.valueOf((char) v);
		}

		return str;
	}
	
}
