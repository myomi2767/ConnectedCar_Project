package connected.car.owner;

public class OwnerVO {
	
	private String owner_id;
	private String owner_password;
	private String shop_id;
	private String owner_name;
	private String owner_phone;
	private String owner_regdate;
	
	public OwnerVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OwnerVO(String owner_id, String owner_password) {
		super();
		this.owner_id = owner_id;
		this.owner_password = owner_password;
	}

	public OwnerVO(String owner_id, String owner_password, String shop_id, String owner_name, String owner_phone) {
		super();
		this.owner_id = owner_id;
		this.owner_password = owner_password;
		this.shop_id = shop_id;
		this.owner_name = owner_name;
		this.owner_phone = owner_phone;
	}
	
	

	public OwnerVO(String owner_id, String owner_password, String shop_id, String owner_name, String owner_phone,
			String owner_regdate) {
		super();
		this.owner_id = owner_id;
		this.owner_password = owner_password;
		this.shop_id = shop_id;
		this.owner_name = owner_name;
		this.owner_phone = owner_phone;
		this.owner_regdate = owner_regdate;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_password() {
		return owner_password;
	}

	public void setOwner_password(String owner_password) {
		this.owner_password = owner_password;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getOwner_phone() {
		return owner_phone;
	}

	public void setOwner_phone(String owner_phone) {
		this.owner_phone = owner_phone;
	}

	public String getOwner_regdate() {
		return owner_regdate;
	}

	public void setOwner_regdate(String owner_regdate) {
		this.owner_regdate = owner_regdate;
	}

	@Override
	public String toString() {
		return "OwnerVO [owner_id=" + owner_id + ", owner_password=" + owner_password + ", shop_id=" + shop_id
				+ ", owner_name=" + owner_name + ", owner_phone=" + owner_phone + ", owner_regdate=" + owner_regdate
				+ "]";
	}


	

}
