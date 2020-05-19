package android.remote.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AndroidControlServer {
	private ServerSocket server;
	public void connect() {
		try {
			server = new ServerSocket(12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					Socket client;
					try {
						client = server.accept();
						String ip = client.getInetAddress().getHostAddress();
						System.out.println(ip+"가 접속\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		thread.start();
	}
	public static void main(String[] args) {
		new AndroidControlServer().connect();
	}

}
