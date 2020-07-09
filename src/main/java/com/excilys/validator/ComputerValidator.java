package com.excilys.validator;

import java.time.format.DateTimeParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;

public class ComputerValidator {

	private static final Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

	/*
	 * public static void lol() { if ((ComputerValidator.emptyName(name))) {
	 * messages.put("computerName", "Computer name cannot be left empty... :( "); }
	 * 
	 * if ((ComputerValidator.wrongFormat(intro))) { messages.put("introduced",
	 * "Your input for introduced has the wrong format :("); } else { if
	 * ((ComputerValidator.wrongFormat(disco)))
	 * 
	 * { messages.put("discontinued",
	 * "Your input for discontinued has the wrong format :("); } else if
	 * ((ComputerValidator.wrongDate(intro, disco))) { messages.put("discontinued",
	 * "Discontinued date cannot be more recent than introduced date"); } } if
	 * (messages.isEmpty()) { messages.put("success",
	 * "Update completed successfully!!!!"); } }
	 */
	public static Map<String, String> validate(ComputerDTO dto, Map<String, String> messages) {

		if ((ComputerValidator.emptyName(dto.getName()))) {
			messages.put("computerName", "Computer name cannot be left empty... :( ");
		}

		if (!(ComputerValidator.emptyDate(dto.getIntro()))) {

			if ((ComputerValidator.wrongFormat(dto.getIntro()))) {
				messages.put("introduced", "Your input for introduced has the wrong format :(");
			}
		}

		if (!(ComputerValidator.emptyDate(dto.getDisco()))) {

			if ((ComputerValidator.wrongFormat(dto.getDisco())))

			{
				messages.put("discontinued", "Your input for discontinued has the wrong format :(");
			}
		}

		if (!(ComputerValidator.emptyDate(dto.getIntro())) && !(ComputerValidator.emptyDate(dto.getDisco()))) {
			if (ComputerValidator.wrongFormat(dto.getIntro()) && (ComputerValidator.wrongFormat(dto.getDisco()))) {
				if (ComputerValidator.wrongDate(dto.getIntro(), dto.getDisco())) {
					messages.put("discontinued", "Discontinued date cannot be more recent than introduced date");
				}
			}
		}

		return messages;
	}

	public static boolean emptyName(String name) {
		boolean result = false;
		if (name == null || name == "") {
			result = true;
		}
		return result;
	}

	public static boolean emptyDate(String intro) {
		boolean result = false;
		if (intro.equals(null) || intro.equals("")) {
			result = true;
		}
		return result;
	}

	public static boolean wrongDate(String intro, String disco) {
		boolean result = false;
		if (ComputerMapper.stringToLocal(intro).compareTo(ComputerMapper.stringToLocal(disco)) > 0) {
			result = true;
		}
		return result;
	}

	public static boolean emptyCompany(String companyId) {
		boolean result = false;
		if (companyId.equals("0")) { // ????
			result = true;
		}
		return result;
	}

	public static boolean wrongFormat(String intro) {
		boolean result = false;

		try {
			ComputerMapper.stringToLocal(intro);
		} catch (DateTimeParseException e) {
			result = true;
			logger.error("Sorry,there was an issue with the format of your discontinued date input. Insertion failed");
		}
		return result;
	}
}
