package com.excilys.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * The Class Mapper.
 */
public class ComputerMapper {


	/**
	 * Local to string.
	 *
	 * @param d the d
	 * @return the string
	 */
	public static String localToString(LocalDate d) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		String string = null;
		if (d != null) {
			string = d.format(formatter);
		}
		return string;
	}


	/**
	 * String to local.
	 *
	 * @param d the d
	 * @return the local date
	 */
	public static LocalDate stringToLocal(String d) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate i = null;
		if (d != null) {
			i = LocalDate.parse(d, formatter);
		}
		return i;
	}

	/**
	 * To computer.
	 *
	 * @param dto the dto
	 * @return the computer
	 */
	public static Computer toComputer(ComputerDTO dto) {

		Integer cId = Integer.parseInt(dto.getCompanyId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate i, d;

		if (dto.getIntroduced() != null && dto.getIntroduced().equals("") == false) {
			i = LocalDate.parse(dto.getIntroduced(), formatter);
		} else {
			i = null;
		}
		if (dto.getDiscontinued() != null && dto.getDiscontinued().equals("") == false) {
			d = LocalDate.parse(dto.getDiscontinued(), formatter);
		} else {
			d = null;
		}

		Company company = new Company.CompanyBuilder().setId(cId).build();
		Computer computer = new Computer.ComputerBuilder().setDisco(d).setIntro(i).setName(dto.getComputerName())
				.setId(Integer.parseInt(dto.getId())).setAny(company).build();
		return computer;
	}

	/**
	 * To computer bis.
	 *
	 * @param dto the dto
	 * @return the computer
	 */
	public static Computer toComputerBis(ComputerDTO dto) {


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate i, d;

		if (dto.getIntroduced() != null && dto.getIntroduced().equals("") == false) {
			i = LocalDate.parse(dto.getIntroduced(), formatter);
		} else {
			i = null;
		}
		if (dto.getDiscontinued() != null && dto.getDiscontinued().equals("") == false) {
			d = LocalDate.parse(dto.getDiscontinued(), formatter);
		} else {
			d = null;
		}

		Computer computer=null;
		if (dto.getCompany()!=null) {
			Integer cId = Integer.parseInt(dto.getCompanyId());
			Company company = new Company.CompanyBuilder().setId(cId).build();
			computer = new Computer.ComputerBuilder().setDisco(d).setIntro(i).setName(dto.getComputerName())
					.setAny(company).build();
		} 
		else {
			computer = new Computer.ComputerBuilder().setDisco(d).setIntro(i).setName(dto.getComputerName()).build();
		}
		// setId not used because when AddingComputers computerId is autoincremented by
		// the sql side
		return computer;
	}

	/**
	 * To dto.
	 *
	 * @param computer the computer
	 * @return the computer DTO
	 */
	public static ComputerDTO toDto(Computer computer) {

		ComputerDTO computerDto = new ComputerDTO.ComputerDTOBuilder().setComputerName(computer.getName())
				.setDiscontinued(localToString(computer.getDiscontinued()))
				.setIntroduced(localToString(computer.getIntroduced())).setId(String.valueOf(computer.getId()))
				.setCompany(CompanyMapper.toDto(computer.getCompany())).build();
		return computerDto;
	}
}
