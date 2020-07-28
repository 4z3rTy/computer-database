package com.excilys.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerDtoMapper {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ComputerDtoMapper.class);

	/**
	 * To computer.
	 *
	 * @param dto the dto
	 * @return the computer
	 */
	public static Computer toComputerEdit(ComputerDTO dto) {

		Integer companyId = Integer.parseInt(dto.getCompanyId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate intro, disco;

		if (dto.getIntroduced() != null && dto.getIntroduced().equals("") == false) {
			intro = LocalDate.parse(dto.getIntroduced(), formatter);
		} else {
			intro = null;
		}
		if (dto.getDiscontinued() != null && dto.getDiscontinued().equals("") == false) {
			disco = LocalDate.parse(dto.getDiscontinued(), formatter);
		} else {
			disco = null;
		}
		Company company = new Company.CompanyBuilder().setId(companyId).build();
		Computer computer = new Computer.ComputerBuilder().setDisco(disco).setIntro(intro).setName(dto.getComputerName())
				.setId(Integer.parseInt(dto.getId())).setAny(company).build();

		return computer;
	}

	/**
	 * To computer bis.
	 *
	 * @param dto the dto
	 * @return the computer
	 */
	public static Computer toComputerAdd(ComputerDTO dto) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate intro, disco;

		if (dto.getIntroduced() != null && dto.getIntroduced().equals("") == false) {
			intro = LocalDate.parse(dto.getIntroduced(), formatter);
		} else {
			intro = null;
		}
		if (dto.getDiscontinued() != null && dto.getDiscontinued().equals("") == false) {
			disco = LocalDate.parse(dto.getDiscontinued(), formatter);
		} else {
			disco = null;
		}

		Computer computer = null;
		if (dto.getCompany() != null && !dto.getCompanyId().equals("0")) {
			Integer cId = Integer.parseInt(dto.getCompanyId());
			Company company = new Company.CompanyBuilder().setId(cId).build();
			computer = new Computer.ComputerBuilder().setDisco(disco).setIntro(intro).setName(dto.getComputerName())
					.setAny(company).build();
		} else {
			computer = new Computer.ComputerBuilder().setDisco(disco).setIntro(intro).setName(dto.getComputerName()).build();
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
				.setCompany(CompanyDtoMapper.toDto(computer.getCompany())).build();
		return computerDto;
	}

	/**
	 * Local to string.
	 *
	 * @param date the d
	 * @return the string
	 */
	public static String localToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		String string = null;
		if (date != null) {
			try {
				string = date.format(formatter);
			} catch (DateTimeParseException e) {
				logger.debug("Error formating your LocalDate");
			}
		}
		return string;
	}

}
