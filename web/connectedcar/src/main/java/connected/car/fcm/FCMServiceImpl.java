package connected.car.fcm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FCMServiceImpl implements FCMService {
	@Autowired
	@Qualifier("fcmdao")
	FCMDAO dao;

	@Override
	public int getToken(FCMVO vo) {
		int result = 0;
		if(dao.getToken(vo)==null) {
			System.out.println("토큰없어");
			result = dao.insert(vo);
		}
		return result;
	}

	@Override
	public List<FCMVO> getClientToken(String car_id) {
		// TODO Auto-generated method stub
		return dao.getClientToken(car_id);
	}

	@Override
	public List<FCMVO> getClientGps(String car_id) {
		return dao.getClientGps(car_id);
	}

	
	

}
