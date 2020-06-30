package com.excilys.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.ui.Page;

// 
/**
 * The Class CompanyS.
 */
public class CompanyS {

	
	
/** The any DAO. */
private CompanyDAO anyDAO = new CompanyDAO();
	Logger logger = LoggerFactory.getLogger(CompanyS.class);

	
	
	public int count(String tbName)  {
		return anyDAO.countDb(tbName);
	}
	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<CompanyDTO> getAllCompanies() throws SQLException, ClassNotFoundException, IOException
	{
		List <Company> temp=anyDAO.viewCompany();
		List <CompanyDTO> res= new ArrayList <CompanyDTO>();
		for(int i=0;i<temp.size();i++)
		{
			CompanyDTO t= new CompanyDTO(temp.get(i));
			res.add(t);
		}
		return res;
	
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
		Page page = new Page(pageNumber,"company");
		logger.debug("Page object initialized",page);
		page.setMax(count("company"));
		page.calcPages();
		return anyDAO.viewSomeCompanies(page);
	}
}
