package com.excilys.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;

/**
 * The Class ComputerValidator.
 */
public class ComputerValidator {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

	/**
	 * Validate.
	 *
	 * @param dto      the dto
	 * @param messages the messages
	 * @return the map
	 */
	public static Map<String, String> validate(ComputerDTO dto, Map<String, String> messages) {

		if ((ComputerValidator.emptyName(dto.getComputerName()))) {
			messages.put("computerName", "Computer name cannot be left empty... :( ");
		}

		if (!(ComputerValidator.emptyDate(dto.getIntroduced()))) {

			if ((ComputerValidator.wrongFormat(dto.getIntroduced()))) {
				messages.put("introduced", "Your input for introduced has the wrong format :(");
			}
		}

		if (!(ComputerValidator.emptyDate(dto.getDiscontinued()))) {

			if ((ComputerValidator.wrongFormat(dto.getDiscontinued())))

			{
				messages.put("discontinued", "Your input for discontinued has the wrong format :(");
			}
		}

		if (!(ComputerValidator.emptyDate(dto.getIntroduced()))
				&& !(ComputerValidator.emptyDate(dto.getDiscontinued()))) {
			if (!ComputerValidator.wrongFormat(dto.getIntroduced())
					&& (!ComputerValidator.wrongFormat(dto.getDiscontinued()))) {
				if (ComputerValidator.wrongDate(dto.getIntroduced(), dto.getDiscontinued())) {
					messages.put("discontinued", "Discontinued date cannot be more recent than introduced date");
				}
			}
		}
		return messages;
	}

	/**
	 * Empty name.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean emptyName(String name) {
		boolean result = false;
		if (name == null || name.equals("")) {
			result = true;
		}
		return result;
	}

	/**
	 * Empty date.
	 *
	 * @param intro the intro
	 * @return true, if successful
	 */
	public static boolean emptyDate(String intro) {
		boolean result = false;
		if (intro==null || intro.equals("")) {
			result = true;
		}
		return result;
	}

	/**
	 * Wrong date.
	 *
	 * @param intro the intro
	 * @param disco the disco
	 * @return true, if successful
	 */
	public static boolean wrongDate(String intro, String disco) {
		boolean result = false;
		if (ComputerMapper.stringToLocal(intro).compareTo(ComputerMapper.stringToLocal(disco)) > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * Empty company.
	 *
	 * @param companyId the company id
	 * @return true, if successful
	 */
	public static boolean emptyCompany(String companyId) {
		boolean result = false;
		if (companyId!=null && companyId.equals("0")) {
			result = true;
		}
		return result;
	}

	/**
	 * Wrong format.
	 *
	 * @param date the intro
	 * @return true, if successful
	 */
	public static boolean wrongFormat(String string) throws DateTimeParseException{
		boolean result = true;
		if(string!=null)
		{
		try {
			LocalDate date=ComputerMapper.stringToLocal(string);
			if(date!=null)
			{
			result = false;
			}
		} catch (DateTimeParseException e) {
			logger.error("Sorry,there was an issue with the format of your date input. Insertion failed");
		}
		}
		return result;
	}
}
