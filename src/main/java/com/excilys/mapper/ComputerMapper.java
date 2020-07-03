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

		Computer c = new Computer.ComputerBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).setAny(temp)
				.setIntro(intr).setDisco(disc).build();
		logger.debug("New Computer Object initialized", c);

		return c;
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

		Computer c = new Computer.ComputerBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).setAny(temp)
				.setIntro(intr).setDisco(disc).build();
		logger.debug("New Computer Object initialized", c);

		return c;
	}

	/**
	 * Local to string.
	 *
	 * @param d the d
	 * @return the string
	 */
	public static String localToString(LocalDate d) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		String s = null;
		if (d != null) {
			s = d.format(formatter);
		}
		return s;
	}

	/**
	 * Local to sql.
	 *
	 * @param d the d
	 * @return the date
	 */
	public static Date localToSql(LocalDate d) {
		Date sqld = Date.valueOf(d);
		
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
		LocalDate i = LocalDate.parse(d, formatter);

		return i;
	}

	/**
	 * To computer.
	 *
	 * @param dto the dto
	 * @return the computer
	 */
	public static Computer toComputer(ComputerDTO dto) {
		int c_id = Integer.parseInt(dto.getCompany_id());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate i = LocalDate.parse(dto.getIntro(), formatter);
		LocalDate d = LocalDate.parse(dto.getDisco(), formatter);

		Company company = new Company.CompanyBuilder().setId(c_id).build();
		Computer c = new Computer.ComputerBuilder().setDisco(d).setIntro(i).setName(dto.getName())
				/* .setId(Integer.parseInt(dto.getId())) */.setAny(company).build();
		return c;
	}

	/**
	 * To dto.
	 *
	 * @param computer the computer
	 * @return the computer DTO
	 */
	public static ComputerDTO toDto(Computer computer) {

		ComputerDTO d = new ComputerDTO.ComputerDTOBuilder().setName(computer.getName())
				.setDisco(localToString(computer.getDiscontinued())).setIntro(localToString(computer.getIntroduced()))
				.setId(String.valueOf(computer.getId())).setAnyId(computer.getCompanyId())
				.setAnyName(computer.getCompanyName()).build();
		return d;
	}
}
