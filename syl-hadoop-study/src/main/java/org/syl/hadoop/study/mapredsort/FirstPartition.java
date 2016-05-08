package org.syl.hadoop.study.mapredsort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FirstPartition extends Partitioner<KeyPair, Text> {

	@Override
	public int getPartition(KeyPair key, Text value, int num) {
		return (key.getYear() * 127) % num;
	}

}
