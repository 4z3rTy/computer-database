package com.excilys.sqlShenanigans;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
  private static String username="admincdb";
  private static String password = "qwerty1234";
  
  public static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	
	/**
	 * Gets the single instance of SqlConnector.

	 * @return single instance of SqlConnector
	 * @throws SQLException 
	 */
	public static Connection getInstance() throws SQLException
	{
		if(con==null||con.isClosed())
		{
			con = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC",
			        username, password);
		}
		return con;
	}
	
	
	
	/**
	 * Instantiates a new sql connector.
	 */
	private SqlConnector(){	
		try {
			con = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC",
			        username, password);
			} catch (SQLException e) {
				logger.error("Connection to the database could not be established",e);
				e.printStackTrace();
			}
	

		}


	
}
	

