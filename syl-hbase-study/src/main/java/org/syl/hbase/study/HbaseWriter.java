package org.syl.hbase.study;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseWriter {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 创建表
	 * @param tableName
	 * @throws Exception
	 */
	public void createTable(String tableName,String...columnFamily) throws Exception{
		Admin admin = HbaseClient.hbase.getAdmin();
		HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
		for (String family : columnFamily) {
			HColumnDescriptor cdes = new HColumnDescriptor(family);
			desc.addFamily(cdes);
		}
		if (isTableExist(tableName)) {
			throw new Exception("该表"+tableName+"已经存在，不可以重复创建");
		}
		admin.createTable(desc);
		logger.info("表:{} 创建成功！", tableName);
	}

	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean isTableExist(String tableName) throws IOException {
		Admin admin = HbaseClient.hbase.getAdmin();
		boolean exists = admin.tableExists(TableName.valueOf(tableName));
		logger.info("表:{},{}",tableName , exists ? "已经存在！" : "不存在!");
		return exists;
	}
	
	/**
	 * 删除表
	 * @throws IOException 
	 */
	public void deleteTable(String tableName) throws Exception{
		Admin admin = HbaseClient.hbase.getAdmin();
		if (!isTableExist(tableName)) {
			throw new Exception("不存在表:"+tableName+"，不可以删除");
		}
		TableName table = TableName.valueOf(tableName);
		admin.disableTable(table);
		admin.deleteTable(table);
		logger.info("删除表:{} 成功！",tableName);
		
	}
	
	
}
