package connected.car.expendable;

public class ExpendableLogVO {
	private String shop_id;
	private String expend_id;
	private String in_out_date;
	private String in_out_code;
	private int expend_count;
	
	public ExpendableLogVO() {
		// TODO Auto-generated constructor stub
	}

	public ExpendableLogVO(String shop_id, String expend_id) {
		super();
		this.shop_id = shop_id;
		this.expend_id = expend_id;
	}

	public ExpendableLogVO(String shop_id, String expend_id, String in_out_code, int expend_count) {
		super();
		this.shop_id = shop_id;
		this.expend_id = expend_id;
		this.in_out_code = in_out_code;
		this.expend_count = expend_count;
	}

	public ExpendableLogVO(String shop_id, String expend_id, String in_out_date, String in_out_code,
			int expend_count) {
		super();
		this.shop_id = shop_id;
		this.expend_id = expend_id;
		this.in_out_date = in_out_date;
		this.in_out_code = in_out_code;
		this.expend_count = expend_count;
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

	public String getIn_out_date() {
		return in_out_date;
	}

	public void setIn_out_date(String in_out_date) {
		this.in_out_date = in_out_date;
	}

	public String getIn_out_code() {
		return in_out_code;
	}

	public void setIn_out_code(String in_out_code) {
		this.in_out_code = in_out_code;
	}

	public int getExpend_count() {
		return expend_count;
	}

	public void setExpend_count(int expend_count) {
		this.expend_count = expend_count;
	}

	@Override
	public String toString() {
		return "ExpendableLogVO [shop_id=" + shop_id + ", expend_id=" + expend_id + ", in_out_date=" + in_out_date
				+ ", in_out_code=" + in_out_code + ", expend_count=" + expend_count + "]";
	}
	
}
