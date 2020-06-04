package latteserver;

public class DistanceDTO {
	String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DistanceDTO(String msg) {
		super();
		this.msg = msg;
	}

	public DistanceDTO() {
		super();
	}

	@Override
	public String toString() {
		return "distanceDTO [msg=" + msg + "]";
	}
	
}
