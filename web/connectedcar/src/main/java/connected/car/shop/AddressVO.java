package connected.car.shop;

public class AddressVO {
	private String address_do;
	private String address_si;
	private String address_gu;
	private String address_street;
	
	public AddressVO(String address_do, String address_si, String address_gu, String address_street) {
		super();
		this.address_do = address_do;
		this.address_si = address_si;
		this.address_gu = address_gu;
		this.address_street = address_street;
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
	
	public String getAddress_street() {
		return address_street;
	}

	public void setAddress_street(String address_street) {
		this.address_street = address_street;
	}

	@Override
	public String toString() {
		return "AddressVO [address_do=" + address_do + ", address_si=" + address_si + ", address_gu=" + address_gu + ", address_street=" + address_street + "]";
	}
}
