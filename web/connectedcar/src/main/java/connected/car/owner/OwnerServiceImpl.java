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
		//System.out.println("service"+ownerlogin);
		return dao.login(ownerlogin);
	}

	@Override
	public boolean idCheck(String ownerid) {
		// TODO Auto-generated method stub
		return dao.idCheck(ownerid);
	}

	
	//==================회원가입======================
	@Override
	public int join(OwnerVO ownerjoin) {
		// TODO Auto-generated method stub
		return dao.join(ownerjoin);
	}

	@Override
	public int joinshop(ShopinfoVO shopjoin) {
		// TODO Auto-generated method stub
		return dao.joinshop(shopjoin);
	}

	
	
	//==================관리자의 회원관리==========================
	@Override
	public List<OwnerVO> admin_ownerlist() {
		// TODO Auto-generated method stub
		return dao.admin_ownerlist();
	}
	


	@Override
	public int admin_ownerdelete(OwnerVO owner_id) {
		// TODO Auto-generated method stub
		return dao.admin_ownerdelete(owner_id);
	}

	@Override
	public ShopinfoVO admin_popupview(String shop_id) {
		// TODO Auto-generated method stub
		return dao.admin_popupview(shop_id);
	}
	

	@Override
	public List<ShopinfoVO> shoplist(AddressVO addressinfo) {
		// TODO Auto-generated method stub
		return dao.shoplist(addressinfo);
	}
	
	
	
	
}