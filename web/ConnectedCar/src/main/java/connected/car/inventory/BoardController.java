package connected.car.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {


	// 게시글을 작성하기 위한 뷰를 response할 메소드
	@RequestMapping(value = "/inventory/inventorymain.do", method = RequestMethod.GET)
	public String insertView() {
		return "inventory/inventoryhome";
	}


	
}
