package connected.car.mycar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

}
