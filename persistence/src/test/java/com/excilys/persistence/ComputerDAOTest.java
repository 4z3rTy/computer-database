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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfigTest.class, ComputerDAO.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
//@DatabaseSetup("sampleData.xml")
public class ComputerDAOTest {

	@Autowired
	private ComputerDAO dao;

	@Test
	public void testCountDb() {
		assertEquals(dao.countDb("computer"), 525);
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
		Computer computer = dao.viewCompDetails(7);
		Computer computer2 = dao.viewCompDetails(32);
		assertTrue(l.size() == 10);
		assertTrue(l.get(0).equals(computer));
		assertTrue(l.get(9).equals(computer2));
	}

	@Test
	public void updateComputerNameTest() {
		Computer computer = new Computer.ComputerBuilder().setName("Not yet Updated").build();
		dao.insertComputer(computer);
		dao.updateComputerName("Updated", 598);
		assertEquals(dao.viewCompDetails(598), new Computer.ComputerBuilder().setId(598).setName("Updated").build());
	}

	@Test
	public void updateComputerDiscTest() {
		Computer computer = new Computer.ComputerBuilder().setName("testUpdateComputerDisc").setIntro(LocalDate.of(2019, 11, 11)).setDisco(LocalDate.of(2019, 12, 12)).build();
		dao.insertComputer(computer);
		dao.updateComputerDisc(LocalDate.of(2020, 11, 11),LocalDate.of(2020, 12, 12), 579);
		assertEquals(dao.viewCompDetails(579).getIntroduced(),LocalDate.of(2020, 11, 11));
		assertEquals(dao.viewCompDetails(579).getDiscontinued(),LocalDate.of(2020, 12, 12));
	}

	@Test
	public void updateComputerTest() {
		Computer beforeUpdate = new Computer.ComputerBuilder().setName("testUpdateComputer").build();
		dao.insertComputer(beforeUpdate);
		Computer afterUpdate= new Computer.ComputerBuilder().setId(590).setName("updated").setIntro(LocalDate.of(2020, 11, 11)).setDisco(LocalDate.of(2020, 12, 12)).build();
		dao.updateComputer(afterUpdate);
		assertEquals(dao.viewCompDetails(590).getName(),"updated");
		assertEquals(dao.viewCompDetails(590).getId(),590);
		assertEquals(dao.viewCompDetails(590).getIntroduced(),LocalDate.of(2020, 11, 11));
		assertEquals(dao.viewCompDetails(590).getDiscontinued(),LocalDate.of(2020, 12, 12));
	}

	@Test
	public void testInsertComputer() {
		int count = dao.countDb("computer");
		Computer computer = new Computer.ComputerBuilder().setName("Not yet Updated").build();
		dao.insertComputer(computer);
		assertTrue(dao.viewComputer().size() == count + 1);
	}

	@Test
	public void testDeleteComputer() {
		Computer computer = new Computer.ComputerBuilder().setName("testDeleteComputer").build();
		dao.insertComputer(computer);
		dao.deleteComputer(591);
		assertNull(dao.viewCompDetails(591));
	}

	@Test
	public void testViewCompDetails() {
		Computer computer= new Computer.ComputerBuilder().setName("Dell Vostro").setId(572).build();
		assertEquals(computer,dao.viewCompDetails(572));
	}

	@Test
	public void testGetSearchId() {
		Page page= new Page(10);
		assertEquals(dao.getSearchId("Apple", page).size(),7);
	}

	@Test
	public void testGetSearchIntro() {
		Page page= new Page(10);
		assertEquals(dao.getSearchIntro("Apple", page).size(),7);
	}

	@Test
	public void testGetSearchName() {
		Page page= new Page(10);
		assertEquals(dao.getSearchName("Apple", page).size(),7);
	}

	@Test
	public void testSearchCount() {
		assertEquals(dao.searchCount("Apple"),7);
	}

}
