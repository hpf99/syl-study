package org.syl.hive.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class HiveTest {
	
	
	public static void main(String[] args) {
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			Connection conn = DriverManager.getConnection("jdbc:hive2://node1:10000/default", "root", "");
			Statement statement = conn.createStatement();
			boolean execute = statement.execute("LOAD DATA LOCAL INPATH '/home/hive.txt' OVERWRITE INTO TABLE financials.emp partition (deptno=2 ,empno=13) ");
			System.out.println(execute);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
