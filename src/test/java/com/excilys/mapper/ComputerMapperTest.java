package com.excilys.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotSame;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.model.Computer;

public class ComputerMapperTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	ComputerMapper mockMapper;

	@Test
	public void computerMapTest() throws SQLException {

	}
}
