package com.excilys.persistence;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Company;
import com.excilys.model.Computer;

// 
/**
 * The Class CompanyDAO.
 */
@Repository
public class CompanyDAO {

	@Autowired
	private EntityManagerFactory emf;

	private EntityManager em;
	
	private CriteriaBuilder cb;
	
	/** The logger. */
	private static Logger logger=LoggerFactory.getLogger(CompanyDAO.class);

	
	private void initialize() {
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
	}
	
	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 */
	@Transactional
	public void deleteCompany(int companyId) {

		initialize();
		CriteriaDelete<Computer> criteriaDelete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> root = criteriaDelete.from(Computer.class);
		criteriaDelete.where(cb.equal(root.get("company").get("id"), companyId));
		
		em.getTransaction().begin();
		int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
		logger.debug("Total computers deleted: " + rowsDeleted);
		
		CriteriaDelete<Company> criteriaDelete2 = cb.createCriteriaDelete(Company.class);
		Root<Company> root2 = criteriaDelete2.from(Company.class);
		criteriaDelete2.where(cb.equal(root2.get("id"), companyId));
		int rowsDeleted2 = em.createQuery(criteriaDelete2).executeUpdate();
		logger.debug("Total companiesdeleted: " + rowsDeleted2);
		em.getTransaction().commit();
	}


	/**
	 * Count db.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int countDb(String tbName) {

		initialize();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Company.class)));
		return em.createQuery(cq).getSingleResult().intValue();

	}

	/**
	 * View company.
	 *
	 * @return The list of all companies queried
	 */

	public List<Company> viewCompany(){

		initialize();
		CriteriaQuery<Company> cq = cb.createQuery(Company.class);
		Root<Company> rootEntry = cq.from(Company.class);
		CriteriaQuery<Company> all = cq.select(rootEntry);
		TypedQuery<Company> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}
	
	public Company getCompany(int companyId){
		em = emf.createEntityManager();
		Company company = em.find(Company.class, companyId);
		return company;
	}

}
