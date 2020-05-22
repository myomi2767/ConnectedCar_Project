package connected.car.owner;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("OwnerDAO")
public class OwnerDAOImpl implements OwnerDAO {
	@Autowired
	SqlSession sqlSession;

	@Override
	public OwnerVO login(OwnerVO owner) {
		// TODO Auto-generated method stub
		System.out.println("service"+owner);
		OwnerVO data = sqlSession.selectOne("connected.car.ownerlogin.login", owner);
		System.out.println(data);
		return data;
	}

	

	@Override
	public boolean idCheck(String ownerid) {
		boolean result = false;
		OwnerVO user = sqlSession.selectOne("connected.car.ownerlogin.idcheck", ownerid);
		if(user!=null) {
			result = true;
		}
		return result;
	}

	@Override
	public int join(OwnerVO ownerjoin) {
		// TODO Auto-generated method stub
		return sqlSession.insert("connected.car.ownerlogin.join",ownerjoin );
	}
}
