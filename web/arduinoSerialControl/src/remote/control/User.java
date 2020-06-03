package remote.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class User extends Thread {
	Socket client;

	InputStream is;
	InputStreamReader isr;
	BufferedReader br;

	OutputStream os;
	PrintWriter pw;

	String nickname;
	StringTokenizer st;

	HashMap<String, User> userlist;
	HashMap<String, User> carlist;
	HashMap<String, User> infolist;
	HashMap<String, User> checklist; //userlist인지 carlist인지 체크하는 용도 
	
	String user;

	public User() {

	}

	public User(Socket client, HashMap<String, User> userlist, HashMap<String, User> carlist, HashMap<String, User> infolist) {
		super();
		this.client = client;
		this.userlist = userlist;
		this.carlist = carlist;
		this.infolist = infolist;
		ioWork();
	}

	// 처음 서버 접속할 때 실행되는 메소드
	public void ioWork() {
		try {
			is = client.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			os = client.getOutputStream();
			pw = new PrintWriter(os, true);

			// 접속할 때 객체의 메세지 받기
			nickname = br.readLine();
			System.out.println("id:" + nickname);
			String[] data = nickname.split(":");
			// 이용자가 app인지 car인지 파악
			user = data[0];

			if (user.equals("phone")) {
				checklist = userlist;
			}else if(user.equals("car")){
				checklist = carlist;
			}else {
				checklist = infolist;
			}
			// data[1]은 user나 car의 ID값
			// 강사님이 말한 동일한 번호가 발생되면 가장 최신 객체가 hashmap에 저장되도록 해놓은 곳
			checklist.put(data[1], this);
			if (!check(data[1], checklist)) {
				checklist.put(data[1], this);
			}
			System.out.println("폰:" + userlist.size());
			System.out.println("카:" + carlist.size());
			System.out.println("인포:"+infolist.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ===============================id에 맞는 차량이 있는지 확인하는 작업========================
	public boolean check(String id, HashMap<String, User> list) {
		boolean result = false;
		if (list.get(id) != null) {
			result = true;
		}
		return result;
	}

	private void filteringMsg(String msg) {
		System.out.println("서버가 받은 클라이언트의 메시지:" + msg);
		st = new StringTokenizer(msg, ":");
		String protocol = st.nextToken();
		System.out.println("protocol:" + protocol);
		if (protocol.equals("job")) {
			String message = st.nextToken();
			String category = st.nextToken();
			String id = st.nextToken().trim();
			System.out.println("job의 내용:" + message + ":" + category + ":" + id);
			User userClient = null;
			User infoClient = null;
			//폰이 보낸 메시지면 carlist를 불러서 확인 - 차가 보낸 메시지면 userlist를 불러서 확인
			//다시말해 앱유저와 차량과 일치하는 것 찾는 과정
			if(category.equals("phone")) {
				//폰이 메세지를 보냄
				System.out.println("폰이 메시지 보냄:"+id);
				userClient = carlist.get(id);
				if(message.charAt(0)=='S'||message.charAt(0)=='T') {
					infoClient = infolist.get(id);
					infoClient.sendMsg("job:"+message+":"+category+":"+id);
				}
			}else if(category.equals("car")){
				System.out.println("카가 메시지보냄"+id);
				userClient = userlist.get(id);
			}

			System.out.println(userClient);
			if (userClient != null) {
				userClient.sendMsg("job:" + message + ":" + category + ":" + id);
			}
		}else if(protocol.equals("message")) {
			String message = st.nextToken();
			String category = st.nextToken();
			String id = st.nextToken().trim();
			System.out.println("message의 내용=>" + message + ":" + category + ":" + id);
			User user = null;
			Set<String> keySet = infolist.keySet();
			for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(key != id) {
					user = infolist.get(key);
					user.sendMsg(msg);
				}
			}
		}else if(protocol.equals("location")) {
			String message = st.nextToken();
			String category = st.nextToken();
			String id = st.nextToken();
			User client = null;
			if(category.equals("phone")) {
				client = infolist.get(id);
			}else {
				client = userlist.get(id);
			}
			client.sendMsg(msg);
		}
	}

	public void sendMsg(String message) {
		pw.println(message);
	}

	public void run() {
		while (true) {
			try {
				String msg = br.readLine();
				if (msg != null) {
					filteringMsg(msg);
				}
			} catch (IOException e) {
				System.out.println("접속이 끊김");
				try {
					is.close();
					isr.close();
					br.close();
					os.close();
					pw.close();
					client.close();
				} catch (IOException e1) {
				}
				break;
			}
		}
	}
}
