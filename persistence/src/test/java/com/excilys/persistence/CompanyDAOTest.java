package com.excilys.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.excilys.config.PersistenceConfigTest;
import com.excilys.model.Company;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfigTest.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("/sampleData.xml")
@DatabaseTearDown
@DbUnitConfiguration(databaseConnection = { "mysqlDataSource" })
public class CompanyDAOTest {

	@Autowired
	private CompanyDAO test;

	@Test
	public void getCompanyTestOk() {

		assertEquals(test.getCompany(1).getId(), 1);
	}

	@Test
	public void getCompanyTestNotOk() {

		assertNotEquals(test.getCompany(2).getId(), 42);
	}

	@Test
	public void deleteCompanyTestExist() {
		int toDelete = 1;
		int count = test.countDb("company");
		test.deleteCompany(toDelete);
		assertEquals(count - 1, test.countDb("company"));
		assertNull(test.getCompany(toDelete));
	}

	@Test
	public void deleteCompanyTestNotExist() {
		int toDelete = 700;
		int count = test.countDb("company");
		test.deleteCompany(toDelete);
		assertEquals(count, test.countDb("company"));
		assertNull(test.getCompany(toDelete));
	}

	@Test
	public void testCountDbOk() {
		assertEquals(test.countDb("company"), 2);

	}

	@Test
	public void testCountDbNotOk() {
		assertNotEquals(test.countDb("company"), -6);

	}

	@Test
	public void testViewCompanyOk() {
		List<Company> l = test.viewCompany();
		assertEquals(test.viewCompany(), l);
	}

	@Test
	public void testGetCompanyOk() {
		Company l = new Company.CompanyBuilder().setId(2).setName("Two").build();
		assertEquals(test.getCompany(2), l);
	}
}
