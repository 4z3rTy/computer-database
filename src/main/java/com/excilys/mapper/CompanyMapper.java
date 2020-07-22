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
	 * Pretty company map.
	 *
	 * @param rs the rs
	 * @return the company
	 * @throws SQLException the SQL exception
	 */
	public static Company prettyCompanyMap(ResultSet rs) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(rs.getInt("computer.company_id"))
				.setName(rs.getString("company.name")).build();
		logger.debug("New Company Object initialized", company);

		return company;
	}

	/**
	 * To company.
	 *
	 * @param dto the dto
	 * @return the company
	 */
	public static Company toCompany(CompanyDTO dto) {
		Integer companyId = Integer.parseInt(dto.getcId());
		String name = dto.getName();
		Company company = new Company.CompanyBuilder().setId(companyId).setName(name).build();
		return company;
	}

	/**
	 * To dto.
	 *
	 * @param company the company
	 * @return the company DTO
	 */
	public static CompanyDTO toDto(Company company) {
		CompanyDTO companyDto;
		if (company != null) {
			companyDto = new CompanyDTO.CompanyDTOBuilder().setcId(String.valueOf(company.getId())).setName(company.getName())
					.build();
		} else {
			companyDto = new CompanyDTO.CompanyDTOBuilder().setcId(null).setName(null).build();
		}
		return companyDto;
	}
}
