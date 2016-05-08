package org.syl.hadoop.study.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class JobRun {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "wordCount");
		job.setJarByClass(JobRun.class);
		job.setMapperClass(WcMapper.class);
		job.setCombinerClass(WcReducer.class);
		job.setReducerClass(WcReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path("/home/hadoop"));
		FileOutputFormat.setOutputPath(job,new Path(args[0]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
