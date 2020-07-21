package com.excilys.persistence;

import java.sql.*;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Computer;
import com.excilys.model.Page;

//
/**
 * The Class ComputerDAO.
 */

@Repository
public class ComputerDAO {

	@Autowired
	private EntityManagerFactory emf;

	private EntityManager em;
	
	private CriteriaBuilder cb;
	
	private static Logger logger=LoggerFactory.getLogger(ComputerDAO.class);

	/*
	 * Count db.
	 *
	 * @param tbName the tb name
	 * 
	 * @return the int
	 */
	public int countDb(String tbName) {
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Computer.class)));
		return em.createQuery(cq).getSingleResult().intValue();
	}

	/**
	 * View computer.
	 *
	 * @return the list
	 */
	public List<Computer> viewComputer() {
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		CriteriaQuery<Computer> all = cq.select(rootEntry);
		TypedQuery<Computer> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	/**
	 * View some computer.
	 *
	 * @param page the page
	 * @return the list
	 * @throws SQLException the SQL exception
	 */
	@Transactional
	public List<Computer> viewSomeComputers(Page page) {

		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();

		em = emf.createEntityManager();
		em.getTransaction().begin();

		cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		CriteriaQuery<Computer> all = cq.select(rootEntry);
		TypedQuery<Computer> allQuery = em.createQuery(all).setFirstResult(offset).setMaxResults(limit);

		em.getTransaction().commit();
		return allQuery.getResultList();

	}

	/**
	 * Update computer name.
	 *
	 * @param newName    the new name
	 * @param computerId the computer ID
	 * @throws SQLException the SQL exception
	 */
	public void updateComputerName(String newName, int computerId) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Computer> update = cb.createCriteriaUpdate(Computer.class);
		Root<Computer> e = update.from(Computer.class);
		update.set("name", newName);
		update.where(cb.equal(e.get("id"), computerId));
		this.em.createQuery(update).executeUpdate();
	}

	/**
	 * Update computer disc.
	 *
	 * @param intr       the intr
	 * @param disc       the disc
	 * @param computerId the computer ID
	 * @return the int
	 * @throws SQLException the SQL exception
	 */
	public boolean updateComputerDisc(Date intr, Date disc, int computerId) {

		boolean res = true;

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Computer> update = cb.createCriteriaUpdate(Computer.class);
		Root<Computer> e = update.from(Computer.class);
		update.set("introduced", intr);
		update.set("discontinued", disc);
		update.where(cb.equal(e.get("id"), computerId));
		this.em.createQuery(update).executeUpdate();

		return res;
	}

	/**
	 * Update computer.
	 *
	 * @param myComp the my comp
	 * @throws SQLException the SQL exception
	 */
	@Transactional
	public void updateComputer(Computer myComp) {

		em.getTransaction().begin();
		em.merge(myComp);
		em.getTransaction().commit();
	}

	/**
	 * Insert computer.
	 *
	 * @param myComp the my comp
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	@Transactional
	public Computer insertComputer(Computer myComp) {

		em.getTransaction().begin();
		em.persist(myComp);
		em.getTransaction().commit();
		return myComp;
	}

	/**
	 * Delete computer.
	 *
	 * @param computerId the computer ID
	 * @throws SQLException the SQL exception
	 */

	@Transactional
	public void deleteComputer(int computerId) {

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> delete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> e = delete.from(Computer.class);
		delete.where(cb.equal(e.get("id"), computerId));
		em.getTransaction().begin();
		int rowsDeleted = em.createQuery(delete).executeUpdate();
		logger.debug("Total of computers deleted: " + rowsDeleted);
		em.getTransaction().commit();

	}

	/**
	 * View comp details.
	 *
	 * @param computerId the computer ID
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	public Computer viewCompDetails(int computerId) {

		em = emf.createEntityManager();
		Computer computer = em.find(Computer.class, computerId);
		return computer;
	}

	/**
	 * Gets the search id.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search id
	 * @throws SQLException the SQL exception
	 */
	public List<Computer> getSearchId(String search, Page page) {

		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();

		em = emf.createEntityManager();
		em.getTransaction().begin();

		cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		rootEntry.join("company", JoinType.LEFT);
		Predicate predicate = cb.like(rootEntry.get("name"), "%" + search + "%");
		Predicate predicate2 = cb.like(rootEntry.get("company").get("name"), "%" + search + "%");
		Predicate predicate3 = cb.or(predicate, predicate2);
		CriteriaQuery<Computer> all = cq.select(rootEntry).where(predicate3).orderBy(cb.asc(rootEntry.get("id")));
		TypedQuery<Computer> allQuery = em.createQuery(all).setFirstResult(offset).setMaxResults(limit);

		em.getTransaction().commit();
		return allQuery.getResultList();

	}

	/**
	 * Gets the search intro.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search intro
	 * @throws SQLException the SQL exception
	 */
	public List<Computer> getSearchIntro(String search, Page page) {

		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();

		em = emf.createEntityManager();
		em.getTransaction().begin();

		cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		rootEntry.join("company", JoinType.LEFT);
		Predicate predicate = cb.like(rootEntry.get("name"), "%" + search + "%");
		Predicate predicate2 = cb.like(rootEntry.get("company").get("name"), "%" + search + "%");
		Predicate predicate3 = cb.or(predicate, predicate2);
		CriteriaQuery<Computer> all = cq.select(rootEntry).where(predicate3)
				.orderBy(cb.asc(rootEntry.get("introduced")));
		TypedQuery<Computer> allQuery = em.createQuery(all).setFirstResult(offset).setMaxResults(limit);

		em.getTransaction().commit();
		return allQuery.getResultList();
	}

	/**
	 * Gets the search name.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search name
	 * @throws SQLException the SQL exception
	 */
	public List<Computer> getSearchName(String search, Page page) {

		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();

		em = emf.createEntityManager();
		em.getTransaction().begin();

		cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		rootEntry.join("company", JoinType.LEFT);
		Predicate predicate = cb.like(rootEntry.get("name"), "%" + search + "%");
		Predicate predicate2 = cb.like(rootEntry.get("company").get("name"), "%" + search + "%");
		Predicate predicate3 = cb.or(predicate, predicate2);
		CriteriaQuery<Computer> all = cq.select(rootEntry).where(predicate3).orderBy(cb.asc(rootEntry.get("name")));
		TypedQuery<Computer> allQuery = em.createQuery(all).setFirstResult(offset).setMaxResults(limit);

		em.getTransaction().commit();
		return allQuery.getResultList();

	}

	/**
	 * Search count.
	 *
	 * @param search the search
	 * @return the int
	 */
	public int searchCount(String search) {

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		rootEntry.join("company", JoinType.LEFT);
		Predicate predicate = cb.like(rootEntry.get("name"), "%" + search + "%");
		Predicate predicate2 = cb.like(rootEntry.get("company").get("name"), "%" + search + "%");
		Predicate predicate3 = cb.or(predicate, predicate2);

		cq.select(cb.count(rootEntry)).where(predicate3);
		return em.createQuery(cq).getSingleResult().intValue();

	}

}