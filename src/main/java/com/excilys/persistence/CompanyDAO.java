package com.excilys.persistence;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.sqlShenanigans.DataSource;
import com.excilys.sqlShenanigans.Xeptions;
import com.excilys.ui.Page;

// 
/**
 * The Class CompanyDAO.
 */
public class CompanyDAO{
	
	/** The table name. */
	static String tbName="company";
	
	/** The Constant SELECT_ALL. */
	private static final String SELECT_ALL="select id, name from "+ CompanyDAO.tbName;
	
	/** The Constant SELECT_SOME. */
	private static final String SELECT_SOME="SELECT * FROM "+CompanyDAO.tbName+ " ORDER BY id LIMIT ? OFFSET ?";
	
	/** The Constant COUNT. */
	private static final String COUNT="SELECT COUNT(*) from " + tbName;

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	
	/**
	 * Count db.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int countDb(String tbName) {
		Statement stmt = null;
		int count = -1;
		try (Connection con = DataSource.getConnection())
		{
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
			
		return count;
	}
	
	
/**
 * View company.
 *
 * @return The list of all companies queried
 * @throws SQLException the SQL exception
 * @throws ClassNotFoundException the class not found exception
 * @throws IOException Signals that an I/O exception has occurred.
 */


public List<Company> viewCompany() throws SQLException, ClassNotFoundException, IOException {
	   

		    Statement stmt = null;
		    Company company=null;
		    List<Company> companies = new ArrayList<Company>();
	
		    try(Connection con = DataSource.getConnection()) {
		       stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(SELECT_ALL);
		        while (rs.next()) {
		        	company=CompanyMapper.companyMap(rs);
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
 * @param page the page number the user wishes to display
 * @return the list of all the companies
 * @throws SQLException the SQL exception
 * @throws ClassNotFoundException the class not found exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
public List<Company> viewSomeCompanies(Page page) throws SQLException, ClassNotFoundException, IOException {
	
	 PreparedStatement pstmt = null;   
   
   Company company=null;
   List<Company> companies = new ArrayList<Company>();

   try(Connection con = DataSource.getConnection()){
	
       pstmt = con.prepareStatement(SELECT_SOME);
       
       int limit=page.getAmount();
       int offset=(page.getPage()-1)*page.getAmount();
       pstmt.setInt(1, limit);
       pstmt.setInt(2, offset);
       
       
       ResultSet rs = pstmt.executeQuery();
       company=new Company.CompanyBuilder().build();
       while (rs.next()) {
    	    company=CompanyMapper.companyMap(rs);
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


