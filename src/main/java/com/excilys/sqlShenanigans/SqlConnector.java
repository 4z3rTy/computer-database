package com.excilys.sqlShenanigans;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: 
/**
 * The Class SqlConnector.
 */
public class SqlConnector {  // Lazy Initialization Singleton

	/** The connection. */
  private static Connection con;
  private static String username="admincdb";
  private static String password = "qwerty1234";

	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

		}


	
}
	

