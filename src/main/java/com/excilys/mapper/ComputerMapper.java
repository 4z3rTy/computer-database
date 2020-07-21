package com.excilys.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * The Class Mapper.
 */
public class ComputerMapper {

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

	/**
	 * Computer map.
	 *
	 * @param rs the rs
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	public static Computer computerMap(ResultSet rs) throws SQLException {
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

		Computer computer = new Computer.ComputerBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).setAny(temp)
				.setIntro(intr).setDisco(disc).build();
		logger.debug("New Computer Object initialized", computer);

		return computer;
	}

	/**
	 * Pretty map.
	 *
	 * @param rs the rs
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	public static Computer prettyMap(ResultSet rs) throws SQLException {
		Company temp = CompanyMapper.prettyCompanyMap(rs);
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

		Computer computer = new Computer.ComputerBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).setAny(temp)
				.setIntro(intr).setDisco(disc).build();
		logger.debug("New Computer Object initialized", computer);

		return computer;
	}

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
	 * Local to sql.
	 *
	 * @param d the d
	 * @return the date
	 */
	public static Date localToSql(LocalDate d) {
		Date sqld = null;
		if (d != null) {
			sqld = Date.valueOf(d);
		}

		return sqld;
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

		ComputerDTO d = new ComputerDTO.ComputerDTOBuilder().setComputerName(computer.getName())
				.setDiscontinued(localToString(computer.getDiscontinued()))
				.setIntroduced(localToString(computer.getIntroduced())).setId(String.valueOf(computer.getId()))
				.setCompany(CompanyMapper.toDto(computer.getCompany())).build();
		return d;
	}
}
