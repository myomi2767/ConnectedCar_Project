package connected.car.expendable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExpendableController {
	@Autowired
	ExpendableService service;
	
	//부품 조회하기
	@RequestMapping(value="/expendable/findExpend.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody ExpendableVO findExpendable(ExpendableVO expendvo) {
		System.out.println("찾으려는 브랜드: " + expendvo.getExpend_brand());
		ExpendableVO expendable = (ExpendableVO) service.findExpendable(expendvo.getExpend_code(), expendvo.getExpend_brand());
		if(expendable == null) return null;
		System.out.println(expendable.toString());
		return expendable;
	}
	
	//부품 추가하기
	@RequestMapping(value="/expendable/insertExpend.do", method=RequestMethod.POST)
	public String insertNewExpendable(String shop_id, HttpServletRequest request) {
		
		return "redirect:inventory/inventoryManagement";
	}
}
