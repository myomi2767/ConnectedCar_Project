package connected.car.period;

import java.util.List;

import connected.car.admin.ExpendableVO;
import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

public interface PeriodService {
	
	List<MyexpendVO> getPeriodlist(String car_id);
	List<ExpendableVO> getExpendlist(ChangeVO expendinfo);
	int updateMyExpendlist(MyexpendVO updateinfo);
}
