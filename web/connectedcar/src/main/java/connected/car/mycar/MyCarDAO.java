
package connected.car.mycar;
import java.util.List;

import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

public interface MyCarDAO {
	public int inseryMyCar(MyCarVO mycar);
	public MyCarVO getCarinfo(String carid);
	public List<TermVO> getTerminfo(String car_brand, String car_fuel_type);
	public void insertTerm(MyexpendVO expendvo);
	
	public int updateDistance(MyCarVO myCarVO);
}
