package connected.car.bigdata;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ShopCodeMapper extends Mapper<LongWritable, Text, CustomKey, Text> {
	private CustomKey outputKey = new CustomKey();
	Text outputVal = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, CustomKey, Text>.Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split(",");
		
		outputKey.setShop_id(line[0]);
		outputKey.setTag(key.get());
		outputVal.set(line[1]);
		
		context.write(outputKey, outputVal);
	}
}
