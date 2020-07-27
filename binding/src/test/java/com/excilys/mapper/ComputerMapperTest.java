package com.excilys.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class ComputerMapperTest {

	@Test
	public void stringToLocalTest_Ok() {
		String s = "2011-11-11";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate test=LocalDate.parse(s,formatter);
		assertEquals(test,ComputerMapper.stringToLocal(s));
	}

	@Test
	public void stringToLocal_notOk() {
		String s = "2011-11-11";
		String t= "2011-11-12";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate test=LocalDate.parse(t,formatter);
		assertNotEquals(test,ComputerMapper.stringToLocal(s));
	}
	
	@Test
	public void stringToLocal_wrongFormat() {
		String s = "2011-11-11";
		String t= "20111111";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate test=LocalDate.parse(s,formatter);
		assertNotEquals(test,ComputerMapper.stringToLocal(t));
	}

	@Test
	public void stringToLocal_empty() {
		String s = "";
		assertNull(ComputerMapper.stringToLocal(s));
	}

}
