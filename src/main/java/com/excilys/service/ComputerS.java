package com.excilys.service;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.ui.Page;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerS.
 */
public class ComputerS {

/** The comp DAO. */
private ComputerDAO compDAO = new ComputerDAO();
public static Logger logger= LoggerFactory.getLogger(ComputerS.class);
	

	/**
	 * Gets the all computer.
	 *
	 * @return the all computer
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> getAllComputer() throws SQLException, ClassNotFoundException, IOException
	{
		return compDAO.viewComputer();
	
	}
	
	/**
	 * View some computer.
	 *
	 * @param pageNumber the page number
	 * @return the list
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	  public Computer getCompDetails(int computerID) throws SQLException, ClassNotFoundException, IOException {
		  return compDAO.viewCompDetails( computerID);
	  }
	  
	public Computer viewSomeComputer(Connection con, int pageNumber) throws SQLException, ClassNotFoundException, IOException
	{
		Page page = new Page(pageNumber);
		logger.debug("Page object initialized",page);
		page.setMax(page.countDb("company"));
		page.calcPages(page.getAmount(),page.getMax());
		return compDAO.viewSomeComputer( page);
	}
	
	
	public void updateComputerName(String newName, int computerID) throws SQLException, ClassNotFoundException, IOException {
		compDAO.updateComputerName( newName, computerID);
	}
	
	 public int updateComputerDisc(Date intr, Date disc, int computerID)
	            throws SQLException, ClassNotFoundException, IOException {
		 return compDAO.updateComputerDisc( intr, disc, computerID);
	 }
	 
	 
	 public void insertComputer(String computerName, int companyID, Date intro, Date disco)
	    		throws SQLException, ClassNotFoundException, IOException {
		 compDAO.insertComputer( computerName, companyID, intro, disco);
	 }
	
	 public void deleteComputer(int computerID)
	            throws SQLException, ClassNotFoundException, IOException {
		 compDAO.deleteComputer( computerID);
	 }
	
	//TODO Add the rest of the methods from the DAO 
}
