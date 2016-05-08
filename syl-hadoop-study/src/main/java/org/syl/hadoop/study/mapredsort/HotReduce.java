package org.syl.hadoop.study.mapredsort;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HotReduce extends Reducer<KeyPair, Text, KeyPair, Text> {

	protected void reduce(KeyPair kp, Iterable<Text> iterable, Context context)
			throws IOException, InterruptedException {
		for (Text v : iterable) {
			context.write(kp, v);
		}
	}
}
