package connected.car.expendable;

import java.util.List;

import connected.car.inventory.ExpendableVO;

public interface ExpendableDAO {
	public ExpendableVO findExpendable(String code, String brand);
	public List<ShopExpendableVO> findShopExpendableList(String shop_id);
	public int insertShopExpendable(String shop_id, ShopExpendableVO vo);
}
