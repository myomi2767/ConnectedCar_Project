package connected.car.period;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import connected.car.admin.ExpendableVO;
import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

@Repository("periodDao")
public class PeriodDAOImpl implements PeriodDAO{
	@Autowired
	SqlSession session;


	@Override
	public List<MyexpendVO> getPeriodlist(String car_id) {
		// TODO Auto-generated method stub
		return session.selectList("connected.car.period.getPeriodlist", car_id);
	}


	@Override
	public List<ExpendableVO> getExpendlist(ChangeVO expendinfo) {
		// TODO Auto-generated method stub
		return session.selectList("connected.car.period.getExpendlist",expendinfo);
	}


	@Override
	public int updateMyExpendlist(MyexpendVO updateinfo) {
		// TODO Auto-generated method stub
		return session.update("connected.car.period.updateMyExpendlist", updateinfo);
	}

	

	
}
