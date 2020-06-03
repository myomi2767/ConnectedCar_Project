package connected.car.bigdata;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("bigdataDao")
public class BigdataDAOImpl implements BigdataDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<BigdataVO> getBigdataList(String shop_id) {
		return sqlSession.selectList("connected.car.bigdata.selectBigdataList", shop_id);
	}
}	
