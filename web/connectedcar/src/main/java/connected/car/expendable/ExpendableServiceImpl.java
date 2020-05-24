package connected.car.expendable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExpendableServiceImpl implements ExpendableService {
	@Autowired
	@Qualifier("expendableDao")
	ExpendableDAO dao;
	
	@Override
	public ExpendableVO findExpendable(String code, String brand) {
		return dao.findExpendable(code, brand);
	}
}
