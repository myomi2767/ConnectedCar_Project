package connected.car.expendable;

import java.util.List;

import connected.car.inventory.ExpendableVO;

public interface ExpendableService {
	public ExpendableVO findExpendable(String code, String brand);
	public List<ShopExpendableVO> findShopExpendableList(String shop_id);
	public int insertShopExpendable(String shop_id, ShopExpendableVO vo);
	public List<ExpendableLogVO> findExpendableLogList(String shop_id, String expend_id);
}
