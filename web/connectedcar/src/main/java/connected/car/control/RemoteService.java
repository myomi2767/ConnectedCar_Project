package connected.car.control;

import java.util.List;

public interface RemoteService {
	public List<RemoteVO> listAll(String car_id);
	public int insertRemote(RemoteVO vo);
}
