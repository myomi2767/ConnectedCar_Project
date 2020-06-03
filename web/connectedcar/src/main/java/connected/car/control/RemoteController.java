package connected.car.control;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import connected.car.member.MemberVO;

@Controller
public class RemoteController {
	@Autowired
	RemoteService service;
	
	@RequestMapping(value="/remote/list.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody String listAll(@RequestBody String json){
		System.out.println(json);
		ObjectMapper mapper = new ObjectMapper();
		JSONObject object = null;
		List<RemoteVO> list = null;
		String result ="";
		try {
			RemoteVO remote = mapper.readValue(json, RemoteVO.class);
			list = service.listAll(remote.getCar_id());
			result = mapper.writeValueAsString(list);
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/remote/insert.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody String insertRemote(@RequestBody String vo) {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject = null;
		int result = 0;
		try {
			System.out.println(vo);
			RemoteVO remoteVO = mapper.readValue(vo, RemoteVO.class);
			System.out.println(remoteVO.toString());
			result = service.insertRemote(remoteVO);
			jsonObject = new JSONObject();
			jsonObject.put("resultNum", result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}

}
