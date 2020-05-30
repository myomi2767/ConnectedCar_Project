package connected.car.expendable;

public class ShopExpendableVO {
	private String expend_id;
	private String expend_code;
	private String expend_type;
	private String expend_name;
	private String expend_price;
	private String expend_brand;
	private String car_model_name;
	private String shop_expend_date;
	private	int	shop_expend_count;
	private String shop_id;
	
	public ShopExpendableVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	


	public ShopExpendableVO(String expend_id, String expend_code, String expend_type, String expend_name,
			String expend_price, String expend_brand, String car_model_name, String shop_expend_date,
			int shop_expend_count, String shop_id) {
		super();
		this.expend_id = expend_id;
		this.expend_code = expend_code;
		this.expend_type = expend_type;
		this.expend_name = expend_name;
		this.expend_price = expend_price;
		this.expend_brand = expend_brand;
		this.car_model_name = car_model_name;
		this.shop_expend_date = shop_expend_date;
		this.shop_expend_count = shop_expend_count;
		this.shop_id = shop_id;
	}

	

	public ShopExpendableVO(String expend_id, String expend_code, String expend_type, String expend_name,
			String expend_price, String expend_brand, String car_model_name, int shop_expend_count) {
		super();
		this.expend_id = expend_id;
		this.expend_code = expend_code;
		this.expend_type = expend_type;
		this.expend_name = expend_name;
		this.expend_price = expend_price;
		this.expend_brand = expend_brand;
		this.car_model_name = car_model_name;
		this.shop_expend_count = shop_expend_count;
	}

	public ShopExpendableVO(String expend_id, String expend_code, String expend_type, String expend_name,
			String expend_price, String expend_brand, String car_model_name, String shop_expend_date,
			int shop_expend_count) {
		super();
		this.expend_id = expend_id;
		this.expend_code = expend_code;
		this.expend_type = expend_type;
		this.expend_name = expend_name;
		this.expend_price = expend_price;
		this.expend_brand = expend_brand;
		this.car_model_name = car_model_name;
		this.shop_expend_date = shop_expend_date;
		this.shop_expend_count = shop_expend_count;
	}
	
	
	
	
	public ShopExpendableVO(String expend_id, String shop_id) {
		super();
		this.expend_id = expend_id;
		this.shop_id = shop_id;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
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

	public String getShop_expend_date() {
		return shop_expend_date;
	}

	public void setShop_expend_date(String shop_expend_date) {
		this.shop_expend_date = shop_expend_date;
	}

	public int getShop_expend_count() {
		return shop_expend_count;
	}

	public void setShop_expend_count(int shop_expend_count) {
		this.shop_expend_count = shop_expend_count;
	}

	@Override
	public String toString() {
		return "ShopExpendableVO [expend_id=" + expend_id + ", expend_code=" + expend_code + ", expend_type="
				+ expend_type + ", expend_name=" + expend_name + ", expend_price=" + expend_price + ", expend_brand="
				+ expend_brand + ", car_model_name=" + car_model_name + ", shop_expend_date=" + shop_expend_date
				+ ", shop_expend_count=" + shop_expend_count + "]";
	}

	
	
}
