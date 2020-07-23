package com.excilys.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.dataset.DataSetException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;



public class PageTest {
	Page p = new Page(22);

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void pageCount() throws SQLException, DataSetException, ClassNotFoundException, IOException {

		
		//assertEquals(mockPage.countDb("computer"), 573);

	}

	@Test
	public void calcPages() {
		assertEquals(p.getTotal(), 58);
	}
}
