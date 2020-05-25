package connected.car.expendable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import connected.car.inventory.ExpendableVO;

@Repository("expendableDao")
public class ExpendableDAOImpl implements ExpendableDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public ExpendableVO findExpendable(String code, String brand) {
		Map<String, String> mFindData = new HashMap<String, String>();
		mFindData.put("expend_code", code);
		mFindData.put("expend_brand", brand);
		
		return sqlSession.selectOne("connected.car.expendable.findExpendInfo", mFindData);
	}
	
	@Override
	public List<ShopExpendableVO> findShopExpendableList(String shop_id) {		
		return sqlSession.selectList("connected.car.expendable.findShopExpendList", shop_id);
	}
	
	@Override
	public int insertShopExpendable(String shop_id, ShopExpendableVO vo) {
		Map<String, Object> mInsertData = new HashMap<String, Object>();
		mInsertData.put("shop_id", shop_id);
		mInsertData.put("expend_id", vo.getExpend_id());
		mInsertData.put("shop_expend_count", vo.getShop_expend_count());
		int shop_expend_result = sqlSession.insert("connected.car.expendable.insertShopExpend", mInsertData);
		
		mInsertData.put("in_out_code", "입고");
		int log_expend_result = sqlSession.insert("connected.car.expendable.insertExpendLog", mInsertData);
		
		return shop_expend_result + log_expend_result;
	}
	
	@Override
	public List<ExpendableLogVO> findExpendableLogList(String shop_id, String expend_id) {
		Map<String, Object> mFindData = new HashMap<String, Object>();
		mFindData.put("shop_id", shop_id);
		mFindData.put("expend_id", expend_id);
		
		return sqlSession.selectList("connected.car.expendable.findExpendLog", mFindData);
	}

}