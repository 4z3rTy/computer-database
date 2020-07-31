package com.excilys.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.excilys.config.PersistenceConfigTest;
import com.excilys.model.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfigTest.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("/sampleUserData.xml")
@DatabaseTearDown
@DbUnitConfiguration(databaseConnection= {"mysqlDataSource"})
public class UserDAOTest {
	
	@Autowired
	UserDAO dao;

	@Test
	public void testGetUserByUsername() {
		User expected=new User();
		expected.setUsername("myadmin");
		expected.setId(1);
		expected.setPassword("$2y$12$ewzLOSXwaQ8ArzkcHAo0zeyx0V3LLySMW5Y34StjkNzhrn9SPfa02");
		//expected.setDateCreated(new Date(2015,Calendar.NOVEMBER,15));
		User got=dao.getUserByUsername("myadmin");
		assertEquals(got.getId(),expected.getId());
		assertEquals(got.getUsername(),expected.getUsername());
		assertEquals(got.getPassword(),expected.getPassword());
		//assertEquals(got.getDateCreated(),expected.getDateCreated());
		
	}

}
