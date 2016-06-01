package org.syl.hbase.study;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseWriter {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 创建表
	 * @param tableName
	 * @throws Exception
	 */
	public void createTable(String tableName,String[] columnFamily) {
		Admin admin = HbaseClient.hbase.getAdmin();
		HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
		if (columnFamily == null || columnFamily.length <1) {
            throw new RuntimeException("创建表时至少需要指定一个列族！");
        }
		for (String family : columnFamily) {
			HColumnDescriptor cdes = new HColumnDescriptor(family);
			desc.addFamily(cdes);
		}
		try {
		    boolean exists = admin.tableExists(TableName.valueOf(tableName));
		    if (exists) {
		        throw new RuntimeException("该表"+tableName+"已经存在，不可以重复创建");
            }
            admin.createTable(desc);
            logger.info("表:{} 创建成功！", tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean isTableExist(String tableName) {
		Admin admin = HbaseClient.hbase.getAdmin();
		boolean exists=false;
        try {
            exists = admin.tableExists(TableName.valueOf(tableName));
            logger.info("表:{},{}",tableName , exists ? "已经存在！" : "不存在!");
        } catch (IOException e) {
            e.printStackTrace();
        }
		return exists;
	}
	
	/**
	 * 删除表
	 * @throws IOException 
	 */
	public void deleteTable(String tableName) {
		Admin admin = HbaseClient.hbase.getAdmin();
		try {
            boolean exists = admin.tableExists(TableName.valueOf(tableName));
            if (!exists) {
                throw new RuntimeException("不存在表:"+tableName+"，不可以删除");
            }
            TableName table = TableName.valueOf(tableName);
            admin.disableTable(table);
            admin.deleteTable(table);
            logger.info("删除表:{} 成功！",tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	
	
}
