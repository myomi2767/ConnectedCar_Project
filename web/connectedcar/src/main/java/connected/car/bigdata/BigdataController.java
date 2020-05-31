package connected.car.bigdata;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import connected.car.owner.OwnerVO;

@Controller
public class BigdataController {

	public ModelAndView getBigdataResult(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String owner_id = owner.getOwner_id();
		String shop_id = owner.getShop_id();
		
		return mav;
		
	}
}
