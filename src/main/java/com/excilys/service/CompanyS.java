package com.excilys.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.ui.Page;

/**
 * The Class CompanyS.
 */
@Service
public class CompanyS {

	/** The any DAO. */
	@Autowired
	private CompanyDAO anyDao;
	
	@Autowired
	public void setComputerDao(CompanyDAO anyDao)
	{
		this.anyDao=anyDao;
	}
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CompanyS.class);

	/**
	 * Count.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int count(String tbName) {
		return anyDao.countDb(tbName);
	}

	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<CompanyDTO> getAllCompanies() throws SQLException, ClassNotFoundException, IOException {
		List<Company> temp = anyDao.viewCompany();
		List<CompanyDTO> res = temp.stream().map(company -> CompanyMapper.toDto(company)).collect(Collectors.toList());

		return res;

	}

	/**
	 * View some companies.
	 *
	 * @param pageNumber the page number
	 * @return the list
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<CompanyDTO> viewSomeCompanies(int pageNumber) throws SQLException, ClassNotFoundException, IOException {
		Page page = new Page(pageNumber, "company");
		logger.debug("Page object initialized", page);
		page.setMax(count("company"));
		page.calcPages();
		List<Company> temp = anyDao.viewSomeCompanies(page);
		List<CompanyDTO> res = temp.stream().map(company -> CompanyMapper.toDto(company)).collect(Collectors.toList());
		return res;
	}

	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 * @throws SQLException the SQL exception
	 */
	public void deleteCompany(int companyId) throws SQLException {
		anyDao.deleteCompany(companyId);
	}
}
