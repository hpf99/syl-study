package org.syl.hbase.study;

import java.util.Properties;


public class ConfigUtil {

	private static Properties properties;
	
	static {
		properties = PropertyConfiger.loadProperties("conf");
	}
	 
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
}
