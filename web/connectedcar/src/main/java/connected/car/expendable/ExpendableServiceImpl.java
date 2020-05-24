package connected.car.expendable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import connected.car.inventory.ExpendableVO;

@Service
public class ExpendableServiceImpl implements ExpendableService {
	@Autowired
	@Qualifier("expendableDao")
	ExpendableDAO dao;
	
	@Override
	public ExpendableVO findExpendable(String code, String brand) {
		return dao.findExpendable(code, brand);
	}
	
	@Override
	public List<ShopExpendableVO> findShopExpendableList(String shop_id) {
		return dao.findShopExpendableList(shop_id);
	}
	
	@Override
	public int insertShopExpendable(String shop_id, ShopExpendableVO vo) {
		return dao.insertShopExpendable(shop_id, vo);
	}
}
