package connected.car.fcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FCMController {
	@Autowired
	FCMService service;
	@RequestMapping(value = "/fcm/fcm_check.do", method = RequestMethod.GET)
	public String getToken(FCMVO vo) {
		System.out.println(vo.getCar_id()+">>>"+vo.getToken());
		
		int result = service.getToken(vo);
		if(result==1) {
			System.out.println("저장완료");
		}
		return "redirect:/index.do";
	}
	@RequestMapping(value = "/fcm/sendClient", method = RequestMethod.GET)
	public String sendMessage(String car_id,String type) {
		
		ArrayList<FCMVO> result = (ArrayList<FCMVO>) service.getClientToken(car_id);
		System.out.println("type??"+type);
		String apiKey = "AAAA-wUmwLE:APA91bGoFRls2UvjFgeLTII6_09wX72BFtDsueMrQ6FL-OA252WxLKutdCcbScbSLwtcq7C7KwFH-I3-vW301MOhWJ-j-vPhsaa6Xjnfz9NVQTeUESRqBZCtAsv7T1zyqubFF8oFtJsF";
		try {
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setRequestProperty("Authorization","key="+apiKey);
			
			MessageDTO msg = new MessageDTO(null,type);//윤피셜 => car_id에 order를 보낸다!!!!
			SendDataDTO senddto = new SendDataDTO(msg, result.get(0).getToken());
			//메시지 정보를 셋팅한다.
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(senddto);
			System.out.println("변환===> "+jsonString);
			//서버로 데이터 전달하기
			OutputStream os = connection.getOutputStream();
			os.write(jsonString.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			//firebase서버가 전달하는 응답메시지 받기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			System.out.println("br====>"+br);
			while(true) {
				String line = br.readLine();
				if(line==null) {
					break;
				}
				sb.append(line);
			}
			br.close();
			System.out.println(sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/index.do";
	}
	
	@RequestMapping(value = "/fcm/sendGps", method = RequestMethod.GET)
	public String sendGps(String car_id,String gps) {
		
		ArrayList<FCMVO> result = (ArrayList<FCMVO>) service.getClientGps(car_id);
		System.out.println("gps??"+result.size()+"=======================jjjjj=====");
		String apiKey = "AAAA-wUmwLE:APA91bGoFRls2UvjFgeLTII6_09wX72BFtDsueMrQ6FL-OA252WxLKutdCcbScbSLwtcq7C7KwFH-I3-vW301MOhWJ-j-vPhsaa6Xjnfz9NVQTeUESRqBZCtAsv7T1zyqubFF8oFtJsF";
		try {
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setRequestProperty("Authorization","key="+apiKey);
				MessageDTO msg = new MessageDTO(car_id,gps);//윤피셜 => car_id에 order를 보낸다!!!!
				SendDataDTO senddto = new SendDataDTO(msg, result.get(0).getToken());
				//메시지 정보를 셋팅한다.
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = mapper.writeValueAsString(senddto);
				System.out.println("변환===> "+jsonString);
				//서버로 데이터 전달하기
				OutputStream os = connection.getOutputStream();
				os.write(jsonString.getBytes("UTF-8"));
				os.flush();
				os.close();
			
			
			//firebase서버가 전달하는 응답메시지 받기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			System.out.println("br====>"+br);
			while(true) {
				String line = br.readLine();
				if(line==null) {
					break;
				}
				sb.append(line);
			}
			br.close();
			System.out.println(sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/index.do";
	}
}







