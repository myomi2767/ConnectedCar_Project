package connected.car.member;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MemberController {
	@Autowired
	MemberService service;

	@RequestMapping(value="/member/join.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String joinMember(String vo) {
		ObjectMapper mapper = new ObjectMapper();
		int result = 0;
		try {
			MemberVO memberVO = mapper.readValue(vo, MemberVO.class);
			result = service.joinMember(memberVO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultNum", result);
		
		return jsonObject.toString();
	}
}
