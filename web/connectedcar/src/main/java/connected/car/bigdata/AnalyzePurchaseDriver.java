package connected.car.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AnalyzePurchaseDriver extends Configured implements Tool {
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new AnalyzePurchaseDriver(), args);
	}

	@Override
	public int run(String[] optionList) throws Exception {
		GenericOptionsParser parser = new GenericOptionsParser(getConf(), optionList);
		String[] otherArgs = parser.getRemainingArgs();
		
		Job job = new Job(getConf(), "expend_analysis");
		
		job.setMapperClass(AnalyzePurchaseMapper.class);
		job.setReducerClass(AnalyzeJoinReducer.class);
		job.setJarByClass(AnalyzePurchaseDriver.class);
		
		job.setPartitionerClass(AnalyzePurchasePartitioner.class);
		job.setGroupingComparatorClass(GroupKeyComparator.class);
		job.setSortComparatorClass(CustomKeyComparator.class);
		
		job.setMapOutputKeyClass(CustomKey.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		job.waitForCompletion(true);
		
		return 0;
	}
}
