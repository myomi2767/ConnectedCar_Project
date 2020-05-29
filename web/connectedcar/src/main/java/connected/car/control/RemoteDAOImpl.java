package connected.car.control;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("remoteDao")
public class RemoteDAOImpl implements RemoteDAO {
	@Autowired
	SqlSession session;

	@Override
	public List<RemoteVO> listAll(String car_id) {
		return session.selectList("connected.car.control.selectRemote", car_id);
	}

	@Override
	public int insertRemote(RemoteVO vo) {
		return session.insert("connected.car.control.insertRemote",vo);
	}

}
