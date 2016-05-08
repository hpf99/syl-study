package org.syl.hadoop.study.mapred;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WcMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

	
	
	//每次调用map方法会传入split中一行数据，key:该行数据所在文件中的位置下标
	//value 这行数据
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		//StringTokenizer 默认会按空格去切分该行数据  所以切完以后得到的每一个元素就是一个单词
		StringTokenizer st = new StringTokenizer(value.toString());
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			//map的输出
			context.write(new Text(word), new IntWritable(1));
		}
		
	}
}
