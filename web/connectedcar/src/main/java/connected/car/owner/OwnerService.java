package connected.car.owner;

import java.util.ArrayList;
import java.util.List;

public interface OwnerService {
	OwnerVO login(OwnerVO ownerlogin);
	boolean idCheck(String ownerid);
	int join(OwnerVO ownerjoin);

}