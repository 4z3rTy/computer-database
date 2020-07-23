package com.excilys.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class PageTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void calcPages_Ok() {
		Page p = new Page(22);
		p.calcPages();
		assertEquals(p.getTotal(), 3);
	}
	
	@Test
	public void calcPages_Exact() {
		Page p = new Page(20);
		p.calcPages();
		assertEquals(p.getTotal(), 2);
	}
	

	@Test
	public void calcPages_NotOk() {
		Page p = new Page(19);
		p.calcPages();
		assertNotEquals(p.getTotal(),3);
	}
}
