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
		//System.out.println("service"+owner);
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
		return sqlSession.insert("connected.car.ownerlogin.ownerjoin",ownerjoin );
	}
	@Override
	public int joinshop(ShopinfoVO shopinfojoin) {
		// TODO Auto-generated method stub
		return sqlSession.insert("connected.car.ownerlogin.shopjoin",shopinfojoin);
	}



	@Override
	public List<OwnerVO> admin_ownerlist() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("connected.car.ownerlogin.admin_ownerlist");
	}



	@Override
	public int admin_ownerdelete(OwnerVO owner_id) {
		// TODO Auto-generated method stub
		return sqlSession.delete("connected.car.ownerlogin.admin_ownerdelete",owner_id);
	}



	@Override
	public ShopinfoVO admin_popupview(String shop_id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("connected.car.ownerlogin.admin_popupview",shop_id);
	}


	@Override
	public List<ShopinfoVO> shoplist(AddressVO addressinfo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("connected.car.ownerlogin.shoplist",addressinfo);
	}


}
