package com.excilys.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;


public class ComputerDtoMapperTest {

	@Test
	public void testToComputerEdit_Ok() {
		assertTrue(true);
	}
	
	@Test
	public void testToComputerEdit_notOk() {
		assertTrue(true);
	}

	@Test
	public void testToComputerAdd_Ok() {
		assertTrue(true);
	}
	
	@Test
	public void testToComputerAdd_notOk() {
		assertTrue(true);
	}

	@Test
	public void testToDto_Ok() {
		assertTrue(true);
	}
	
	@Test
	public void testToDto_notOk() {
		assertTrue(true);
	}

	@Test
	public void testLocalToString_Ok() {
		LocalDate ld = LocalDate.of(2020, 10, 2);
		String test="2020-10-02";
		assertEquals(test,ComputerDtoMapper.localToString(ld));
	}
	
	@Test
	public void testLocalToString_notOk() {
		LocalDate ld = LocalDate.of(2020, 10, 2);
		String test="2020-10-2";
		assertNotEquals(test,ComputerDtoMapper.localToString(ld));
	}
	
	
	
}
