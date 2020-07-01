package com.excilys.sqlShenanigans;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	 
	private static HikariConfig config = new HikariConfig(
		    "/hikari.properties" );
	private static HikariDataSource ds = new HikariDataSource( config );
	private static Connection con;
	private static final Logger logger = LoggerFactory.getLogger(DataSource.class);
 
 
  
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
   