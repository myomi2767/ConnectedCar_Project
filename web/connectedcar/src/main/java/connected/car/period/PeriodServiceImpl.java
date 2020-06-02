package connected.car.period;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import connected.car.admin.ExpendableVO;
import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

@Service
public class PeriodServiceImpl implements PeriodService {
	@Autowired
	@Qualifier("periodDao")
	PeriodDAO dao;

	@Override
	public List<MyexpendVO> getPeriodlist(String car_id) {
		// TODO Auto-generated method stub
		return dao.getPeriodlist(car_id);
	}

	@Override
	public List<ExpendableVO> getExpendlist(ChangeVO expendinfo) {
		// TODO Auto-generated method stub
		return dao.getExpendlist(expendinfo);
	}

	@Override
	public int updateMyExpendlist(MyexpendVO updateinfo) {
		// TODO Auto-generated method stub
		return dao.updateMyExpendlist(updateinfo);
	}
	

	


}
