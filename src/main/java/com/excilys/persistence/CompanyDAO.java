package com.excilys.persistence;

import java.sql.*;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Company;
import com.excilys.model.Computer;

// 
/**
 * The Class CompanyDAO.
 */
@Repository
public class CompanyDAO {


	private CriteriaBuilder cb;

	@Autowired
	private EntityManagerFactory emf;

	private EntityManager em;

	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 * @throws SQLException the SQL exception
	 */
	@Transactional
	public void deleteCompany(int companyId) throws SQLException {

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> root = criteriaDelete.from(Computer.class);
		criteriaDelete.where(cb.equal(root.get("company").get("id"), companyId));
		
		em.getTransaction().begin();
		int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
		System.out.println("entities deleted: " + rowsDeleted);

		CriteriaDelete<Company> criteriaDelete2 = cb.createCriteriaDelete(Company.class);
		Root<Company> root2 = criteriaDelete2.from(Company.class);
		criteriaDelete2.where(cb.equal(root2.get("id"), companyId));
		int rowsDeleted2 = em.createQuery(criteriaDelete2).executeUpdate();
		System.out.println("entities deleted: " + rowsDeleted2);

		em.getTransaction().commit();
	}

	/**
	 * Count db.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int countDb(String tbName) {

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Company.class)));
		return em.createQuery(cq).getSingleResult().intValue();

	}

	/**
	 * View company.
	 *
	 * @return The list of all companies queried
	 * @throws SQLException the SQL exception
	 */

	public List<Company> viewCompany() throws SQLException {

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> cq = cb.createQuery(Company.class);
		Root<Company> rootEntry = cq.from(Company.class);
		CriteriaQuery<Company> all = cq.select(rootEntry);
		TypedQuery<Company> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

}
