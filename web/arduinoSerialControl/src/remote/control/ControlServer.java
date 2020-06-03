package remote.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ControlServer {
	private ServerSocket server;
	HashMap<String, User> userlist = new HashMap<String, User>();
	HashMap<String, User> carlist = new HashMap<String, User>();
	HashMap<String, User> infolist = new HashMap<String, User>();
	
	public static void main(String[] args) {
		new ControlServer().connect();
	}
	public void connect() {
		try {
			server = new ServerSocket(12345);
			if(server != null) {
				connecton();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void connecton() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Socket client = server.accept();
						String ip = client.getInetAddress().getHostAddress();
						System.out.println(ip+"가 접속\n");
						
						User user = new User(client, userlist, carlist, infolist);
						user.start();
					
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		thread.start();
	}
}
