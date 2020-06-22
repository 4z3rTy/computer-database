package com.excilys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

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
		java.sql.Date disco = Date.valueOf(d);
		
		ResultSet mockRS= Mockito.mock(ResultSet.class);
		Computer c=Mapper.map(mockRS);
		Mockito.when(mockRS.getInt("id")).thenReturn(580);
		//Mockito.when(c.setId(mockRS.getInt("id"))).thenReturn(580);
		Mockito.when(mockRS.getString("name")).thenReturn("RRR");
		Mockito.when(mockRS.getDate("intro")).thenReturn(intro);
		Mockito.when(mockRS.getDate("disco")).thenReturn(disco);
		
		
		assertEquals(c.getId(),580);
		assertEquals(c.getName(),"RRR");
		assertEquals(c.getIntro(),intro);
		assertEquals(c.getIntro(),disco);
		// ??????????????????????????????
		
		
		
	}
	
}
