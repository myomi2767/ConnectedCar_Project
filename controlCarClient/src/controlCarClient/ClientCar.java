package controlCarClient;
//서버에서 데이터 받고, 캔으로 데이터 보내는 작업 수행
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
//서버에 붙이는 클라이언트 객체로
public class ClientCar {
	Socket socket;
	
	InputStream is;
	InputStreamReader isr;
	BufferedReader br;
	
	OutputStream os;
	PrintWriter pw;
	
	StringTokenizer st;
	String carId;
	
	String sum; //hex로 변환하기 위한 sum 변수
	String zerosum; //0을 추가하기 위한 변수
	String HexCode; //변환된 HexCode로 받은 변수
	// CAN으로 송신할 id, data;
	String id="";
	String data="";
	char[] data_arr;
	
	CANReadWriteTest canReadWriteTest;
	public static void main(String[] args) {
		new ClientCar().connect();
	}
	public void connect() {
		try {
			socket = new Socket("192.168.43.175", 12345);
			System.out.println("접속 성공!!!");
			if(socket!=null) {
				carId="12가1234";
				ioWork();
				
				//CAN통신 열기
				canReadWriteTest = new CANReadWriteTest("COM6", socket, carId);
			}
			Thread t1 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                while (true) {
	                    String msg;
	                    try {
	                        msg = br.readLine();
	                        System.out.println("서버로 부터 수신된 메시지>>"
	                                + msg);
	                        filteringMsg(msg);
	                    } catch (IOException e) {
	                        try {
	                            //2. 서버쪽에서 연결이 끊어지는 경우 사용자는 자원을 반납======
	                            //자원반납
	                            is.close();
	                            isr.close();
	                            br.close();
	                            os.close();
	                            pw.close();
	                            socket.close();
	                          
	
	                        } catch (IOException e1) {
	                            // TODO Auto-generated catch block
	                            e1.printStackTrace();
	                        }
	                        break;//반복문 빠져나가도록 설정
	                    }
	                }
	            }
	        });
	        t1.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void filteringMsg(String msg) {
		HexCode ="";
		sum = "";
		zerosum = "";
		
		System.out.println("클라이언트가 받은 메시지=>"+msg);
		st = new StringTokenizer(msg, ":");
		String protocol = st.nextToken();
		//app에게 받은 메시지 수행!!
		if(protocol.equals("job")) {
			String message = st.nextToken();
			System.out.println("제어구분자==>"+message);
			HexCode = convertToHex(message);
			
			id = "00000001";//송신할 메시지의 구분 id
			data = HexCode.trim();//송신할 데이터 
			
			String sendmsg = id+data;
			canReadWriteTest.send(sendmsg);
		}
	}
	
	public String convertToHex(String aData) {
		data_arr = aData.trim().toUpperCase().toCharArray();//대문자로 변경 후 한 글자씩 data_arr에 넣기
		for (int i = 0; i < data_arr.length; i++) {
			sum = sum + Integer.toHexString(data_arr[i]);//data_arr에서 값 하나씩 빼서 hex로 변환
		}
		for( int i = 0; i < 16 - sum.length();i++) {
			zerosum = zerosum + "0";
		}
		String result = zerosum.concat(sum);//16개에 맞춰서 => 0000000... + hex값
		System.out.println("Dec > Hex 변환==>"+result);
		return result;
	}
	
	public void ioWork() {
		try {
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			os = socket.getOutputStream();
			pw = new PrintWriter(os, true);
			pw.println("car:"+carId);
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
