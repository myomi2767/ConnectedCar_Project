package connected.car.mycar;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MyCarController {
	@Autowired
	MyCarService service;
	
	@RequestMapping(value="/mycar/insert.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody String insertMyCar(@RequestBody String vo) {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject = null;
		int result = 0;
		try {
			System.out.println(vo);
			MyCarVO mycarVO = mapper.readValue(vo, MyCarVO.class);
			System.out.println(mycarVO.toString());
			result = service.inseryMyCar(mycarVO);
			jsonObject = new JSONObject();
			jsonObject.put("resultNum", result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
}
