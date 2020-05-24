package connected.car.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import connected.car.inventory.ExpendableVO;
import connected.car.shop.AddressVO;

//admin의 전체 회원 정보확인 기능은 connected.car.owner패키지에 작성

@Controller
public class AdminController {
	@Autowired
	AdminService service;
	
	
	
	//부품 관리, 삭제 관리자 페이지
	@RequestMapping(value = "/admin/expendable.do", method = RequestMethod.GET)
	public ModelAndView expendableView() {
		ModelAndView mav = new ModelAndView();
		List<ExpendableVO> list = service.listAll();
		System.out.println("결과"+list);
		mav.addObject("expendList", list);
		mav.setViewName("admin/expendableManage");
		return mav;
	}
	
	//부품 추가
	@RequestMapping(value = "/admin/adminexpendableAdd.do", method = RequestMethod.GET)
	public String managerexpendableAdd() {
		return "admin/adminexpendableAdd";
	}
	
	//추가 버튼 클릭 시, 부품 추가
	@RequestMapping(value = "/admin/adminexpendableAdd.do", method = RequestMethod.POST)
	public void expendableAdd(ExpendableVO expendableVO) {
		System.out.println("***컨트롤러 받은 값:"+expendableVO);
		service.expendableAdd(expendableVO);
	}
	
	//정비소 추가
	@RequestMapping(value="/admin/crawling.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8") 
	public @ResponseBody String crawlingRepairShop(AddressVO findshop) {
		System.out.println(findshop.toString());
		String result = "";
		StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_auto_maintenance_company_api"); /*URL*/
        try {
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=TKsD4I6N3fflOMczLT%2FPQ5zmc1daGFBjtDRi3JB%2FPbYHAKIubqT0wJew%2BVCVtX8h3Ljvc663%2B5m3ygZETNGaTA%3D%3D"); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("20", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
	        //urlBuilder.append("&" + URLEncoder.encode("inspofcNm","UTF-8") + "=" + URLEncoder.encode("세종모터스", "UTF-8")); /*자동차정비업체명*/
	        //urlBuilder.append("&" + URLEncoder.encode("inspofcType","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*자동차정비업체종류*/
	        urlBuilder.append("&" + URLEncoder.encode("rdnmadr","UTF-8") + "=" + URLEncoder.encode(findshop.getAddress_do() + " " + findshop.getAddress_si() + " " + findshop.getAddress_gu(), "UTF-8")); /*소재지도로명주소*/
	        urlBuilder.append("&" + URLEncoder.encode("lnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지지번주소*/
	        //urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode("36.519360", "UTF-8")); /*위도*/
	        //urlBuilder.append("&" + URLEncoder.encode("hardness","UTF-8") + "=" + URLEncoder.encode("127.368817", "UTF-8")); /*경도*/
	        //urlBuilder.append("&" + URLEncoder.encode("bizrnoDate","UTF-8") + "=" + URLEncoder.encode("2018-12-18", "UTF-8")); /*사업등록일자*/
	        //urlBuilder.append("&" + URLEncoder.encode("ar","UTF-8") + "=" + URLEncoder.encode("1197", "UTF-8")); /*면적*/
	        //urlBuilder.append("&" + URLEncoder.encode("bsnSttus","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*영업상태*/
	        //urlBuilder.append("&" + URLEncoder.encode("clsbizDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*폐업일자*/
	        //urlBuilder.append("&" + URLEncoder.encode("sssBeginDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*휴업시작일자*/
	        //urlBuilder.append("&" + URLEncoder.encode("sssEndDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*휴업종료일자*/
	        //urlBuilder.append("&" + URLEncoder.encode("operOpenHm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*운영시작시각*/
	        //urlBuilder.append("&" + URLEncoder.encode("operCloseHm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*운영종료시각*/
	        //urlBuilder.append("&" + URLEncoder.encode("phoneNumber","UTF-8") + "=" + URLEncoder.encode("044-273-8272", "UTF-8")); /*전화번호*/
	        //urlBuilder.append("&" + URLEncoder.encode("institutionNm","UTF-8") + "=" + URLEncoder.encode("세종특별자치시", "UTF-8")); /*관리기관명*/
	        //urlBuilder.append("&" + URLEncoder.encode("institutionPhoneNumber","UTF-8") + "=" + URLEncoder.encode("044-300-5554", "UTF-8")); /*관리기관전화번호*/
	        urlBuilder.append("&" + URLEncoder.encode("referenceDate","UTF-8") + "=" + URLEncoder.encode("2019-09-06", "UTF-8")); /*데이터기준일자*/
	        //urlBuilder.append("&" + URLEncoder.encode("insttCode","UTF-8") + "=" + URLEncoder.encode("5690000", "UTF-8")); /*제공기관코드*/
	        //urlBuilder.append("&" + URLEncoder.encode("insttNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*제공기관명*/
	        
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
	        
	        result = sb.toString();
	        
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        
		return result;
	}


	
}
