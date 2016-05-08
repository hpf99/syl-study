package org.syl.hadoop.study.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WcReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	protected void reduce(Text key, Iterable<IntWritable> iterable,
			Context context) throws IOException, InterruptedException {
		//统计出单词一样的个数并输出
		int sum = 0;
		for (IntWritable count : iterable) {
			sum += count.get();
		}
		context.write(key, new IntWritable(sum));

	}
}
