package connected.car.sales;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import connected.car.expendable.ShopExpendableVO;
import connected.car.owner.OwnerVO;

@Controller
public class SalesController {
	@Autowired
	SalesService service;
	
	@RequestMapping(value="/sales/loadData.do", method=RequestMethod.POST)
	public ModelAndView getChartData(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		OwnerVO loginUser = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = loginUser.getShop_id();
		ArrayList<ShopExpendableVO> expendList = 
				(ArrayList<ShopExpendableVO>)service.getExpendList(shop_id);
		int[] annualSalesList = service.getAnnualSales(shop_id);
		Map<String, Integer> typeSalesMap = service.getTypeSales(shop_id);
		
		System.out.println(annualSalesList.toString());
		System.out.println(typeSalesMap.toString());
		mav.addObject("expendList", expendList);
		mav.addObject("annualList", annualSalesList);
		mav.addObject("typeMap", typeSalesMap);
		
		mav.setViewName("inventory/inventorymain");
		return mav;
	}
}
