package connected.car.fcm;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("fcmdao")
public class FCMDAOImpl implements FCMDAO {
	@Autowired
	SqlSession sqlsession;
	//private String car_id;

	@Override
	public FCMVO getToken(FCMVO token) {
		System.out.println("============"+token.getToken());
		 return sqlsession.selectOne("connected.car.fcm.readToken", token.getToken());
	}

	@Override
	public int insert(FCMVO vo) {
		// TODO Auto-generated method stubmulti.shop.member.fcm
		//Map<String, String> carData = new HashMap<String, String>();
		//carData.put("car_id", car_id);
		//carData.put("token", token);
		int result = sqlsession.insert("connected.car.fcm.insertToken",vo);
		System.out.println(result+"insert 완료");
		return result;
	}

	@Override
	public List<FCMVO> getClientToken(String car_id) {
		System.out.println("============"+car_id);
		
		List<FCMVO> data =  sqlsession.selectList("connected.car.fcm.readClientToken", car_id);
		System.out.println(data + ":::"+ data.size());
		return data;
		
	}

	@Override
	public List<FCMVO> getClientGps(String car_id) {
		System.out.println("gps받기"+car_id);
		List<FCMVO> data =  sqlsession.selectList("connected.car.fcm.readClientGps", car_id);
		System.out.println(data + ":::"+ data.size());
		return data;
	}
	

}
