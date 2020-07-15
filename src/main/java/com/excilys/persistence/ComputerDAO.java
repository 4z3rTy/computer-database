package com.excilys.persistence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.ComputerRowMapperBis;
import com.excilys.model.Computer;
import com.excilys.model.Page;

//
/**
 * The Class ComputerDAO.
 */

@Repository
public class ComputerDAO {

	/** The table name. */
	private static final String tbName = "computer";

	/** The Constant SELECT_ALL. */
	private static final String SELECT_ALL = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id ";

	/** The Constant SELECT_SOME. */
	private static final String SELECT_SOME = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY id LIMIT :limit OFFSET :offset";

	/** The Constant UPDATE_NAME. */
	private static final String UPDATE_NAME = "UPDATE computer SET name= :name WHERE id= :id";

	/** The Constant UPDATE_DATE. */
	private static final String UPDATE_DATE = "UPDATE computer SET introduced= :introduced , discontinued= :discontinued WHERE id= :id";

	/** The Constant UPDATE_ALL. */
	private static final String UPDATE_ALL = "UPDATE computer SET name= :name , introduced= :introduced , discontinued= :discontinued ,company_id= :company_id WHERE id= :id";

	/** The Constant DELETE. */
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id";

	/** The Constant SELECT_WHERE. */
	private static final String SELECT_WHERE = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.id= :id ";

	/** The Constant COUNT. */
	private static final String COUNT = "SELECT COUNT(*) FROM " + tbName;

	/** The Constant INSERT. */
	private static final String INSERT = "INSERT into computer(name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :company_id)";

	private static final String SEARCH_ID = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY id LIMIT :limit OFFSET :offset ";

	private static final String SEARCH_INTRO = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY introduced LIMIT :limit OFFSET :offset ";

	private static final String SEARCH_NAME = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name FROM computer LEFT JOIN company ON computer.company_id=company.id "
			+ "WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY computer.name LIMIT :limit OFFSET :offset ";

	private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM (SELECT computer.id FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE :search OR company.name LIKE :search ) AS S ";

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	DataSource ds;
	
	@Autowired
	public ComputerDAO(DataSource ds) {
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(ds);
	}

	/**
	 * Count db.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int countDb(String tbName) {
		int count = -1;
		count = namedJdbcTemplate.query(COUNT, ((ResultSet rs) -> {
			if (rs.next()) {
				return rs.getInt(1);
			}
			
			return -1;
		}));
		return count;
	}

	/**
	 * View computer.
	 *
	 * @return the list
	 */
	public List<Computer> viewComputer() {

		List<Computer> computers = new ArrayList<Computer>();
		computers = namedJdbcTemplate.query(SELECT_ALL, new ComputerRowMapperBis());

		return computers;
	}

	/**
	 * View some computer.
	 *
	 * @param page the page
	 * @return the list
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public List<Computer> viewSomeComputers(Page page) throws SQLException {

		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		
		List<Computer> computers = new ArrayList<>();
		SqlParameterSource sp= new MapSqlParameterSource().addValue("limit", limit).addValue("offset", offset);
		computers = namedJdbcTemplate.query(SELECT_SOME,sp, new ComputerRowMapperBis());
		
		return computers;
	}

	/**
	 * Update computer name.
	 *
	 * @param newName    the new name
	 * @param computerId the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void updateComputerName(String newName, int computerId) throws SQLException {

		SqlParameterSource sp= new MapSqlParameterSource().addValue("id", computerId).addValue("name", newName);
		namedJdbcTemplate.update(UPDATE_NAME, sp);
	}

	/**
	 * Update computer disc.
	 *
	 * @param intr       the intr
	 * @param disc       the disc
	 * @param computerId the computer ID
	 * @return the int
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public boolean updateComputerDisc(Date intr, Date disc, int computerId) throws SQLException {
		boolean res = false;
		SqlParameterSource sp= new MapSqlParameterSource().addValue("id", computerId).addValue("introduced", intr).addValue("discontinued", disc);
		namedJdbcTemplate.update(UPDATE_DATE, sp);
		
		return res;
	}

	public void updateComputer(Computer myComp) throws SQLException {

		SqlParameterSource sp= new MapSqlParameterSource().addValue("id", myComp.getId()).addValue("introduced", myComp.getIntroduced()).addValue("discontinued", myComp.
				getDiscontinued()).addValue("name", myComp.getName()).addValue("company_id",myComp.getCompanyId());
		namedJdbcTemplate.update(UPDATE_ALL, sp);
	}

	/**
	 * Insert computer.
	 *
	 * @param computerName the computer name
	 * @param companyID    the company ID
	 * @param intro        the intro
	 * @param disco        the disco
	 * @return the computer
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public Computer insertComputer(Computer myComp) throws SQLException {
		SqlParameterSource sp=null;
		if (myComp.getCompanyId() != 0) {
			sp= new MapSqlParameterSource().addValue("id", myComp.getId()).addValue("introduced", myComp.getIntroduced()).addValue("discontinued", myComp.
					getDiscontinued()).addValue("name", myComp.getName()).addValue("company_id",myComp.getCompanyId());
		} else {
			sp= new MapSqlParameterSource().addValue("id", myComp.getId()).addValue("introduced", myComp.getIntroduced()).addValue("discontinued", myComp.
					getDiscontinued()).addValue("name", myComp.getName()).addValue("company_id",null);
				
		}

		namedJdbcTemplate.update(INSERT, sp);
		return myComp;
	}

	/**
	 * Delete computer.
	 *
	 * @param computerId the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public void deleteComputer(int computerId) throws SQLException {

		SqlParameterSource sp= new MapSqlParameterSource().addValue("id", computerId);
		namedJdbcTemplate.update(DELETE_COMPUTER,sp);		
	}

	/**
	 * View comp details.
	 *
	 * @param computerId the computer ID
	 * @return the computer
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public Computer viewCompDetails(int computerId) throws SQLException {

		Computer computer = new Computer.ComputerBuilder().build();
		SqlParameterSource sp= new MapSqlParameterSource().addValue("id", computerId);
		computer=namedJdbcTemplate.queryForObject(SELECT_WHERE,sp,new ComputerRowMapperBis());
		
		return computer;
	}

	public List<Computer> getSearchId(String search, Page page) throws SQLException {

		
		List<Computer> computers = new ArrayList<Computer>();
		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		SqlParameterSource sp= new MapSqlParameterSource().addValue("search", '%' + search + '%').addValue("limit", limit).addValue("offset", offset);
		computers=namedJdbcTemplate.query(SEARCH_ID,sp,new ComputerRowMapperBis());
		
		return computers;
	}

	public List<Computer> getSearchIntro(String search, Page page) throws SQLException {

		List<Computer> computers = new ArrayList<Computer>();
		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		SqlParameterSource sp= new MapSqlParameterSource().addValue("search", '%' + search + '%').addValue("limit", limit).addValue("offset", offset);
		computers=namedJdbcTemplate.query(SEARCH_INTRO,sp,new ComputerRowMapperBis());
		
		return computers;
	}

	public List<Computer> getSearchName(String search, Page page) throws SQLException {

		List<Computer> computers = new ArrayList<Computer>();
		int limit = page.getAmount();
		int offset = (page.getPage() - 1) * page.getAmount();
		SqlParameterSource sp= new MapSqlParameterSource().addValue("search", '%' + search + '%').addValue("limit", limit).addValue("offset", offset);
		computers=namedJdbcTemplate.query(SEARCH_NAME,sp,new ComputerRowMapperBis());

		return computers;
	}

	public int searchCount(String search) {
		int count = -1;
		SqlParameterSource sp= new MapSqlParameterSource().addValue("search", '%' + search + '%');
		count= namedJdbcTemplate.query(SEARCH_COUNT,sp,(ResultSet rs) -> {
			if (rs.next()) {
				return rs.getInt(1);
			}
			
			return -1;
		});
		return count;
	}

}