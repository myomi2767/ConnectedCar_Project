package latteserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import can.CANReadWriteTest;
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
	CANReadWriteTest canReadWriteTest;
	//CANReadWriteTest canReadWriteTest;
	AndroidClient client;
	public SerialClient(AndroidClient androidClient,CANReadWriteTest canReadWriteTest) {
		System.out.println("시리얼클라이언트 포트 불러오는곳");
		this.client = androidClient;
		this.canReadWriteTest = canReadWriteTest;
		connect("COM14","masterLatte");
		setSpeedZero();
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
	
	public void sendMessageToArduino(byte[] data) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("char형"+data);
					out.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
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
	public void setSpeedZero() {
		String id = "00000001";// 송신할 메시지의 구분 id
		String data = "0000000000000011";// 송신할 데이터 -> 내 마음대로 정해주기,
											// 16글자는 맞춰야함
		String mesaage = id + data;
		canReadWriteTest.send(mesaage);
	}

}
