package com.excilys.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.ComputerRowMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;

//
/**
 * The Class ComputerDAO.
 */

@Repository
public class ComputerDAO {

	private static final String tbName = "computer";

	private static final String SELECT_ALL = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id ";

	private static final String SELECT_SOME = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY id LIMIT :limit OFFSET :offset";

	private static final String UPDATE_NAME = "UPDATE computer SET name= :name WHERE id= :id";

	private static final String UPDATE_DATE = "UPDATE computer SET introduced= :introduced , discontinued= :discontinued WHERE id= :id";

	private static final String UPDATE_ALL = "UPDATE computer SET name= :name , introduced= :introduced , discontinued= :discontinued ,company_id= :company_id WHERE id= :id";

	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id";

	private static final String SELECT_WHERE = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id= :id ";

	private static final String COUNT = "SELECT COUNT(*) FROM " + tbName;

	private static final String INSERT = "INSERT into computer(name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :company_id)";

	private static final String SEARCH_ID = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM "
			+ "computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY id LIMIT :limit OFFSET :offset ";

	private static final String SEARCH_INTRO = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM "
			+ "computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY introduced LIMIT :limit OFFSET :offset ";

	private static final String SEARCH_NAME = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY computer.name LIMIT :limit OFFSET :offset ";

	private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM (SELECT computer.id FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE :search OR company.name LIKE :search ) AS S ";

	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	@Autowired
	DataSource ds;

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	private CriteriaBuilder cb;

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private EntityManager em;

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
	public List<Computer> viewSomeComputers(Page page) throws SQLException {

		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		/*
		 * List<Computer> computers = new ArrayList<>(); SqlParameterSource sp = new
		 * MapSqlParameterSource().addValue("limit", limit).addValue("offset", offset);
		 * computers = namedJdbcTemplate.query(SELECT_SOME, sp, new
		 * ComputerRowMapper()); return computers;
		 */
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> rootEntry = cq.from(Computer.class);
		CriteriaQuery<Computer> all = cq.select(rootEntry);
		TypedQuery<Computer> allQuery = em.createQuery(all).setFirstResult(offset).setMaxResults(limit);
		return allQuery.getResultList();
	}

	/**
	 * Update computer name.
	 *
	 * @param newName    the new name
	 * @param computerId the computer ID
	 * @throws SQLException the SQL exception
	 */
	public void updateComputerName(String newName, int computerId) throws SQLException {

		/*
		 * SqlParameterSource sp = new MapSqlParameterSource().addValue("id",
		 * computerId).addValue("name", newName); namedJdbcTemplate.update(UPDATE_NAME,
		 * sp);
		 */

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
	public boolean updateComputerDisc(Date intr, Date disc, int computerId) throws SQLException {

		boolean res = true;
		/*
		 * SqlParameterSource sp = new MapSqlParameterSource().addValue("id",
		 * computerId).addValue("introduced", intr) .addValue("discontinued", disc);
		 * namedJdbcTemplate.update(UPDATE_DATE, sp);
		 */
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
	public void updateComputer(Computer myComp) throws SQLException {

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
	public Computer insertComputer(Computer myComp) throws SQLException {

		em.getTransaction().begin();
		if (myComp.getCompanyId() != 0) {
			em.persist(myComp);
		} else {
			// TODO Company.id==null
		}
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
	public void deleteComputer(int computerId) throws SQLException {

		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> delete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> e = delete.from(Computer.class);
		delete.where(cb.equal(e.get("id"), computerId));
		em.getTransaction().begin();
		int rowsDeleted = em.createQuery(delete).executeUpdate();
		System.out.println("entities deleted: " + rowsDeleted);
		em.getTransaction().commit();

	}

	/**
	 * View comp details.
	 *
	 * @param computerId the computer ID
	 * @return the computer
	 * @throws SQLException the SQL exception
	 */
	public Computer viewCompDetails(int computerId) throws SQLException {

		/*
		 * Computer computer = new Computer.ComputerBuilder().build();
		 * SqlParameterSource sp = new MapSqlParameterSource().addValue("id",
		 * computerId); computer = namedJdbcTemplate.queryForObject(SELECT_WHERE, sp,
		 * new ComputerRowMapper());
		 * 
		 * return computer;
		 */
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
	public List<Computer> getSearchId(String search, Page page) throws SQLException {

		List<Computer> computers = new ArrayList<Computer>();
		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		SqlParameterSource sp = new MapSqlParameterSource().addValue("search", '%' + search + '%')
				.addValue("limit", limit).addValue("offset", offset);
		computers = namedJdbcTemplate.query(SEARCH_ID, sp, new ComputerRowMapper());

		return computers;
	}

	/**
	 * Gets the search intro.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search intro
	 * @throws SQLException the SQL exception
	 */
	public List<Computer> getSearchIntro(String search, Page page) throws SQLException {

		List<Computer> computers = new ArrayList<Computer>();
		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		SqlParameterSource sp = new MapSqlParameterSource().addValue("search", '%' + search + '%')
				.addValue("limit", limit).addValue("offset", offset);
		computers = namedJdbcTemplate.query(SEARCH_INTRO, sp, new ComputerRowMapper());

		return computers;
	}

	/**
	 * Gets the search name.
	 *
	 * @param search the search
	 * @param page   the page
	 * @return the search name
	 * @throws SQLException the SQL exception
	 */
	public List<Computer> getSearchName(String search, Page page) throws SQLException {

		List<Computer> computers = new ArrayList<Computer>();
		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		SqlParameterSource sp = new MapSqlParameterSource().addValue("search", '%' + search + '%')
				.addValue("limit", limit).addValue("offset", offset);
		computers = namedJdbcTemplate.query(SEARCH_NAME, sp, new ComputerRowMapper());

		return computers;
	}

	/**
	 * Search count.
	 *
	 * @param search the search
	 * @return the int
	 */
	public int searchCount(String search) {
		int count = -1;
		SqlParameterSource sp = new MapSqlParameterSource().addValue("search", '%' + search + '%');
		count = namedJdbcTemplate.query(SEARCH_COUNT, sp, (ResultSet rs) -> {
			if (rs.next()) {
				return rs.getInt(1);
			}

			return -1;
		});
		return count;
	}

}