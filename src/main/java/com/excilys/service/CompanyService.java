package com.excilys.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;

/**
 * The Class CompanyS.
 */
@Service
public class CompanyService {

	/** The company DAO. */
	private CompanyDAO companyDao;
	
	@Autowired
	public void setCompanyDao(CompanyDAO companyDao)
	{
		this.companyDao=companyDao;
	}

	/**
	 * Count.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int count(String tbName) {
		return companyDao.countDb(tbName);
	}

	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<CompanyDTO> getAllCompanies() throws SQLException {
		List<Company> temp = companyDao.viewCompany();
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
		companyDao.deleteCompany(companyId);
	}
}
