package connected.car.bigdata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class CustomKey implements WritableComparable<CustomKey>{
	//shop_id가 조인키가 된다.
	private String shop_id;
	private String year;
	private String expend_type;
	private String car_model_name;
	//조인을 위한 태그
	private Long tag;
	
	public CustomKey() {
		// TODO Auto-generated constructor stub
	}

	public CustomKey(String shop_id, String year, String expend_type, String car_model_name) {
		super();
		this.shop_id = shop_id;
		this.year = year;
		this.expend_type = expend_type;
		this.car_model_name = car_model_name;
	}

	public CustomKey(String shop_id, String year, String expend_type, String car_model_name, Long tag) {
		super();
		this.shop_id = shop_id;
		this.year = year;
		this.expend_type = expend_type;
		this.car_model_name = car_model_name;
		this.tag = tag;
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

	public Long getTag() {
		return tag;
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		shop_id = WritableUtils.readString(in);
		year = WritableUtils.readString(in);
		expend_type = WritableUtils.readString(in);
		car_model_name = WritableUtils.readString(in);
		tag = in.readLong();
	}
	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, shop_id);
		WritableUtils.writeString(out, year);
		WritableUtils.writeString(out, expend_type);
		WritableUtils.writeString(out, car_model_name);
		out.writeLong(tag);
		
	}
	@Override
	public int compareTo(CustomKey obj) {
		int result = shop_id.compareTo(obj.shop_id);
		if(result == 0) {
			result = year.compareTo(obj.year);
		}
		if(result == 0) {
			result = expend_type.compareTo(obj.expend_type);
		}
		if(result == 0) {
			result = car_model_name.compareTo(obj.car_model_name);
		}
		return result;
	}

	@Override
	public String toString() {
		return (new StringBuffer().append(shop_id).append("\t").append(year).append("\t")
				.append(expend_type).append("\t").append(car_model_name)).toString();
	}

}
