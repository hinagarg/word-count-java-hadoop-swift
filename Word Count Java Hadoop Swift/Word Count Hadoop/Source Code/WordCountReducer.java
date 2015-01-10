import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class WordCountReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

	//Reduce method that overrides reduce method in Reducer interface of Hadoop
	//Sums the number of occurrences for each word in the intermediate key/value pair generated in map process
	//Text key: intermediate key (words recognized by mapper)
	//Iterator values: intermediate values (ONEs emitted by mapper)
	//OutputCollector output: results will be stored in this variable
	//Reporter reporter: reports progress to Hadoop 
	public void reduce(Text key, Iterator values, OutputCollector output, Reporter reporter) throws IOException {
		try{
			//Variable to store the total number of counts for each key(word)
			int sum = 0;
			
			//Loop on list of words (in the intermediate key/value pairs)
			while (values.hasNext()) {
				//value for each word is ONE
				IntWritable value = (IntWritable) values.next();
				//Increments 'sum' for each occurrence of the word 
				sum += value.get(); 
			}

			//Emit results as another key/value pair (word , sum) 
			output.collect(key, new IntWritable(sum));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}