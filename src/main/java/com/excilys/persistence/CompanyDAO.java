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
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.excilys.model.Company;
import com.excilys.model.Computer;

// 
/**
 * The Class CompanyDAO.
 */
@Repository
public class CompanyDAO {

	private static final String tbName = "company";

	private static final String SELECT_ALL = "SELECT id, name FROM " + tbName;

	private static final String COUNT = "SELECT COUNT(*) FROM " + tbName;

	private static final String DELETE_COMPANY = "DELETE FROM " + tbName + " WHERE id = :id";

	private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id= :id ";

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	@Autowired
	DataSource ds;

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	private CriteriaBuilder cb;

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private EntityManager em;

	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 * @throws SQLException the SQL exception
	 */
	@Transactional
	public void deleteCompany(int companyId) throws SQLException {

		/*
		 * SqlParameterSource sp = new MapSqlParameterSource().addValue("id",
		 * companyId); namedJdbcTemplate.update(DELETE_COMPUTERS, sp);
		 * namedJdbcTemplate.update(DELETE_COMPANY, sp);
		 */

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> root = criteriaDelete.from(Computer.class);
		criteriaDelete.where(cb.equal(root.get("company_id"), companyId));
		em.getTransaction().begin();
		int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
		System.out.println("entities deleted: " + rowsDeleted);

		CriteriaDelete<Company> criteriaDelete2 = cb.createCriteriaDelete(Company.class);
		Root<Company> root2 = criteriaDelete2.from(Company.class);
		criteriaDelete2.where(cb.equal(root2.get("id"), companyId));
		em.getTransaction().begin();
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
		/*
		 * List<Company> companies = new ArrayList<Company>();
		 * logger.debug("Company List initialized"); companies =
		 * namedJdbcTemplate.query(SELECT_ALL, new CompanyRowMapper()); return
		 * companies;
		 */
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> cq = cb.createQuery(Company.class);
		Root<Company> rootEntry = cq.from(Company.class);
		CriteriaQuery<Company> all = cq.select(rootEntry);
		TypedQuery<Company> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

}
