package server;

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
	boolean status;
	CANReadWriteTest canReadWriteTest;

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
	}

	public void sendMessage(String msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				st = new StringTokenizer(msg,"/");
				String protocol = st.nextToken();
				String message = st.nextToken();
				System.out.println("자르기 전: " + msg);
				System.out.println("프로토콜:"+protocol+",메시지:"+message);
				
				// 캔에게 메시지 전
				/*if (!msg.equals("")) {
					if(protocol.equals("sonic")){
						filterUS(message);
						pw.println(msg);
						pw.flush();
					}else if (protocol.equals("speed")){
						pw.println(msg);
						pw.flush();
					}	
				}*/
				
			}
		}).start();
	}

	class ReceiveThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					String msg = br.readLine();
					if (serialClient != null) {
						if (msg.equals("auto_on")) {
							status = true;
							// System.out.println(msg+"::::auto on");
							serialClient.sendMessageToArduino('1');
						} else if (msg.equals("auto_off")) {
							serialClient.sendMessageToArduino('0');
							status = false;
						}
						if (status) {
							// filteringMsg(msg);
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void filterUS(String msg) {
		if (msg.equals(""))
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
	/*
	 * private void filteringMsg(String msg) {//+3 -3 30 60 90구분
	 * System.out.println("클라이언트가 받은 메시지"+msg); st = new StringTokenizer(msg,
	 * "/"); String protocol = st.nextToken(); //app에게 받은 메시지 수행!!
	 * if(protocol.equals("tablet")) { String message = st.nextToken();
	 * if(message.equals("plus3")) { String id = "00000010";//송신할 메시지의 구분 id
	 * String data = "0000000000000003";//송신할 데이터 -> 내 마음대로 정해주기, 16글자는 맞춰야함
	 * String mesaage = id+data; canReadWriteTest.send(mesaage); }else
	 * if(message.equals("minus3")){ String id = "00000010";//송신할 메시지의 구분 id
	 * String data = "1000000000000003";//송신할 데이터 -> 내 마음대로 정해주기, 16글자는 맞춰야함
	 * String mesaage = id+data; canReadWriteTest.send(mesaage); }else
	 * if(message.equals("speed30")){ String id = "00000010";//송신할 메시지의 구분 id
	 * String data = "0000000000000030";//송신할 데이터 -> 내 마음대로 정해주기, 16글자는 맞춰야함
	 * String mesaage = id+data; canReadWriteTest.send(mesaage); }else
	 * if(message.equals("speed60")){ String id = "00000010";//송신할 메시지의 구분 id
	 * String data = "0000000000000060";//송신할 데이터 -> 내 마음대로 정해주기, 16글자는 맞춰야함
	 * String mesaage = id+data; canReadWriteTest.send(mesaage); }else
	 * if(message.equals("speed90")){ String id = "00000010";//송신할 메시지의 구분 id
	 * String data = "0000000000000090";//송신할 데이터 -> 내 마음대로 정해주기, 16글자는 맞춰야함
	 * String mesaage = id+data; canReadWriteTest.send(mesaage); } } }
	 */
}
