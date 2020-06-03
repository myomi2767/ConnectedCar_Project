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
		String shop_id = line[0];
		String in_out_date = line[1];
		String in_out_code = line[2];
		String expend_type = line[3];
		String car_model_name = line[4];
		int	in_out_count = Integer.parseInt(line[5]);
		
		
		if(in_out_code.equals("출고")) {
			StringTokenizer stk = new StringTokenizer(in_out_date, "-");
			String year = stk.nextToken();
			int month = Integer.parseInt(stk.nextToken());
			int date = Integer.parseInt(stk.nextToken());
			
			outputKey.setShop_id(shop_id);
			outputKey.setYear(year);
			outputKey.setExpend_type(expend_type);
			outputKey.setCar_model_name(car_model_name);
			outputKey.setTag(key.get());
			
			
			outputVal.set(Math.abs(in_out_count));
			
			context.write(outputKey, outputVal);				
		}
	}
}