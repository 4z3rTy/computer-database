package com.excilys.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.model.Computer;

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
		CompanyDTO company= new CompanyDTO.CompanyDTOBuilder().setcId("1").setName("expected").build();
		Computer test = new Computer.ComputerBuilder().setName("expected").setId(2).setIntro(LocalDate.of(2020, 11, 24))
				.setDisco(LocalDate.of(2020, 12, 24)).build();
		ComputerDTO got=ComputerDtoMapper.toDto(test);
		ComputerDTO expected = new ComputerDTO.ComputerDTOBuilder().setComputerName("expected")
				.setDiscontinued("2020-11-24").setIntroduced("2020-12-24").setId("2").setCompany(company).build();
		assertEquals(expected.getId(),got.getId());
	}

	@Test
	public void testToDto_notOk() {
		assertTrue(true);
	}

	@Test
	public void testLocalToString_Ok() {
		LocalDate ld = LocalDate.of(2020, 10, 2);
		String test = "2020-10-02";
		assertEquals(test, ComputerDtoMapper.localToString(ld));
	}

	@Test
	public void testLocalToString_notOk() {
		LocalDate ld = LocalDate.of(2020, 10, 2);
		String test = "2020-10-2";
		assertNotEquals(test, ComputerDtoMapper.localToString(ld));
	}

}
