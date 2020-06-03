package controlCarClient;
// 캔에서 데이터 받고, 서버로 보내는 작업 수행 
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serial.SerialConnect;
//시리얼포트를 통해서 데이터가 전송되었을 때 실행되는 클래스
public class SerialListener implements SerialPortEventListener {
	BufferedInputStream bis;//캔통신으로 input되는 데이터를 읽기 위해 오픈된
	Socket socket;
	OutputStream os;
	PrintWriter pw;
	String carId;
	
	String hexmessage;
	String result;
	String fromMaster;
	String fromSlave;
	String hexdata;
	int start;
	int end;
	
	public SerialListener(BufferedInputStream bis, Socket socket, String carId) {
		this.bis = bis;
		this.socket = socket;
		this.carId = carId;
		try {
			os = socket.getOutputStream();
			pw = new PrintWriter(os, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				System.out.println("Can시리얼 포트로 전송된데이터=>"+data);
				if(pw!=null) {
					if (data.trim().startsWith(":W2800000001")) {
						fromMaster = data.trim();
					} else if (data.trim().startsWith(":U2800000002")) {
						fromSlave = data.trim();
					}
					//can에서 읽은 data가 slave에서 넘어온 데이터와 같을 때
					if (data.trim().equals(fromSlave)) {
						result = getHexToDec(data);
						System.out.println("하위ECU에서 보내온 데이터(Hex > Dec)=> " + result);
						pw.println("job:"+result+":car:"+carId);
						pw.flush();
					} else {
						System.out.println(":U28 ERROR");
					}
				}
				//내가 추가한 부분.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getHexToDec(String hex) {
		StringBuilder sb = new StringBuilder();
		long v = 0;
		String data = hex.substring(12, 28);

		for (int i = 0; i <= data.length() - 2; i += 2) {
			start = i;
			end = i + 2;
			v = Long.parseLong(data.substring(start, end), 16);
			sb.append((char)v);
		}
		return sb.toString().trim();
	}
}
