package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

/**
 * The Class CompanyMapper.
 */
public class CompanyMapper {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	/**
	 * Company map.
	 *
	 * @param rs the rs
	 * @return the company
	 * @throws SQLException the SQL exception
	 */
	public static Company companyMap(ResultSet rs) throws SQLException {
		Company c = new Company.CompanyBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).build();
		logger.debug("New Company Object initialized", c);

		return c;
	}

	/**
	 * Pretty company map.
	 *
	 * @param rs the rs
	 * @return the company
	 * @throws SQLException the SQL exception
	 */
	public static Company prettyCompanyMap(ResultSet rs) throws SQLException {
		Company c = new Company.CompanyBuilder().setId(rs.getInt("computer.company_id"))
				.setName(rs.getString("company.name")).build();
		logger.debug("New Company Object initialized", c);

		return c;
	}

	/**
	 * To company.
	 *
	 * @param dto the dto
	 * @return the company
	 */
	public static Company toCompany(CompanyDTO dto) {
		Integer c_id = Integer.parseInt(dto.getcId());
		String name = dto.getName();
		Company c = new Company.CompanyBuilder().setId(c_id).setName(name).build();
		return c;
	}

	/**
	 * To dto.
	 *
	 * @param company the company
	 * @return the company DTO
	 */
	public static CompanyDTO toDto(Company company) {
		CompanyDTO d;
		if (company != null) {
			d = new CompanyDTO.CompanyDTOBuilder().setcId(String.valueOf(company.getId())).setName(company.getName())
					.build();
		} else {
			d = new CompanyDTO.CompanyDTOBuilder().setcId(null).setName(null).build();
		}
		return d;
	}
}
