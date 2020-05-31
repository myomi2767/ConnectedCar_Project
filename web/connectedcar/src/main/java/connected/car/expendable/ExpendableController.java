package connected.car.expendable;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import connected.car.admin.ExpendableVO;
import connected.car.owner.OwnerVO;

@Controller
public class ExpendableController {
	@Autowired
	ExpendableService service;
	
	//부품 조회하기
	@RequestMapping(value="/expendable/findExpend.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody ExpendableVO findExpendable(ShopExpendableVO expendvo) {
		//System.out.println("찾으려는 브랜드: " + expendvo.getExpend_brand());
		ExpendableVO expendable = (ExpendableVO) service.findExpendable(expendvo.getExpend_code(), expendvo.getExpend_brand());
		if(expendable == null) return null;
		//System.out.println(expendable.toString());
		return expendable;
	}
	
	//부품 추가하기
	@RequestMapping(value="/expendable/insertExpend.do", method=RequestMethod.POST)
	public ModelAndView insertNewExpendable(ShopExpendableVO vo, Model model, HttpSession session) {
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		ModelAndView mav = new ModelAndView();
		int count = service.expendableConfirm(shop_id, vo.getExpend_id());
		String result = "";
		if(count==1) {
			result = "이미 추가되어 있는 부품이 있습니다.";
		}else {
			service.insertShopExpendable(shop_id, vo);
			result = "부품이 삽입되었습니다.";
		}
		mav.addObject("msg", result);
		mav.setViewName("empty");
		return mav;
		/*if(result == 2) System.out.println("부품 추가 성공");
		else if(result == 1) System.out.println("로그나 부품에서 실패한 부분이 있습니다.");
		else System.out.println("부품 추가 실패");*/
	}
	
	//로그 추가하기
	@RequestMapping(value="/expendable/insertLog.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody String insertNewLog(ExpendableLogVO log) {
		//System.out.println(log.toString());
		int result = service.insertExpendableLog(log);
		//System.out.println(result);
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		
		return obj.toString();
	}
	

	//부품삭제 - 개별삭제
	@RequestMapping(value="/expendable/deleteExpend.do", method = RequestMethod.POST)
	public String deleteExpend(String expend_id, HttpSession session) {
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		System.out.println("loginuser?"+shop_id);
		System.out.println("들어온 expend_id값:"+expend_id);
		ShopExpendableVO sevo = new ShopExpendableVO(expend_id, shop_id);
		service.deleteExpend(sevo);
		service.deleteLog(sevo);
		return "redirect:/inventory/manageList.do";
	}
}
