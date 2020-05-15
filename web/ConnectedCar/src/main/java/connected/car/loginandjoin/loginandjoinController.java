package connected.car.loginandjoin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class loginandjoinController {



	@RequestMapping(value = "/loginandjoin/login.do", method = RequestMethod.GET)
	public String loginView() {
		return "loginandjoin/login";
	}
	
	// 게시글을 작성하기 위한 뷰를 response할 메소드
		@RequestMapping(value = "/loginandjoin/join.do", method = RequestMethod.GET)
		public String joinView() {
			return "loginandjoin/join";
		}


	
}
