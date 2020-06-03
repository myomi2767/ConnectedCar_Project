package connected.car.bigdata;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class AnalyzeJoinReducer extends Reducer<CustomKey, IntWritable, CustomKey, IntWritable> {
	private CustomKey outputKey = new CustomKey();
	private IntWritable outputVal = new IntWritable();
	@Override
	protected void reduce(CustomKey key, Iterable<IntWritable> values, Reducer<CustomKey, IntWritable, CustomKey, IntWritable>.Context context)
			throws IOException, InterruptedException {
		Iterator<IntWritable> iter = values.iterator();
		
		//String shop_id = key.getShop_id();
		//String year = key.getYear();
		//String expend_type = key.getExpend_type();
		String car_model_name = key.getCar_model_name();
		int sum = 0;
		while(iter.hasNext()) {
			if(!car_model_name.equals(key.getCar_model_name())) {
				outputKey.setShop_id(key.getShop_id());
				outputKey.setYear(key.getYear());
				outputKey.setExpend_type(key.getExpend_type());
				outputKey.setCar_model_name(car_model_name);
				outputVal.set(sum);
				context.write(outputKey, outputVal);
				sum = 0;
			}
			sum += iter.next().get();
			car_model_name = key.getCar_model_name();
		}
		
		if(car_model_name.equals(key.getCar_model_name())) {
			outputKey.setShop_id(key.getShop_id());
			outputKey.setYear(key.getYear());
			outputKey.setExpend_type(key.getExpend_type());
			outputKey.setCar_model_name(car_model_name);
			outputVal.set(sum);
			context.write(outputKey, outputVal);
		}
	}
}
