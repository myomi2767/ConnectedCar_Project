package android.remote.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class AndroidControlServer {
	ServerSocket server;
	Socket client;
	BufferedReader br;
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
						br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						String msg = br.readLine();
						System.out.println("서버가 받은 메시지"+msg);
						if(msg.equals("user")) {
							AppUser user = new AppUser(client, userlist, carlist);
							user.start();
						}else if(msg.equals("car")){
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
