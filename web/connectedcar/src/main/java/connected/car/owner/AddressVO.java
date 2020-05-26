package connected.car.owner;

public class AddressVO {
	private String address_do;
	private String address_si;
	private String address_gu;
	
	public AddressVO() {
		// TODO Auto-generated constructor stub
	}
	
	public AddressVO(String address_do, String address_si, String address_gu) {
		super();
		this.address_do = address_do;
		this.address_si = address_si;
		this.address_gu = address_gu;
	}

	public String getAddress_do() {
		return address_do;
	}

	public void setAddress_do(String address_do) {
		this.address_do = address_do;
	}

	public String getAddress_si() {
		return address_si;
	}

	public void setAddress_si(String address_si) {
		this.address_si = address_si;
	}

	public String getAddress_gu() {
		return address_gu;
	}

	public void setAddress_gu(String address_gu) {
		this.address_gu = address_gu;
	}

	@Override
	public String toString() {
		return "AddressVO [address_do=" + address_do + ", address_si=" + address_si + ", address_gu=" + address_gu + "]";
	}
}
