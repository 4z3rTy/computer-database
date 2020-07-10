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
/*	
	@Test
	public void computerMapTest() throws SQLException
	{
		
		String i="2017-12-12";
		String d="2018-12-12";
		
		String name="RRR";
		LocalDate intro2=LocalDate.of(2017,12,12);
		LocalDate disco2=LocalDate.of(2018,12,12);
		int id=580;
		
		Computer c2= new Computer.ComputerBuilder().setName(name).setId(id).setIntro(intro2).setDisco(disco2).build();
		
		
		ResultSet mockRS= Mockito.mock(ResultSet.class);
		Mockito.when(mockRS.getInt("id")).thenReturn(580);
		Mockito.when(mockRS.getString("name")).thenReturn("RRR");
		Mockito.when(mockRS.getDate("intro")).thenReturn(ComputerMapper.localToSql(ComputerMapper.stringToLocal(i)));
		Mockito.when(mockRS.getDate("disco")).thenReturn(ComputerMapper.localToSql(ComputerMapper.stringToLocal(d)));
		Computer c=ComputerMapper.computerMap(mockRS);
		
		assertEquals(c.getId(),c2.getId());
		assertEquals(c.getName(),c2.getName());
		assertEquals(c.getIntroduced(),c2.getIntroduced());
		assertEquals(c.getDiscontinued(),c2.getDiscontinued());	
	}
*/	
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
	
	@Test
	public void localToSql_not_ok()
	{
		LocalDate d=LocalDate.of(2020, 11, 11);
		java.sql.Date sqld=new java.sql.Date(Calendar.getInstance().getTime().getTime());
		assertNotSame(ComputerMapper.localToSql(d),sqld);
	}
	
	@Test
	public void stringToLocal_null()
	{
		assertNull(ComputerMapper.stringToLocal(null));
	}
	
	@Test
	public void stringToLocal_ok()
	{
		String s="2012-11-11";
		LocalDate date=LocalDate.of(2012, 11, 11);
		assertEquals(ComputerMapper.stringToLocal(s),date);
	}
	
	@Test
	public void stringToLocal_not_ok()
	{
		String s="2020-11-11";
		LocalDate date=LocalDate.of(2012, 11, 11);
		assertNotSame(ComputerMapper.stringToLocal(s),date);
	}
	
	
	
	
	
	
	
}
