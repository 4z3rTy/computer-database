package com.excilys.validator;

import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.mapper.ComputerMapper;

public class ComputerValidator {

	private static final Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

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
		if (companyId.equals(0)) {
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
