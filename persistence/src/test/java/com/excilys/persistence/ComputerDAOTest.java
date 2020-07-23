package com.excilys.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import javax.sql.DataSource;

public class ComputerDAOTest {

	@Mock
	ComputerDAO mockDAO;


	@Mock
	Statement stmt;

	@Mock
	Computer computer;

	@Mock
	private Connection c;

	@Mock
	ResultSet mockRS;

	@Mock
	Page mockPage;

	@Mock
	DataSource ds;

	@Before
	public void setup() throws SQLException, ClassNotFoundException, IOException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(ds.getConnection()).thenReturn(c);
		assertNotNull(c);
		Mockito.when(c.createStatement()).thenReturn(stmt);
		Mockito.when(stmt.executeQuery(Matchers.any(String.class))).thenReturn(mockRS);
	}

	@Test
	public void getAllComptTest()  {


	}
}