package com.excilys.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.excilys.mapper.CompanyRowMapper;
import com.excilys.model.Company;

// 
/**
 * The Class CompanyDAO.
 */
@Repository
public class CompanyDAO {

	private static final String tbName = "company";

	private static final String SELECT_ALL = "SELECT id, name FROM " + tbName;

	private static final String COUNT = "SELECT COUNT(*) FROM " + tbName;

	private static final String DELETE_COMPANY = "DELETE FROM " + tbName + " WHERE id = :id";
	
	private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id= :id ";

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	DataSource ds;
	
	/**
	 * Instantiates a new company DAO.
	 *
	 * @param ds the ds
	 */
	@Autowired
	public CompanyDAO(DataSource ds) {
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(ds);
	}


	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 * @throws SQLException the SQL exception
	 */
	public void deleteCompany(int companyId) throws SQLException {
		SqlParameterSource sp= new MapSqlParameterSource().addValue("id", companyId);
		namedJdbcTemplate.update(DELETE_COMPUTERS,sp);
		namedJdbcTemplate.update(DELETE_COMPANY,sp);
	}

	/**
	 * Count db.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int countDb(String tbName) {
		int count = -1;
		count= namedJdbcTemplate.query(COUNT,((ResultSet rs) -> rs.getInt(1)));
		return count;
	}

	/**
	 * View company.
	 *
	 * @return The list of all companies queried
	 * @throws SQLException           the SQL exception
	 */

	public List<Company> viewCompany() throws SQLException {

		List<Company> companies = new ArrayList<Company>();
		logger.debug("Company List initialized");
		companies = namedJdbcTemplate.query(SELECT_ALL, new CompanyRowMapper());
		return companies;
	}

}
