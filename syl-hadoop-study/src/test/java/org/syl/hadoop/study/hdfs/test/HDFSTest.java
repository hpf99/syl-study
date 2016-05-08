package org.syl.hadoop.study.hdfs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

public class HDFSTest {
	
	
	
	/**
	 * 创建文件
	 */
	@Test
	public void createPath(){
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			Path path = new Path("file:///192.168.0.106:8088//home/hadoop");
			FSDataOutputStream out = fs.create(path);
			byte[] content = readFile("d:\\test.pdf");
			out.write(content);
			out.close();
			fs.close();
			System.out.println("文件创建成功！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public byte[] readFile(String path) throws IOException{
		File file = new File(path);
		FileInputStream in = new FileInputStream(file);
		byte[] b = new byte[1024];
		int i = in.read(b);
		
		
		return b;
		
		
	}
	
	public static void main(String[] args) {
		String s = "46ºC";
		int i = s.indexOf("ºC");
		int hot = Integer.parseInt(s.substring(0, i));
		System.out.println(hot);
	}
	

}
