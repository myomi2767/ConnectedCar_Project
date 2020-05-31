package connected.car.mycar;

import java.util.List;

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
	public List<TermVO> getTerminfo(MyCarVO carinfoForTerm) {
		// TODO Auto-generated method stub
		return session.selectList("connected.car.mycar.getTerminfo", carinfoForTerm);
	}

	@Override
	public void insertTerm(MyexpendVO expendvo) {
		// TODO Auto-generated method stub
		 session.insert("connected.car.mycar.insertTerm", expendvo);
		
	}

	
}
