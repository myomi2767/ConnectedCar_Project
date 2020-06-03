package connected.car.mycar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import connected.car.period.MyexpendVO;
import connected.car.period.TermVO;

@Service
public class MyCarServiceImpl implements MyCarService {
	@Autowired
	@Qualifier("mycarDao")
	MyCarDAO dao;
	
	@Override
	public int inseryMyCar(MyCarVO mycar) {
		return dao.inseryMyCar(mycar);
	}

	@Override
	public MyCarVO getCarinfo(String carid) {
		// TODO Auto-generated method stub
		return dao.getCarinfo(carid);
	}

	@Override
	public List<TermVO> getTerminfo(String car_brand, String car_fuel_type) {
		// TODO Auto-generated method stub
		return dao.getTerminfo(car_brand, car_fuel_type);
	}

	@Override
	public void insertTerm(MyexpendVO expendvo) {
		// TODO Auto-generated method stub
		dao.insertTerm(expendvo);
		
	}

	@Override
	public int updateDistance(MyCarVO myCarVO) {
		return dao.updateDistance(myCarVO);
	}

}
