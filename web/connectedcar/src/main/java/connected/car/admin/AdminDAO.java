package connected.car.admin;

import java.util.List;

public interface AdminDAO {
	int expendableAdd(ExpendableVO expendableVO);
	List<ExpendableVO> listAll(Pagination pagination);
	int listAllCnt();
	int expendableDelete(String expend_id);
	List<ExpendableVO> searchlist(Pagination pagination);
	int searchCnt(String keyword);
}
