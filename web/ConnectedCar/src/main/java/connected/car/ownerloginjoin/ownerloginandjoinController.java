package connected.car.ownerloginjoin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ownerloginandjoinController {

	// 로그인 화면
	@RequestMapping(value = "/loginandjoin/login.do", method = RequestMethod.GET)
	public String loginView() {
		return "loginandjoin/login";
	}

	// 회원가입 화면
	@RequestMapping(value = "/loginandjoin/join.do", method = RequestMethod.GET)
	public String joinView() {
		return "loginandjoin/join";
	}

}
