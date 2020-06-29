package com.excilys.persistence;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.Mapper;
import com.excilys.model.Computer;
import com.excilys.sqlShenanigans.SqlConnector;
import com.excilys.sqlShenanigans.Xeptions;
import com.excilys.ui.Page;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerDAO.
 */
public class ComputerDAO {

	/** The table name. */
	static String tbName = "computer";
	
	/** The Constant SELECT_ALL. */
	private static final String SELECT_ALL = "select computer.id, computer.name, computer.company_id, introduced, discontinued, company.name from computer LEFT JOIN company ON computer.company_id=company.id ";
	
	/** The Constant SELECT_SOME. */
	private static final String SELECT_SOME = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name from computer LEFT JOIN company ON computer.company_id=company.id ORDER BY id LIMIT ? OFFSET ?";
	
	/** The Constant UPDATE_NAME. */
	private static final String UPDATE_NAME = "UPDATE computer SET name=? WHERE id=?";
	
	/** The Constant UPDATE_DATE. */
	private static final String UPDATE_DATE = "UPDATE computer SET introduced=? , discontinued=? WHERE id=?";
	
	/** The Constant DELETE. */
	private static final String DELETE = "DELETE FROM computer WHERE id =?";
	
	/** The Constant SELECT_WHERE. */
	private static final String SELECT_WHERE = "SELECT computer.id, computer.name, computer.company_id, introduced, discontinued, company.name from computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=? ";
	
	/** The Constant COUNT. */
	private static final String COUNT = "SELECT COUNT(*) from " + tbName;
	
	/** The Constant INSERT. */
	private static final String INSERT = "INSERT into computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	
	/** The logger. */
	public static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	/**
	 * Count db.
	 *
	 * @param tbName the tb name
	 * @return the int
	 */
	public int countDb(String tbName) {
		int count = -2;
		try {
			Connection con = SqlConnector.getInstance();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(COUNT);
			rs.next();
			count = rs.getInt(1);
			stmt.close();
			logger.debug("Connection to the database was terminated");
		} catch (SQLException e) {
			logger.error("Connection to the database could not be established", e);
			Xeptions.printSQLException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * View computer.
	 *
	 * @return the list
	 */
	public List<Computer> viewComputer() {

		Statement stmt = null;
		Computer computer = null;
		List<Computer> computers = new ArrayList<Computer>();

		try (Connection con = SqlConnector.getInstance()) {

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				computer = Mapper.prettyMap(rs);
				computers.add(computer);

			}
			stmt.close();
			logger.debug("Connection to the database was terminated");
		} catch (SQLException e) {
			logger.error("Connection to the database could not be established", e);
			Xeptions.printSQLException(e);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return computers;
	}

	/**
	 * View some computer.
	 *
	 * @param page the page
	 * @return the list
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<Computer> viewSomeComputers(Page page) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;

		Computer computer = null;
		List<Computer> computers = new ArrayList<Computer>();

		try (Connection con = SqlConnector.getInstance()) {
			pstmt = con.prepareStatement(SELECT_SOME);

			int limit = page.getAmount();
			int offset = (page.getPage() - 1) * page.getAmount();
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);

			ResultSet rs = pstmt.executeQuery();
			computer = new Computer();
			while (rs.next()) {
				computer = Mapper.prettyMap(rs);
				computers.add(computer);
			}
		} catch (SQLException e) {
			logger.error("Connection to the database could not be established", e);
			Xeptions.printSQLException(e);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return computers;
	}

	/**
	 * Update computer name.
	 *
	 * @param newName    the new name
	 * @param computerID the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateComputerName(String newName, int computerID)
			throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;

		try {

			Connection con = SqlConnector.getInstance();
			pstmt = con.prepareStatement(UPDATE_NAME);

			pstmt.setString(1, newName);
			pstmt.setInt(2, computerID);
			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
	}

	/**
	 * Update computer disc.
	 *
	 * @param intr       the intr
	 * @param disc       the disc
	 * @param computerID the computer ID
	 * @return the int
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int updateComputerDisc(Date intr, Date disc, int computerID)
			throws SQLException, ClassNotFoundException, IOException {
		int bool = 0;
		PreparedStatement pstmt = null;

		try (Connection con = SqlConnector.getInstance()) {

			pstmt = con.prepareStatement(UPDATE_DATE);
			if (disc.compareTo(intr) > 0) {
				pstmt.setDate(1, intr);
				pstmt.setDate(2, disc);
				pstmt.setInt(3, computerID);
				pstmt.executeUpdate();
				bool = 1;

			} else {
				logger.info(
						"Sorry there seems to be an incoherence with your date format input. Update failed to resolve");
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return bool;
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
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Computer insertComputer(Computer myComp)
			throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement pstmt = null;
		try (Connection con = SqlConnector.getInstance()) {


			pstmt = con.prepareStatement(INSERT);

			if (myComp.getDisco().compareTo(myComp.getIntro()) > 0) {
				pstmt.setString(1, myComp.getName());
				pstmt.setDate(2, Mapper.localToSql(myComp.getIntro()));
				pstmt.setDate(3, Mapper.localToSql(myComp.getDisco()));
				pstmt.setInt(4, myComp.getCompanyId());
				pstmt.executeUpdate();

			} else {
				logger.info("Sorry there seems to be an incoherence with your date format input. Creation was aborted");

			}
		} catch (SQLException e) {
			Xeptions.printSQLException(e);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return myComp;
	}
 
	/**
	 * Delete computer.
	 *
	 * @param computerID the computer ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteComputer(int computerID) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;

		try (Connection con = SqlConnector.getInstance()) {

			pstmt = con.prepareStatement(DELETE);
			// "DELETE FROM "+tbName+ "WHERE id =?");

			pstmt.setInt(1, computerID);
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
	}

	/**
	 * View comp details.
	 *
	 * @param computerID the computer ID
	 * @return the computer
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Computer viewCompDetails(int computerID) throws SQLException, ClassNotFoundException, IOException {

		PreparedStatement pstmt = null;
		Computer computer = new Computer();
		try (Connection con = SqlConnector.getInstance()) {

			pstmt = con.prepareStatement(SELECT_WHERE);
			// "SELECT id, name, introduced, discontinued, company_id FROM "+tbName +"WHERE
			// id=? ");

			pstmt.setInt(1, computerID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				computer = Mapper.prettyMap(rs);
				// System.out.println(computer.toString());
				logger.info(computer.toString());
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
				logger.debug("Connection to the database was terminated");
			}
		}
		return computer;
	}

}