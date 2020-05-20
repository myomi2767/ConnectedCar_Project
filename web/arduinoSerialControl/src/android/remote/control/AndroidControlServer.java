package android.remote.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class AndroidControlServer {
	ServerSocket server;
	Socket client;
	Vector<AppUser> userlist = new Vector<AppUser>();
	Vector<Car> carlist = new Vector<Car>();
	public static void main(String[] args) {
		new AndroidControlServer().serverStart();
	}
	public void serverStart() {
		try {
			server = new ServerSocket(12345);
			if(server != null) {
				connecton();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void connecton() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						client = server.accept();
						String ip = client.getInetAddress().getHostAddress();
						System.out.println(ip+"가 접속\n");
						//user인지 차인지 판단해야 하는 부분
						if(client.getInetAddress().getHostName()!=null) {
							AppUser user = new AppUser(client, userlist);
							user.start();
						}else {
							Car car = new Car(client, carlist);
							car.start();
						}
						//new ReceiverThread(client).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		thread.start();
	}
}
