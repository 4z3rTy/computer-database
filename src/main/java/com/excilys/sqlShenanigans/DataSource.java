package com.excilys.sqlShenanigans;

import java.sql.Connection;
import java.sql.SQLException;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	 
	private static HikariConfig config = new HikariConfig(
		    "/hikari.properties" );
	private static HikariDataSource ds = new HikariDataSource( config );
	private static Connection con;
 
 
  
    public static Connection getConnection() throws SQLException {
        if(con==null||con.isClosed())
        {
        	
        	con=ds.getConnection();
        }
        return con;
    }
    
    private DataSource() throws SQLException {
    	con=ds.getConnection();
    }
}
   