package connected.car.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RemoteServiceImpl implements RemoteService {
	@Autowired
	@Qualifier("remoteDao")
	RemoteDAO dao;

	@Override
	public List<RemoteVO> listAll(String car_id) {
		return dao.listAll(car_id);
	}

	@Override
	public int insertRemote(RemoteVO vo) {
		return dao.insertRemote(vo);
	}

}
