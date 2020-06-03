package connected.car.mycar;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import connected.car.member.MemberVO;
import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

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
			
			//회원가입 할 때는 my_expendable에 연료 주기 정보를 넣어줍니다.
			List<TermVO> termlist = service.getTerminfo(mycarVO.getCar_brand(), mycarVO.getCar_fuel_type());
			
			System.out.println("termlist를 잘 가져왔습니까?갯수는요?"+ termlist.size());
			//순서는 kind, type, term
			String car_id = mycarVO.getCar_id();
			for(int i=0; i<termlist.size();i++) {
				MyexpendVO expendvo = new MyexpendVO(car_id,
						termlist.get(i).getExpend_kind(),
						termlist.get(i).getExpend_type(),
						termlist.get(i).getExpend_term());
				
				service.insertTerm(expendvo);
				System.out.println("insert 된 상태 : expendvo:"+expendvo);
				
			}
			jsonObject = new JSONObject();
			jsonObject.put("resultNum", result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	
	//로그인 하면 car_id로 자동차 상태정보를 메인액티비티에 갖고 오는 컨트롤러
	@RequestMapping(value ="/mycar/getcarinfo.do", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	public @ResponseBody MyCarVO getCarinfo(HttpServletRequest req, HttpServletResponse res) {
		String carid = req.getParameter("car_id");
		System.out.println("get car_id from android:"+ carid);
		MyCarVO carinfo = service.getCarinfo(carid);
		System.out.println("mapper거치고 and로 보낼 carinfo:"+carinfo);
		return carinfo;
	}
	
	@RequestMapping(value="/mycar/update.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody String joinMember(@RequestBody String vo) {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject = null;
		int result = 0;
		try {
			System.out.println(vo);
			MyCarVO myCarVO = mapper.readValue(vo, MyCarVO.class);
			System.out.println(myCarVO.toString());
			result = service.updateDistance(myCarVO);
			jsonObject = new JSONObject();
			jsonObject.put("resultNum", result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
}
