package com.excilys.service;

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
	
	/**
	 * Sets the company dao.
	 *
	 * @param companyDao the new company dao
	 */
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
	 */
	public List<CompanyDTO> getAllCompanies() {
		List<Company> temp = companyDao.viewCompany();
		List<CompanyDTO> res = temp.stream().map(company -> CompanyMapper.toDto(company)).collect(Collectors.toList());

		return res;

	}

	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 */
	public void deleteCompany(int companyId) {
		companyDao.deleteCompany(companyId);
	}
}
