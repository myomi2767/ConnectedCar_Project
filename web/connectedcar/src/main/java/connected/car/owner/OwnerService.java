package connected.car.owner;

import java.util.ArrayList;
import java.util.List;

public interface OwnerService {
	OwnerVO login(OwnerVO ownerlogin);
	boolean idCheck(String ownerid);
	int join(OwnerVO ownerjoin);
	int joinshop(ShopinfoVO shopjoin);
	
	List<OwnerVO> admin_ownerlist();//관리자가 회원 전체보기
	int admin_ownerdelete(OwnerVO owner_id);//관리자가 회원 삭제
	
	ShopinfoVO admin_popupview(String shop_id);
	
	
	List<ShopinfoVO> shoplist(AddressVO addressinfo);//전체샵검색

}