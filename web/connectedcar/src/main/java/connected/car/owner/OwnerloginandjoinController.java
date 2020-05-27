package connected.car.owner;

import java.util.List;

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

	// 웹의 로그인 화면
	@RequestMapping(value = "/ownerlogin/login.do", method = RequestMethod.GET)
	public String loginView() {
		return "ownerlogin/login";
	}

	// owner의 로그인 시도
	@RequestMapping(value = "/ownerlogin/login.do", method = RequestMethod.POST)
	public ModelAndView login(OwnerVO loginOwnerInfo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println(loginOwnerInfo);
		OwnerVO loginuser = service.login(loginOwnerInfo);
		System.out.println("로그인성공! 로그인된 회원정보: " + loginuser);
		String viewName = "";
		mav.addObject("loginuser", loginuser);
		if (loginuser != null) {
			// 로그인 성공시.
			HttpSession ses = request.getSession();
			// 2. 세션에 데이터 공유
			ses.setAttribute("loginuser", loginuser);
			viewName = "forward:/sales/loadData.do";
		} else {
			// 로그인 실패시 로그인 페이지 보여준다는 의미
			viewName = "ownerlogin/login";
		}
		mav.setViewName(viewName);// viewName을 변수처리.
		return mav;
	}

	// 로그아웃
	@RequestMapping("/ownerlogin/logout.do")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/ownerlogin/login.do";
	}

	// 회원가입 화면으로 가기
	@RequestMapping(value = "/ownerlogin/join.do", method = RequestMethod.GET)
	public String joinView() {
		return "ownerlogin/join";
	}



	// 정비소찾기 팝업에서 정비소추가팝업으로 가기
	@RequestMapping(value = "/ownerlogin/addnewshoppopup.do", method = RequestMethod.GET)
	public String joinAddNewShopPopupView() {
		return "ownerlogin/addshoppopup";
	}

	// 회원가입하기 - owner회원가입
	@RequestMapping(value = "/ownerlogin/join.do", method = RequestMethod.POST)
	public String join(OwnerVO owner, ShopinfoVO shopinfo) {
		// System.out.println("회원가입창에서 넘어온 정보(owner):" + owner);
		// System.out.println("회원가입창에서 넘어온 정보(shop):" + shopinfo);

		service.join(owner);
		return "redirect:/ownerlogin/login.do";
	}

	// 회원가입하기 - 찾는 정비소가 없을 경우, 정비소를 등록한다.
	@RequestMapping(value = "/ownerlogin/addnewshoppopup.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody void join(ShopinfoVO shopinfo) {
		System.out.println("넘어온 정보 : " + shopinfo);
		service.joinshop(shopinfo);
	}

	@RequestMapping(value = "/ownerlogin/idCheck.do", method = RequestMethod.GET, produces = "application/text;charset=utf-8")
	public @ResponseBody String idCheck(String owner_id) {
		boolean state = service.idCheck(owner_id);
		String result = "";
		if (state) {
			result = "아이디 중복 : 사용 불가능한 아이디";
		} else {
			result = "사용 가능한 아이디";
		}
		return result;
	}

	// ====================ADMIN 기능 : 회원 관리
	// ===========================================================
	// (Admin용)전체 회원 보기
	// 회원 확인, 삭제 관리자 페이지
	@RequestMapping(value = "/admin/memberlist.do")
	public ModelAndView memberView() {
		ModelAndView mav = new ModelAndView();
		List<OwnerVO> admin_ownerlist = service.admin_ownerlist();
		mav.addObject("admin_ownerlist", admin_ownerlist);
		mav.setViewName("admin/memberManage");
		return mav;

	}

	@RequestMapping(value = "/admin/admin_ownerdelete.do", method = RequestMethod.GET)
	public String admin_delete(OwnerVO owner_id) {
		System.out.println("삭제할 owner_id: " + owner_id);
		service.admin_ownerdelete(owner_id);

		return "redirect:/admin/memberlist.do";
	}

	// 회원의 정비소코드를 누르면 자세히보기 팝업을 불러온다.
	@RequestMapping(value = "/admin/shop_popup_view.do", method = RequestMethod.GET)
	public ModelAndView shop_popup_view(String shop_id) {
		ModelAndView mav = new ModelAndView();
		System.out.println(shop_id);
		ShopinfoVO shopinfo = service.admin_popupview(shop_id);

		String viewName = "";
		mav.addObject("shopinfo", shopinfo);
		mav.setViewName("admin/shopinfoPopup");

		System.out.println("mav에 들어간 shopinfo" + shopinfo.toString());

		return mav;
	}

	// ====================ADMIN 기능 : 회원 관리 끝!@@@@@@@@@@@@@@@@@@@@@@@@@
	// ===========================================================
	

	// 회원가입에서 정비소찾기 팝업으로 가기
	@RequestMapping(value = "/ownerlogin/joinshoppopup.do", method = RequestMethod.GET)
	public String joinPopupView() {
		return "ownerlogin/joinshoppopup";
	}
	
	// 주소 검색 시, 주소 리스트를 가져오는 컨트롤러
	@RequestMapping(value = "/ownerlogin/searchshop.do")
	public ModelAndView shopSearchlist(AddressVO addressinfo) {
		List<ShopinfoVO> shoplist = service.shoplist(addressinfo);
		ModelAndView mav = new ModelAndView();
		mav.addObject("shoplist", shoplist);
		mav.setViewName("ownerlogin/joinshoppopup");
		return mav;

	}

}
