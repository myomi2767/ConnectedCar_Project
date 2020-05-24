package connected.car.admin;

import java.util.List;

import connected.car.inventory.ExpendableVO;

public interface AdminService {
	int expendableAdd(ExpendableVO expendableVO);
	List<ExpendableVO> listAll();
}
