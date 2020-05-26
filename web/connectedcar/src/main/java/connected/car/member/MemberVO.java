package connected.car.member;

public class MemberVO {
	private String user_id;
	private String car_id;
	private String user_password;
	private String user_name;
	private String user_birthdate;
	private String user_gender;
	private String driver_license;
	
	public MemberVO() {
		// TODO Auto-generated constructor stub
	}
	
	public MemberVO(String user_id, String user_password) {
		super();
		this.user_id = user_id;
		this.user_password = user_password;
	}

	public MemberVO(String user_id, String car_id, String user_password, String user_name, String user_birthdate,
			String user_gender, String driver_license) {
		super();
		this.user_id = user_id;
		this.car_id = car_id;
		this.user_password = user_password;
		this.user_name = user_name;
		this.user_birthdate = user_birthdate;
		this.user_gender = user_gender;
		this.driver_license = driver_license;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_birthdate() {
		return user_birthdate;
	}

	public void setUser_birthdate(String user_birthdate) {
		this.user_birthdate = user_birthdate;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getDriver_license() {
		return driver_license;
	}

	public void setDriver_license(String driver_license) {
		this.driver_license = driver_license;
	}

	@Override
	public String toString() {
		return "MemberVO [user_id=" + user_id + ", car_id=" + car_id + ", user_password=" + user_password
				+ ", user_name=" + user_name + ", user_birthdate=" + user_birthdate + ", user_gender=" + user_gender
				+ ", driver_license=" + driver_license + "]";
	}
	
	
}
