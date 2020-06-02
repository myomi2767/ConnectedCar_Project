package connected.car.bigdata;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AnalyzeJoinReducer extends Reducer<CustomKey, Text, Text, Text> {

}
