package connected.car.mycar;

import java.util.List;

import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

public interface MyCarService {
	public int inseryMyCar(MyCarVO mycar);
	public MyCarVO getCarinfo(String carid);
	
	public List<TermVO> getTerminfo(MyCarVO carinfoForTerm);
	public void insertTerm(MyexpendVO expendvo);
}
