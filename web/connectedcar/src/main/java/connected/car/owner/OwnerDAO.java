package connected.car.owner;

import java.util.ArrayList;
import java.util.List;

public interface OwnerDAO {

	OwnerVO login(OwnerVO ownerlogin);
	void join(OwnerVO ownerinfo);
	
}
