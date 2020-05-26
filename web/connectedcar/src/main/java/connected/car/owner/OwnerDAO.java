package connected.car.owner;

import java.util.ArrayList;
import java.util.List;



public interface OwnerDAO {

	OwnerVO login(OwnerVO ownerlogin);
	int join(OwnerVO ownerinfo);
	int joinshop(ShopinfoVO shopinfo);
	
	boolean idCheck(String ownerid);
	
	List<OwnerVO> admin_ownerlist();
	int admin_ownerdelete(OwnerVO owner_id);
	ShopinfoVO admin_popupview(String shop_id);
	
	List<ShopinfoVO> shoplist(AddressVO addressinfo);
	
}
