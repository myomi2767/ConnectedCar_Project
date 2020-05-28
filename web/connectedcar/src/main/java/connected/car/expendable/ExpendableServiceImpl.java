package connected.car.expendable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import connected.car.admin.Pagination;
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
	public List<ShopExpendableVO> findShopExpendableList(String shop_id, Pagination pagination) {
		return dao.findShopExpendableList(shop_id, pagination);
	}
	
	@Override
	public int insertShopExpendable(String shop_id, ShopExpendableVO vo) {
		return dao.insertShopExpendable(shop_id, vo);
	}
	
	@Override
	public List<ExpendableLogVO> findExpendableLogList(String shop_id, String expend_id) {
		return dao.findExpendableLogList(shop_id, expend_id);
	}
	
	@Override
	public int insertExpendableLog(ExpendableLogVO log) {
		return dao.insertExpendableLog(log);
	}
	
	@Override
	public int getAllCnt(String shop_id) {
		return dao.getAllCnt(shop_id);
	}
}
