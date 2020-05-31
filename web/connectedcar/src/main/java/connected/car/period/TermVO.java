package connected.car.period;

public class TermVO {
	
	private String car_brand;
	private String car_fuel_type;
	private String expend_kind;
	private String expend_term;
	private String expend_type;
	
	
	public TermVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	//select 해서 가져온 결과를 저장하려고 만든 생성자
	public TermVO(String expend_kind, String expend_term, String expend_type) {
		super();
		this.expend_kind = expend_kind;
		this.expend_term = expend_term;
		this.expend_type = expend_type;
	}

	
	

	public TermVO(String car_brand, String car_fuel_type, String expend_kind, String expend_term, String expend_type) {
		super();
		this.car_brand = car_brand;
		this.car_fuel_type = car_fuel_type;
		this.expend_kind = expend_kind;
		this.expend_term = expend_term;
		this.expend_type = expend_type;
	}


	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}

	public String getCar_fuel_type() {
		return car_fuel_type;
	}

	public void setCar_fuel_type(String car_fuel_type) {
		this.car_fuel_type = car_fuel_type;
	}

	public String getExpend_kind() {
		return expend_kind;
	}

	public void setExpend_kind(String expend_kind) {
		this.expend_kind = expend_kind;
	}

	public String getExpend_term() {
		return expend_term;
	}

	public void setExpend_term(String expend_term) {
		this.expend_term = expend_term;
	}

	public String getExpend_type() {
		return expend_type;
	}

	public void setExpend_type(String expend_type) {
		this.expend_type = expend_type;
	}

	@Override
	public String toString() {
		return "TermVO [car_brand=" + car_brand + ", car_fuel_type=" + car_fuel_type + ", expend_kind=" + expend_kind
				+ ", expend_term=" + expend_term + ", expend_type=" + expend_type + "]";
	}
	
	
	
	
	

}
