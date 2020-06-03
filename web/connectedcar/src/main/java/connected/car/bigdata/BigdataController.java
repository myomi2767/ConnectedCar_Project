package connected.car.bigdata;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import connected.car.owner.OwnerVO;

@Controller
public class BigdataController {
	@Autowired
	BigdataService service;

	public ModelAndView getBigdataResult(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		
		//sqoop 실행 및 mapreduce 실행된 결과 가져오기
		ArrayList<BigdataVO> bigdataList = (ArrayList<BigdataVO>)service.getBigdataList(shop_id);
		
		mav.addObject("bList", bigdataList);
		mav.setViewName("inventory/bigdata");
		return mav;
		
	}
}
