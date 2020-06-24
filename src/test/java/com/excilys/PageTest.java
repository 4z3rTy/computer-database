package com.excilys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.dataset.DataSetException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.service.CompanyS;
import com.excilys.ui.Page;


public class PageTest {
	Page p = new Page(22);
	CompanyS c= new CompanyS();

	// p.calcPages();
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void pageCount() throws SQLException, DataSetException, ClassNotFoundException, IOException {

		
		assertEquals(c.count("computer"), 573);
		// assertEquals(mockPage.countDb("computer"), 573);

	}

/*	@Test
	public void calcPages() {
		assertEquals(p.getTotal(), 58);
	}
	*/
}
