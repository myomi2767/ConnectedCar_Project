package android.remote.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialArduinoControl {
	OutputStream out;
	public SerialArduinoControl() {
		
	}
	public void connect(String portName) {
		try {
			CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if(commPortIdentifier.isCurrentlyOwned()) {
				System.out.println("포트 사용 불가");
			}else {
				System.out.println("포트 사용 가능");
				CommPort commPort = commPortIdentifier.open("basic_serial",5000);
				if(commPort instanceof SerialPort) {
					System.out.println("SerialPort");
					SerialPort serialPort = (SerialPort) commPort;
					//SerialPort에 대한 설정
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					InputStream in = serialPort.getInputStream();
					out = serialPort.getOutputStream();
				}else {
					System.out.println("SerialPort만 작업할 수 있습니다.");
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
}
