package com.excilys.mapper;

import static org.junit.Assert.assertEquals;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

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
	public void computerMapTest() throws SQLException
	{
		
		String i="2017-12-12";
		String d="2018-12-12";
		
		String name="RRR";
		java.sql.Date intro = Date.valueOf(i);
		LocalDate intro2=intro.toLocalDate();
		java.sql.Date disco = Date.valueOf(d);
		LocalDate disco2=disco.toLocalDate();
		int id=580;
		
		Computer c2= new Computer.ComputerBuilder().setName(name).setId(id).setIntro(intro2).setDisco(disco2).build();
		
		
		ResultSet mockRS= Mockito.mock(ResultSet.class);
		Mockito.when(mockRS.getInt("id")).thenReturn(580);
		Mockito.when(mockRS.getString("name")).thenReturn("RRR");
		Mockito.when(mockRS.getDate("intro")).thenReturn(intro);
		Mockito.when(mockRS.getDate("disco")).thenReturn(disco);
		Computer c=ComputerMapper.computerMap(mockRS);
		
		assertEquals(c.getId(),c2.getId());
		assertEquals(c.getName(),c2.getName());
		assertEquals(c.getIntroduced(),c2.getIntroduced());
		assertEquals(c.getIntroduced(),c2.getIntroduced());	
	}
	
	@Test
	public void localToStringTest_null()
	{
		assertEquals(ComputerMapper.localToString(null),null);
	}
	
	@Test
	public void localToStringTest_ok()
	{
		LocalDate d=LocalDate.of(2020, 11, 11);
		assertEquals(ComputerMapper.localToString(d),"2020-11-11");
	}
	
	@Test
	public void localToSql_null()
	{
		assertEquals(ComputerMapper.localToSql(null),null);
	}
	
	@Test
	public void localToSql_ok()
	{
		LocalDate d=LocalDate.of(2020, 11, 11);
		java.sql.Date sqld=Date.valueOf(d);
		assertEquals(ComputerMapper.localToSql(d),sqld);
	}
	
	
	
	
	
	
	
}
