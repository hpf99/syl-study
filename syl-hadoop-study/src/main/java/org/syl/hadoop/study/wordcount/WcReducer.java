package org.syl.hadoop.study.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WcReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	protected void reduce(Text key, Iterable<IntWritable> iterable,
			Context context) throws IOException, InterruptedException {
		
		int sum = 0;
		for (IntWritable i : iterable) {
			sum +=i.get();
		}
		context.write(key, new IntWritable(sum));
	}
}
