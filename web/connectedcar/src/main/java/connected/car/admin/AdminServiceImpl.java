package connected.car.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import connected.car.inventory.ExpendableVO;

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
	public List<ExpendableVO> listAll() {
		return dao.listAll();
	}


}
