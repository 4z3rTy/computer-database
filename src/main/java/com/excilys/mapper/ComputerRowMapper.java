package com.excilys.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.model.Computer;
import com.excilys.model.Company;

public class ComputerRowMapper implements RowMapper<Computer>{
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerRowMapper.class);
	
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company temp = CompanyMapper.companyMap(rs);
		logger.debug("Temporary Company Object initialized", temp);
		LocalDate intr;
		if ((rs.getDate("introduced")) == null) {
			intr = null;
			logger.info("Introduced date is set as NULL inside the database");
		} else {

			Date sqlDate = (rs.getDate("introduced"));
			intr = sqlDate.toLocalDate();
		}

		LocalDate disc;
		if ((rs.getDate("discontinued")) == null) {
			disc = null;
			logger.info("Discontinued date is set as NULL inside the database");
		} else {
			Date sqlDate2 = (rs.getDate("discontinued"));
			disc = sqlDate2.toLocalDate();
		}

		Computer c = new Computer.ComputerBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).setAny(temp)
				.setIntro(intr).setDisco(disc).build();
		logger.debug("New Computer Object initialized", c);

		return c;	
	}

}