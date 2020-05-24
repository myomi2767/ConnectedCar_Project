package connected.car.inventory;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import connected.car.expendable.ExpendableService;
import connected.car.expendable.ShopExpendableVO;

@Controller
public class InventoryController {
	@Autowired
	ExpendableService service;

	//재고관리 리스트 페이지
	@RequestMapping(value = "/inventory/manageList.do", method = RequestMethod.GET)
	public ModelAndView manageView(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		String shop_id = (String)session.getAttribute("shop_id");
		ArrayList<ShopExpendableVO> list = (ArrayList<ShopExpendableVO>)service.findShopExpendableList(shop_id);
		System.out.println(list.toString());
		
		mav.addObject("expendList", list);
		
		mav.setViewName("inventory/inventoryManagement");
		return mav;
	}

	//재고관리 메인화면
	@RequestMapping(value = "/inventory/inventorymain.do", method = RequestMethod.GET)
	public String insertView() {
		return "inventory/inventorymain";
	}
	//재고관리 상세 페이지
	@RequestMapping(value = "/inventory/manageDetail.do", method = RequestMethod.GET)
	public String manageDetailView() {
		return "inventory/inventoryManagementDetail";
	}

	//입고 팝업
	@RequestMapping(value = "/inventory/recieve.do", method = RequestMethod.GET)
	public String recieveView() {
		return "inventory/inventoryRecieve";
	}

	//출고 팝업
	@RequestMapping(value = "/inventory/release.do", method = RequestMethod.GET)
	public String releaseView() {
		return "inventory/inventoryRelease";
	}
	
	//부품 추가, 삭제 정비소업주 페이지
	@RequestMapping(value = "/inventory/expendableAdd.do", method = RequestMethod.GET)
	public String expendableAdd() {
		return "inventory/expendableAdd";
	}	
		
	
}
