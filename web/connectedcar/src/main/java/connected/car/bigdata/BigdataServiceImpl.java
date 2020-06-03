package connected.car.bigdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BigdataServiceImpl implements BigdataService {
	@Autowired
	@Qualifier("bigdataDao")
	BigdataDAO dao;
	
	@Override
	public List<BigdataVO> getBigdataList(String shop_id) {
		return dao.getBigdataList(shop_id);
	}
}
