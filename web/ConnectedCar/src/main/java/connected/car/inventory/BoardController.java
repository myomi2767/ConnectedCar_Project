package connected.car.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {

	//재고관리 리스트 페이지
	@RequestMapping(value = "/inventory/manageList.do", method = RequestMethod.GET)
	public String manageView() {
		return "inventory/inventoryManagement";
	}

	// 게시글을 작성하기 위한 뷰를 response할 메소드
	@RequestMapping(value = "/inventory/inventorymain.do", method = RequestMethod.GET)
	public String insertView() {
		return "inventory/inventorymain";
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
	
}
