package connected.car.expendable;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
