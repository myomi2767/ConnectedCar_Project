package connected.car.bigdata;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AnalyzePurchaseMapper extends Mapper<LongWritable, Text, CustomKey, Text> {
	private CustomKey outputKey = new CustomKey();
	private Text outputVal = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, CustomKey, Text>.Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split(",");
		String in_out_date = line[0];
		String in_out_code = line[1];
		String expend_type = line[2];
		int	in_out_count = Integer.parseInt(line[3]);
		String expend_price = line[4] + line[5];
		String shop_id = line[6];
		
		if(in_out_count < 0) {
			outputKey.setShop_id(shop_id);
			outputKey.setExpend_type(expend_type);
			StringTokenizer stk = new StringTokenizer(in_out_date, "-");
			int year = Integer.parseInt(stk.nextToken());
			int month = Integer.parseInt(stk.nextToken());
			int date = Integer.parseInt(stk.nextToken());
			outputKey.setMonth(month);				
			outputKey.setTag(1);

			outputVal.set(Integer.toString(Math.abs(in_out_count)));
			
			context.write(outputKey, outputVal);				
		}
	}
}