package connected.car.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class AnalyzePurchasePartitioner extends Partitioner<CustomKey, IntWritable> {

	@Override
	public int getPartition(CustomKey key, IntWritable value, int numPartitions) {
		int partition = key.getShop_id().hashCode() % numPartitions;
		return partition;
	}
	
}
