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
	public void join(OwnerVO ownerinfo) {
		System.out.println("mapper로 갈 ownerinfo:"+ownerinfo);
		sqlSession.insert("connected.car.ownerlogin.join",ownerinfo);
	}
}
