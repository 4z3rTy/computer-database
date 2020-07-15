package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.model.*;

public class CompanyRowMapperBis implements RowMapper<Company> {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CompanyRowMapperBis.class);

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(rs.getInt("computer.company_id"))
				.setName(rs.getString("company.name")).build();
		logger.debug("New Company Object initialized", company);
		return company;

	}

}
