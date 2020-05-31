package connected.car.mycar;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mycarDao")
public class MyCarDAOImpl implements MyCarDAO{
	@Autowired
	SqlSession session;

	@Override
	public int inseryMyCar(MyCarVO mycar) {
		return session.insert("connected.car.mycar.insertMycar", mycar);
	}

	@Override
	public MyCarVO getCarinfo(String carid) {
		return session.selectOne("connected.car.mycar.getCarinfo", carid);
	}

}
