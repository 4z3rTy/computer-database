package com.excilys.sqlShenanigans;

import java.sql.Connection;
import java.sql.SQLException;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	private static HikariConfig config = new HikariConfig("/hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);
	private static Connection con;
	//private static DataSource instance;

	public static Connection getConnection() throws SQLException {
		if (con == null || con.isClosed()) {

			con = ds.getConnection();
		}
		return con;
	}
	
	/*public static synchronized DataSource getInstance() throws SQLException {
		if (instance==null) {
			instance = new DataSource();
		}
		return instance;
	}
*/
	private DataSource() throws SQLException {
		con = ds.getConnection();
	}
}
