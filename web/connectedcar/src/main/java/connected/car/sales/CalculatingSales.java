package connected.car.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatingSales {
	
	public Map<String, Integer> getTypeSalesArray(List<SalesVO> list) {
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			SalesVO vo = list.get(i);
			Data data = getParsingData(vo);
			if(returnMap.get(vo.getExpend_type()) == null) {
				returnMap.put(vo.getExpend_type(), vo.getExpend_count() * data.price);
			} else {
				int curSales = returnMap.get(vo.getExpend_type());
				returnMap.replace(vo.getExpend_type(), curSales + vo.getExpend_count() * data.price);
			}
		}
		return returnMap;
	}
	
	public int[] getAnnualSalesArray(List<SalesVO> list) {
		int[] returnArray = new int[12];
		for (int i = 0; i < list.size(); i++) {
			SalesVO curVo = list.get(i);
			Data data = getParsingData(curVo);
			returnArray[data.month - 1] += curVo.getExpend_count() * data.price;
		}
		
		return returnArray;
	}
	
	public Data getParsingData(SalesVO vo) {
		String dateInfo = vo.getIn_out_date();
		int year = 0;
		int month = 0; 
		int date = 0;
		if(dateInfo != null) {
			String[] dateData = vo.getIn_out_date().split("-");
			year = Integer.parseInt(dateData[0]);
			month = Integer.parseInt(dateData[1]);
			date = Integer.parseInt(dateData[2]);
		}
		
		String[] priceData = vo.getExpend_price().split("원");
		int price = Integer.parseInt(priceData[0].replaceAll(",", ""));
		
		Data newData = new Data();
		newData.month = month;
		newData.price = price;
		
		return newData;
	}
	
	class Data {
		int month;
		int price;
	}
}
