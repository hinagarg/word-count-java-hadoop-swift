//Driver: Main class to run MySort MapReduce on Hadoop

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class MySortDriver {
	
	public static void main(String[] args) {
	
		//Create a JobClient 
		JobClient job = new JobClient();
	
		//Create a JobConf object to specify the configurations of the job
		//Later this object of configurations will be assigned to the job 
		JobConf configurations = new JobConf(MySortDriver.class);

		//Set the name of the job
		configurations.setJobName("MySort");

		//Output Key type
		configurations.setOutputKeyClass(Text.class);
		
		//Output Value type
		configurations.setOutputValueClass(Text.class);

		//Input Directory
		FileInputFormat.addInputPath(configurations, new Path("input"));
		
		//Output Directory
		//This output directory must not be existed. Otherwise throws exception
		FileOutputFormat.setOutputPath(configurations, new Path("output"));

		//Set MAPPER class
		configurations.setMapperClass(MySortMapper.class);

		//Set REDUCER class
		configurations.setReducerClass(MySortReducer.class);
		
		//Set COMBINER class
		//In our case, combiner and reducer are take care in a single class
		configurations.setCombinerClass(MySortReducer.class);

		//Assign the configurations to the job
		job.setConf(configurations);
		
		try {
			//RUN the job
			JobClient.runJob(configurations);
		} catch (Exception e) {
			//Any exception that may happen during the job execution is caught here
			e.printStackTrace();
		}
	}
}
