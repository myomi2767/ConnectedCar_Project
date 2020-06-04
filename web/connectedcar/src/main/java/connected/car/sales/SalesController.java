package connected.car.sales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

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
		if(loginUser != null) {
			String shop_id = loginUser.getShop_id();
			ArrayList<ShopExpendableVO> expendList = 
					(ArrayList<ShopExpendableVO>)service.getExpendList(shop_id);
			int[] annualSalesList = service.getAnnualSales(shop_id);
			TreeMap<String, Integer[]> typeSalesMap = (TreeMap<String, Integer[]>) service.getTypeSales(shop_id);
			
			Entry<String, Integer[]> map1 = typeSalesMap.pollLastEntry();
			Entry<String, Integer[]> map2 = typeSalesMap.pollLastEntry();
			Entry<String, Integer[]> map3 = typeSalesMap.pollLastEntry();
			Entry<String, Integer[]> map4 = typeSalesMap.pollLastEntry();
			Entry<String, Integer[]> map5 = typeSalesMap.pollLastEntry();
			
			mav.addObject("expendList", expendList);
			mav.addObject("annualList", annualSalesList);
			mav.addObject("map1", map1);
			mav.addObject("map2", map2);
			mav.addObject("map3", map3);
			mav.addObject("map4", map4);
			mav.addObject("map5", map5);
			
			DecimalFormat df = new DecimalFormat("00");
	        Calendar currentCalendar = Calendar.getInstance();
			mav.addObject("thisMonth", Integer.parseInt(df.format(currentCalendar.get(Calendar.MONTH))));
		}
		
		mav.setViewName("inventory/inventorymain");
		return mav;
	}
}
