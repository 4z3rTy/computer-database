package com.excilys.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class computerValidatorTest {

	
	@Test
	public void emptyNameTestOk() {
		assertTrue(true);
}
	@Test
	public void emptyNameTestNull() {
}
	@Test
	public void emptyNameTestEmpty() {
}
	
	@Test
	public void emptyDateTestOk() {
}
	@Test
	public void emptyDateTestNull() {
}
	@Test
	public void emptyDateTestEmpty() {
}
	
	
	@Test
	public void emptyCompanyTestOk() {
}
	@Test
	public void emptyCompanyTestNull() {
}
	@Test
	public void emptyCompanyTestEmpty() {
}
	@Test
	public void wrongDateTestOk() {
}	
	@Test
	public void wrongDateTestNotOk() {
}
	@Test
	public void wrongDateTestEquals() {
}
	@Test
	public void wrongDateTestNull() {
}
	
	@Test
	public void wrongFormatTestOk() {
}	
	@Test
	public void wrongFormatTestNotOk() {
}
	@Test
	public void wrongFormatTestNull() {
}
}