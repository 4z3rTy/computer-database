package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapper {

	private static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);
	
	
	/**
	 * Company map.
	 *
	 * @param rs the rs
	 * @return the company
	 * @throws SQLException the SQL exception
	 */
	public static Company companyMap(ResultSet rs) throws SQLException
	{
		Company c=new Company.CompanyBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).build();
		logger.debug("New Company Object initialized",c);
        
		return c;
	}
	
	/**
	 * Pretty company map.
	 *
	 * @param rs the rs
	 * @return the company
	 * @throws SQLException the SQL exception
	 */
	public static Company prettyCompanyMap(ResultSet rs) throws SQLException
	{
		Company c=new Company.CompanyBuilder().setId(rs.getInt("computer.company_id")).setName(rs.getString("company.name")).build();
		logger.debug("New Company Object initialized",c);
        
		return c;
	}
	
	public static Company toCompany(CompanyDTO dto)
	{
		int c_id=Integer.parseInt(dto.getId());				
		String name=dto.getName();
		Company c=new Company.CompanyBuilder().setId(c_id).setName(name).build();
		return c;
	}
	
	public static CompanyDTO toDto(Company company)
	{
		CompanyDTO d=new CompanyDTO(company);
		return d;
	}
}
