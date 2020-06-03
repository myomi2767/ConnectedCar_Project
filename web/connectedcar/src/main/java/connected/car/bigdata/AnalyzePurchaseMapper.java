package connected.car.bigdata;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AnalyzePurchaseMapper extends Mapper<LongWritable, Text, CustomKey, IntWritable> {
	private CustomKey outputKey = new CustomKey();
	private IntWritable outputVal = new IntWritable();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, CustomKey, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split(",");
		String in_out_date = line[0];
		String in_out_code = line[1];
		String expend_type = line[2];
		String car_model_name = line[3];
		int	in_out_count = Integer.parseInt(line[4]);
		String shop_id = line[5];
		
		if(in_out_count < 0) {
			StringTokenizer stk = new StringTokenizer(in_out_date, "-");
			String year = stk.nextToken();
			int month = Integer.parseInt(stk.nextToken());
			int date = Integer.parseInt(stk.nextToken());
			
			outputKey.setShop_id(shop_id);
			outputKey.setTag(key.get());
			outputKey.setYear(year);
			outputKey.setExpend_type(expend_type);
			outputKey.setCar_model_name(car_model_name);
			
			
			outputVal.set(Math.abs(in_out_count));
			
			context.write(outputKey, outputVal);				
		}
	}
}