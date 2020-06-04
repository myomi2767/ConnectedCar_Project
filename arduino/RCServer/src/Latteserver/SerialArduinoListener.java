package latteserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialArduinoListener implements SerialPortEventListener{
	private InputStream in;
	private DistanceDTO dto;
	private boolean readingStatus;
	PrintWriter pw;//서버와 통신
	AndroidClient androidClient;
	public SerialArduinoListener(InputStream in,AndroidClient androidClient) {
		super();
		this.in = in;
		dto = new DistanceDTO();
		readingStatus = false;
		this.androidClient = androidClient;
	}
	//이벤트가 발생할때마다 호출되는 메소드
	//발생한 이벤트의 정보를 담고있는 객체 - SerialPortEvent
	@Override
	public void serialEvent(SerialPortEvent event) {
		//전송된 데이터가 있는 경우 데이터를 읽어서 콘솔에 출력
		if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE) {
			byte[] buffer = new byte[1024]; //사이즈(1024)는 임의로 설정
			int len = -1;
			try {
				while((len=in.read(buffer))!=-1) {	
					String msg = new String(buffer,0,len);
					//System.out.println(msg); //buffer의0번부터len까지
					//dto.setMsg(new String(buffer,0,len));
					if (msg!=null) {
						androidClient.sendMessage("sonic:"+msg);
					}
					Thread.sleep(1000);
					//밖에서 포트가 데이터값을 읽고 있는지 아닌지 판단해 밖에서 접근할 수 있게 함
					if(len == -1) readingStatus = false;
					else readingStatus = true;
					
				}
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public DistanceDTO getDto() {
		return dto;
	}
	
	public boolean getStatus() {
		return readingStatus;
	}
	public void setServerOutputStream(PrintWriter pw) {
		// TODO Auto-generated method stub
		
	}

}
