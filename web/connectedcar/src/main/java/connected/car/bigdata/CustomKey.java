package connected.car.bigdata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class CustomKey implements WritableComparable<CustomKey>{
	//shop_id가 조인키가 된다.
	private String shop_id;
	private String expend_type;
	private Integer month;
	//조인을 위한 태그
	private Integer tag;
	
	public CustomKey() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomKey(String shop_id, String expend_type, Integer month, Integer tag) {
		super();
		this.shop_id = shop_id;
		this.expend_type = expend_type;
		this.month = month;
		this.tag = tag;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getExpend_type() {
		return expend_type;
	}

	public void setExpend_type(String expend_type) {
		this.expend_type = expend_type;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		shop_id = WritableUtils.readString(in);
		expend_type = WritableUtils.readString(in);
		month = in.readInt();
		tag = in.readInt();
	}
	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, shop_id);
		WritableUtils.writeString(out, expend_type);
		out.writeInt(month);
		out.writeInt(tag);
		
	}
	@Override
	public int compareTo(CustomKey obj) {
		int result = shop_id.compareTo(obj.shop_id);
		if(result == 0) {
			result = tag.compareTo(obj.tag);
		}
		return result;
	}
	
	
	

}
