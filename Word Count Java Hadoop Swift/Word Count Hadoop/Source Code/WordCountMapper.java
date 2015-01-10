 import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import java.util.StringTokenizer;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class WordCountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>
{
	//Variable to store recognized words
	private Text word = new Text();

	//Variable to emit for each word recognized
	private final IntWritable one = new IntWritable(1);

	//Map method that overrides map method in Mapper interface of Hadoop
	//Maps input key/value to intermediate key/value
	//inputs: 
	//LongWritable key: Key of the input
	//Text value: Value of the input. One line of the input text to count the words of
	//OutputCollector: result of the map as key,value pairs. Maps each recognized word to 
	//                 variable one. Intermediate key/value pairs will be stored here 
	//Reporter reporter: Hadoop reporter. It reports the progress of the tasks being done
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
	{
		try{
			//Cast input line from Hadoop Text to Java String 
			String line = value.toString();
			
			//Tokenize the read line to recognize the words
			StringTokenizer itr = new StringTokenizer(line.toLowerCase());
			
			//Emit a key/value pair for each token (recognized word)
			while(itr.hasMoreTokens()) {
			
				//Store the read word into variable 'word'
				word.set(itr.nextToken());
				
				//Make a key/pair for the read word: <word, 1> and stre them in OutPut
				output.collect(word, one);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

