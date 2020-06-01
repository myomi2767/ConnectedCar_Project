package connected.car.mycar;

public class MyCarVO {
	private String car_id;
	private String car_brand;
	private String car_model;
	private String car_fuel_type;
	private String car_year;
	private String car_volume;
	private int rapid_speed;
	private int drop_speed;
	private int overspeed;
	private String special_car;
	private int drive_distance;
	private String car_model_name;
	
	public MyCarVO() {
		
	}
	
	
	//My_expendable 데이터를 회원가입과 동시에 insert하기 위한 생성자 
	public MyCarVO(String car_brand, String car_fuel_type) {
		super();
		this.car_brand = car_brand;
		this.car_fuel_type = car_fuel_type;
	}



	public MyCarVO(String car_id, String car_brand, String car_model, String car_fuel_type, String car_year,
			String car_volume, int rapid_speed, int drop_speed, int overspeed, String special_car,
			int drive_distance, String car_model_name) {
		super();
		this.car_id = car_id;
		this.car_brand = car_brand;
		this.car_model = car_model;
		this.car_fuel_type = car_fuel_type;
		this.car_year = car_year;
		this.car_volume = car_volume;
		this.rapid_speed = rapid_speed;
		this.drop_speed = drop_speed;
		this.overspeed = overspeed;
		this.special_car = special_car;
		this.drive_distance = drive_distance;
		this.car_model_name = car_model_name;
	}

	

	@Override
	public String toString() {
		return "MyCarVO [car_id=" + car_id + ", car_brand=" + car_brand + ", car_model=" + car_model
				+ ", car_fuel_type=" + car_fuel_type + ", car_year=" + car_year + ", car_volume=" + car_volume
				+ ", rapid_speed=" + rapid_speed + ", drop_speed=" + drop_speed + ", overspeed=" + overspeed
				+ ", special_car=" + special_car + ", drive_distance=" + drive_distance + ", car_model_name="
				+ car_model_name + "]";
	}

	public String getCar_model_name() {
		return car_model_name;
	}

	public void setCar_model_name(String car_model_name) {
		this.car_model_name = car_model_name;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}

	public String getCar_model() {
		return car_model;
	}

	public void setCar_model(String car_model) {
		this.car_model = car_model;
	}

	public String getCar_fuel_type() {
		return car_fuel_type;
	}

	public void setCar_fuel_type(String car_fuel_type) {
		this.car_fuel_type = car_fuel_type;
	}

	public String getCar_year() {
		return car_year;
	}

	public void setCar_year(String car_year) {
		this.car_year = car_year;
	}

	public String getCar_volume() {
		return car_volume;
	}

	public void setCar_volume(String car_volume) {
		this.car_volume = car_volume;
	}

	public int getRapid_speed() {
		return rapid_speed;
	}

	public void setRapid_speed(int rapid_speed) {
		this.rapid_speed = rapid_speed;
	}

	public int getDrop_speed() {
		return drop_speed;
	}

	public void setDrop_speed(int drop_speed) {
		this.drop_speed = drop_speed;
	}

	public int getOverspeed() {
		return overspeed;
	}

	public void setOverspeed(int overspeed) {
		this.overspeed = overspeed;
	}

	public String getSpecial_car() {
		return special_car;
	}

	public void setSpecial_car(String special_car) {
		this.special_car = special_car;
	}

	public int getDrive_distance() {
		return drive_distance;
	}

	public void setDrive_distance(int drive_distance) {
		this.drive_distance = drive_distance;
	}
	
	
}
