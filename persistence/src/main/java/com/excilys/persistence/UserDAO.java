package com.excilys.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.User;

@Repository
public class UserDAO {

	/** The emf. */
	@Autowired
	private EntityManagerFactory emf;

	/** The em. */
	private EntityManager em;

	/** The cb. */
	private CriteriaBuilder cb;

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	public User getUserByUsername(String username) {
		em = emf.createEntityManager();
		User user = em.find(User.class, username);
		return user;
	}
}
