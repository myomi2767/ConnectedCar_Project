package latteserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import can.CANReadWriteTest;

public class AndroidClient {
	Socket client;
	InputStream in;
	InputStreamReader ir;
	BufferedReader br;
	PrintWriter pw;
	StringTokenizer st;
	//static boolean status = true;
	CANReadWriteTest canReadWriteTest;
	String status;
	SerialClient serialClient;

	public AndroidClient(Socket client) {
		this.client = client;
		ioWork();
		new ReceiveThread().start();
	}

	public void ioWork() {
		try {
			in = client.getInputStream();
			ir = new InputStreamReader(in);
			br = new BufferedReader(ir);
			pw = new PrintWriter(client.getOutputStream(), true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 시리얼과 연동할 수 있도록 연결해주는 메서드
	public void setSerialClient(SerialClient client, CANReadWriteTest canReadWriteTest) {
		this.serialClient = client;
		this.canReadWriteTest = canReadWriteTest;
		this.canReadWriteTest.setAndroidClient(this);
	}

	public void sendMessage(String msg) {
		System.out.println("확인중" + msg);
		new Thread(new Runnable() {
			@Override
			public void run() {
				st = new StringTokenizer(msg, ":");
				String protocol = st.nextToken();
				if (st.hasMoreTokens()) {
					String message = st.nextToken();
					//System.out.println("자르기 전: " + msg);
					System.out.println("프로토콜:" + protocol + ",메시지:" + message);

					if (!msg.equals("")) {
						if (protocol.equals("sonic")) {
							if (message != null) {
								filterUS(message);
								pw.println(msg);
								pw.flush();
							}
						} else if (protocol.equals("speed")) {
							System.out.println("rc카가 보내는 속도:::>>" + message);
							pw.println(msg);
							pw.flush();
						}
					}
				}

			}
		}).start();
	}

	class ReceiveThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					String msg = br.readLine();
					System.out.println(msg+":::::");
					if (serialClient != null) {
						
						if (msg.equals("system:auto_on")) {
							status = "on";
							String id = "00000001";// 송신할 메시지의 구분 id
							String data = "0000000000000008";// 송신할 데이터 -> 내 마음대로 정해주기, 16글자는
																// 맞춰야함
							String mesaage = id + data;
							canReadWriteTest.send(mesaage);
						} else if (msg.equals("system:auto_off")) {
							
							String id = "00000001";// 송신할 메시지의 구분 id
							String data = "0000000000000009";// 송신할 데이터 -> 내 마음대로 정해주기, 16글자는
																// 맞춰야함
							String mesaage = id + data;
							canReadWriteTest.send(mesaage);
							status = "off";
							
						}
						//System.out.println("auto_status222222"+status);
						if (status.equals("on")) {
							//System.out.println("status"+status);
							filteringMsg(msg);
						}
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void filterUS(String msg) {
		if (msg.equals("system:auto_on"))
			return;

		if (Integer.parseInt(msg) > 50) {
			String id = "00000001";// 송신할 메시지의 구분 id
			String data = "0000000000000007";// 송신할 데이터 -> 내 마음대로 정해주기, 16글자는
												// 맞춰야함
			String mesaage = id + data;
			canReadWriteTest.send(mesaage);
		} else if (Integer.parseInt(msg) <= 50) {
			String id = "00000001";// 송신할 메시지의 구분 id
			String data = "0000000000000006";// 송신할 데이터 -> 내 마음대로 정해주기, 16글자는
												// 맞춰야함
			String mesaage = id + data;
			canReadWriteTest.send(mesaage);
		}

	}

	private void filteringMsg(String msg) {// +3 -3 30 60 90구분
		System.out.println("클라이언트가 받은 메시지" + msg);
		if (msg.equals("system:auto_on"))
			return;

		st = new StringTokenizer(msg, ":");
		String protocol = st.nextToken(); // app에게 받은 메시지 수행!!
		String value = st.nextToken();
		if (protocol.equals("tablet")) {
			if (value.equals("plus3")) {
				String id = "00000001";// 송신할 메시지의 구분 id
				String data = "0000000000000001";// 송신할 데이터 -> 내 마음대로 정해주기,
													// 16글자는 맞춰야함
				String mesaage = id + data;
				canReadWriteTest.send(mesaage);
				//System.out.println("버튼 plus3 누름");
			} else if (value.equals("minus3")) {
				String id = "00000001";// 송신할 메시지의 구분 id
				String data = "0000000000000002";// 송신할 데이터 -> 내 마음대로 정해주기,
													// 16글자는 맞춰야함
				String mesaage = id + data;
				canReadWriteTest.send(mesaage);
				//System.out.println("버튼 minus3 누름");
			} else if (value.equals("speed30")) {
				String id = "00000001";// 송신할 메시지의 구분 id
				String data = "0000000000000003";// 송신할 데이터 -> 내 마음대로 정해주기,
													// 16글자는 맞춰야함
				String mesaage = id + data;
				canReadWriteTest.send(mesaage);
				//System.out.println("버튼 speed30 누름");
			} else if (value.equals("speed60")) {
				String id = "00000001";// 송신할 메시지의 구분 id
				String data = "0000000000000004";// 송신할 데이터 -> 내 마음대로 정해주기,
													// 16글자는 맞춰야함
				String mesaage = id + data;
				canReadWriteTest.send(mesaage);
			} else if (value.equals("speed90")) {
				String id = "00000001";// 송신할 메시지의 구분 id
				String data = "0000000000000005";// 송신할 데이터 -> 내 마음대로 정해주기,
													// 16글자는 맞춰야함
				String mesaage = id + data;
				canReadWriteTest.send(mesaage);
			} else if (value.equals("speed0")) {
				
				//System.out.println("버튼 minus3 누름");
			}
		}
	}

}
