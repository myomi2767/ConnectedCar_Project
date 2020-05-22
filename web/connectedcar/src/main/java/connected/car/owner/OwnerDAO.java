package connected.car.owner;

import java.util.ArrayList;
import java.util.List;

public interface OwnerDAO {

	OwnerVO login(OwnerVO ownerlogin);
	int join(OwnerVO ownerinfo);
	boolean idCheck(String ownerid);
	
	
}
