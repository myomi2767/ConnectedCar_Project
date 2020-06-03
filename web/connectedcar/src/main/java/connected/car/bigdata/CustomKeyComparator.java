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
		
		return c1.compareTo(c2);
	}
}
