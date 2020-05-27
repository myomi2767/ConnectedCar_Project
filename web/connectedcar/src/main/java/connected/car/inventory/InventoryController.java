package connected.car.inventory;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import connected.car.expendable.ExpendableLogVO;
import connected.car.expendable.ExpendableService;
import connected.car.expendable.ShopExpendableVO;
import connected.car.owner.OwnerVO;

@Controller
public class InventoryController {
	@Autowired
	ExpendableService service;


	//재고관리 메인화면
	@RequestMapping(value = "/inventory/inventorymain.do", method = RequestMethod.GET)
	public ModelAndView mainView(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		ArrayList<ShopExpendableVO> list = (ArrayList<ShopExpendableVO>)service.findShopExpendableList(shop_id);
		mav.addObject("expendList", list);
		mav.setViewName("inventory/inventorymain");
		return mav;
	}
	
	
	//재고관리 리스트 페이지
	@RequestMapping(value = "/inventory/manageList.do", method = RequestMethod.GET)
	public ModelAndView manageView(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		ArrayList<ShopExpendableVO> list = (ArrayList<ShopExpendableVO>)service.findShopExpendableList(shop_id);
		//System.out.println(list.toString());
		
		mav.addObject("expendList", list);
		
		mav.setViewName("inventory/inventoryManagement");
		return mav;
	}

	//재고관리 상세 페이지
	@RequestMapping(value = "/inventory/manageDetail.do", method = RequestMethod.GET)
	public ModelAndView manageDetailView(String expend_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		OwnerVO owner = (OwnerVO)session.getAttribute("loginuser");
		String shop_id = owner.getShop_id();
		
		ArrayList<ExpendableLogVO> logList = (ArrayList<ExpendableLogVO>)service.findExpendableLogList(shop_id, expend_id);
		//System.out.println(logList.toString());
		mav.addObject("logList", logList);
		mav.addObject("shop_id", shop_id);
		
		mav.setViewName("inventory/inventoryManagementDetail");
		return mav;
	}

	//입고 팝업
	@RequestMapping(value = "/inventory/recieve.do", method = RequestMethod.GET)
	public ModelAndView recieveView(String shop_id, String expend_id) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("shop_id", shop_id);
		mav.addObject("expend_id", expend_id);
		
		mav.setViewName("inventory/inventoryRecieve");
		return mav;
	}

	//출고 팝업
	@RequestMapping(value = "/inventory/release.do", method = RequestMethod.GET)
	public ModelAndView releaseView(String shop_id, String expend_id) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("shop_id", shop_id);
		mav.addObject("expend_id", expend_id);
		
		mav.setViewName("inventory/inventoryRelease");
		return mav;
	}
	
	//부품 추가, 삭제 정비소업주 페이지
	@RequestMapping(value = "/inventory/expendableAdd.do", method = RequestMethod.GET)
	public String expendableAdd() {
		return "inventory/expendableAdd";
	}	
		
	
}
