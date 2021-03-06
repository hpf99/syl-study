package org.syl.hbase.study.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syl.hbase.study.HbaseWriter;

public class PhoneTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Test
	public void testCreateTable() throws Exception{
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "node1,node2,node3");
		HBaseAdmin admin = new HBaseAdmin(config);
		String table = "t_cdr";
		if (admin.tableExists(table)) {
			logger.info("table:[{}] 表已经存在",table);
			System.exit(0);
		}else{
			HTableDescriptor tdes = new HTableDescriptor(TableName.valueOf(table));
			HColumnDescriptor cdes = new HColumnDescriptor("cf1");
			tdes.addFamily(cdes);
			admin.createTable(tdes);
			logger.info("创建表 table [{}] 成功!",table);
		}
		admin.close();
		
	}
	
	
	@Test
	public void testDeleteTable() throws Exception {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node1,node2,node3");
		HBaseAdmin admin = new HBaseAdmin(conf);
		String table = "t_cdr";
		if (admin.tableExists(table)) {
			admin.disableTable(table);
			admin.deleteTable(table);
			logger.info("删除表 table[{}] 成功！",table);
		}
	}
	
	@Test
	public void addData() throws Exception{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node1,node2,node3");
		HTable table = new HTable(conf, "t_cdr".getBytes());
		String row = "18310667310"+System.currentTimeMillis();
		Put put = new Put(row.getBytes());
		put.add("cf1".getBytes(), "dest".getBytes(), "18931961856".getBytes());
		put.add("cf1".getBytes(), "type".getBytes(), "1".getBytes());
		put.add("cf1".getBytes(), "time".getBytes(), LocalDateTime.now().toString().getBytes());
		table.put(put);
		table.close();
	}
	
	@Test
	public void getData() throws IOException{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node1,node2,node3");
		HTable table = new HTable(conf, "t_cdr".getBytes());
		
		Get get = new Get("183106673101463065865889".getBytes());
		Result result = table.get(get);
		List<Cell> cells = result.listCells();
		System.out.println();
		for (Cell cell : cells) {
			if (cell.getQualifierArray().equals("type".getBytes())) {
				System.out.println("呼叫类型："+new String(cell.getQualifierArray()));
			}
		}
		
		Scan scan = new Scan();
		ResultScanner resultScanner = table.getScanner(scan);
		System.out.println("scan");
		for (Result r : resultScanner) {
			List<Cell> cs = r.listCells();
			for (Cell cell : cs) {
				System.out.println(new String(cell.getQualifierArray()));
			}
		}
	}
	
	
	@Test
	public void createT(){
		HbaseWriter write = new HbaseWriter();
		try {
			write.deleteTable("test_tdc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPhoenix(){
	    Statement stmt = null;
        ResultSet rset = null;
	    try {
	        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            Connection con = DriverManager.getConnection("jdbc:phoenix:node1,node2,node3");
//            stmt = con.createStatement();
//            stmt.executeUpdate("create table test (mykey integer not null primary key, mycolumn varchar)");
//            stmt.executeUpdate("upsert into test values (1,'Hello')");
//            stmt.executeUpdate("upsert into test values (2,'World!')");
//            con.commit();
            
            PreparedStatement statement = con.prepareStatement("select * from t_cdr");
            rset = statement.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getString("name"));
                System.out.println(rset.getString("id"));
                System.out.println(rset.getString("age"));
            }
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	
	

}
