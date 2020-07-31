package com.excilys.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfigTest.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/sampleData.xml")
@DatabaseTearDown
@DbUnitConfiguration(databaseConnection= {"mysqlDataSource"})
public class ComputerDAOTest {

	@Autowired
	private ComputerDAO dao;

	@Test
	public void testCountDb() {
		assertEquals(dao.countDb("computer"), 10);
	}

	@Test
	public void testViewComputer() {
		List<Computer> l = dao.viewComputer();
		assertEquals(dao.viewComputer(), l);
	}

	@Test
	public void testViewSomeComputers() {
		Page p = new Page(10);
		p.setPage(1);
		List<Computer> l = dao.viewSomeComputers(p);
		Computer computer = dao.viewCompDetails(1);
		Computer computer2 = dao.viewCompDetails(11);
		assertTrue(l.size() == 10);
		assertTrue(l.get(0).equals(computer));
		assertTrue(l.get(9).equals(computer2));
	}

	@Test
	public void updateComputerNameTest() {
		Computer computer = new Computer.ComputerBuilder().setName("Not yet Updated").build();
		dao.insertComputer(computer);
		dao.updateComputerName("Updated", 11);
		assertEquals(dao.viewCompDetails(11), new Computer.ComputerBuilder().setId(11).setName("Updated").build());
	}

	@Test
	public void updateComputerDiscTest() {
		dao.updateComputerDisc(LocalDate.of(2020, 11, 11),LocalDate.of(2020, 12, 12), 11);
		assertEquals(dao.viewCompDetails(11).getIntroduced(),LocalDate.of(2020, 11, 11));
		assertEquals(dao.viewCompDetails(11).getDiscontinued(),LocalDate.of(2020, 12, 12));
	}

	@Test
	public void updateComputerTest() {
		Computer afterUpdate= new Computer.ComputerBuilder().setId(11).setName("updated").setIntro(LocalDate.of(2020, 11, 11)).setDisco(LocalDate.of(2020, 12, 12)).build();
		dao.updateComputer(afterUpdate);
		assertEquals(dao.viewCompDetails(11).getName(),"updated");
		assertEquals(dao.viewCompDetails(11).getId(),11);
		assertEquals(dao.viewCompDetails(11).getIntroduced(),LocalDate.of(2020, 11, 11));
		assertEquals(dao.viewCompDetails(11).getDiscontinued(),LocalDate.of(2020, 12, 12));
	}

	@Test
	public void testInsertComputer() {
		int count = dao.countDb("computer");
		Computer computer = new Computer.ComputerBuilder().setName("Added Computer").build();
		dao.insertComputer(computer);
		assertTrue(dao.viewComputer().size() == count + 1);
	}

	@Test
	public void testDeleteComputer() {
		dao.deleteComputer(11);
		assertNull(dao.viewCompDetails(11));
	}

	@Test
	public void testViewCompDetails() {
		Computer computer= new Computer.ComputerBuilder().setName("computerEleven").setId(11).build();
		assertEquals(computer,dao.viewCompDetails(11));
	}

	@Test
	public void testGetSearchId() {
		Page page= new Page(10);
		assertEquals(dao.getSearchId("One", page).size(),4);
	}

	@Test
	public void testGetSearchIntro() {
		Page page= new Page(10);
		assertEquals(dao.getSearchIntro("One", page).size(),4);
	}

	@Test
	public void testGetSearchName() {
		Page page= new Page(10);
		assertEquals(dao.getSearchName("One", page).size(),4);
	}

	@Test
	public void testSearchCount() {
		assertEquals(dao.searchCount("One"),4);
	}

}
