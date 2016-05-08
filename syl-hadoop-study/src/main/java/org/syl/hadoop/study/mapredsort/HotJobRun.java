package org.syl.hadoop.study.mapredsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HotJobRun {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "hot sort");
		job.setJarByClass(HotJobRun.class);
		job.setMapperClass(HotMapper.class);
		job.setCombinerClass(HotReduce.class);
		job.setReducerClass(HotReduce.class);
		job.setOutputKeyClass(KeyPair.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(7);
		job.setPartitionerClass(FirstPartition.class);
		job.setSortComparatorClass(SortHot.class);
		job.setGroupingComparatorClass(GroupHot.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
