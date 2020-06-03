package connected.car.bigdata;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupKeyComparator extends WritableComparator {
	public GroupKeyComparator() {
		super(CustomKey.class, true);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable obj1, WritableComparable obj2) {
		CustomKey c1 = (CustomKey)obj1;
		CustomKey c2 = (CustomKey)obj2;
		
		int result = c1.getShop_id().compareTo(c2.getShop_id());
		if(result == 0) {
			result = c1.getYear().compareTo(c2.getYear());
		}
		if(result == 0) {
			result = c1.getExpend_type().compareTo(c2.getExpend_type());
		}
		if(result == 0) {
			result = c1.getCar_model_name().compareTo(c2.getCar_model_name());
		}
		
		return result;
	}
}
