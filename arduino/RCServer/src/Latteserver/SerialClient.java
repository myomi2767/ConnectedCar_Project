package server;

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

public class SerialClient {
	SerialArduinoListener listener;
	OutputStream out;
	String msg;

	InputStream in;
	BufferedInputStream bis;
	CommPort commPort;
	//CANReadWriteTest canReadWriteTest;
	AndroidClient client;
	public SerialClient(AndroidClient androidClient) {
		//System.out.println("시리얼클라이언트 포트 불러오는곳");
		this.client = androidClient;
		connect("COM10","masterLatte");
	}
	public void connect(String portName,String appName) {
		try {
			CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if(commPortIdentifier.isCurrentlyOwned()) {
				//System.out.println("포트사용 불가");
			}else {
				//System.out.println("포트사용 가능");
					CommPort commPort = commPortIdentifier.open("basic_serial",5000);
					if(commPort instanceof SerialPort) {
						//System.out.println("SerialPort");
						SerialPort serialPort = (SerialPort)commPort;
						serialPort.setSerialPortParams(38400,
														SerialPort.DATABITS_8,
														SerialPort.STOPBITS_1,
														SerialPort.PARITY_NONE);
						in = serialPort.getInputStream();
						out = serialPort.getOutputStream();
						listener = new SerialArduinoListener(in,client);
						
						try {
							serialPort.addEventListener(listener);
						} catch (TooManyListenersException e) {
							e.printStackTrace();
						}
						serialPort.notifyOnDataAvailable(true);
					//	new ReceiveThread().start();
					}else {
						//System.out.println("SerialPort만 작업할 수 있습니다.");
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
	
	/*public void setAndoridClient(AndroidClient client) {
		this.androidClient = client;
	}*/
	
	public void sendMessageToArduino(char data) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					out.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/*class ReceiveThread extends Thread {
		@Override
		public void run() {
			while(true) {
				System.out.println("listener.getStatus()"+listener.getStatus());
				System.out.println("listener.getDto().getMsg()"+listener.getDto().getMsg());
				if(listener.getStatus()) {
					msg = listener.getDto().getMsg();
					System.out.println("------------"+msg);
				} else {
					msg = "";
					System.out.println("+++++++++++++++"+msg);
				}
				System.out.println("현재 받은 메시지: " + msg);
				client.sendMessage(msg);
				//캔에 보낼 초음파에서 받은 속도
				//sendUSMsg(msg);//
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
	public BufferedInputStream getBis() {
		return bis;
	}
	public OutputStream getOut() {
		return out;
	}
	
	public InputStream getIn() {
		return in;
	}
	
	public CommPort getCommPort() {
		return commPort;
	}
	/*private void sendUSMsg(String msg){
		String id = "00000011";//송신할 메시지의 구분 id
		String data = "0000000000000000";//송신할 데이터 -> 내 마음대로 정해주기, 16글자는 맞춰야함
		String mesaage = id+data;
		canReadWriteTest.send(mesaage);
	}*/

}
