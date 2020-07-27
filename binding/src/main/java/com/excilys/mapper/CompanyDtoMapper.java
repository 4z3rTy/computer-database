package com.excilys.mapper;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyDtoMapper {

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
