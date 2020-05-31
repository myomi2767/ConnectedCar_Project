package connected.car.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	@Qualifier("adminDAO")
	AdminDAO dao;
	
	@Override
	public int expendableAdd(ExpendableVO expendableVO) {
		return dao.expendableAdd(expendableVO);
	}

	@Override
	public List<ExpendableVO> listAll(Pagination pagination) {
		return dao.listAll(pagination);
	}
	//부품 전체 갯수
	@Override
	public int listAllCnt() {
		return dao.listAllCnt();
	}

	@Override
	public int expendableDelete(String expend_id) {
		return dao.expendableDelete(expend_id);
	}

	@Override
	public List<ExpendableVO> searchlist(Pagination pagination) {
		return dao.searchlist(pagination);
	}
	//Search한 부품의 갯수 구하는 메소드
	@Override
	public int searchCnt(String keyword) {
		return dao.searchCnt(keyword);
	}

	


}
