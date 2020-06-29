package com.excilys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.model.Computer;
import com.excilys.mapper.Mapper;

public class MapperTest {

	@Before
	public void setup() {
	    MockitoAnnotations.initMocks(this);
	}
	

	@Mock
	Mapper mockMapper;
	@Test
	public void computerMapTest() throws SQLException
	{
		
		String i="2017-12-12";
		String d="2018-12-12";
		java.sql.Date intro = Date.valueOf(i);
		LocalDate intro2=intro.toLocalDate();
		java.sql.Date disco = Date.valueOf(d);
		LocalDate disco2=disco.toLocalDate();
		Computer c2= new Computer(580,"RRR",intro2,disco2);
		ResultSet mockRS= Mockito.mock(ResultSet.class);
		//Mockito.when(mockRS.getInt("id")).thenReturn(580);
		//Mockito.when(mockRS.getString("name")).thenReturn("RRR");
		//Mockito.when(mockRS.getDate("intro")).thenReturn(intro);
		//Mockito.when(mockRS.getDate("disco")).thenReturn(disco);
		Mockito.when(mockRS.getInt("id")).thenReturn(0);
		Mockito.when(mockRS.getString("name")).thenReturn(null);
		Mockito.when(mockRS.getDate("intro")).thenReturn(null);
		Mockito.when(mockRS.getDate("disco")).thenReturn(null);
		Computer c=Mapper.computerMap(mockRS);
		
		assertEquals(c.getId(),c2.getId());
		assertEquals(c.getName(),c2.getName());
		assertEquals(c.getIntro(),c2.getIntro());
		assertEquals(c.getIntro(),c2.getIntro());
		
		
		
	}
	
}
