package connected.car.sales;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import connected.car.expendable.ShopExpendableVO;

@Repository("salesDao")
public class SalesDAOImpl implements SalesDAO {
	@Autowired
	SqlSession session;
	
	@Override
	public List<ShopExpendableVO> getExpendList(String shop_id) {
		return session.selectList("connected.car.sales.getExpendableList", shop_id);
	}
	
	@Override
	public List<SalesVO> getAnnualSales(String shop_id) {
		return session.selectList("connected.car.sales.getAnuualSalesInfo", shop_id);
	}

	@Override
	public List<SalesVO> getTypeSales(String shop_id) {
		return session.selectList("connected.car.sales.getTypeSalesInfo", shop_id);
	}

}
