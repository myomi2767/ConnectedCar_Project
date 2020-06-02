package connected.car.period;

public class MyexpendVO {
	
	private String my_expend_no;
	private String car_id;
	private String expend_kind;
	private String expend_type;
	private String expend_term;
	private String my_expend_replace;
	private String my_expend_km;
	private String expend_id;
	
	public MyexpendVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
//앱에서 부품교체 시 업데이트 되는 기능의 생성자 : my_expend_no와 일치하는 행에서 "주행거리"와 "교체한부품정보"가 update된다.
	public MyexpendVO(String my_expend_no, String my_expend_km, String expend_id) {
		super();
		this.my_expend_no = my_expend_no;
		this.my_expend_km = my_expend_km;
		this.expend_id = expend_id;
	}







	public MyexpendVO(String car_id, String expend_kind, String expend_type, String expend_term) {
		super();
		this.car_id = car_id;
		this.expend_kind = expend_kind;
		this.expend_type = expend_type;
		this.expend_term = expend_term;
		
	}





	public MyexpendVO(String my_expend_no, String car_id, String expend_kind, String expend_type, String expend_term,
			String my_expend_replace, String my_expend_km, String expend_id) {
		super();
		this.my_expend_no = my_expend_no;
		this.car_id = car_id;
		this.expend_kind = expend_kind;
		this.expend_type = expend_type;
		this.expend_term = expend_term;
		this.my_expend_replace = my_expend_replace;
		this.my_expend_km = my_expend_km;
		this.expend_id = expend_id;
	}




	public String getMy_expend_no() {
		return my_expend_no;
	}

	public void setMy_expend_no(String my_expend_no) {
		this.my_expend_no = my_expend_no;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getExpend_kind() {
		return expend_kind;
	}

	public void setExpend_kind(String expend_kind) {
		this.expend_kind = expend_kind;
	}

	public String getExpend_type() {
		return expend_type;
	}

	public void setExpend_type(String expend_type) {
		this.expend_type = expend_type;
	}

	public String getExpend_term() {
		return expend_term;
	}

	public void setExpend_term(String expend_term) {
		this.expend_term = expend_term;
	}

	public String getMy_expend_replace() {
		return my_expend_replace;
	}

	public void setMy_expend_replace(String my_expend_replace) {
		this.my_expend_replace = my_expend_replace;
	}

	public String getMy_expend_km() {
		return my_expend_km;
	}

	public void setMy_expend_km(String my_expend_km) {
		this.my_expend_km = my_expend_km;
	}

	public String getExpend_id() {
		return expend_id;
	}

	public void setExpend_id(String expend_id) {
		this.expend_id = expend_id;
	}

	@Override
	public String toString() {
		return "MyexpendVO [my_expend_no=" + my_expend_no + ", car_id=" + car_id + ", expend_kind=" + expend_kind
				+ ", expend_type=" + expend_type + ", expend_term=" + expend_term + ", my_expend_replace="
				+ my_expend_replace + ", my_expend_km=" + my_expend_km + ", expend_id=" + expend_id + "]";
	}

	
	



}
