package connected.car.sales;

import java.util.List;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import connected.car.expendable.ShopExpendableVO;

@Service
public class SalesServiceImpl implements SalesService {
	@Autowired
	@Qualifier("salesDao")
	SalesDAO dao;
	CalculatingSales calc;
	
	@Override
	public List<ShopExpendableVO> getExpendList(String shop_id) {
		return dao.getExpendList(shop_id);
	}
	
	@Override
	public int[] getAnnualSales(String shop_id) {
		calc = new CalculatingSales();
		return calc.getAnnualSalesArray(dao.getAnnualSales(shop_id));
	}

	@Override
	public SortedMap<String, Integer[]> getTypeSales(String shop_id) {
		calc = new CalculatingSales();
		return calc.getTypeSalesArray(dao.getTypeSales(shop_id));
	}

}
