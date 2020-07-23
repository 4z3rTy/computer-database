package com.excilys.model;

import static org.junit.Assert.assertEquals;


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
	public void pageCount()  {

		

	}

	@Test
	public void calcPages() {
		assertEquals(p.getTotal(), 58);
	}
}
