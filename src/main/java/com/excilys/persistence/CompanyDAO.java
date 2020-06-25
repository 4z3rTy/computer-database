package com.excilys.persistence;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.sqlShenanigans.SqlConnector;
import com.excilys.sqlShenanigans.Xeptions;
import com.excilys.ui.Page;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyDAO.
 */
public class CompanyDAO{
	
	/** The table name. */
	static String tbName="company";
	private static final String SELECT_ALL="select id, name from "+ CompanyDAO.tbName;
	private static final String SELECT_SOME="SELECT * FROM "+CompanyDAO.tbName+ " ORDER BY id LIMIT ? OFFSET ?";
	private static final String COUNT="SELECT COUNT(*) from " + tbName;

	public static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	
	public int countDb(String tbName) {
		Statement stmt = null;
		int count = -1;
		try {
			Connection con = SqlConnector.getInstance();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(COUNT);
			rs.next();
			count = rs.getInt(1);
			stmt.close();
			logger.debug("Connection to the database was terminated");
			} 
			catch (SQLException e) {
				logger.error("Connection to the database could not be established", e);
				Xeptions.printSQLException(e);
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return count;
	}
	
	
/**
 * View company.
 *
 * @param con the SqlConnection
 * @return The list of all companies queried
 * @throws SQLException 
 * @throws IOException 
 * @throws ClassNotFoundException 
 */


public List<Company> viewCompany() throws SQLException, ClassNotFoundException, IOException {
	   

		    Statement stmt = null;
		    Company company=null;
		    List<Company> companies = new ArrayList<Company>();
	
		    try(Connection con=SqlConnector.getInstance();) {
		       stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(SELECT_ALL);
		        while (rs.next()) {
		        	company=Mapper.map1(rs);
		        	companies.add(company);
		        }
		    } catch (SQLException e ) {
		    	logger.error("Connection to the database could not be established",e);
		        Xeptions.printSQLException(e);
		    } finally {
		        if (stmt != null) { stmt.close(); logger.debug("Connection to the database was terminated"); }
		    }
		    
		    return companies;
}

/**
 * View some companies.
 *
 * @param con the SqlConnection
 * @param page the page number the user wishes to display
 * @return the list of all the companies
 * @throws SQLException
 * @throws IOException 
 * @throws ClassNotFoundException 
 */
public List<Company> viewSomeCompanies(Page page) throws SQLException, ClassNotFoundException, IOException {
	
	 PreparedStatement pstmt = null;   
   
   Company company=null;
   List<Company> companies = new ArrayList<Company>();

   try(Connection con=SqlConnector.getInstance()){
	
       pstmt = con.prepareStatement(SELECT_SOME);
       
       int limit=page.getAmount();
       int offset=(page.getPage()-1)*page.getAmount();
       pstmt.setInt(1, limit);
       pstmt.setInt(2, offset);
       
       
       ResultSet rs = pstmt.executeQuery();
       company=new Company();
       while (rs.next()) {
    	    company=Mapper.map1(rs);
       		companies.add(company);
       				}
   } catch (SQLException e ) {
	   logger.error("Connection to the database could not be established",e);
       Xeptions.printSQLException(e);
   } finally {
       if (pstmt != null) { pstmt.close(); logger.debug("Connection to the database was terminated"); }
   }
   return companies;
}
}


