package connected.car.fcm;

import java.util.List;

public interface FCMService {
	int getToken(FCMVO vo);

	List<FCMVO> getClientToken(String car_id);
	List<FCMVO> getClientGps(String car_id);
}
