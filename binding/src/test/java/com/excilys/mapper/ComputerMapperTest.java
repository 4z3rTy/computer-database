package com.excilys.mapper;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ComputerMapperTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	ComputerMapper mockMapper;

	@Test
	public void computerMapTest() throws SQLException {

		assertTrue(true);
	}
}
