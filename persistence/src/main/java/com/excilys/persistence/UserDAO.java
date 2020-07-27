package com.excilys.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	private CriteriaBuilder cb;

	public User getUserByUsername(String username) {
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		Predicate predicate = cb.equal(root.get("username"), username);
		cq.select(root).where(predicate);
		TypedQuery<User> userQuery = em.createQuery(cq);
		User user = userQuery.getSingleResult();

		return user;
	}
}
