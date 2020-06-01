package connected.car.period;

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

import connected.car.admin.ExpendableVO;
import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

@Controller
public class PeriodController {
	@Autowired
	PeriodService service;
	
	@RequestMapping(value ="/period/getPeriodlist.do", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	public @ResponseBody List<MyexpendVO> getPeriodlist(HttpServletRequest req, HttpServletResponse res) {
		String car_id = req.getParameter("car_id");
		//System.out.println("period의 서비스를 수행하기 직전의 car_id:"+car_id);
		List<MyexpendVO> periodlist = service.getPeriodlist(car_id);
		//System.out.println("mapper 거치고 periodlist 가 제대로 나왔나????:"+periodlist);
		return periodlist;
	}
	
	@RequestMapping(value ="/period/getExpendlist.do", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	public @ResponseBody List<ExpendableVO> getExpendlist(HttpServletRequest req, HttpServletResponse res) {
		String expend_type = req.getParameter("expend_type");
		String car_model_name = req.getParameter("car_model_name");
		
		ChangeVO expendinfo = new ChangeVO(expend_type, car_model_name);
		System.out.println("getExpendlist의 서비스를 수행하기 직전의 expendinfo:"+expendinfo);
		List<ExpendableVO> expendlist = service.getExpendlist(expendinfo);
		System.out.println("mapper 거치고 expendlist 가 제대로 나왔나????:"+expendlist);
		return expendlist;
	}
	

}
