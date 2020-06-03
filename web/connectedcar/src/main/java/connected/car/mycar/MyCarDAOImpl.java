package connected.car.mycar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

@Repository("mycarDao")
public class MyCarDAOImpl implements MyCarDAO{
	@Autowired
	SqlSession session;

	@Override
	public int inseryMyCar(MyCarVO mycar) {
		return session.insert("connected.car.mycar.insertMycar", mycar);
	}

	@Override
	public MyCarVO getCarinfo(String carid) {
		return session.selectOne("connected.car.mycar.getCarinfo", carid);
	}

	@Override
	public List<TermVO> getTerminfo(String car_brand, String car_fuel_type) {
		Map<String, String> mFindInfo = new HashMap<String, String>();
		mFindInfo.put("car_brand", car_brand);
		mFindInfo.put("car_fuel_type", car_fuel_type);
		return session.selectList("connected.car.mycar.getTerminfo", mFindInfo);
	}

	@Override
	public void insertTerm(MyexpendVO expendvo) {
		// TODO Auto-generated method stub
		 session.insert("connected.car.mycar.insertTerm", expendvo);
		
	}

	@Override
	public int updateDistance(MyCarVO myCarVO) {
		return session.update("connected.car.mycar.updateDistance", myCarVO);
	}

	
}
