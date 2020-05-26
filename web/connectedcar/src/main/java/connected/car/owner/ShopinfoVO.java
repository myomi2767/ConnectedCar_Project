package connected.car.owner;

public class ShopinfoVO {
	
	/*private String owner_id;
	private String owner_password;
	private String shop_id;
	private String owner_name;
	private String owner_phone;
	private String owner_regdate;
	*/
	
	private String shop_id;
	private String shop_name;
	private String shop_phone;
	private String shop_location;
	private String shop_latitude;
	private String shop_longitude;
	
	public ShopinfoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopinfoVO(String shop_id, String shop_name, String shop_phone, String shop_location, String shop_latitude,
			String shop_longitude) {
		super();
		this.shop_id = shop_id;
		this.shop_name = shop_name;
		this.shop_phone = shop_phone;
		this.shop_location = shop_location;
		this.shop_latitude = shop_latitude;
		this.shop_longitude = shop_longitude;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_phone() {
		return shop_phone;
	}

	public void setShop_phone(String shop_phone) {
		this.shop_phone = shop_phone;
	}

	public String getShop_location() {
		return shop_location;
	}

	public void setShop_location(String shop_location) {
		this.shop_location = shop_location;
	}

	public String getShop_latitude() {
		return shop_latitude;
	}

	public void setShop_latitude(String shop_latitude) {
		this.shop_latitude = shop_latitude;
	}

	public String getShop_longitude() {
		return shop_longitude;
	}

	public void setShop_longitude(String shop_longitude) {
		this.shop_longitude = shop_longitude;
	}

	@Override
	public String toString() {
		return "ShopinfoVO [shop_id=" + shop_id + ", shop_name=" + shop_name + ", shop_phone=" + shop_phone
				+ ", shop_location=" + shop_location + ", shop_latitude=" + shop_latitude + ", shop_longitude="
				+ shop_longitude + "]";
	}

	
	
	
	

}
