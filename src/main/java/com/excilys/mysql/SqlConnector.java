package com.excilys.mysql;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.persistence.ComputerDAO;

//  
/**
 * The Class SqlConnector.
 */
@Component
public class SqlConnector {

	/** The connection. */
  private static Connection con;
  
  private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	
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
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			//Class.forName("com.mysql.cj.jdbc.Driver");
			InputStream inputStream = SqlConnector.class.getResourceAsStream("/local.properties");
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
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			//Class.forName("com.mysql.cj.jdbc.Driver");
			InputStream inputStream = SqlConnector.class.getResourceAsStream("/local.properties");
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
	

