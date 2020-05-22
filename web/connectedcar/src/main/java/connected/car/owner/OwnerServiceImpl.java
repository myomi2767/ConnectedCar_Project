package connected.car.owner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {
	@Autowired
	@Qualifier("OwnerDAO")
	OwnerDAO dao;

	@Override
	public OwnerVO login(OwnerVO ownerlogin) {
		System.out.println("service"+ownerlogin);
		return dao.login(ownerlogin);
	}

	@Override
	public boolean idCheck(String ownerid) {
		// TODO Auto-generated method stub
		return dao.idCheck(ownerid);
	}

	@Override
	public int join(OwnerVO ownerjoin) {
		// TODO Auto-generated method stub
		return dao.join(ownerjoin);
	}


	
	
	
	
}