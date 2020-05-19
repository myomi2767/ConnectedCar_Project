package connected.car.owner;

import javax.servlet.http.HttpServletRequest;//
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession ses = request.getSession(false);
		if(ses!=null) {
			OwnerVO loginuser = (OwnerVO)ses.getAttribute("loginuser");
			if(loginuser==null) {
				response.sendRedirect("/connectedcar/ownerlogin/login.do");
				return false;
			}
		}
		return true;
	}
	
	
}
