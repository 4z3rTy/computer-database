package com.excilys.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class Mapper.
 */
public class ComputerMapper {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	/**
	 * String to local.
	 *
	 * @param string the d
	 * @return the local date
	 */
	public static LocalDate stringToLocal(String string) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate date = null;
		try {
			date = LocalDate.parse(string, formatter);
			}
		catch(DateTimeParseException e)
		{
			logger.error("Sorry,there was an issue with the format of your discontinued date input. Insertion failed");
		}
		return date;
	}
}
