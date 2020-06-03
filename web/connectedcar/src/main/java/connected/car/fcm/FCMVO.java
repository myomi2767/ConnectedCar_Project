package connected.car.fcm;

public class FCMVO {
	String car_id;
	String token;
	String type;
	String gps;
	public FCMVO() {
		// TODO Auto-generated constructor stub
	}
	public FCMVO(String car_id, String token) {
		super();
		this.car_id = car_id;
		this.token = token;
	}
	public FCMVO(String car_id, String token,String type) {
		super();
		this.car_id = car_id;
		this.token = token;
		this.type = type;
	}
	
	public FCMVO(String car_id, String token, String type, String gps) {
		super();
		this.car_id = car_id;
		this.token = token;
		this.type = type;
		this.gps = gps;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "FCMVO [car_id=" + car_id + ", token=" + token + ", type=" + type + ", gps=" + gps + "]";
	}

}
