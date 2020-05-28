package connected.car.sales;

import java.util.List;
import java.util.Map;

import connected.car.expendable.ShopExpendableVO;

public interface SalesService {
	public List<ShopExpendableVO> getExpendList(String shop_id);
	public int[] getAnnualSales(String shop_id);
	public Map<String, Integer> getTypeSales(String shop_id);
}
