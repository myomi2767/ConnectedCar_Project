package connected.car.sales;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CalculatingSales {
	
	public SortedMap<String, Integer[]> getTypeSalesArray(List<SalesVO> list) {
		Map<String, Integer[]> returnMap = new HashMap<String, Integer[]>();
		
		for (int i = 0; i < list.size(); i++) {
			SalesVO vo = list.get(i);
			Data data = getParsingData(vo);
			//부품의 월 별 매출을 담고 있는 맵
			if(returnMap.get(vo.getExpend_type()) == null) {
				Integer[] monthSales = new Integer[12];
				Arrays.fill(monthSales, 0);
				monthSales[data.month - 1] = vo.getExpend_count() * data.price;
				returnMap.put(vo.getExpend_type(), monthSales);
			} else {
				Integer[] curSales = returnMap.get(vo.getExpend_type());
				curSales[data.month - 1] += vo.getExpend_count() * data.price;
				returnMap.replace(vo.getExpend_type(), curSales);
			}
		}
		
		SortedMap<String, Integer[]> orderMap = new TreeMap<String, Integer[]>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Integer[] a1 = returnMap.get(o1);
				Integer[] a2 = returnMap.get(o2);
				
				Integer sum1 = 0;
				Integer sum2 = 0;
				for (int i = 0; i < a1.length; i++) {
					sum1 += a1[i];
					sum2 += a2[i];
				}
				
				return ((Comparable)sum2).compareTo(sum1);
			}
		});
			
		returnMap.keySet().forEach(key -> {
			orderMap.put(key, returnMap.get(key));
		});
		
		return orderMap;
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
	
	/*public List<String> sortByValue(Map map) {
		List<String> list = new ArrayList<String>();
		list.addAll(map.keySet());
		
		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Object v1 = map.get(o1);
				Object v2 = map.get(o2);
				
				return ((Comparable)v2).compareTo(v1);
			}
		});
		Collections.reverse(list);
		return list;
	}*/
	
	class Data {
		int month;
		int price;
	}

}
