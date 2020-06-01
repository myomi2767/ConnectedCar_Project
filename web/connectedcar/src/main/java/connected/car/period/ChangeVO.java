package connected.car.period;


//ExpendableVO와 동일한 VO입니다. 
//ExpendableVO에서 사용하려고 했는데 "다른 매개변수지만 매개변수가 갯수가 같은 생성자가 있어서" 사용불가로 뜨기때문에 새롭게 만들었습니다. 
public class ChangeVO {
	private String expend_id;
	private String expend_code;
	private String expend_type;
	private String expend_name;
	private String expend_price;
	private String expend_brand;
	private String car_model_name;
	
	public ChangeVO() {
		
	}
	

	public ChangeVO(String expend_type, String car_model_name) {
		super();
		this.expend_type = expend_type;
		this.car_model_name = car_model_name;
	}







	public ChangeVO(String expend_code, String expend_type, String expend_name, String expend_price,
			String expend_brand, String car_model_name) {
		super();
		this.expend_code = expend_code;
		this.expend_type = expend_type;
		this.expend_name = expend_name;
		this.expend_price = expend_price;
		this.expend_brand = expend_brand;
		this.car_model_name = car_model_name;
	}
	
	public ChangeVO(String expend_id, String expend_code, String expend_type, String expend_name,
			String expend_price, String expend_brand, String car_model_name) {
		super();
		this.expend_id = expend_id;
		this.expend_code = expend_code;
		this.expend_type = expend_type;
		this.expend_name = expend_name;
		this.expend_price = expend_price;
		this.expend_brand = expend_brand;
		this.car_model_name = car_model_name;
	}

	@Override
	public String toString() {
		return "ExpendableVO [expend_id=" + expend_id + ", expend_code=" + expend_code + ", expend_type=" + expend_type
				+ ", expend_name=" + expend_name + ", expend_price=" + expend_price + ", expend_brand=" + expend_brand
				+ ", car_model_name=" + car_model_name + "]";
	}

	public String getExpend_id() {
		return expend_id;
	}

	public void setExpend_id(String expend_id) {
		this.expend_id = expend_id;
	}

	public String getExpend_code() {
		return expend_code;
	}

	public void setExpend_code(String expend_code) {
		this.expend_code = expend_code;
	}

	public String getExpend_type() {
		return expend_type;
	}

	public void setExpend_type(String expend_type) {
		this.expend_type = expend_type;
	}

	public String getExpend_name() {
		return expend_name;
	}

	public void setExpend_name(String expend_name) {
		this.expend_name = expend_name;
	}

	public String getExpend_price() {
		return expend_price;
	}

	public void setExpend_price(String expend_price) {
		this.expend_price = expend_price;
	}

	public String getExpend_brand() {
		return expend_brand;
	}

	public void setExpend_brand(String expend_brand) {
		this.expend_brand = expend_brand;
	}

	public String getCar_model_name() {
		return car_model_name;
	}

	public void setCar_model_name(String car_model_name) {
		this.car_model_name = car_model_name;
	}
	
	
	
}
