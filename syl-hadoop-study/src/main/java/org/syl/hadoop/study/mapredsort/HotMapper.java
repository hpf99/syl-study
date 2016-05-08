package org.syl.hadoop.study.mapredsort;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syl.hadoop.study.util.LocalDateUtil;

public class HotMapper extends Mapper<LongWritable, Text, KeyPair, Text> {
	
	
	Logger log = LoggerFactory.getLogger(HotMapper.class);

	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		log.info("map param key:[{}] , value:[{}]", key,value);
		String[] line = value.toString().split("\t");
		LocalDateTime date = LocalDateUtil.str2LocalDateTime(line[0]);
		int year = date.getYear();
		int hot = Integer.parseInt(line[1].substring(0, line[1].indexOf("ºC")));
		KeyPair kp = new KeyPair();
		kp.setHot(hot);
		kp.setYear(year);
		log.info("keypair:[{}]",kp.toString());
		context.write(kp, value);
	}

}
