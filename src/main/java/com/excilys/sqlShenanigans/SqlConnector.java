package com.excilys.sqlShenanigans;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.persistence.ComputerDAO;

//  
/**
 * The Class SqlConnector.
 */
public class SqlConnector {  // Lazy Initialization Singleton

	/** The connection. */
  private static Connection con;
  //private static String username="admincdb";
  //private static String password = "qwerty1234";
  
  public static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	
	/**
	 * Gets the single instance of SqlConnector.
	 *
	 * @return single instance of SqlConnector
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static Connection getInstance() throws SQLException, ClassNotFoundException, IOException
	{
		if(con==null||con.isClosed())
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			InputStream inputStream = new FileInputStream("local.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			con = DriverManager.getConnection(properties.getProperty("db.url"), 
					properties.getProperty("db.username"), properties.getProperty("db.password"));
		}
		return con;
	}
	
	
	
	/**
	 * Instantiates a new sql connector.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private SqlConnector() throws ClassNotFoundException, IOException{	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			InputStream inputStream = new FileInputStream("local.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			con = DriverManager.getConnection(properties.getProperty("db.url"), 
					properties.getProperty("db.username"), properties.getProperty("db.password"));
			} catch (SQLException e) {
				logger.error("Connection to the database could not be established",e);
				e.printStackTrace();
			}
	

		}


	
}
	

