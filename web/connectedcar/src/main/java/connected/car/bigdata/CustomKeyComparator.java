package connected.car.bigdata;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class CustomKeyComparator extends WritableComparator {
	public CustomKeyComparator() {
		super(CustomKey.class, true);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable obj1, WritableComparable obj2) {
		CustomKey c1 = (CustomKey)obj1;
		CustomKey c2 = (CustomKey)obj2;
		
		int cmp = c1.getShop_id().compareTo(c2.getShop_id());
		if(cmp != 0) {
			return cmp;
		}
		cmp = c1.getExpend_type().compareTo(c2.getExpend_type());
		if(cmp != 0) {
			return cmp;
		}
		cmp = c1.getMonth().compareTo(c2.getMonth());
		if(cmp != 0) {
			return cmp;
		}
		return c1.getTag().compareTo(c1.getTag());
	}
}
