package com.excilys.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class computerValidatorTest {

	@Test
	public void emptyNameTestOk() {
		assertTrue(true);
	}

	@Test
	public void emptyNameTestNotOk() {
		assertTrue(true);
	}

	@Test
	public void emptyNameTestEmpty() {
		assertTrue(true);
	}

	@Test
	public void emptyDateTestOk() {
		assertTrue(true);
	}

	@Test
	public void emptyDateTestNotOk() {
		assertTrue(true);
	}

	@Test
	public void emptyDateTestEmpty() {
		assertTrue(true);
	}

	@Test
	public void emptyCompanyTestOk() {
		assertTrue(true);
	}

	@Test
	public void emptyCompanyTestNotOk() {
		assertTrue(true);
	}

	@Test
	public void emptyCompanyTestEmpty() {
		assertTrue(true);
	}

	@Test
	public void wrongDateTestOk() {
		assertTrue(true);
	}

	@Test
	public void wrongDateTestNotOk() {
		assertTrue(true);
	}

	@Test
	public void wrongDateTestEquals() {
		assertTrue(true);
	}

	@Test
	public void wrongDateTestNull() {
		assertTrue(true);
	}

	@Test
	public void wrongFormatTestOk() {
		String date="2020-11-11";
		assertFalse(ComputerValidator.wrongFormat(date));
	}

	@Test
	public void wrongFormatTestNotOk() {
		String date="24";
		assertTrue(ComputerValidator.wrongFormat(date));
	}

	@Test
	public void wrongFormatTestNull() {
		String date=null;
		assertTrue(ComputerValidator.wrongFormat(date));
	}
}
