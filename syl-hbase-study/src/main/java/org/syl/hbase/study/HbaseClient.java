package org.syl.hbase.study;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseClient {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static HbaseClient hbase = new HbaseClient();
	
	private Connection conn;
	
	private Object _lock = new Object ();
	
	private HbaseClient(){
		
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public Connection getConnection(){
		if (conn == null) {
			synchronized (_lock) {
				if (conn == null) {
					Configuration config = HBaseConfiguration.create();
					config.set("hbase.zookeeper.quorum", ConfigUtil.getValue("hbase.zk"));
					try {
						conn = ConnectionFactory.createConnection(config);
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("创建连接时找不到hbase-client.jar");
					} 
				}
			}
		}
		return conn;
	}
	
	public Admin getAdmin() throws IOException{
		try (Admin admin = getConnection().getAdmin()){
			return admin;
		} 
	}
	
	public Table getTable(String tableName) throws IOException{
		try(Table table = getConnection().getTable(TableName.valueOf(tableName))){
			return table;
		}
	}
	
}
