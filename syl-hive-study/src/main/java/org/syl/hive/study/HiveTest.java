package org.syl.hive.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class HiveTest {
	
	
	public static void main(String[] args) {
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			Connection conn = DriverManager.getConnection("jdbc:hive2://node1:10000/default", "root", "");
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from t_emp"); 
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				System.out.println(id);
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
