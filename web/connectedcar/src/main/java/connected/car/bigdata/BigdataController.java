package connected.car.bigdata;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import connected.car.owner.OwnerVO;

@Controller
public class BigdataController {
	@Autowired
	BigdataService service;

	@RequestMapping(value="bigdata/result.do", method=RequestMethod.GET)
	public ModelAndView getBigdataResult(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		
		//sqoop 실행 및 mapreduce 실행된 결과 가져오기
		ArrayList<BigdataVO> bigdataList = (ArrayList<BigdataVO>)service.getBigdataList(shop_id);
		System.out.println(bigdataList.toString());
		
		mav.addObject("bList", bigdataList);
		mav.setViewName("inventory/bigdata");
		return mav;
		
	}
}
