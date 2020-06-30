package com.excilys.sqlShenanigans;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	 
	private static HikariConfig config = new HikariConfig(
		    "/hikari.properties" );
    private static HikariDataSource ds;
 
    static {
     /*   config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );*/
        ds = new HikariDataSource( config );
    }
 
    private DataSource() {}
 
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}