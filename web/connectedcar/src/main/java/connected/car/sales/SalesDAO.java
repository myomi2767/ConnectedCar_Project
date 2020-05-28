package connected.car.sales;

import java.util.List;

import connected.car.expendable.ShopExpendableVO;

public interface SalesDAO {
	public List<ShopExpendableVO> getExpendList(String shop_id);
	public List<SalesVO> getAnnualSales(String shop_id);
	public List<SalesVO> getTypeSales(String shop_id);
}
