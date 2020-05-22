package connected.car.owner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OwnerloginandjoinController {
	@Autowired
	OwnerService service;

	//웹의 로그인 화면
	@RequestMapping(value = "/ownerlogin/login.do", method = RequestMethod.GET)
	public String loginView() {
		return "ownerlogin/login";
	}
	
	//owner의 로그인 시도
	@RequestMapping(value="/ownerlogin/login.do", method = RequestMethod.POST)
	public ModelAndView login(OwnerVO loginOwnerInfo,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println(loginOwnerInfo);
		OwnerVO loginuser = service.login(loginOwnerInfo);
		System.out.println("로그인성공! 로그인된 회원정보: "+ loginuser);
		String viewName="";
		mav.addObject("loginuser", loginuser);
		if(loginuser!=null) {
			//로그인 성공시.
			HttpSession ses = request.getSession();
			//2. 세션에 데이터 공유
			ses.setAttribute("loginuser", loginuser);
			viewName = "inventory/inventorymain";
		}else {
			//로그인 실패시 로그인 페이지 보여준다는 의미
			viewName = "ownerlogin/login";
		}
		mav.setViewName(viewName);//viewName을 변수처리.
		return mav;
	}
	
	//로그아웃
	@RequestMapping("/ownerlogin/logout.do")
	public String logout(HttpSession session) {
		if(session!=null) {
			session.invalidate();
		}
		return "redirect:/ownerlogin/login.do";
	}
	
	//회원가입 화면으로 가기
	@RequestMapping(value = "/ownerlogin/join.do", method = RequestMethod.GET)
	public String joinView() {
		return "ownerlogin/join";
	}
	
	//회원가입에서 정비소찾기 팝업으로 가기
		@RequestMapping(value = "/ownerlogin/joinshoppopup.do", method = RequestMethod.GET)
		public String joinPopupView() {
			return "ownerlogin/joinshoppopup";
		}
		
	//회원가입하기
		@RequestMapping(value = "/ownerlogin/join.do", method = RequestMethod.POST)
		public String join(OwnerVO owner) {
			System.out.println("회원가입창에서 넘어온 정보:"+owner);
			service.join(owner);
			return "redirect:/ownerlogin/login.do";
		}
		
		@RequestMapping(value = "/ownerlogin/idCheck.do", method = RequestMethod.GET ,
				produces="application/text;charset=utf-8")
		public @ResponseBody String idCheck(String owner_id) {
			boolean state = service.idCheck(owner_id);
		    String result ="";
			if(state) {
				result = "사용 불가능한 아이디";
			}else {
				result = "사용 가능한 아이디";
			}
			return result;
		}	
		
	
	


}
