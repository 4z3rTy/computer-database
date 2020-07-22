package com.excilys.mapper;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyDtoMapper {

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
