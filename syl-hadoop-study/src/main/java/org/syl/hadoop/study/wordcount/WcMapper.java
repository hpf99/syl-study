package org.syl.hadoop.study.wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WcMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		StringTokenizer st = new StringTokenizer(value.toString());
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			context.write(new Text(word), new IntWritable(1));
		}
		
	}
}
