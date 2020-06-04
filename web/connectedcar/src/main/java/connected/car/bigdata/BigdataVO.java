package connected.car.bigdata;

public class BigdataVO {
	private String shop_id;
	private String year;
	private String expend_type;
	private String car_model_name;
	private int out_count;
	
	public BigdataVO() {
		// TODO Auto-generated constructor stub
	}

	public BigdataVO(String shop_id, String year, String expend_type, String car_model_name, int out_count) {
		super();
		this.shop_id = shop_id;
		this.year = year;
		this.expend_type = expend_type;
		this.car_model_name = car_model_name;
		this.out_count = out_count;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getExpend_type() {
		return expend_type;
	}

	public void setExpend_type(String expend_type) {
		this.expend_type = expend_type;
	}

	public String getCar_model_name() {
		return car_model_name;
	}

	public void setCar_model_name(String car_model_name) {
		this.car_model_name = car_model_name;
	}

	public int getOut_count() {
		return out_count;
	}

	public void setOut_count(int out_count) {
		this.out_count = out_count;
	}

	@Override
	public String toString() {
		return "BigdataVO [shop_id=" + shop_id + ", year=" + year + ", expend_type=" + expend_type + ", car_model_name="
				+ car_model_name + ", out_count=" + out_count + "]";
	}
	
}
