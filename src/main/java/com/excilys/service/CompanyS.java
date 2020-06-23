package com.excilys.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.sqlShenanigans.SqlConnector;
import com.excilys.ui.Page;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyS.
 */
public class CompanyS {

/** The any DAO. */
private CompanyDAO anyDAO = new CompanyDAO();
	Logger logger = LoggerFactory.getLogger(CompanyS.class);

	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Company> getAllCompanies() throws SQLException, ClassNotFoundException, IOException
	{
		SqlConnector.getInstance();
		return anyDAO.viewCompany();
	
	}
	
	/**
	 * View some companies.
	 *
	 * @param con the con
	 * @param pageNumber the page number
	 * @return the list
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Company> viewSomeCompanies(int pageNumber) throws SQLException, ClassNotFoundException, IOException
	{
		Page page = new Page(pageNumber);
		logger.debug("Page object initialized",page);
		page.setMax(page.countDb("company"));
		page.calcPages(page.getAmount(),page.getMax());
		return anyDAO.viewSomeCompanies(page);
	}
}
