package com.excilys.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class computerValidatorTest {

	@Test
	public void emptyNameTestOk() {
		String name = "";
		assertTrue(ComputerValidator.emptyName(name));
	}

	@Test
	public void emptyNameTestNotOk() {
		String name = "notEmpty";
		assertFalse(ComputerValidator.emptyName(name));
	}

	@Test
	public void emptyNameTestNull() {
		String name = null;
		assertTrue(ComputerValidator.emptyName(name));
	}

	@Test
	public void emptyDateTestOk() {
		String date = "";
		assertTrue(ComputerValidator.emptyDate(date));
	}

	@Test
	public void emptyDateTestNotOk() {
		String date = "notEmpty";
		assertFalse(ComputerValidator.emptyDate(date));
	}

	@Test
	public void emptyDateTestEmpty() {
		String date = null;
		assertTrue(ComputerValidator.emptyDate(date));
	}

	@Test
	public void emptyCompanyTestOk() {
		String companyId = "0";
		assertTrue(ComputerValidator.emptyCompany(companyId));
	}

	@Test
	public void emptyCompanyTestNotOk() {
		String companyId = "notZero";
		assertFalse(ComputerValidator.emptyCompany(companyId));
	}

	@Test
	public void emptyCompanyTestEmpty() {
		String companyId = "";
		assertFalse(ComputerValidator.emptyCompany(companyId));
	}

	@Test
	public void emptyCompanyTestNull() {
		String companyId = null;
		assertFalse(ComputerValidator.emptyCompany(companyId));
	}

	@Test
	public void wrongDateTestOk() {
		String intro = "2020-11-12";
		String disco = "2019-12-23";
		assertTrue(ComputerValidator.wrongDate(intro, disco));
	}

	@Test
	public void wrongDateTestNotOk() {
		String intro = "2019-11-20";
		String disco = "2020-10-23";
		assertFalse(ComputerValidator.wrongDate(intro, disco));
	}

	@Test
	public void wrongDateTestEquals() {
		String intro = "2019-11-20";
		String disco = "2019-11-20";
		assertFalse(ComputerValidator.wrongDate(intro, disco));
	}

	@Test
	public void wrongFormatTestOk() {
		String date = "2020-11-11";
		assertFalse(ComputerValidator.wrongFormat(date));
	}

	@Test
	public void wrongFormatTestNotOk() {
		String date = "24";
		assertTrue(ComputerValidator.wrongFormat(date));
	}

	@Test
	public void wrongFormatTestNull() {
		String date = null;
		assertTrue(ComputerValidator.wrongFormat(date));
	}

	@Test
	public void validateTestOk() {
		assertTrue(true);
	}

	@Test
	public void validateTestNotOk() {
		assertTrue(true);
	}

}
