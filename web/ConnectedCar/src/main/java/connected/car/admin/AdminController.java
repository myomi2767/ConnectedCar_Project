package connected.car.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	//회원 확인, 삭제 관리자 페이지
	@RequestMapping(value = "/admin/member.do", method = RequestMethod.GET)
	public String memberView() {
		return "admin/memberManage";
	}
	
	//부품 추가, 삭제 관리자 페이지
	@RequestMapping(value = "/admin/expendable.do", method = RequestMethod.GET)
	public String expendableView() {
		return "admin/expendableManage";
	}
	
	//부품 추가, 삭제 관리자 페이지
	@RequestMapping(value = "/admin/adminexpendableAdd.do", method = RequestMethod.GET)
	public String managerexpendableAdd() {
		return "admin/adminexpendableAdd";
	}


	
}
