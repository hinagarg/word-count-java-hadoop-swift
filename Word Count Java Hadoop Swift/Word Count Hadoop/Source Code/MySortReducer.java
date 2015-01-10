import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class MySortReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	//Reduce method that overrides reduce method in Reducer interface of Hadoop
	//Sums the number of occurrences for each word in the intermediate key/value pair generated in map process
	//Text key: intermediate key (words recognized by mapper)
	//Iterator values: intermediate values (ONEs emitted by mapper)
	//OutputCollector output: results will be stored in this variable
	//Reporter reporter: reports progress to Hadoop 
	
	//Reducer groups similar words together
	public void reduce(Text key, Iterator values, OutputCollector output, Reporter reporter) throws IOException {
		try{

  			String result = "";

			output.collect(key, new Text(result));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
