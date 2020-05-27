package connected.car.sales;

public class SalesVO {
	private int expend_count;
	private String in_out_date;
	private String expend_price;
	private String expend_type;
	
	public SalesVO() {
		// TODO Auto-generated constructor stub
	}
	
	public SalesVO(String in_out_date, String expend_price, int expend_count) {
		super();
		this.expend_count = expend_count;
		this.in_out_date = in_out_date;
		this.expend_price = expend_price;
	}

	public SalesVO(int expend_count, String expend_price, String expend_type) {
		super();
		this.expend_count = expend_count;
		this.expend_price = expend_price;
		this.expend_type = expend_type;
	}

	public SalesVO(int expend_count, String in_out_date, String expend_price, String expend_type) {
		super();
		this.expend_count = expend_count;
		this.in_out_date = in_out_date;
		this.expend_price = expend_price;
		this.expend_type = expend_type;
	}

	public int getExpend_count() {
		return expend_count;
	}

	public void setExpend_count(int expend_count) {
		this.expend_count = expend_count;
	}

	public String getIn_out_date() {
		return in_out_date;
	}

	public void setIn_out_date(String in_out_date) {
		this.in_out_date = in_out_date;
	}

	public String getExpend_price() {
		return expend_price;
	}

	public void setExpend_price(String expend_price) {
		this.expend_price = expend_price;
	}

	public String getExpend_type() {
		return expend_type;
	}

	public void setExpend_type(String expend_type) {
		this.expend_type = expend_type;
	}

	@Override
	public String toString() {
		return "SalesVO [expend_count=" + expend_count + ", in_out_date=" + in_out_date
				+ ", expend_price=" + expend_price + ", expend_type=" + expend_type + "]";
	}
	
	
}
