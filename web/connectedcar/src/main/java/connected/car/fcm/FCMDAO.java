package connected.car.fcm;

import java.util.List;

public interface FCMDAO {
	FCMVO getToken(FCMVO vo);
	int insert(FCMVO vo);
	List<FCMVO> getClientToken(String car_id);
	List<FCMVO> getClientGps(String car_id);
}